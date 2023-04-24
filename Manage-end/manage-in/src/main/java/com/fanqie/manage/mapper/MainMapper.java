package com.fanqie.manage.mapper;

import com.fanqie.manage.entity.Main;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
public interface MainMapper extends BaseMapper<Main> {

    Main selectByUniqueNumber(String uniqueNumber);
}
