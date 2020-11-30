package com.dome.mp.server.config;

import com.dome.mp.server.web.interceptor.DefaultInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 接口拦截配置
 *
 * @author tanxin
 * @date 2019/08/09
 */
@Slf4j
@Configuration
public class InterceptorConfigurer implements WebMvcConfigurer {


    private final DefaultInterceptor defaultInterceptor;

    public InterceptorConfigurer(DefaultInterceptor defaultInterceptor) {
        this.defaultInterceptor = defaultInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(defaultInterceptor)
                .excludePathPatterns("/**/**");
        log.info("拦截器注册完成");
    }
}
