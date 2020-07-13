package com.tx.mq.manage.config;

import com.tx.mq.manage.listen.InitThreadLocalPoolListen;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ServletListenerRegistrationConfig {

    /**
     * 注册自定义的Bean
     * 并且设置监听器，该监听器初始化线程池
     *
     * @return
     */
    @Bean
    public ServletListenerRegistrationBean registrationBean() {
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new InitThreadLocalPoolListen());
        return servletListenerRegistrationBean;
    }
}
