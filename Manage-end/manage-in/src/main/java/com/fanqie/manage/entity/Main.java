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
    private String additionalId;

    /**
     * 是否教师已确认，0或者null就是没有确认， 1为教师个人确认 （这是不存在修改的情况）， 5为院长确认， 9为管理员确认
     *   1  跟  9  都是可以直接统计的
     */
    private Integer isSure;

    /**
     * 工作量计算过程 id  对应表 calculation_process 的 additional_id
     */
    private String caseloadProcessId;

    /**
     * 工作量结果
     */
    private String caseload;

    /**
     * 院系同意人的id
     */
    private String user_plus_id;

    /**
     * 管理员同意人的id
     */
    private String admin_id;

    /**
     *   备注
     */
    private String info;

    /**
     * 添加类型：null或者是0，就是管理员导入的数据。1是用户提交修改的。2是用户自己添加的
     */
    private String addType;

    /**
     * 修改的话，保留之前的  unique_number
     */
    private String beforeId;

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
