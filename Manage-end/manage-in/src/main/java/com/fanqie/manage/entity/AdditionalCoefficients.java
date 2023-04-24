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
public class AdditionalCoefficients implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自动增长的id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 唯一id
     */
    private String additionalId;

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
     * 院长是否确认，0未确认 1确认  2不同意
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

    public AdditionalCoefficients(int IsFirst, int IsDoubleLanguage, int IsWeekend) {
        this.setIsFirst(IsFirst);
        this.setIsDoubleLanguage(IsDoubleLanguage);
        this.setIsWeekend(IsWeekend);
    }
    public AdditionalCoefficients(){    }

}
