package com.fanqie.manage.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author fq
 * @since 2023-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MainSimpleView implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 学期（时间）
     */
    private String term;

    /**
     * 教职工工号，也是登录的账号
     */
    private String userId;

    /**
     * 字符串，用于记录老师教授的班级数量
     */
    private String uniqueNumber;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 其他情况加的系数
     */
    private Long additional;

    /**
     * 课程教授的班级
     */
    private Long classRow;

    /**
     * 课程名称
     */
    private String teachName;

    /**
     * 理论学时
     */
    private String theoreticalHours;

    /**
     * 实践学时
     */
    private String practicalHours;


}
