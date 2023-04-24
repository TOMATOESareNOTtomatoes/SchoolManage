package com.fanqie.notice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author fq
 * @since 2023-04-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Announcement implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自动增长的id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
     * 是否撤回，0已发布；1待发布；2撤销
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
     * 逻辑删除；0表示未删除；1表示删除
     */
    private Integer isDelete;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;


}
