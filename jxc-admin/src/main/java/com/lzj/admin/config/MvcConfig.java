package com.lzj.admin.config;

import com.lzj.admin.interceptors.NoLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 张明伟
 * @version 1.0
 **/
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public NoLoginInterceptor noLoginInterceptor() {
        return new NoLoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(noLoginInterceptor()).addPathPatterns("/**")//拦截所有的请求
        .excludePathPatterns("/index","/user/login",
                "/css/**","/js/**","/images/**","/error/**","/lib/**");//放行的
    }
}
