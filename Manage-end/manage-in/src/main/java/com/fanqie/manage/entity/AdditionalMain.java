package com.fanqie.manage.entity;

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
 * @since 2023-05-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AdditionalMain implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自动增长的id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 唯一id  是保存在main表的 对应
     */
    private String additionalId;

    /**
     * 对应的系数情况的id  对应系数的 id
     */
    private String additionalCoefficientsId;

    /**
     * 院系同意人id
     */
    private String userPlusId;

    /**
     * 管理员同意id
     */
    private String adminId;

    /**
     * 院长和管理员是否确认，自己申请则是 1  院长确认则值为5；管理员确认了则值为9；其他情况的话，应该是被删除了。
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
