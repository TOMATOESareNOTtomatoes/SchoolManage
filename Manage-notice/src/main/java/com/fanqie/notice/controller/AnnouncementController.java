package com.fanqie.notice.controller;


import com.fanqie.commonutils.param.UserCheckParam;
import com.fanqie.commonutils.utils.Permission;
import com.fanqie.commonutils.utils.PermissionEnum;
import com.fanqie.commonutils.utils.R;
import com.fanqie.notice.entity.Announcement;
import com.fanqie.notice.service.AnnouncementService;
import lombok.extern.log4j.Log4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fq
 * @since 2023-04-18
 */
@RestController
@RequestMapping("/notice/announcement")
@Log4j
public class AnnouncementController {

    @Autowired
    private AnnouncementService service;

    /**
     * 添加新的公告/通知
     * @param announcement
     * @return
     */
    @Permission({PermissionEnum.ADMIN,PermissionEnum.UserPlus})
    @PostMapping("addAnnouncement")
    public R addAnnouncement(@RequestBody Announcement announcement) {
        System.out.println(announcement);
        return service.addAnnouncement(announcement);
    }

    /**
     * 用户获取公告信息，通过用户的id ，和院系
     * @param userCheckParam
     * @return
     */
    @PostMapping("getAnnouncements")
    public R getAnnouncements(@RequestBody UserCheckParam userCheckParam){
        System.out.println("获取公告列表："+userCheckParam);
        return service.getAnnouncements(userCheckParam);
    }

    /**
     * 撤销公告
     * @param announcement
     * @return
     */
    @Delete("deleteAnnouncement")
    public R deleteAnnouncement(@RequestBody Announcement announcement){
        return service.deleteAnnouncement(announcement);
    }

    /**
     * 查看是否需要确认，接口调用，这创建了记录
     * @param announcement
     * @return
     */
    @PostMapping("isSureAnnouncement")
    public R isSureAnnouncement(@RequestBody Announcement announcement){
        return service.isSureAnnouncement(announcement);
    }

    /**
     * 确认 公告
     * @param announcement
     * @return
     */
    @PostMapping("SureAnnouncement")
    public R SureAnnouncement(@RequestBody Announcement announcement){
        return service.SureAnnouncement(announcement);
    }

    /**
     * 查看自己发布的公告。
     * @param userCheckParam
     * @return
     */
    @PostMapping("myAnnouncements")
    public R myAnnouncements(@RequestBody UserCheckParam userCheckParam){
        return service.myAnnouncements(userCheckParam);
    }
    //TODO：返回未确认公告名单等等   撤销公告，




}

