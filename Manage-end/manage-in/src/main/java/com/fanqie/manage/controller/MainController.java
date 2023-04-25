package com.fanqie.manage.controller;

import com.fanqie.commonutils.utils.Permission;
import com.fanqie.commonutils.utils.PermissionEnum;
import com.fanqie.commonutils.utils.R;
import com.fanqie.manage.entity.User;
import com.fanqie.manage.param.acSure;
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
     * 添加getUserDo方法，返回是自定义的R封装类，没有请求参数，处理是通过调用main_all_view视图映射类查询实现 的方法实现的
     * 这个返回的是没有确认的工作量实时更新的，
     * 通过视图结合逻辑查询得出的工作量，这是老师还没有确认的版本
     * 总
     */

    /**
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
    //@Permission({PermissionEnum.UserPlus})
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
        //TODO:数据校验。
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
     * 用来给教务处人员确认教师特殊情况申请的。
     * @return 未确认的特殊情况申请列表
     */
    @GetMapping("getAdditionalSure")
    @Permission({PermissionEnum.ADMIN,PermissionEnum.UserPlus})
    public R getAdditionalSure(){
        return mainService.getAdditionalSure();
    }

    /**
     * 通过教师的特殊申请
     * @param ac
     * @return
     */
    @PostMapping("AdditionalSure")
    @Permission({PermissionEnum.ADMIN,PermissionEnum.UserPlus})
    public R AdditionalSure(@RequestBody acSure ac){
        System.out.println("同意教师的特殊申请！");
        return mainService.AdditionalSure(ac);
    }

    /**
     * 不通过教师的特殊申请。
     * @param ac
     * @return
     */

    @PostMapping("AdditionalUnSure")
    @Permission({PermissionEnum.ADMIN,PermissionEnum.UserPlus})
    public R AdditionalUnSure(@RequestBody acSure ac){
        return mainService.AdditionalUnSure(ac);
    }
//  @Permission({PermissionEnum.ADMIN,PermissionEnum.UserPlus})


}

