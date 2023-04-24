package com.fanqie.manage.controller;


import com.fanqie.commonutils.utils.R;
import com.fanqie.manage.service.MainOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 'ssm23javatest.main_all' is not BASE TABLE 前端控制器
 * </p>
 *
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
     * @return
     */
    @GetMapping("AllUser")
    public R AllUser(){
        return service.AllUser();
    }

}

