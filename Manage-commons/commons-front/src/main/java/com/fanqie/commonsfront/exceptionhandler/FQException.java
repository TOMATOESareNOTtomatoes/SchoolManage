package com.fanqie.commonsfront.exceptionhandler;

//自定义异常类

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  //get set
@AllArgsConstructor //全参构造方法
@NoArgsConstructor  //无参构造方法
public class FQException extends RuntimeException{//继承这个类才行
    private Integer code;//状态吗
    private String msg;//异常信息
}
