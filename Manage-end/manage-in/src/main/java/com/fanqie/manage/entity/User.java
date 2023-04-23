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
 * @since 2023-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自动增长的主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 教职工工号，也是登录的账号
     */
    private String userId;

    /**
     * 名字
     */
    private String userName;

    /**
     * 手机号，用来更改密码等
     */
    private String number;

    /**
     * 密码
     */
    private String password;

    /**
     * 院系
     */
    private String faculty;

    /**
     * 逻辑删除；0表示未删除；1表示删除
     */
    private Integer isDelete;

    /**
     * 更新时间
     */
    private Date gmtModified;

    /**
     * 创建时间
     */
    private Date gmtCreate;


}
