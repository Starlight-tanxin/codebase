package com.zyb.mini.mall.framework.life.cycle.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @author Created by 谭健 on 2019/10/28. 星期一. 14:29.
 * © All Rights Reserved.
 */
@Slf4j
@Configuration
public class RedisRunner {


    @Order(500)
    @Bean
    ApplicationRunner redisCheck(StringRedisTemplate stringRedisTemplate, Executor executor) {
        return args -> executor.execute(() -> Optional.ofNullable(stringRedisTemplate).ifPresent(template -> {
            String s = String.valueOf(UUID.randomUUID());
            template.opsForValue().set(s, s, 1, TimeUnit.SECONDS);
            String value = template.opsForValue().get(s);
            if (s.equals(value) && log.isInfoEnabled()) {
                log.info("------------------------------Redis 连接正常 {}  ----------------------------", s);
            }
        }));
    }
}
