package com.fanqie.notice.service;

import com.fanqie.notice.entity.AnnouncementUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fq
 * @since 2023-04-18
 */
public interface AnnouncementUserService extends IService<AnnouncementUser> {
    //通过两个id查询记录。
    AnnouncementUser getOneByIdId(String announcementId, String userId);

    int updateByIdId(String announcementId, String userId);
}
