package com.fanqie.notice.mapper;

import com.fanqie.notice.entity.AnnouncementUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fq
 * @since 2023-04-18
 */
public interface AnnouncementUserMapper extends BaseMapper<AnnouncementUser> {
    AnnouncementUser selectOneByIdId(String announcementId, String userId);

    int updateByIdId(String announcementId, String userId);

}
