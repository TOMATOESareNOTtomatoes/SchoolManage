package com.fanqie.manage.controller;

import com.fanqie.commonutils.param.UserCheckParam;
import com.fanqie.commonutils.utils.Permission;
import com.fanqie.commonutils.utils.PermissionEnum;
import com.fanqie.commonutils.utils.R;
import com.fanqie.manage.entity.Main;
import com.fanqie.manage.entity.User;
import com.fanqie.manage.param.acSure;
import com.fanqie.manage.param.soleAndUser;
import com.fanqie.manage.param.userDoInfo;
import com.fanqie.manage.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fq
 * @since 2023-03-30
 */
@RestController
@RequestMapping("/manage/main")
public class MainController {

    @Autowired
    MainService mainService;

    /**
     * 管理员  上传  数据
     * @param file
     * @return
     */
    @Permission({PermissionEnum.ADMIN})
    @PostMapping("upload")
    @ResponseBody
    public R upload(MultipartFile file) {
        System.out.print("目录：" + file);
        if (file.isEmpty()) {
            return R.error().message("上传的参数为空！");
        }
        return mainService.uploadExcel(file);
    }

    /**
     * 查询所有的系数   管理员
     * @return 几张系数表
     */
    @GetMapping("getCoefficient")
    @Permission({PermissionEnum.ADMIN})
    public R getCoefficient(){
        return mainService.getCoefficient();
    }

    /**
     * 添加getUserDo方法，返回是自定义的R封装类，没有请求参数，处理是通过调用main_all_view视图映射类查询实现 的方法实现的
     * 这个返回的是没有确认的工作量实时更新的，
     * 通过视图结合逻辑查询得出的工作量，这是老师还没有确认的版本
     * 总

     * 获取所有教师的工作量（未确认）
     * @return mainAllView实体类的列表
     */
    @GetMapping("getUserDo")
    @ResponseBody
    @Permission({PermissionEnum.ADMIN,PermissionEnum.UserPlus})
    public R getUserDo() {
        System.out.println("获取所有未确认用户的 工作量列表接口正常！");
        return mainService.getUserDoSimple();
    }

    /**
     * 教师自己查看自己的课程信息。
     * @param user
     * @return
     */
    @PostMapping("getUserDoInfo")
    public R getUserDoInfo(@RequestBody User user) {
        //System.out.println("getUserDoInfo------:" + user);
        if (user == null || user.getUserId() == null) {
            R.error().message("参数有误@！！");
        }
        return mainService.getUserDoInfo(String.valueOf(user.getUserId()));
    }

    /**
     * 用户自己补充特殊情况，新课等等这些。
     * @param userDoInfo
     * @return
     */
    @PostMapping("addAdditional")
    public R addAdditional(@RequestBody userDoInfo userDoInfo) {
        System.out.println("特殊情况参数："+userDoInfo.toString());
        return mainService.addAdditional(userDoInfo);
    }

    /**
     * 用户确认课程工作量信息
     * @param userDoInfo
     * @return
     */
    @PostMapping("UserSureDo")
    public R UserSureDo(@RequestBody userDoInfo userDoInfo){
        return mainService.UserSureDo(userDoInfo);
    }

    /**
     * 用户  修改   课程 信息
     * @param userDoInfo
     * @return
     */
    @PostMapping("UserReviseDo")
    public R UserReviseDo(@RequestBody userDoInfo userDoInfo){
        return mainService.UserReviseDo(userDoInfo);
    }

    /**
     *  管理员  特殊情况申请表
     * @return 未确认的特殊情况申请列表
     */
    @GetMapping("getAdditionalSure")
    @Permission({PermissionEnum.ADMIN,PermissionEnum.UserPlus})
    public R getAdditionalSure(){
        return mainService.getAdditionalSure();
    }

    /**
     * 院长   特殊情况申请表
     * @return 未确认的特殊情况申请列表
     */
    @PostMapping("getAdditionalListByF")
    @Permission({PermissionEnum.ADMIN,PermissionEnum.UserPlus})
    public R getAdditionalListByF(@RequestBody UserCheckParam userCheckParam){
        return mainService.getAdditionalListByF(userCheckParam);
    }

    /**
     *   院长  通过   教师的特殊申请
     * @param soleAndUser
     * @return
     */
    @PostMapping("AdditionalSure")
    @Permission({PermissionEnum.ADMIN,PermissionEnum.UserPlus})
    public R AdditionalSure(@RequestBody soleAndUser soleAndUser){
        System.out.println("同意教师的特殊申请！"+soleAndUser);
        return mainService.AdditionalSure(soleAndUser);
    }

    /**
     * 不通过教师的特殊申请。
     * @param acSure
     * @return
     */

    @PostMapping("AdditionalUnSure")
    @Permission({PermissionEnum.ADMIN,PermissionEnum.UserPlus})
    public R AdditionalUnSure(@RequestBody acSure acSure){
        System.out.println("butongyi"+acSure);
        return mainService.AdditionalUnSure(acSure);
    }
//  @Permission({PermissionEnum.ADMIN,PermissionEnum.UserPlus})

    /**
     * todo：加个教师课程信息错误提交，搞个简单的统计结果表，就是全部教职工的工作量情况（只有名字、院系、总工作量）三个属性。
     * todo：1、教务处同意后，回复用户特定的通知、2修改某一教师的课程信息，课时等等。3、（这个感觉必要）管理员可以修改课程的系数
     */
    //实现教师课程错误信息提交申请。根据教师的id查询课程信息，课程信息提交申请。
    // 要新建表。教务处确认。删除原表信息，添加新的课程信息

    /**
     * 用来给教务处人员确认教师特殊情况申请的。管理员确认特殊情况用表。
     * @return 特殊情况申请列表
     */
    @GetMapping("getAdditionalSureA")
    @Permission({PermissionEnum.ADMIN,PermissionEnum.UserPlus})
    public R getAdditionalSureA(){
        return mainService.getAdditionalSureA();
    }

    /**
     * 通过教师的特殊申请  管理员
     * @param soleAndUser
     * @return
     */
    @PostMapping("AdditionalSureA")
    @Permission({PermissionEnum.ADMIN,PermissionEnum.UserPlus})
    public R AdditionalSureA(@RequestBody soleAndUser soleAndUser){
        System.out.println("G同意教师的特殊申请！"+soleAndUser);
        return mainService.AdditionalSureA(soleAndUser);
    }

    /**
     * 不通过教师的特殊申请。管理员
     * @param acSure
     * @return
     */

    @PostMapping("AdditionalUnSureA")
    @Permission({PermissionEnum.ADMIN,PermissionEnum.UserPlus})
    public R AdditionalUnSureA(@RequestBody acSure acSure){
        System.out.println("GGGGGG"+acSure);
        return mainService.AdditionalUnSureA(acSure);
    }

    /**
     * 教师 申请 添加 课程记录
     * @param userDoInfo
     * @return
     */
    @PostMapping("addMain")
    public R addMain(@RequestBody userDoInfo userDoInfo){
        System.out.println("教师添加课程："+userDoInfo);
        return mainService.addMain(userDoInfo);
    }

    /**
     * 院长 获取  待确认  课程信息 列表
     */
    @PostMapping("getAddMainList")
    public R getAddMainList(@RequestBody UserCheckParam userCheckParam){
        return mainService.getAddMainList(userCheckParam);
    }

    /**
     * 管理员 获取  待确认  课程信息 列表
     */
    @PostMapping("getAddMainListA")
    public R getAddMainListA(){
        return mainService.getAddMainListA();
    }

    /**
     * 院长 同意 课程信息
     */
    @PostMapping("sureAddMain")
    public R sureAddMain(@RequestBody soleAndUser soleAndUser){
        System.out.println("院长确认课程信息："+soleAndUser);
        return mainService.sureAddMain(soleAndUser);
    }

    /**
     * 管理员 同意 课程信息
     */
    @PostMapping("sureAddMainA")
    public R sureAddMainA(@RequestBody soleAndUser soleAndUser){
        System.out.println("院长确认课程信息："+soleAndUser);
        return mainService.sureAddMainA(soleAndUser);
    }

    /**
     * 修改用户的课程信息。 院长修改 用户的 课程信息
     * @param main
     * @return
     */
    @PostMapping("upDataByMain")
    public R upDataByMain(@RequestBody Main main){
        return mainService.upDataByMain(main);
    }

    /**
     * 获取 理论 学时的 系数
     * @return
     */
    @GetMapping("getCExperiment")
    public R getCExperiment(){
        return mainService.getCExperiment();
    }

    /**
     * 获取 有理论学时的 实验部分的 系数
     * @return
     */
    @GetMapping("getCTheory")
    public R getCTheory(){
        return mainService.getCTheory();
    }

    /**
     * 获取 全实际 学时的 系数
     * @return
     */
    @GetMapping("getCPractice")
    public R getCPractice(){
        return mainService.getCPractice();
    }

    //todo：修改接口

    /**
     * 管理员   获取总课程信息统计结果，简略版
     * @return
     */
    @GetMapping("simpleAllDo")
    public R simpleAllDo(){
        return mainService.simpleAllDo();
    }



}

