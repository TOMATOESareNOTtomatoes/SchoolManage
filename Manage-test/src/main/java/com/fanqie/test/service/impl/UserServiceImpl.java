package com.fanqie.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanqie.commonsfront.exceptionhandler.FQException;
import com.fanqie.commonutils.utils.JwtUtils;
import com.fanqie.test.entity.User;
import com.fanqie.test.mapper.UserMapper;
import com.fanqie.test.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fq
 * @since 2023-03-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public String login(User user) {
        if(user.getUserName().equals("")){
            throw new FQException(2001,"登录失败");
        }
        String jwtToken= JwtUtils.getJwtToken(user.getUserName(),user.getPassword());
        //简单通过。。。。

        return jwtToken;
    }
}
