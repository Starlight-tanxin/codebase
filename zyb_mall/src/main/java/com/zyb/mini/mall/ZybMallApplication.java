package com.zyb.mini.mall;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author: Tx
 * @date: 2019/10/26
 */
@Slf4j
@MapperScan("com.zyb.mini.mall.dao")
@SpringBootApplication
public class ZybMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZybMallApplication.class, args);
    }


    /**
     * 开启分页插件
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
