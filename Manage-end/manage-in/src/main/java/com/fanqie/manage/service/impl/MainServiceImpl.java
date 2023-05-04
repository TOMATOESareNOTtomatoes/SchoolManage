package com.fanqie.manage.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanqie.commonutils.param.UserCheckParam;
import com.fanqie.commonutils.utils.R;
import com.fanqie.commonutils.utils.UUIDStringUtils;
import com.fanqie.manage.entity.*;
import com.fanqie.manage.listener.DemoDataListener;
import com.fanqie.manage.mapper.*;
import com.fanqie.manage.param.acSure;
import com.fanqie.manage.param.excelDemo;
import com.fanqie.manage.param.soleAndUser;
import com.fanqie.manage.param.userDoInfo;
import com.fanqie.manage.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private CalculationProcessService calculationProcessService;
    @Autowired
    private AdditionalMainService additionalMainService;


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
        return null;
//        List<MainAllView> allViews = mainAllViewViewService.queryAll();
//        //查询出视图。遍历视图，统计出计算结果。
//        for (MainAllView view : allViews) {
//            double classCoefficient = 0;//班级人数多
//            if ((view.getClassNumber() / view.getClassRow()) > 45) {
//                classCoefficient = (view.getClassNumber() / view.getClassRow()) * 0.01;
//            }
//
//            //TODO：特殊情况
//
//
//            double dou = 0;//保存工作量的过程量
//            int iPracticalHours = 0;
//            if (view.getPracticalHours() != null) {
//                iPracticalHours = Integer.parseInt(view.getPracticalHours());//实验学时
//            }
//            //  TheoreticalHours 理论学时  ，为零，说明全是实验，当作上机
//            if (view.getTheoreticalHours() == null || view.getTheoreticalHours().equals("") ||
//                    view.getTheoreticalHours().equals("0")) {
//                //全实践课，查看根据班级情况，返回实践课内容
//                CoefficientPractice cp;
//                CoefficientPractice cp1;//因为重复的系数不一样，创建了两个变量，虽然一个也可以。
//
//                if (view.getClassRow() % 2 == 0) {
//                    cp = coefficientPracticeService.getBYClassNumber(2);//算两个班一起上，不存在重复
//                    // doInfo.setCoefficientS(String.valueOf(cp.getCoefficient()));//设置上机系数
//                    dou += iPracticalHours * (cp.getCoefficient() + ac.getIsFirst() + classCoefficient);//2个班一起上的工作量
//                    if (view.getClassRow() > 3) {
//                        //说明是4班以上，并且是双数
//                        cp1 = coefficientPracticeService.getBYClassNumber(4);//四个班以上，重复的系数
//                        // doInfo.setCoefficientS(String.valueOf(cp.getCoefficient()) + "|" + cp1.getCoefficient());//设置上机系数
//                        dou += iPracticalHours * (cp1.getCoefficient() + ac.getIsFirst() + classCoefficient) * (view.getClassRow() / 2 - 1);//2个班一起上的工作量
//                    }
//                    //实验时长*（系数+班级人数系数+是否新课
//                } else {
//                    //
//                    cp = coefficientPracticeService.getBYClassNumber(1);
//                    dou += iPracticalHours * (cp.getCoefficient() + classCoefficient + ac.getIsFirst());//2个班一起上的工作量
//                    if (view.getClassRow() > 2) {
//                        //说明是2个班以上，是单数
//                        cp1 = coefficientPracticeService.getBYClassNumber(3);
//                        //doInfo.setCoefficientS(String.valueOf(cp.getCoefficient()) + "|" + cp1.getCoefficient());//设置上机系数
//                        dou += iPracticalHours * (cp1.getCoefficient() + ac.getIsFirst() + classCoefficient) * (view.getClassRow() - 1);//单个班上课
//                    }
//                }
//
//            } else {
//                //有两种情况，一种上机学时为零，全是理论；一种一半一半， 实验系数查询不同的表，理论一样
//                // 根据班级数量，查看理论课的系数
//                CoefficientExperiment ce;//理论表系数
//                int iTheoreticalHours = 0;
//                if (view.getTheoreticalHours() != null) {
//                    iTheoreticalHours = Integer.parseInt(view.getTheoreticalHours());
//                }
//                if (view.getClassRow() <= 4) {
//                    //四个班以下
//                    //System.out.println("班级数量；" + view.getClassRow());
//                    ce = coefficientExperimentService.getByClassNumber(view.getClassRow());
//                } else { //四个班以上，按照四个班算
//                    ce = coefficientExperimentService.getByClassNumber(4);
//                }
//                dou += (iTheoreticalHours * (ce.getCoefficient() + ac.getIsFirst() + classCoefficient));//理论课部分的工作量
//                dou *= ac.getIsDoubleLanguage();//如果是双语，乘以1.5,理论才有
//
//                //doInfo.setCoefficientL(String.valueOf(ce.getCoefficient()));//设置理论系数
//                CoefficientTheory ct = coefficientTheoryService.getByClassNumber(1);//有理论的，实验系数
//                //System.out.println("ct:" + ct.getCoefficient());
//                //doInfo.setCoefficientS(String.valueOf(ct.getCoefficient()));
//                dou += iPracticalHours * (ct.getCoefficient() + ac.getIsFirst() + classCoefficient);
//                CoefficientTheory ct1;
//                if (view.getClassRow() > 1) {//班级数大于一的话，
//                    ct1 = coefficientTheoryService.getByClassNumber(2);
//                    //doInfo.setCoefficientS(String.valueOf(ct.getCoefficient()) + "|" + ct1.getCoefficient());
//                    dou += iPracticalHours * (ct1.getCoefficient() + ac.getIsFirst() + classCoefficient) * (view.getClassRow() - 1);
//                }
//            }
//            dou *= ac.getIsWeekend();//是否周末，实验部分没有双语的
//            view.setOutcome(dou);
//        }
//        return R.ok().data("allUserDoNuSure", allViews);

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
        //System.out.println("该教师的课程数量为：" + mainAllViews.size());
        //System.out.println("该教师名称：" + mainAllViews.get(0).getUserName());
        //创建队列 用来返回的封装类
        List<userDoInfo> doInfoList = new ArrayList<>();
        for (MainAllView view : mainAllViews) {
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
            //todo：可以删除了，重新录入数据的话。
            if(view.getPracticalHours()==null){
                view.setPracticalHours("0");
            }
            if(view.getTheoreticalHours()==null){
                view.setTheoreticalHours("0");
            }
            doInfo.setPracticalHours(Integer.parseInt(view.getPracticalHours()));
            doInfo.setTheoreticalHours(Integer.parseInt(view.getTheoreticalHours()));
            //---------------------------------------------
            //查看 特殊情况是否有效
            AdditionalMain am=null;
            if(view.getAdditionalId()!=null){
                // 查询有 有效的特殊情况。
                am= additionalMainService.getByAdditionalId(view.getAdditionalId());
            }
            if (am != null) {
                //通过唯一 id 查询 返回特殊情况的系数
                AdditionalCoefficients additionalCoefficients = additionalCoefficientsService.selectByAdditionalId(am.getAdditionalCoefficientsId());//查询系数
                doInfo.setIsFirst(additionalCoefficients.getIsFirst());
                doInfo.setIsDoubleLanguage(additionalCoefficients.getIsDoubleLanguage());
                doInfo.setIsWeekend(additionalCoefficients.getIsWeekend());
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
                        doInfo.setCoefficientS(cp.getCoefficient() + "|" + cp1.getCoefficient());//设置上机系数
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
                    doInfo.setCoefficientS(ct.getCoefficient() + "|" + ct1.getCoefficient());
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
     * 有，查询该记录，并修改。没有，就添加记录
     *
     * @param userDoInfo
     * @return
     */
    @Override
    public R addAdditional(userDoInfo userDoInfo) {
        //先查询是否有记录，在main表
        Main main = mainMapper.selectByUniqueNumber(userDoInfo.getUniqueNumber());
        if (main == null) {
            return R.error().message("查询不到课程信息！");
        }
        String number = "";
        //是第一次授课
        if (userDoInfo.getIsFirst() == 0.1) {
            number = "1";
        } else {
            number = "0";
        }
        //是双语授课
        if (userDoInfo.getIsDoubleLanguage() == 1.5) {
            number += "1";
        } else {
            number += "0";
        }
        if (userDoInfo.getIsWeekend() == 1.1) {
            number += "1";
        } else {
            number += "0";
        }

        if (main.getAdditionalId() == null) {
            //添加记录，
            String additionalID = UUIDStringUtils.randomUUID();
            AdditionalMain am = new AdditionalMain();

            am.setAdditionalId(additionalID);
            am.setAdditionalCoefficientsId(number);
            am.setIsSure(1);
            am.setIsDelete(0);
            am.setGmtCreate(new Date());
            am.setGmtCreate(new Date());
            int row = additionalMainService.getBaseMapper().insert(am);
            if (row == 1) {
                main.setAdditionalId(additionalID);
                int i = mainMapper.updateById(main);
                if (i == 1) {
                    return R.ok().message("添加用户特殊情况成功！");
                }
            }
            return R.error().message("添加用户特殊情况失败！！");
        }
        //存在特殊情况！先查询，后修改 如果已经被删除，则返回null 则不能申请
        AdditionalMain am = additionalMainService.getByAdditionalId(main.getAdditionalId());
        if (am == null) {
            return R.error().message("用户的特殊情况申请异常");
        } else {
            if (am.getIsSure() == 5) {
                return R.error().message("等待管理员确认！");
            }
        }
        am.setAdditionalCoefficientsId(number);
        boolean b = additionalMainService.updateById(am);
        if (b) {
            return R.ok().message("成功修改用户特殊系数！");
        }
        return R.error().message("修改用户特殊情况失败！！！");
    }

    /**
     * 教师确认课程信息
     * 处理流程：
     * 如果不存在修改信息，则可以直接确认
     *    存在修改/特殊情况添加，
     *    则先查看是否已通过 两次同意
     * @param userDoInfo
     * @return
     */
    @Override
    public R UserSureDo(userDoInfo userDoInfo) {
        //先确认是否存在特殊情况，且不是修改或者添加的。
        //TODO:,应该创建新方法的，偷懒了，通过用户id查询比较符合逻辑
        Main main = mainMapper.selectByUniqueNumber(userDoInfo.getUniqueNumber());

        //说明存在特殊情况申请，需要检查是否 通过
        if (main.getAdditionalId() != null) {
            //通过additionalId查询是否存在未确认的特殊情况申请，存在的话，则需要进一步确认，申请是否通过
            AdditionalMain am = additionalMainService.getByAdditionalId(main.getAdditionalId());
            if (am != null) {
                //说明院长已经确认，等待管理员进一步确认。
                if (am.getIsSure() == 5) {
                    return R.error().message("存在未确认的特殊记录！等待管理员进一步确认！");
                }
            }
        }

        //TODO：因为是这个逻辑，所以要在院长通过哪里再设置一次计算过程
        //添加类型查询出来没有用上，如果是手动添加的话，不需要 教师 手动 确认 管理员同意就直接算是确认了。

        //添加计算过程
        String cp = UUIDStringUtils.randomUUID();
        CalculationProcess calculationProcess = new CalculationProcess();
        calculationProcess.setAdditionalId(cp);
        calculationProcess.setIsFirst(userDoInfo.getIsFirst());
        calculationProcess.setIsDoubleLanguage(userDoInfo.getIsDoubleLanguage());
        calculationProcess.setIsWeekend(userDoInfo.getIsWeekend());
        calculationProcess.setCoefficientPractical(Double.valueOf(userDoInfo.getCoefficientL()));//理论学时系数
        calculationProcess.setCoefficientTheoretical(Double.valueOf(userDoInfo.getCoefficientS()));//实践学时系数
        calculationProcessService.Insert(calculationProcess);//添加记录
        //理论学时*（系数+是否新课+班级人数是否超标）*是否双语*是否周末  +  实验学时*（系数+是否新课+班级人数是否超标）*是否周末

        main.setCaseloadProcessId(cp);//保存计算过程.
        main.setCaseload(String.valueOf(userDoInfo.getAdd()));  //保存工作量
        main.setIsSure(1);
        int i = mainMapper.updateById(main);
        if(i==1){
            return R.ok().message("确认工作量成功！");
        }
        return R.error().message("确认课程工作量失败");
    }

    /**
     * 修改为管理员的  确认用户特殊请求
     * @return 特殊情况申请列表
     */
    @Override
    public R getAdditionalSure() {
        //查询ac表，所有未确认的记录，并联合teach表和teachClass表查询课程名称和班级信息，返回结果。
        List<acSure> ac = additionalMainService.getAdditionalSure();
        System.out.println("返回 的数据，用户特殊情况申请：" + ac);
        if (ac == null) {
            return R.error().message("不存在未确认的信息！！");
        }
        return R.ok().message("成功查询").data("AdditionalSure", ac);
    }

    @Override
    public R AdditionalSure(soleAndUser soleAndUser) {
        int i = additionalMainService.updateByAdditionalId(soleAndUser);
        if(i==1){
            return R.ok().message("同意成功！");
        }
        return R.error().message("修改失败！");
    }

    @Override
    public R AdditionalUnSure(acSure ac) {
        return null;
    }

    @Override
    public R addMain(Main main) {
        return R.ok().message("接口正常");
    }

    @Override
    public R upDataByMain(Main main) {
        return R.ok().message("接口正常");
    }

    @Override
    public R getCExperiment() {
        return R.ok().message("接口正常");
    }

    @Override
    public R getCTheory() {
        return R.ok().message("接口正常");
    }

    @Override
    public R getCPractice() {
        return R.ok().message("接口正常");
    }

    @Override
    public R getAdditionalSureA() {
        return R.ok().message("接口正常");
    }

    @Override
    public R AdditionalSureA(acSure acSure) {
        return R.ok().message("接口正常");
    }

    @Override
    public R AdditionalUnSureA(acSure acSure) {
        return R.ok().message("接口正常");
    }

    /**
     * 获取教师特殊情况  院长的
     *      处理流程，查询待确认的特殊情况信息
     *      isSure = 1
     * @param userCheckParam
     * @return
     */
    @Override
    public R getAdditionalListByF(UserCheckParam userCheckParam) {
        List<acSure> acSureList = additionalMainService.getByFaculty(userCheckParam.getFaculty());
        if(acSureList.isEmpty()){
            return R.ok().message("不存在未确认的申请！！");
        }
        return R.ok().data("AdditionalSure",acSureList);
    }
}
