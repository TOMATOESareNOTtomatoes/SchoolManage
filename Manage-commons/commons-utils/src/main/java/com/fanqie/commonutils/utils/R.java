package com.fanqie.commonutils.utils;


import com.fanqie.commonutils.constants.ResultCode;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R {

   private Boolean success;
   private Integer code;
   private String message;
   private Map<String ,Object> data =new HashMap<String ,Object>();

   //构造方法私有
    private  R(){}

    public static R ok(){
        R r=new R();
        r.setCode(ResultCode.SUCCESS);
        r.setSuccess(true);
        r.setMessage("成功");
        return r;
    }

    public static R error(){
        R r=new R();
        r.setCode(ResultCode.ERROR);
        r.setSuccess(false);
        r.setMessage("失败");
        return r;
    }

    public static R lapsed(){
        R r=new R();
        r.setCode(ResultCode.LAPSED);
        r.setSuccess(false);
        r.setMessage("THE TOKEN HAS EXPIRED");
        return r;
    }

    //这样就可以用点来填写参数
    public  R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R code (Integer code){
        this.setCode(code);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R data(String key,Object value){
        this.data.put(key,value);
        return  this;

    }

    public R data(Map<String,Object> map){
        this.setData(map);
        return  this;

    }

}
