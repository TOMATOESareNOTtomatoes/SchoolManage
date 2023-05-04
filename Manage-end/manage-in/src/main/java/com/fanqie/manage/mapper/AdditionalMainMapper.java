package com.fanqie.manage.mapper;

import com.fanqie.manage.entity.AdditionalMain;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fanqie.manage.param.acSure;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fq
 * @since 2023-05-04
 */
@Mapper
@Repository
public interface AdditionalMainMapper extends BaseMapper<AdditionalMain> {

    List<acSure> getAdditionalSure();
    
}
