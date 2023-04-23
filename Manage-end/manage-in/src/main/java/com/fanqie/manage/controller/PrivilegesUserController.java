package com.fanqie.manage.controller;


import com.fanqie.commonutils.utils.R;
import com.fanqie.manage.service.PrivilegesUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fq
 * @since 2023-04-03
 */
@RestController
@RequestMapping("/manage/privileges-user")
public class PrivilegesUserController {

    @Autowired
    private PrivilegesUserService userService;

    /**
     * 通过 token 返回用户的权限等级
     * @param request
     * @return
     */
    @GetMapping("Info")
    public R Info(HttpServletRequest request){
        //根据request的头查询用户的id
        System.out.println("请求用户权限了！");
        return userService.getPrivilegesByToken(request);
    }

}

