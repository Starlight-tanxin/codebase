package com.zyb.mini.mall.config;

import com.zyb.mini.mall.framework.interceptor.LoginInterceptor;
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


    private final LoginInterceptor loginInterceptor;

    public InterceptorConfigurer(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(loginInterceptor)
                .addPathPatterns("/zyb/**")
                .excludePathPatterns("/zyb/login/**", "/zyb/open/**")
                .excludePathPatterns("/template/**")
                .excludePathPatterns("/zyb/shop/**")
                .excludePathPatterns("/zyb/resource/**")
                .excludePathPatterns("/zyb/goods/**")
                .excludePathPatterns("/zyb/common/**")
                .excludePathPatterns("/zyb/flie//**")
                .excludePathPatterns("/zyb/order/pay/callback");

        log.info("拦截器注册完成");
    }
}
