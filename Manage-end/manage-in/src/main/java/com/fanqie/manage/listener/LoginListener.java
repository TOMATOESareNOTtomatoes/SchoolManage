package com.fanqie.manage.listener;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 登录检查拦截器
 */
@Configuration
public class LoginListener implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor())
                .excludePathPatterns("/**/user/login") // 登录页面不需要检查 token
                .excludePathPatterns("/manage/privileges-user/**") // 权限 请求不拦截
                //.excludePathPatterns("/manage/main/**")
                .addPathPatterns("/**"); //
    }
}