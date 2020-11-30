package com.dome.mp.server.framework.life;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Disposable {

    private static Logger log = LoggerFactory.getLogger(Disposable.class);

    @Bean
    DisposableBean disposableBean(@Value("${spring.application.name}") String applicationName) {
        return () -> {
            if (log.isInfoEnabled()) {
                log.info(" -------------------  {} 服务关闭 ----------------------- ", applicationName);
            }
        };

    }
}
