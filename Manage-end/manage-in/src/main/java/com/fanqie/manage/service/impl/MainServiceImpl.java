package com.fanqie.manage.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanqie.commonutils.utils.R;
import com.fanqie.commonutils.utils.UUIDStringUtils;
import com.fanqie.manage.entity.*;
import com.fanqie.manage.listener.DemoDataListener;
import com.fanqie.manage.mapper.*;
import com.fanqie.manage.param.acSure;
import com.fanqie.manage.param.excelDemo;
import com.fanqie.manage.param.userDoInfo;
import com.fanqie.manage.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fq
 * @since 2023-03-30
 */
@Service
@Slf4j
public class MainServiceImpl extends ServiceImpl<MainMapper, Main> implements MainService {

    //todo:将 mapper 换成 service
    @Autowired
    private MainMapper mainMapper;
    @Autowired
    private TeachMapper teachMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private TeachClassMapper teachClassMapper;
    @Autowired
    private MainSimpleViewService simpleViewService;
    @Autowired
    private MainAllViewServiceImpl mainAllViewViewService;
    @Autowired
    private AdditionalCoefficientsService additionalCoefficientsService;
    @Autowired
    private CoefficientExperimentService coefficientExperimentService;  //理论部分
    @Autowired
    private CoefficientPracticeService coefficientPracticeService;  //全实验课（这里算成上机）
    @Autowired
    private CoefficientTheoryService coefficientTheoryService;  //有理论课的实验部分（这里算实验）
    @Autowired
    private MainOutService mainOutService;


    /**
     * 这个主要是用来录入工作量统计的数据的，
     * 处理流程包括。
     * 解析每条记录
     * 将记录按照属性分别存储到不同的数据库。
     * 以完成对数据的处理，方便统计。
     *
     * @param file
     * @return
     */
    @Override
    public R uploadExcel(MultipartFile file) {
        //TODO:优化，理论上应该放service来处理这个流程的，现在放到监听器里面处理了。
        try {
            EasyExcel.read(file.getInputStream(), excelDemo.class, new DemoDataListener(userMapper, teachMapper, teachClassMapper, classMapper, mainMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.ok();
    }

    /**
     * 获取用户的工作量统计结果。简单的 动态的 未确认的
     * 包含：时间、教师名称、课程名称（全部课程）、班级（全部班）、统计结果（工作量）
     * 处理过程，查询main_all视图，获取计算工作量所需的参数，
     * 然后将结果复制的返回结果的封装类
     *
     * @return
     */
    @Override
    public R getUserDoSimple() {
        List<MainAllView> allViews = mainAllViewViewService.queryAll();
        //查询出视图。遍历视图，统计出计算结果。

        System.out.println("shitu:" + allViews.get(5).getTerm());
        return R.ok();
    }

    /**
     * 根据用户的id 查询用户的 工作量详细信息
     *
     * @param userId
     * @return
     */
    @Override
    public R getUserDoInfo(String userId) {
        //todo：这个方法太长了，应该封装起来的
        //根据用户id 查询用户的课程记录
        List<MainAllView> mainAllViews = mainAllViewViewService.queryByUserId(userId);
        if (mainAllViews.size() == 0) {
            R.error().message("查询教师课程信息失败！！,该教师没有授课记录！！！");
        }
        System.out.println("该教师的课程数量为：" + mainAllViews.size());
        //System.out.println("该教师名称：" + mainAllViews.get(0).getUserName());
        //创建队列 用来返回的封装类
        List<userDoInfo> doInfoList = new ArrayList<>();
        for (MainAllView view : mainAllViews) {
            //如果已经确认过了，则直接去查询结果表
            if (view.getIsSure()!=null && view.getIsSure() == 1) {
                //todo:去mainOut表查询数据返回
                userDoInfo u1 = mainOutService.seleceByUserId(view.getUserId());
                doInfoList.add(u1);
                continue;
            }
            double classCoefficient = 0;
            if ((view.getClassNumber() / view.getClassRow()) > 45) {
                classCoefficient = (view.getClassNumber() / view.getClassRow()) * 0.01;
            }
            //将部分信息直接复制过来
            userDoInfo doInfo = new userDoInfo();
            doInfo.setIsSure(0);//设置未确认
            //直接赋值
            doInfo.setUserId(view.getUserId());
            doInfo.setUserName(view.getUserName());
            doInfo.setClassName(view.getClassName());
            doInfo.setUniqueNumber(view.getUniqueNumber());
            doInfo.setClassNumber(view.getClassNumber());
            doInfo.setTeachName(view.getTeachName());
            doInfo.setPracticalHours(Integer.parseInt(view.getPracticalHours()));
            doInfo.setTheoreticalHours(Integer.parseInt(view.getTheoreticalHours()));
            //System.out.println("课时：" + doInfo.getPracticalHours() + "#" + doInfo.getTheoreticalHours());
            //System.out.println("课@时：" + view.getPracticalHours() + "#" + view.getTheoreticalHours());
            //查看是否登记了特殊情况
            AdditionalCoefficients ac = additionalCoefficientsService.getByAdditionalId(view.getAdditional());
            if (ac != null) {
                doInfo.setIsFirst(ac.getIsFirst());
                doInfo.setIsDoubleLanguage(ac.getIsDoubleLanguage());
                doInfo.setIsWeekend(ac.getIsWeekend());
            } else {//默认就没有系数
                System.out.println("没查到特殊情况");
                doInfo.setIsFirst(0);//这个是加的系数，所以要设置为零
                doInfo.setIsDoubleLanguage(1);//这些是乘的
                doInfo.setIsWeekend(1);
            }
            //计算工作量
            double dou = 0;//保存工作量的过程量
            //  TheoreticalHours 理论学时  ，为零，说明全是实验，当作上机
            if (view.getTheoreticalHours() == null || view.getTheoreticalHours().equals("") ||
                    view.getTheoreticalHours().equals("0")) {
                //全实践课，查看根据班级情况，返回实践课内容
                CoefficientPractice cp;
                CoefficientPractice cp1;//因为重复的系数不一样，创建了两个变量，虽然一个也可以。
                if (view.getClassRow() % 2 == 0) {
                    cp = coefficientPracticeService.getBYClassNumber(2);//算两个班一起上，不存在重复
                    doInfo.setCoefficientS(String.valueOf(cp.getCoefficient()));//设置上机系数
                    dou += doInfo.getPracticalHours() * (cp.getCoefficient() + doInfo.getIsFirst() + classCoefficient);//2个班一起上的工作量
                    if (view.getClassRow() > 3) {
                        //说明是4班以上，并且是双数
                        cp1 = coefficientPracticeService.getBYClassNumber(4);//四个班以上，重复的系数
                        doInfo.setCoefficientS(String.valueOf(cp.getCoefficient()) + "|" + cp1.getCoefficient());//设置上机系数
                        dou += doInfo.getPracticalHours() * (cp1.getCoefficient() + doInfo.getIsFirst() + classCoefficient) * (view.getClassRow() / 2 - 1);//2个班一起上的工作量
                    }
                    //实验时长*（系数+班级人数系数+是否新课
                } else {
                    cp = coefficientPracticeService.getBYClassNumber(1);
                    dou += doInfo.getPracticalHours() * (cp.getCoefficient() + classCoefficient + doInfo.getIsFirst());//2个班一起上的工作量
                    if (view.getClassRow() > 2) {
                        //说明是2个班以上，是单数
                        cp1 = coefficientPracticeService.getBYClassNumber(3);
                        doInfo.setCoefficientS(String.valueOf(cp.getCoefficient()) + "|" + cp1.getCoefficient());//设置上机系数
                        dou += doInfo.getPracticalHours() * (cp1.getCoefficient() + doInfo.getIsFirst() + classCoefficient) * (view.getClassRow() - 1);//单个班上课
                    }
                }
            } else {
                //有两种情况，一种上机学时为零，全是理论；一种一半一半， 实验系数查询不同的表，理论一样
                // 根据班级数量，查看理论课的系数
                CoefficientExperiment ce;//理论表系数
                if (view.getClassRow() <= 4) {
                    //四个班以下
                    System.out.println("班级数量；" + view.getClassRow());
                    ce = coefficientExperimentService.getByClassNumber(view.getClassRow());
                } else { //四个班以上，按照四个班算
                    ce = coefficientExperimentService.getByClassNumber(4);
                }
                dou += (doInfo.getTheoreticalHours() * (ce.getCoefficient() + doInfo.getIsFirst() + classCoefficient));//理论课部分的工作量
                dou *= doInfo.getIsDoubleLanguage();//如果是双语，乘以1.5

                doInfo.setCoefficientL(String.valueOf(ce.getCoefficient()));//设置理论系数
                CoefficientTheory ct = coefficientTheoryService.getByClassNumber(1);//有理论的，实验系数
                System.out.println("ct:" + ct.getCoefficient());
                doInfo.setCoefficientS(String.valueOf(ct.getCoefficient()));
                dou += doInfo.getPracticalHours() * (ct.getCoefficient() + doInfo.getIsFirst() + classCoefficient);
                CoefficientTheory ct1;
                if (view.getClassRow() > 1) {//班级数大于一的话，
                    ct1 = coefficientTheoryService.getByClassNumber(2);
                    doInfo.setCoefficientS(String.valueOf(ct.getCoefficient()) + "|" + ct1.getCoefficient());
                    dou += doInfo.getPracticalHours() * (ct1.getCoefficient() + doInfo.getIsFirst() + classCoefficient) * (view.getClassRow() - 1);
                }
            }
            dou *= doInfo.getIsWeekend();//是否周末，实验部分没有双语的
            doInfo.setAdd(dou);
            //tODO: 四舍五入。现在小数位数太多
            doInfoList.add(doInfo);
        }
        return R.ok().data("userDoInfo", doInfoList);
    }

    /**
     * 实现用户自己添加课程 特殊 情况。
     * 处理过程：
     * 根据唯一编码：查询Main表。看看是否有过特殊情况记录
     *              有，查询该记录，并修改。没有，就添加记录
     *
     * @param userDoInfo
     * @return
     */
    @Override
    public R addAdditional(userDoInfo userDoInfo) {
        //先查询是否有记录，在main表
        Main main = mainMapper.selectByUniqueNumber(userDoInfo.getUniqueNumber());
        if(main==null){
            return R.error().message("查询不到课程信息！");
        }
        if(main.getAdditional()==null){
            //添加记录，
            String additionalID = UUIDStringUtils.randomUUID();
            AdditionalCoefficients ac=new AdditionalCoefficients();
            ac.setAdditionalId(additionalID);
            //TODO：这个应该查表的，这些比例
            //现在是写死的，（在前端）。查表的话，要再写一个接收对象，参数是true 或 false的
            ac.setIsFirst(userDoInfo.getIsFirst());
            ac.setIsDoubleLanguage(userDoInfo.getIsDoubleLanguage());
            ac.setIsWeekend(userDoInfo.getIsWeekend());
            ac.setIsSure(0);
            int row = additionalCoefficientsService.addAdditional(ac);
            if(row==1){
                main.setAdditional(additionalID);
                int i = mainMapper.updateById(main);
                if(i==1){
                    return R.ok().message("添加用户特殊情况成功！");
                }
            }
            return R.error().message("添加用户特殊情况失败！！");
        }
        //存在特殊情况！
        AdditionalCoefficients acs = additionalCoefficientsService.getByAdditionalId(main.getAdditional());
        if(acs==null){
            return R.error().message("用户的特殊情况申请异常");//查询不到记录，
        }
        acs.setIsSure(userDoInfo.getIsSure());
        acs.setIsWeekend(userDoInfo.getIsWeekend());
        acs.setIsDoubleLanguage(userDoInfo.getIsDoubleLanguage());
        acs.setIsSure(0);
        boolean b=additionalCoefficientsService.updateById(acs);
        if(b){
            return R.ok().message("成功修改用户特殊系数！");
        }
        //先在AC表添加记录，
        return R.error().message("修改用户特殊情况失败！！！");
    }

    /**
     * 教师确认课程信息
     * 处理流程：
     *      在main表上面确认信息：is-sure=1
     *    在mainOut表上面添加一条记录。
     * @param userDoInfo
     * @return
     */
    @Override
    public R UserSureDo(userDoInfo userDoInfo) {
        MainOut mainOut=new MainOut();
        //先确认是否存在特殊情况：
        //TODO:,应该创建新方法的，偷懒了，通过用户id查询比较符合逻辑
        Main main= mainMapper.selectByUniqueNumber(userDoInfo.getUniqueNumber());
        if(main.getAdditional()!=null){
            //通过additionalId查询是否存在未确认的特殊情况申请，存在的话，不能通过确认。
            int i = additionalCoefficientsService.sureUnSureByAdditionalId(main.getAdditional());
            if(i==1){
                //说明存在未确认的特殊情况，不能确认课程信息
                return R.error().message("存在未确认的特殊记录！请等待相关人员确认信息后再确认！");
            }
            //存在，且确认了，
            //todo：确认教务处同意，而不是不同意。查询数据库实现。
            mainOut.setAdditional(main.getAdditional());
        }
        //查询教职工信息
        User u1 = userService.getUserByUserId(userDoInfo.getUserId());
        if(u1==null){
            return R.error().message("用户的信息查询不到！");
        }
        mainOut.setFaculty(u1.getFaculty());
        mainOut.setNumber(u1.getNumber());
        mainOut.setTerm(main.getTerm());
        mainOut.setUserId(userDoInfo.getUserId());
        mainOut.setUserName(userDoInfo.getUserName());
        mainOut.setClassName(userDoInfo.getClassName());
        mainOut.setClassNumber(String.valueOf(userDoInfo.getClassNumber()));
        mainOut.setTeachName(userDoInfo.getTeachName());
        mainOut.setPracticalHours(userDoInfo.getPracticalHours());
        mainOut.setTheoreticalHours(userDoInfo.getTheoreticalHours());
        mainOut.setTeachType("auto");
        mainOut.setIsSure("0");
        mainOut.setCaseload(String.valueOf(userDoInfo.getAdd()));
        //TODO:必要的，完善功能，保存计算过程，
        //初步流程，新表，理论学时、系数、
        //理论学时*（系数+是否新课+班级人数是否超标）*是否双语*是否周末  +  实验学时*（系数+是否新课+班级人数是否超标）*是否周末
        int i = mainOutService.Insert(mainOut);
        if(i==1){
            return R.ok().message("成功确认！");
        }
        return R.error().message("确认课程工作量失败");
    }

    /**
     * 用来给教务处人员确认教师特殊情况申请的。
     *
     * @return 特殊情况申请列表
     */
    @Override
    public R getAdditionalSure() {
        //查询ac表，所有未确认的记录，并联合teach表和teachClass表查询课程名称和班级信息，返回结果。
        List<acSure> ac= additionalCoefficientsService.getAdditionalSure();
        if(ac==null){
            return R.error().message("不存在未确认的信息！！");
        }
        return R.ok().message("成功查询").data("AdditionalSure",ac);
    }

    //确认用户特殊情况的接口
    @Override
    public R AdditionalSure(acSure ac) {
        return null;
    }

    //否认用户特殊情况的接口
    @Override
    public R AdditionalUnSure(acSure ac) {
        return null;
    }
}
