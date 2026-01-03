package com.boot.techsupportscheduler.config;

import com.boot.techsupportscheduler.support.interceptor.LoginInterceptor;
import com.boot.techsupportscheduler.support.interceptor.RoleActionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/support/**");

        registry.addInterceptor(new RoleActionInterceptor())
                .addPathPatterns("/support/**");
    }
}

