package com.fanqie.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fanqie.manage.entity.User;
import com.fanqie.manage.param.userInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fq
 * @since 2023-03-30
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    List<User> getAllUser();
    List<userInfo> getUserList();
    int updateByUerId(User user);

    User getUserByUserId(String userId);

}
