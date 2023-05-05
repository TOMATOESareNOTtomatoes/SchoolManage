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
 *        保存计算过程的系数的
 * </p>
 *
 * @author fq
 * @since 2023-05-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CalculationProcess implements Serializable {

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
     * 是否教授新课，记录系数。0则不是新课。
     */
    private Double isFirst;

    /**
     * 是否双语授课
     */
    private Double isDoubleLanguage;

    /**
     * 是否周末或晚上授课
     */
    private Double isWeekend;

    /**
     * 理论学时系数
     */
    private String coefficientTheoretical;

    /**
     * 实践学时系数
     */
    private String coefficientPractical;

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
