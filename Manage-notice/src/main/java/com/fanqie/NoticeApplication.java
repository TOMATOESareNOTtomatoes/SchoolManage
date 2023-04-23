package com.fanqie;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 前端用户-教职工-启动类
 */

//@ComponentScan({"com.fanqie"})
@MapperScan(basePackages = "com.fanqie.notice.mapper")
@SpringBootApplication
public class NoticeApplication {
    public static void main(String[] args) {
        SpringApplication.run(NoticeApplication.class,args);
    }
}
