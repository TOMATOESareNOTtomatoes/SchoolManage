package com.fanqie.manage.entity;

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
 * @since 2023-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MainAllView implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 随机生成的数，用于记录老师教授的班级数量   以及对应的课程信息
     */
    private String uniqueNumber;

    /**
     * 学期（时间）
     */
    private String term;

    /**
     * 教职工工号，也是登录的账号
     */
    private String userId;

    /**
     * 其他情况加的系数
     */
    private String additional;

    /**
     * 是否教师已确认
     */
    private Integer isSure;

    /**
     * 名字
     */
    private String userName;

    /**
     * 手机号，用来更改密码等
     */
    private String number;

    /**
     * 院系
     */
    private String faculty;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 班级人数
     */
    private int classNumber;

    /**
     * 班级数量
     */
    private int classRow;

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

    /**
     * 手动选择统计的类型
     */
    private String teachType;

}
