package com.zyb.mini.mall.framework.life.cycle.function;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Optional;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/10/27 星期日 11:54.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */
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
