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
 * @since 2023-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MainOut implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 自动增长的id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 特殊情况id
     */
    private String additional;

    /**
     * 已经确认
     */
    private String isSure;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 手机号
     */
    private String number;

    /**
     * 学院
     */
    private String faculty;

    /**
     * 班级名称（全部）
     */
    private String className;

    /**
     * 班级人数
     */
    private String classNumber;

    /**
     * 班级数量
     */
    private Integer classRow;

    /**
     * 课程名称（全部）
     */
    private String teachName;

    /**
     * 理论学时
     */
    private Integer theoreticalHours;

    /**
     * 实践学时
     */
    private Integer practicalHours;

    /**
     * 计算类型
     */
    private String teachType;

    /**
     * 工作量计算过程 id
     */
    private String caseloadProcessId;

    /**
     * 工作量结果
     */
    private String caseload;

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

    /**
     * 学期（时间）
     */
    private String term;

}
