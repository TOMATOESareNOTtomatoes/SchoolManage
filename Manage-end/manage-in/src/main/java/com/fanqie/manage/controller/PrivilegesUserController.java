package com.fanqie.manage.controller;


import com.fanqie.commonutils.utils.R;
import com.fanqie.manage.service.PrivilegesUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    //@CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("Info")
    public R Info(HttpServletRequest request){
        //根据request的头查询用户的id
        System.out.println("请求用户权限了！");
        return userService.getPrivilegesByToken(request);
    }

    /**
     * 通过用户的 id，返回用户的 权限
     * @param id
     * @return 不是统一结果封装类，需要看到时候优化吧。
     */
    @PostMapping("getPrivilegesById/{id}")
    public String getPrivilegesById(@PathVariable("id") String id){
        System.out.println("请求用户权限成功！通过用户的Id");
            return userService.getPrivilegesByUserId(id);
    }

}

