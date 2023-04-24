package com.fanqie.manage.mapper;

import com.fanqie.manage.entity.MainOut;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 'ssm23javatest.main_all' is not BASE TABLE Mapper 接口
 * </p>
 *
 * @author fq
 * @since 2023-04-10
 */
@Mapper
@Repository
public interface MainOutMapper extends BaseMapper<MainOut> {

    int Insert(MainOut mainOut);
}
