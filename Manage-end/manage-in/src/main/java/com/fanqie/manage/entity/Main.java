package com.fanqie.manage.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * </p>
 * @author fq
 * @since 2023-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Main implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自动增长的id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 学期（时间）
     */
    private String term;

    /**
     * 教职工工号，也是登录的账号
     */
    private String userId;


    /**
     * 随机生成的数，用于记录老师教授的班级数量   以及对应的课程信息
     */
    private String uniqueNumber;

    /**
     * 其他情况加的系数
     */
    private String additional;

    /**
     * 教师是否已确认  1：教师确认。2.教师不同意
     */

    private Integer isSure;

    /**
     * 逻辑删除；0表示未删除；1表示删除
     */
    @TableField()
    private Integer isDelete;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

}
