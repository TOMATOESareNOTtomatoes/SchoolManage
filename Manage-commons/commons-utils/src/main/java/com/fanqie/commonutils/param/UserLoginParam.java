package com.fanqie.commonutils.param;


import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录 参数实体 校验需要的
 */

@Data
public class UserLoginParam {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
}
