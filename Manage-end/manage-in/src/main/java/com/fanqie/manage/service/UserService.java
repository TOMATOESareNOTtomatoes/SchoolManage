package com.fanqie.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fanqie.commonutils.utils.R;
import com.fanqie.manage.entity.User;
import com.fanqie.manage.param.userInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fq
 * @since 2023-03-30
 */
public interface UserService extends IService<User> {

    /**
     * 登录 TODO：token校验接口
     * @param user
     * @return
     */
    R login(User user);

    /**
     * 查询所有教师的信息
     * @return
     */
    R userInfo();

    R getUserInfoById(String memberid);

    R update(User user);

    R logout();

    R getAllUser();

    R getUserList();

    R updateUser(userInfo user);

    R deleteUser(User user);

    User getUserByUserId(String userId);

    /**
     * 管理员添加用户接口
     * @param info
     * @return
     */
    R AddUser(userInfo info);
}
