package com.dome.mp.server.framework.life;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Optional;

@Slf4j
@Configuration
public class StartRunner {

    @Order(9999)
    @Bean
    ApplicationRunner startRunnerDo(@Value("${spring.application.name}") String name) {
        return args -> Optional.ofNullable(name).ifPresent(s -> {
            if (log.isInfoEnabled()) {
                log.info("------------------------------服务 {}  启动成功 ----------------------------", s);
            }
        });
    }


}
