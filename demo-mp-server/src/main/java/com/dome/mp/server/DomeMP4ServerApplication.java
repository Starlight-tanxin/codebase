package com.dome.mp.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.dome.mp.server.dao")
@SpringBootApplication
public class DomeMP4ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DomeMP4ServerApplication.class, args);
    }
}
