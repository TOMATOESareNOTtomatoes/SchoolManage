package com.fanqie.notice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanqie.commonutils.param.UserCheckParam;
import com.fanqie.commonutils.utils.R;
import com.fanqie.commonutils.utils.UUIDStringUtils;
import com.fanqie.notice.entity.Announcement;
import com.fanqie.notice.entity.AnnouncementUser;
import com.fanqie.notice.mapper.AnnouncementMapper;
import com.fanqie.notice.service.AnnouncementService;
import com.fanqie.notice.service.AnnouncementUserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fq
 * @since 2023-04-18
 */
@Service
@Log4j
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement> implements AnnouncementService {

    @Autowired
    private AnnouncementMapper mapper;

    @Autowired
    private AnnouncementUserService announcementUserService;

    // 创建 PrivilegesUserServiceImpl 类的实例
//PrivilegesUserServiceImpl privilegesUserService = new PrivilegesUserServiceImpl();

    /**
     * 添加公告
     * 查看是否需要确认，需要，在另一个表新建记录
     * 然后添加 记录
     *
     * @param announcement
     * @return
     */
    @Override
    public R addAnnouncement(Announcement announcement) {
        Announcement announcement1 = new Announcement();
        if (announcement.getIsNeedSure() == 0) {
            //需要确认
            if (announcement.getIsAll() == 0) {
                //全体
                //TODO:全体添加记录，算了，换一种方式
                announcement1.setFaculty("null");
                System.out.println("发布了需要全体确认的通知！");
            } else {
                announcement1.setFaculty(announcement.getFaculty());
                System.out.println("发布了需要院系确认的通知");
            }
        }

        String id = UUIDStringUtils.randomUUID();

        announcement1.setAnnouncementId(id);
        announcement1.setTitle(announcement.getTitle());
        announcement1.setContent(announcement.getContent());
        announcement1.setUserId(announcement.getUserId());
        announcement1.setUserName(announcement.getUserName());
        announcement1.setIsAll(announcement.getIsAll());
        announcement1.setIsNeedSure(announcement.getIsNeedSure());
        announcement1.setPushTime(new Date());//TODO:时间实现用户指定，发布的时间

        announcement1.setIsCancel(0);//TODO:应该用注解实现的，现在先写上
        announcement1.setIsDelete(0);

        int i = mapper.insert(announcement1);
        if (i == 1) {
            return R.ok().message("成功发布公告！！");
        }
        return R.error().message("失败发现未知错误");
    }

    /**
     * 用户查看公告/通知
     *
     * @param user
     * @return
     */
    @Override
    public R getAnnouncements(UserCheckParam user) {
        QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_all", 0)
                .or()
                .eq("faculty", user.getFaculty())
                .eq("is_cancel", 0)
                .eq("is_delete", 0)
                .orderByDesc("push_time");
        List<Announcement> announcements = mapper.selectList(queryWrapper);

        if (announcements.isEmpty()) {
            return R.ok().message("没有公告！！！！！！");
        }
        return R.ok().data("announcements", announcements);
    }

    @Override
    public R deleteAnnouncement(Announcement announcement) {

        return null;
    }

    /**
     * 查看是否需要确认
     * 通过announcement查看是否需要确认，需要：查看announcementUser是否有记录，没有则创建。
     *
     * @param announcement
     * @return
     */
    @Override
    public R isSureAnnouncement(Announcement announcement) {
        //注意这里userId不是发布的人的，
        QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("announcement_id", announcement.getAnnouncementId())
                .eq("is_need_sure", 0)
                .eq("is_cancel", 0)
                .eq("is_delete", 0);
        Announcement announcementResult = mapper.selectOne(queryWrapper);
        //需要确认并且未被删除和撤销
        if (announcementResult != null) {
            AnnouncementUser announcementUse = announcementUserService.getOneByIdId(announcement.getAnnouncementId(), announcement.getUserId());
            if (announcementUse == null) {
                //AnnouncementUse表没有记录，则创建记录，将确认值设置为未确认
                AnnouncementUser newAnnouncementUse = new AnnouncementUser();
                newAnnouncementUse.setAnnouncementId(announcement.getAnnouncementId());
                newAnnouncementUse.setUserId(announcement.getUserId());
                newAnnouncementUse.setIsSure(0);
                if (announcementUserService.save(newAnnouncementUse)) {
                    return R.ok().data("unSure", newAnnouncementUse)
                            .message("说明是第一次加载，并没有确认");
                } else {
                    return R.error().message("创建用户确认公告失败！");
                }
            } else {
                return R.ok().data("unSure", announcementUse);
            }
        }
        return R.error().message("该公告不需要确认！！");
    }

    @Override
    public R SureAnnouncement(Announcement announcement) {
        int i = announcementUserService.updateByIdId(announcement.getAnnouncementId(), announcement.getUserId());
        if (i == 1) {
            return R.ok().message("成功确认公告");
        }
        return R.error().message("用户确认公告遇到错误！！");
    }

    /**
     * 查看自己发布的 公告。
     *
     * @param userCheckParam
     * @return
     */
    @Override
    public R myAnnouncements(UserCheckParam userCheckParam) {
        QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userCheckParam.getUserId())
                .eq("is_delete", 0)
                .orderByDesc("push_time");
        List<Announcement> announcements = mapper.selectList(queryWrapper);
        if (announcements.isEmpty()) {
            return R.ok().message("没有公告！！！！！！");
        }
        return R.ok().data("announcements", announcements);
    }
}
