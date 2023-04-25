package com.fanqie.notice.param;

import com.fanqie.notice.entity.Announcement;
import lombok.Data;

import java.util.Date;

/**
 * 公告的返回对象，多了几个属性是否确认。
 */
@Data
public class announcementsAddSure {

    /**
     * 公告的唯一id
     */
    private String announcementId;

    /**
     * 发布人id
     */
    private String userId;

    /**
     * 发布人姓名
     */
    private String userName;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 是否需要确认，0需要确认；1不需要确认
     */
    private Integer isNeedSure;

    /**
     * 发布时间
     */
    private Date pushTime;

    /**
     * 是否撤回，0已发布 ；1待发布 ；2撤销
     */
    private Integer isCancel;

    /**
     * 是否全体，0是全体，1不是全体
     */
    private Integer isAll;

    /**
     * 学院名称，部分通知使用的，偷懒了，直接放这里了
     */
    private String faculty;

    /**
     *  是否已经确认  未确认 0， 确认 1
     */
    private int hadSure;

    public announcementsAddSure(Announcement a) {
        this.announcementId=a.getAnnouncementId();
        this.content=a.getContent();
        this.faculty=a.getFaculty();
        this.isNeedSure=a.getIsNeedSure();
        this.isAll=a.getIsAll();
        this.title=a.getTitle();
        this.userId=a.getUserId();
        this.userName=a.getUserName();
        this.isCancel=a.getIsCancel();
        this.pushTime=a.getPushTime();
    }
}
