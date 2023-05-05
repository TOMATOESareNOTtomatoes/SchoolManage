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
public class Teach implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自动增长的id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 工具生成的唯一id，放在工作量统计表里对应用的
     */
    private String teachId;

    /**
     * 课程名称
     */
    private String teachName;

    /**
     * 课程学生的人数
     */
    private int teachNumber;

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
