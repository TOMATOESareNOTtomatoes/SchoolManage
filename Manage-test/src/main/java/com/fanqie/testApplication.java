package com.fanqie;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.fanqie.test.mapper")
@SpringBootApplication
public class testApplication {
    public static void main(String[] args) {
        SpringApplication.run(testApplication.class,args);
    }
}
