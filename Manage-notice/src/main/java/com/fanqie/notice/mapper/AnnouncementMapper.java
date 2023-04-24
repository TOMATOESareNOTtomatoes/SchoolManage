package com.fanqie.notice.mapper;

import com.fanqie.notice.entity.Announcement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fq
 * @since 2023-04-18
 */
@Mapper
@Repository
public interface AnnouncementMapper extends BaseMapper<Announcement> {

}
