package com.fanqie.commonutils.param;

import lombok.Data;

/*
*接收前端参数的param  用户 注册 需要
* 使用   jsr   注解校验参数
* @NotEmpty 集合类型 长度不能为 0
 */
@Data
public class UserCheckParam {

//    @NotBlank//字符串 不能为null和“”
    private String userId;//名字要等于前端传递的json key 名称一样才行
    private String faculty;

}
