package com.fanqie.manage.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanqie.commonutils.param.UserCheckParam;
import com.fanqie.commonutils.utils.R;
import com.fanqie.commonutils.utils.RandomNumberGenerator;
import com.fanqie.commonutils.utils.UUIDStringUtils;
import com.fanqie.manage.entity.Class;
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
import java.util.stream.Collectors;

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
            doInfo.setIsSure("0");//设置未确认
            //直接赋值
            doInfo.setUserId(view.getUserId());
            doInfo.setUserName(view.getUserName());
            doInfo.setClassName(view.getClassName());
            doInfo.setUniqueNumber(view.getUniqueNumber());
            doInfo.setClassNumber(view.getClassNumber());
            doInfo.setTeachName(view.getTeachName());
            //todo：可以删除了，重新录入数据的话。
            if (view.getPracticalHours() == null) {
                view.setPracticalHours("0");
            }
            if (view.getTheoreticalHours() == null) {
                view.setTheoreticalHours("0");
            }
            doInfo.setPracticalHours(Integer.parseInt(view.getPracticalHours()));
            doInfo.setTheoreticalHours(Integer.parseInt(view.getTheoreticalHours()));
            //---------------------------------------------
            //查看 特殊情况是否有效
            AdditionalMain am = null;
            if (view.getAdditionalId() != null) {
                // 查询有 有效的特殊情况。
                am = additionalMainService.getByAdditionalId(view.getAdditionalId());
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
                doInfo.setCoefficientL("0");
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
                    } else {
                        doInfo.setCoefficientS(String.valueOf(cp.getCoefficient()));
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
        //根据特殊情况获取特殊情况的id  偷懒了。应该查表的。
        String number= getAdditionalCoefficientsId(userDoInfo.getIsFirst(),
                userDoInfo.getIsDoubleLanguage(), userDoInfo.getIsWeekend());
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
     * 存在修改/特殊情况添加，
     * 则先查看是否已通过 两次同意
     *
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
        if (i == 1) {
            return R.ok().message("确认工作量成功！");
        }
        return R.error().message("确认课程工作量失败");
    }

    /**
     * 修改为管理员的  确认用户特殊请求
     *
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
        if (i == 1) {
            return R.ok().message("同意成功！");
        }
        return R.error().message("修改失败！");
    }

    @Override
    public R AdditionalUnSure(acSure ac) {
        return null;
    }

    /**
     * 教师添加课程信息
     *
     * @param userDoInfo is_sure=3 的话，被当作不存在特殊情况
     * @return
     */
    @Override
    public R addMain(userDoInfo userDoInfo) {
        //添加main记录
        String uNumber = UUIDStringUtils.randomUUID();//唯一id
        Main main = new Main();
        if (!userDoInfo.getIsSure().equals("3")) {
            main.setAdditionalId(uNumber);
        }
        main.setTerm(String.valueOf(new Date()));
        main.setUniqueNumber(uNumber);
        main.setUserId(userDoInfo.getUserId());
        main.setAddType(2);//添加的类型，2 说明是添加的
        int insert = mainMapper.insert(main);
        if (insert != 1) {
            return R.error().message("添加课程信息失败");
        }
        //课程表添加信息
        Teach teach = new Teach();
        teach.setTeachId(uNumber);
        teach.setTeachName(userDoInfo.getTeachName());
        teach.setTheoreticalHours(String.valueOf(userDoInfo.getTheoreticalHours()));
        teach.setPracticalHours(String.valueOf(userDoInfo.getPracticalHours()));
        int insert1 = teachMapper.insert(teach);
        if (insert1 != 1) {
            return R.error().message("添加课程信息失败2");
        }
        //保存班级信息
        String[] arr = userDoInfo.getClassName().split(",");
        System.out.print("班级：" + userDoInfo.getClassName() + "\n");
        for (String y : arr) {
            System.out.print("一个班级：" + y + "\n");
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("class_name", y);
            Class aClass = classMapper.selectOne(queryWrapper);
            if (aClass == null) {
                //查询不到班级信息，添加班级
                Class c = new Class();
                c.setClassName(y);
                c.setClassId(RandomNumberGenerator.generate());
                c.setClassNumber(userDoInfo.getClassNumber() / arr.length);
                int i = classMapper.insert(c);
                if (i != 1) {
                    System.out.printf("添加班级信息失败");
                } else {
                    aClass = classMapper.selectOne(queryWrapper);
                    if (aClass == null) {
                        System.out.printf("查询班级id出错");
                    }
                }
            }

            //TODO:bug,人数应该跟课程的，而不是班级，现在的逻辑，课程的人数以班级为准，而且不能修改
            //查询到班级id  添加teachClass表信息
            TeachClass teachClass = new TeachClass();
            teachClass.setClassId(aClass.getClassId());
            teachClass.setUniqueNumber(uNumber);
            int itc = teachClassMapper.insert(teachClass);
            if (itc != 1) {
                return R.error().message("添加课程信息失败3");
            }
        }
        //添加特殊情况  isSure=3 说明不存在特殊情况。
        if (!userDoInfo.getIsSure().equals("3")) {
            AdditionalMain additionalMain = new AdditionalMain();
            additionalMain.setAdditionalId(uNumber);//新课的话，其特殊情况值是跟唯一值相同的。
            //TOdo:写成一个方法来调用
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
            additionalMain.setAdditionalCoefficientsId(number);
            //todo:目前是还会再特殊情况里面出现，要想不出现的话，设置成其他值。
            additionalMain.setIsSure(1);
            int ami = additionalMainService.getBaseMapper().insert(additionalMain);
            if (ami != 1) {
                return R.error().message("添加课程信息失败4");
            }
        }

        return R.ok().message("添加课程信息成功！");
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

    /**
     * 管理员 同意 特殊情况申请
     *
     * @param soleAndUser
     * @return
     */
    @Override
    public R AdditionalSureA(soleAndUser soleAndUser) {
        int i = additionalMainService.updateByAdditionalIdA(soleAndUser);
        if (i == 1) {
            return R.ok().message("同意成功！");
        }
        return R.error().message("修改失败！");
    }

    @Override
    public R AdditionalUnSureA(acSure acSure) {
        return R.ok().message("接口正常");
    }

    /**
     * 获取教师特殊情况  院长的
     * 处理流程，查询待确认的特殊情况信息
     * isSure = 1
     *
     * @param userCheckParam
     * @return
     */
    @Override
    public R getAdditionalListByF(UserCheckParam userCheckParam) {
        List<acSure> acSureList = additionalMainService.getByFaculty(userCheckParam.getFaculty());
        if (acSureList.isEmpty()) {
            return R.ok().message("不存在未确认的申请！！");
        }
        return R.ok().data("AdditionalSure", acSureList);
    }

    /**
     * 获取教师 新添加 的课程信息
     *
     * @param userCheckParam
     * @return
     */
    @Override
    public R getAddMainList(UserCheckParam userCheckParam) {
        System.out.println(userCheckParam);
        QueryWrapper<Main> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("add_type", 1, 2)
                .and(wrapper -> wrapper.isNull("is_delete")
                        .or().ne("is_delete", 1))
                .and(wrapper -> wrapper.isNull("is_sure")
                        .or().in("is_sure", 0, 1, 2));
        List<Main> mainList = mainMapper.selectList(queryWrapper);
        System.out.println("mainList" + mainList);
        List<MainAllView> mainAllViewList = new ArrayList<>();
        for (Main main : mainList) {
            QueryWrapper<MainAllView> wrapper = new QueryWrapper<>();
            wrapper.eq("unique_number", main.getUniqueNumber());
            wrapper.eq("faculty", userCheckParam.getFaculty());
            List<MainAllView> list = mainAllViewViewService.selectList(wrapper);
            mainAllViewList.addAll(list);
        }
        System.out.println("mainAllViewList" + mainAllViewList);
        List<userDoInfo> userDoInfoList = mainAllViewList.stream()
                .map(mainAllView -> {
                    QueryWrapper<AdditionalMain> additionalMainWrapper = new QueryWrapper<>();
                    additionalMainWrapper.eq("additional_id", mainAllView.getUniqueNumber());
                    AdditionalMain additionalMain = additionalMainService.getOne(additionalMainWrapper);
                    userDoInfo userDoInfo = new userDoInfo();
                    userDoInfo.setUniqueNumber(mainAllView.getUniqueNumber());
                    userDoInfo.setIsFirst(0);
                    userDoInfo.setIsDoubleLanguage(0);
                    userDoInfo.setIsWeekend(0);
                    userDoInfo.setUserName(mainAllView.getUserName());
                    userDoInfo.setClassName(mainAllView.getClassName());
                    userDoInfo.setClassNumber(mainAllView.getClassNumber());
                    userDoInfo.setTeachName(mainAllView.getTeachName());
                    userDoInfo.setPracticalHours(Integer.parseInt(mainAllView.getPracticalHours()));
                    userDoInfo.setTheoreticalHours(Integer.parseInt(mainAllView.getTheoreticalHours()));
                    return getUserDoInfo(additionalMain, userDoInfo);
                })
                .collect(Collectors.toList());
        return R.ok().data("userDoInfoList", userDoInfoList);

    }

    /**
     * 院长同意 新添加的 课程信息
     *
     * @param soleAndUser
     * @return
     */
    @Override
    public R sureAddMain(soleAndUser soleAndUser) {
        QueryWrapper<Main> mainQueryWrapper = new QueryWrapper<>();
        mainQueryWrapper.eq("unique_number", soleAndUser.getSole());
        Main main = mainMapper.selectOne(mainQueryWrapper);

        QueryWrapper<AdditionalMain> additionalMainWrapper = new QueryWrapper<>();
        additionalMainWrapper.eq("additional_id", soleAndUser.getSole());
        AdditionalMain additionalMain = additionalMainService.getOne(additionalMainWrapper);

        if (main != null) {
            main.setIsSure(5);
            main.setUserPlusId(soleAndUser.getUserId());
            int i = mainMapper.updateById(main);
            if (i != 1) {
                return R.error().message("同意失败！");
            }
        }

        if (additionalMain != null) {
            additionalMain.setIsSure(5);
            additionalMain.setUserPlusId(soleAndUser.getUserId());
            boolean b = additionalMainService.updateById(additionalMain);
            if (!b) {
                return R.error().message("同意失败!！");
            }
        }
        return R.ok().message("成功同意了！");
    }

    /**
     * 管理员获取 新添加课程 列表
     *
     * @return
     */
    @Override
    public R getAddMainListA() {
        QueryWrapper<Main> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("add_type", 1, 2)
                .and(wrapper -> wrapper.isNull("is_delete")
                        .or().ne("is_delete", 1))
                .eq("is_sure", 5);
        List<Main> mainList = mainMapper.selectList(queryWrapper);
        System.out.println("mainList" + mainList);
        List<MainAllView> mainAllViewList = new ArrayList<>();
        for (Main main : mainList) {
            QueryWrapper<MainAllView> wrapper = new QueryWrapper<>();
            wrapper.eq("unique_number", main.getUniqueNumber());
            List<MainAllView> list = mainAllViewViewService.selectList(wrapper);
            mainAllViewList.addAll(list);
        }
        System.out.println("mainAllViewList" + mainAllViewList);
        List<userDoInfo> userDoInfoList = mainAllViewList.stream()
                .map(mainAllView -> {
                    QueryWrapper<AdditionalMain> additionalMainWrapper = new QueryWrapper<>();
                    additionalMainWrapper.eq("additional_id", mainAllView.getUniqueNumber());
                    AdditionalMain additionalMain = additionalMainService.getOne(additionalMainWrapper);
                    userDoInfo userDoInfo = new userDoInfo();
                    userDoInfo.setUniqueNumber(mainAllView.getUniqueNumber());
                    userDoInfo.setIsFirst(0);
                    userDoInfo.setIsDoubleLanguage(0);
                    userDoInfo.setIsWeekend(0);
                    userDoInfo.setUserName(mainAllView.getUserName());
                    userDoInfo.setClassName(mainAllView.getClassName());
                    userDoInfo.setClassNumber(mainAllView.getClassNumber());
                    userDoInfo.setTeachName(mainAllView.getTeachName());
                    userDoInfo.setPracticalHours(Integer.parseInt(mainAllView.getPracticalHours()));
                    userDoInfo.setTheoreticalHours(Integer.parseInt(mainAllView.getTheoreticalHours()));
                    userDoInfo.setIsSure(mainAllView.getFaculty());//todo:这是不好的，应该创建新的接口类用来返回数据

                    return getUserDoInfo(additionalMain, userDoInfo);
                })
                .collect(Collectors.toList());
        return R.ok().data("userDoInfoList", userDoInfoList);
    }

    /**
     * 管理员同意课程信息
     *
     * @param soleAndUser
     * @return
     */
    @Override
    public R sureAddMainA(soleAndUser soleAndUser) {
        QueryWrapper<Main> mainQueryWrapper = new QueryWrapper<>();
        mainQueryWrapper.eq("unique_number", soleAndUser.getSole());
        Main main = mainMapper.selectOne(mainQueryWrapper);

        QueryWrapper<AdditionalMain> additionalMainWrapper = new QueryWrapper<>();
        additionalMainWrapper.eq("additional_id", soleAndUser.getSole());
        AdditionalMain additionalMain = additionalMainService.getOne(additionalMainWrapper);

        if (main != null) {
            main.setIsSure(9);
            main.setAdminId(soleAndUser.getUserId());
            int i = mainMapper.updateById(main);
            if (i != 1) {
                return R.error().message("同意失败！");
            }
        }

        if (additionalMain != null) {
            additionalMain.setIsSure(9);
            additionalMain.setAdminId(soleAndUser.getUserId());
            boolean b = additionalMainService.updateById(additionalMain);
            if (!b) {
                return R.error().message("同意失败!！");
            }
        }
        return R.ok().message("成功同意了！");
    }

    /**
     * 用户  修改  课程信息     isSure=3 说明不存在特殊情况。
     * 实现 逻辑
     *
     * @param userDoInfo
     * @return
     */
    @Override
    public R UserReviseDo(userDoInfo userDoInfo) {
        QueryWrapper<Main> mainQueryWrapper = new QueryWrapper<>();
        mainQueryWrapper.eq("unique_number", userDoInfo.getUniqueNumber());
        //todo：添加未被删除查询。
        Main main = mainMapper.selectOne(mainQueryWrapper);
        if (main != null) {
            //说明不是用户自己修改。
            if (!main.getUserId().equals(userDoInfo.getUserId())) {

                Teach teach = teachMapper.selectOne(new QueryWrapper<Teach>()
                        .eq("teach_id", userDoInfo.getUniqueNumber()));
                if (teach != null) {
                    teach.setTeachName(userDoInfo.getTeachName());
                    teach.setTeachNumber(userDoInfo.getClassNumber());
                    teach.setTheoreticalHours(String.valueOf(userDoInfo.getTheoreticalHours()));
                    teach.setPracticalHours(String.valueOf(userDoInfo.getPracticalHours()));
                    int i = teachMapper.updateById(teach);
                    if (i != 1) {
                        return R.error().message("院长修改出错！teach");
                    }
                }
                String[] arr = userDoInfo.getClassName().split("\\+");
                for (String y : arr) {
                    QueryWrapper<Class> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("class_name", y);
                    Class aClass = classMapper.selectOne(queryWrapper);
                    if (aClass == null) {
                        // Add new class
                        Class c = new Class();
                        c.setClassName(y);
                        c.setClassId(RandomNumberGenerator.generate());
                        classMapper.insert(c);
                        aClass = classMapper.selectOne(queryWrapper);
                    }
                    TeachClass teachClass = new TeachClass();
                    teachClass.setClassId(aClass.getClassId());
                    teachClass.setUniqueNumber(userDoInfo.getUniqueNumber());
                    int i = teachClassMapper.insert(teachClass);
                    if (i != 1) {
                        return R.error().message("院长修改出错！teachClass");
                    }
                }

                AdditionalMain additionalMain = additionalMainService.getOne(new QueryWrapper<AdditionalMain>()
                        .eq("additional_id", userDoInfo.getUniqueNumber())
                        .isNull("is_delete")
                        .or()
                        .eq("is_delete", 0)
                );
                if (additionalMain != null) {
                    //说明本来存在特殊情况。
                    if (!userDoInfo.getIsSure().equals("3")) {
                        //说明现在取消了。
                        additionalMain.setIsDelete(1);
                        boolean b = additionalMainService.updateById(additionalMain);
                        if (!b) {
                            return R.error().message("院长修改出错！AdditionalA");
                        }
                    }
                    additionalMain.setAdditionalCoefficientsId(getAdditionalCoefficientsId(userDoInfo.getIsFirst(),
                            userDoInfo.getIsDoubleLanguage(), userDoInfo.getIsWeekend()));
                    boolean b = additionalMainService.updateById(additionalMain);
                    if (!b) {
                        return R.error().message("院长修改出错！Additional");
                    }
                }
                main.setUserPlusId(userDoInfo.getUserId());
                main.setIsSure(5);//代表院长已经确认
                int i = mainMapper.update(main, new UpdateWrapper<Main>()
                        .eq("unique_number", userDoInfo.getUniqueNumber()));
                if(i!=1){
                    return R.error().message("院长修改出错！main");
                }
                return R.ok().message("院长修改记录成功！");
            } else {
                //删除旧的记录。
                main.setIsDelete(1);
                int iu = mainMapper.updateById(main);
                if (iu != 1) {
                    return R.error().message("修改信息失败！");
                }
                //添加新的main 记录
                Main newMain = new Main();
                String uNumber = UUIDStringUtils.randomUUID();
                newMain.setUniqueNumber(uNumber);
                newMain.setBeforeId(userDoInfo.getUniqueNumber());
                newMain.setUserId(userDoInfo.getUserId());
                newMain.setTerm(String.valueOf(new Date()));
                if (!userDoInfo.getIsSure().equals("3")) {
                    //说明存在特殊情况。
                    newMain.setAdditionalId(uNumber);
                }
                mainMapper.insert(newMain);
                //课程表添加信息
                Teach teach = new Teach();
                teach.setTeachId(uNumber);
                teach.setTeachName(userDoInfo.getTeachName());
                teach.setTeachNumber(userDoInfo.getClassNumber());//课程人数
                teach.setTheoreticalHours(String.valueOf(userDoInfo.getTheoreticalHours()));
                teach.setPracticalHours(String.valueOf(userDoInfo.getPracticalHours()));
                int insert1 = teachMapper.insert(teach);
                if (insert1 != 1) {
                    return R.error().message("添加课程信息失败2");
                }
                //保存班级信息
                String[] arr = userDoInfo.getClassName().split("\\+");
                System.out.print("班级：" + userDoInfo.getClassName() + "\n");
                for (String y : arr) {
                    System.out.print("一个班级：" + y + "\n");
                    QueryWrapper queryWrapper = new QueryWrapper();
                    queryWrapper.eq("class_name", y);
                    Class aClass = classMapper.selectOne(queryWrapper);
                    if (aClass == null) {
                        //查询不到班级信息，添加班级
                        Class c = new Class();
                        c.setClassName(y);
                        c.setClassId(RandomNumberGenerator.generate());
                        //c.setClassNumber(userDoInfo.getClassNumber() / arr.length);
                        int i = classMapper.insert(c);
                        if (i != 1) {
                            return R.error().message("修改信息失败3！");
                        } else {
                            aClass = classMapper.selectOne(queryWrapper);
                            if (aClass == null) {
                                return R.error().message("修改信息失败4！");
                            }
                        }
                    }
                    //查询到班级id  添加teachClass表信息
                    TeachClass teachClass = new TeachClass();
                    teachClass.setClassId(aClass.getClassId());
                    teachClass.setUniqueNumber(uNumber);
                    int itc = teachClassMapper.insert(teachClass);
                    if (itc != 1) {
                        return R.error().message("添加课程信息失败3");
                    }
                }
                //添加特殊情况  isSure=3 说明不存在特殊情况。
                if (!userDoInfo.getIsSure().equals("3")) {
                    AdditionalMain additionalMain = new AdditionalMain();
                    additionalMain.setAdditionalId(uNumber);//新课的话，其特殊情况值是跟唯一值相同的。
                    additionalMain.setAdditionalCoefficientsId(getAdditionalCoefficientsId(userDoInfo.getIsFirst(),
                            userDoInfo.getIsDoubleLanguage(), userDoInfo.getIsWeekend()));
                    //todo:目前是还会再特殊情况里面出现，要想不出现的话，设置成其他值。比如 9
                    additionalMain.setIsSure(1);
                    int ami = additionalMainService.getBaseMapper().insert(additionalMain);
                    if (ami != 1) {
                        return R.error().message("添加课程信息失败4");
                    }
                }
                return R.ok().message("修改成功！！");
            }
        }
        return R.error().message("查询不到课程信息！！");
    }

    /**
     * 根据特殊情况 得出特殊情况的 id
     *
     * @param isFirst          等于 0.1  说明是第一次授课  其他值说明不是
     * @param isDoubleLanguage 等于1.5  说明是双语授课
     * @param isWeekend        等于1.1 说明是周末上课
     * @return
     */
    private String getAdditionalCoefficientsId(double isFirst, double isDoubleLanguage, double isWeekend) {
        String number = "";
        //是第一次授课
        if (isFirst == 0.1) {
            number = "1";
        } else {
            number = "0";
        }
        //是双语授课
        if (isDoubleLanguage == 1.5) {
            number += "1";
        } else {
            number += "0";
        }
        if (isWeekend == 1.1) {
            number += "1";
        } else {
            number += "0";
        }
        return number;
    }

    /**
     * 将 特殊情况的 id  解析为对应的 情况  理论上应该根据 id查询表，然后 得出的
     *
     * @param additionalMain
     * @param userDoInfo
     * @return
     */
    private userDoInfo getUserDoInfo(AdditionalMain additionalMain, userDoInfo userDoInfo) {
        if (additionalMain != null) {
            String additionalCoefficientsId = additionalMain.getAdditionalCoefficientsId();
            int ccc = Integer.parseInt(additionalCoefficientsId);
            if ((ccc / 100) == 1) {
                userDoInfo.setIsFirst(0.1);
            }
            if (((ccc / 10) % 10) == 1) {
                userDoInfo.setIsDoubleLanguage(1.5);
            }
            if ((ccc % 10) == 1) {
                userDoInfo.setIsWeekend(1.1);
            }
        }
        return userDoInfo;
    }
}
