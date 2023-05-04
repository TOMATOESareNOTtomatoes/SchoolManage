package com.fanqie.manage.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用来实现管理员确认用户的 特殊情况的接收参数
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class soleAndUser {
    private String sole;
    private String userId;
    private String faculty;
}
