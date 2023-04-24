package com.fanqie;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 公告系统-启动类
 */

@MapperScan(basePackages = "com.fanqie.notice.mapper")
@SpringBootApplication
@EnableFeignClients     //微服务调用其他微服务需要的注解，没调用的可以去掉。
public class NoticeApplication {
    public static void main(String[] args) {
        SpringApplication.run(NoticeApplication.class,args);
    }
}
