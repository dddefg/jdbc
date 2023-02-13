package com.dk.jdbc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 板凳宽宽
 * MVC配置文件
 */
@Configuration
public class MvcConfig {

    /**
     * 文件上传解析器
     * @return
     */
    @Bean
    public CommonsMultipartResolver fileUpload(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        return multipartResolver;
    }
}
