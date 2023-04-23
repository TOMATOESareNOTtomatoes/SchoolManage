package com.fanqie.test.service;

import com.fanqie.test.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fq
 * @since 2023-03-14
 */
public interface UserService extends IService<User> {

    String login(User user);
}
