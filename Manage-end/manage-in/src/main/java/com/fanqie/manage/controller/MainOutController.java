package com.fanqie.manage.controller;


import com.fanqie.commonutils.param.UserCheckParam;
import com.fanqie.commonutils.utils.R;
import com.fanqie.manage.entity.MainOut;
import com.fanqie.manage.service.MainOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 'ssm23javatest.main_all' is not BASE TABLE 前端控制器
 * </p>
 *  在这里实现 统计结果的两次确认。以及可能会实现的各种统计结果
 * @author fq
 * @since 2023-04-10
 */
@RestController
@RequestMapping("/manage/main-out")
public class MainOutController {

    @Autowired
    MainOutService service;

    /**
     * 返回所有已确认的用户工作量信息。
     * 搞个  名字  单位  工作量
     * @return
     */
    @GetMapping("AllUser")
    public R AllUser(){
        return service.AllUser();
    }


    /**
     * 获取用户的 课程信息，用户被二次确认。管理员确认
     * @return 用户的课程信息列表
     */
    @GetMapping("getAllToSure")
    public R getAllToSure(){
        return service.getAllToSure();
    }

    /**
     *  院长 获取用户的 课程信息，
     * @return 用户的课程信息列表
     */
    @PostMapping("getAllToSureByFaculty")
    public R getAllToSureByFaculty(@RequestBody UserCheckParam userCheckParam){
        return service.getAllToSureByFaculty(userCheckParam);
    }

    /**
     * 管理员 确认 /否认  课程信息  看看要不要分开两个实现
     * @param mainOut
     * @return
     */
    @PostMapping("upDataByMainOut")
    public R upDataByMainOut(@RequestBody MainOut mainOut){
        return service.upDataByMainOut(mainOut);
    }

    /**
     * 院长 确认 /否认  课程信息  看看要不要分开两个实现
     * @param mainOut
     * @return
     */
    @PostMapping("UserUpDataByMainOut")
    public R UserUpDataByMainOut(@RequestBody MainOut mainOut){
        return service.UserUpDataByMainOut(mainOut);
    }

    //todo：管理员不同意后，院长的修改功能





}

