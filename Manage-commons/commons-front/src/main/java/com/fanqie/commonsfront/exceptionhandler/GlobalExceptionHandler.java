package com.fanqie.commonsfront.exceptionhandler;

import com.fanqie.commonutils.utils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//统一异常处理类
@ControllerAdvice //
public class GlobalExceptionHandler {

    //Exception.class  应该算是内置的异常处理类？ 参数指明要处理的异常类型，这个是指全部异常？
    @ExceptionHandler(Exception.class)
    @ResponseBody //为了返回数据
    public R error(Exception e){
        e.printStackTrace();//控制台输出错误信息
        return R.error().message("执行了全局异常处理。。。");
    }

    //特定异常 这个是经典的10/0错误
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理..");
    }

    //自定义异常
//    @ExceptionHandler(FQException.class)
//    @ResponseBody //为了返回数据
//    public R error(FQException e) {
//        e.printStackTrace();
//        return R.error().code(e.getCode()).message(e.getMsg());
//    }
}
