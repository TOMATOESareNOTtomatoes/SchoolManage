package com.fanqie.notice.service;

import com.fanqie.commonutils.param.UserCheckParam;
import com.fanqie.commonutils.utils.R;
import com.fanqie.notice.entity.Announcement;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fq
 * @since 2023-04-18
 */
public interface AnnouncementService extends IService<Announcement> {

    R addAnnouncement(Announcement announcement);

    R getAnnouncements(UserCheckParam user);

    R deleteAnnouncement(Announcement announcement);

    R isSureAnnouncement(Announcement announcement);

    R SureAnnouncement(Announcement announcement);

    R myAnnouncements(UserCheckParam userCheckParam);
}
