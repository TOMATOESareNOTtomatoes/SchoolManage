package com.fanqie.manage.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户和权限的映射类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class userInfo implements Serializable {

    /**
     * 用户的id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 手机号
     */
    private String number;

    /**
     * 密码
     */
    private String password;

    /**
     * 学院
     */
    private String faculty;

    /**
     * 用户的权限
     */
    private String permissions;

}
    
