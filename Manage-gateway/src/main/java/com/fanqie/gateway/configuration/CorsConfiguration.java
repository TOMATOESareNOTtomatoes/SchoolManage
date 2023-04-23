//package com.fanqie.gateway.configuration;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.config.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsConfiguration implements WebMvcConfigurer {
//
//    /**
//     * 解决跨域问题 并不行
//     */
//
//    public void addCorsMappings(CorsRegistry registry) {
//        // 允许跨域访问的路径和方法
//        registry.addMapping("/**")
//                .allowedOrigins("*") // 设置允许跨域访问的前端域名或 IP 地址
//                .allowCredentials(true) // 是否允许发送 Cookie
//                .allowedMethods("*"); // 允许任何方法（GET、POST等）
//    }
//}
