package com.fanqie.notice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fanqie.notice.entity.Announcement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
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

    @Update("UPDATE announcement SET title=#{title}, content=#{content}, is_need_sure=#{isNeedSure}," +
            " is_cancel=#{isCancel}, is_all=#{isAll} WHERE announcement_id=#{announcementId}")
    int upDateByAnnouncementId(Announcement announcement);

}
