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
public class CoefficientPractice implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自动增长的id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 班级数量
     */
    private Integer classNumber;

    /**
     * 系数。这个表是保存 全是实践学时的课，
     * 要是一个班级，查询班级为 一 获得系数
     * 要是两个班级，默认两班级一起上课，查询班级为 2 获得系数
     *
     * 要是三个班级，默认全是一个一个班上，查询班级为 1 和 3 获得系数
     * 要是4个班级，默认全是两个班两个班一起上的，查询班级为2 和 4 获得系数
     */
    private Double coefficient;

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
