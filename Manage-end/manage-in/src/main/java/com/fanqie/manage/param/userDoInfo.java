package com.fanqie.manage.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用来返回用户的 工作量显示的
 */
@Data
@EqualsAndHashCode()
@Accessors(chain = true)
public class userDoInfo {

    /**
     * 随机生成的数，用于记录老师教授的班级数量   以及对应的课程信息
     */
    private String uniqueNumber;
    /**
     * 教职工工号，也是登录的账号
     */
    private String userId;
    /**
     * 名字
     */
    private String userName;
    /**
     *  班级名称
     */
    private String className;
    /**
     *  班级人数
     */
    private int classNumber;
    /**
     * 课程名称
     */
    private String teachName;

    /**
     * 理论学时
     */
    private int theoreticalHours;

    /**
     * 理论学时系数
     */
    private String coefficientL;

    /**
     * 实践学时
     */
    private int practicalHours;

    /**
     * 实践学时系数
     */
    private String coefficientS;

    /**
     * 是否教授新课
     */
    private double isFirst;

    /**
     * 是否双语授课
     */
    private double isDoubleLanguage;

    /**
     * 是否周末或晚上授课
     */
    private double isWeekend;

    /**
     * 合计
     */
    private double add;

    /**
     * 是否确认
     */
    private String isSure;

}
