package com.fanqie.manage.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用于查询未确认的特殊情况的，用来给教务处确认用的映射类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class acSure {
    private String userName;
    private String additionalId;

    private double isSure;

    private double isFirst;
    private double isDoubleLanguage;
    private double isWeekend;
    private String teachName;
    private String className;
}