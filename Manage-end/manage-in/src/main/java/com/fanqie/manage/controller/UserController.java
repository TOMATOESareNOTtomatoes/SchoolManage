package com.fanqie.manage.controller;

import com.fanqie.commonutils.utils.JwtUtils;
import com.fanqie.commonutils.utils.Permission;
import com.fanqie.commonutils.utils.PermissionEnum;
import com.fanqie.commonutils.utils.R;
import com.fanqie.manage.entity.User;
import com.fanqie.manage.param.userInfo;
import com.fanqie.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fq
 * @since 2023-03-30
 */
@RestController
@RequestMapping("/manage/user")
public class UserController {
    @Autowired
    private UserService service;

    /**
     * 用户登录接口
     *
     * @param user
     * @return token
     */

    @PostMapping("login")
    public R login(@RequestBody User user) {
        System.out.print("user:" + user.getUserName() +"密码："+ user.getPassword());
        if (user.getUserId().equals("") || user.getPassword().equals("")) {
            return R.error().message("参数有误，请重新输入");
        }
        return service.login(user);
    }

    @GetMapping("logout")
    public R logout(HttpServletRequest request) {
        //清理Session中保存的当前登录员工的id
        request.getSession().removeAttribute("token");
        return R.ok().message("退出登录成功！");
        //return service.logout();
    }

    /**
     * 根据用户的token返回用户信息
     *
     * @param request
     * @return
     */
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        // 根据request的头查询用户的id
        String memberid = JwtUtils.getMemberIdByJwtToken(request);
        return service.getUserInfoById(memberid);
    }

    /**
     * 查询所有教师的信息
     *
     * @return
     */
    @Permission({PermissionEnum.ADMIN})
    @GetMapping("userInfo")
    public R userInfo() {
//        return service.getAllUser();
        return service.getUserList();
    }

    /**
     * 更新/修改用户表信息
     * @param user
     * @return
     */
    @Permission({PermissionEnum.ADMIN})
    @PostMapping("updateUser")
    public R updateUser(@RequestBody userInfo user) {
        System.out.println(user);
        return service.updateUser(user);
    }

    /**
     * 删除用户的接口
     *
     * @param user
     * @return
     */
    @Permission({PermissionEnum.ADMIN})
    @DeleteMapping("deleteUser")
    public R deleteUser(@RequestBody User user) {
        System.out.println(user);
        return service.deleteUser(user);
    }

}
