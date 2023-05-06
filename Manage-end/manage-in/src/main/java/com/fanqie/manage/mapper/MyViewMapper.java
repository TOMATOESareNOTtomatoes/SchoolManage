package com.fanqie.manage.mapper;

import com.fanqie.manage.entity.MyView;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author fq
 * @since 2023-04-10
 */
@Mapper
@Repository
public interface MyViewMapper extends BaseMapper<MyView> {

}
