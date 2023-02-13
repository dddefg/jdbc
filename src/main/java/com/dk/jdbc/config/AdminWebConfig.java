package com.dk.jdbc.config;


import com.dk.jdbc.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 板凳宽宽
 * 定制SpringMVC的功能
 */
@Configuration
public class AdminWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")  //拦截全部
                .excludePathPatterns("/","/login","/registered","/registeredUser"
                ,"/css/**","/fonts/**","/images/**","/js/**","/sql"); //放行请求
    }
}
