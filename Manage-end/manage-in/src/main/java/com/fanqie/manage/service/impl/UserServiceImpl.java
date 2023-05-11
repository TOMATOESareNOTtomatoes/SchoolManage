package com.fanqie.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanqie.commonutils.constants.UserConstants;
import com.fanqie.commonutils.utils.JwtUtils;
import com.fanqie.commonutils.utils.MD5Util;
import com.fanqie.commonutils.utils.R;
import com.fanqie.manage.entity.PrivilegesUser;
import com.fanqie.manage.entity.User;
import com.fanqie.manage.mapper.UserMapper;
import com.fanqie.manage.param.userInfo;
import com.fanqie.manage.service.PrivilegesService;
import com.fanqie.manage.service.PrivilegesUserService;
import com.fanqie.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    private PrivilegesService privilegesService;
    @Autowired
    private PrivilegesUserService privilegesUserService;

    /**
     * 退出登录
     *
     * @return
     */
    @Override
    public R logout() {
        return null;
    }

    @Override
    public R getAllUser() {
        List<User> uList = userMapper.getAllUser();
        System.out.println("聯合查詢：" + uList.get(1).getUserId());
        return null;
    }

    //查询所有用户的信息，
    @Override
    public R getUserList() {
        List<userInfo> userList = userMapper.getUserList();
        //todo:***可以手写sql语句，而不是后台将密码制空
        for (userInfo user : userList) {
            //System.out.println("用户姓名：" + user.getUserName());
            user.setPassword(null);
        }
        return R.ok().data("userInfo", userList);

    }

    /**
     * 更新 /修改用户信息，分权限修改和用户基本信息修改
     *
     * @param userinfo
     * @return
     */
    @Override
    public R updateUser(userInfo userinfo) {
        boolean type=false; //判断权限是否修改的。ture：成功修改权限  false：没有修改权限
        User user = new User();
        user.setUserId(userinfo.getUserId());
        user.setUserName(userinfo.getUserName());
        user.setFaculty(userinfo.getFaculty());
        System.out.println("新密码："+userinfo.getPassword());

        String newPwd = MD5Util.encode(userinfo.getPassword() + UserConstants.USER_SLAT);
        //密码加密存储
        user.setPassword(newPwd);
        user.setNumber(userinfo.getNumber());

        //根据用户的权限名，查询对应权限的id
        System.out.println("权限："+userinfo.getPermissions());
        String privilegesId = privilegesService.getPrivilegesIdByName(userinfo.getPermissions());
        System.out.println("权限id："+privilegesId);
        if (privilegesId == null) {
            //说明用户没有登录过，没有权限信息。
            return R.error().message("用户权限信息查询出错");
        }
        PrivilegesUser privilegesUser = new PrivilegesUser();
        privilegesUser.setUserId(userinfo.getUserId());
        privilegesUser.setPrivilegesId(privilegesId);
        type = privilegesUserService.Update(privilegesUser);
        //TODO:update里应该加个查询，这样不修改过的就能区分出来
        //跟新用户信息
        int i = userMapper.updateByUerId(user);
        //System.out.println("updateUser！"+type+"___"+i);
        if (i == 1) {
            if(type){
                return R.ok().message("成功修改用户信息和权限！");
            }
            return R.ok().message("成功修改用户信息！");
        }else {
            if(type){
                return R.ok().message("成功修改权限！");
            }
            return R.error().message("没有修改信息！");
        }
    }

    /**
     * 通过调用userMapper的方法，实现逻辑删除用户
     *
     * @param user
     * @return
     */
    @Override
    public R deleteUser(User user) {
        user.setIsDelete(1);
        //todo:先查询一下是否已经删除，不然这样的删除过的还能继续删除。或者自己写sql语句。算了
        int i = userMapper.update(user, new UpdateWrapper<User>().eq("user_id", user.getUserId()));
        if (i == 1) {
            return R.ok().message("成功删除用户！");
        } else {
            return R.error().message("删除用户失败！");
        }
    }

    @Override
    public User getUserByUserId(String userId) {
        return userMapper.getUserByUserId(userId);
    }

    /**
     * 管理员添加用户接口
     *
     * @param info
     * @return
     */
    @Override
    public R AddUser(userInfo info) {
        QueryWrapper<User> qwu=new QueryWrapper<User>();
        qwu.eq("user_id",info.getUserId());
        User user1 = userMapper.selectOne(qwu);
        if(user1!=null){
            return R.error().message("该用户已存在！");
        }
        User user=new User();
        user.setUserId(info.getUserId());
        user.setUserName(info.getUserName());
        user.setNumber(info.getNumber());
        user.setFaculty(info.getFaculty());
        String newPwd = MD5Util.encode(info.getPassword() + UserConstants.USER_SLAT);
        user.setPassword(newPwd);

        user.setIsDelete(0);
        user.setGmtCreate(new Date());
        user.setGmtModified(new Date());

        int i = userMapper.insert(user);
        if(i!=1){
            return R.error().message("添加用户失败！");
        }
        String privilegesId = privilegesService.getPrivilegesIdByName(info.getPermissions());
        if(privilegesId==null){
            return R.error().message("查询用户权限信息失败！");
        }
        PrivilegesUser privilegesUser=new PrivilegesUser();
        privilegesUser.setPrivilegesId(privilegesId);
        privilegesUser.setUserId(info.getUserId());
        //添加用户权限信息
        int ipus = privilegesUserService.addPrivilegesUser(privilegesUser);
        if(ipus!=1){
            return R.error().message("添加用户权限信息失败！");
        }
        //System.out.println("新添加用户的权限是：");
        //System.out.println(info.getPermissions());
        return R.ok().message("添加用户成功！");
    }

    /**
     * 登录方法
     *
     * @param user
     * @return
     */
    @Override
    public R login(User user) {
        //检查参数 TODO:做一些校验
        String userId = user.getUserName();
        String password = user.getPassword();
        //查数据库，校验账号
        String newPwd = MD5Util.encode(password + UserConstants.USER_SLAT);
        //System.out.println("密码："+newPwd);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("password", newPwd);
        User user1 = userMapper.selectOne(queryWrapper);
        if (user1 == null) {
            return R.error().message("账号或者密码错误");
        }
        //token带上用户权限
        String userP = privilegesUserService.getPrivilegesByUserId(userId);
        String token = JwtUtils.getJwtToken(userId, userP);
//        todo:将用户数据封装成对象返回。其实可以直接返回对象的，可是这样信息太多，先这样吧。
        return R.ok().data("token", token).data("url", "/home").data("userName",user1.getUserName())
                .data("userId",user1.getUserId()).data("faculty",user1.getFaculty());
    }

    /**
     * 查询所有教师的信息
     *
     * @return
     */
    @Override
    public R userInfo() {
        return R.ok();
    }

    /**
     * 通过用户的id 返回用户信息
     *
     * @param memberid
     * @return
     */

    @Override
    public R getUserInfoById(String memberid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", memberid);
        User user = userMapper.selectOne(queryWrapper);
        if (user != null) {
            user.setPassword("");
            return R.ok().data("userInfo", user);
        } else return R.error().message("根据用户id，查询用户信息失败");
    }

    //没用了-----------------------------
    @Override
    public R update(User user) {
        int i = userMapper.updateById(user);
        if (i == 1) {
            return R.ok().message("更新成功！");
        }
        return R.error();
    }

    /**
     * 教师信息的管理，这个是查询所有信息
     *
     * @return
     */
    public R userManage() {
        return R.ok();
    }
}
