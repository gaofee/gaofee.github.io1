package com.gaofei.config;

import com.gaofei.intercepters.LoginIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : gaofee
 * @date : 15:38 2021/4/12
 * @码云地址 : https://gitee.com/itgaofee
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginIntercepter())
                .addPathPatterns("/**")
                .excludePathPatterns("/**/*.ftl")
                .excludePathPatterns("/**/*.html");

    }

}
