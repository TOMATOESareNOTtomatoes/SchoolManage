package com.fanqie.manage.mapper;

import com.fanqie.manage.entity.Main;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author fq
 * @since 2023-03-30
 */
@Mapper
@Repository
public interface MainMapper extends BaseMapper<Main> {

    /**
     * 通过唯一的字符串查询教师的 课程 信息记录。
     *
     * @param uniqueNumber
     * @return  全部信息，表里面的
     */
    Main selectByUniqueNumber(String uniqueNumber);
}
