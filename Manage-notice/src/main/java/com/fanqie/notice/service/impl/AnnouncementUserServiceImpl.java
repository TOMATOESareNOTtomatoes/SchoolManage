package com.fanqie.notice.service.impl;

import com.fanqie.notice.entity.AnnouncementUser;
import com.fanqie.notice.mapper.AnnouncementUserMapper;
import com.fanqie.notice.service.AnnouncementUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fq
 * @since 2023-04-18
 */
@Service
public class AnnouncementUserServiceImpl extends ServiceImpl<AnnouncementUserMapper, AnnouncementUser> implements AnnouncementUserService {

    @Autowired
    AnnouncementUserMapper mapper;
    @Override
    public AnnouncementUser getOneByIdId(String announcementId, String userId) {
        return mapper.selectOneByIdId(announcementId,userId);
    }

    @Override
    public int updateByIdId(String announcementId, String userId) {
        return mapper.updateByIdId(announcementId,userId);
    }
}
