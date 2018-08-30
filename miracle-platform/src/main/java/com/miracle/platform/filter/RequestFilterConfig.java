package com.miracle.platform.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 请求拦截器配置
 * @Created at 2018-08-29 22:28:38
 * @author Allen
 */
@Configuration
public class RequestFilterConfig extends WebMvcConfigurationSupport {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CrossFilterHandler()).addPathPatterns("/**").excludePathPatterns("/*", "/api/**");
        super.addInterceptors(registry);
    }
}
