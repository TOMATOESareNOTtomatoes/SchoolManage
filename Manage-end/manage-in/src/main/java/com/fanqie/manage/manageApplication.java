package com.fanqie.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan(basePackages = "com.fanqie.manage.mapper")
@SpringBootApplication
public class manageApplication {
        public static void main(String[] args) {
            SpringApplication.run(manageApplication.class,args);
        }
}
