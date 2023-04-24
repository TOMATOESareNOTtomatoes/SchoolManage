package com.fanqie.manage.mapper;

import com.fanqie.manage.entity.PrivilegesUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fq
 * @since 2023-04-03
 */
@Mapper
@Repository
public interface PrivilegesUserMapper extends BaseMapper<PrivilegesUser> {

}
