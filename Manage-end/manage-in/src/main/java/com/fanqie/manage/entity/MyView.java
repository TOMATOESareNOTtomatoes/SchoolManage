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
public class MyView implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 名字
     */
    private String userName;

    /**
     * 教职工工号，也是登录的账号
     */
    private String userId;

    /**
     * 院系
     */
    private String faculty;

    /**
     * 工作量结果
     */
    private String caseload;


}
