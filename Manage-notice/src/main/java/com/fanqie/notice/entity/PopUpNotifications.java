package com.fanqie.notice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 'ssm23javatest.my_view' is not BASE TABLE
 * </p>
 *
 * @author fq
 * @since 2023-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PopUpNotifications implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自动增长的id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 发布人的id
     */
    private String userPushId;

    /**
     * 接收人的id
     */
    private String userId;

    /**
     * 内容
     */
    private String content;

    /**
     * 是否已经确认过了（是否显示过了
     */
    private Integer isSure;

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
