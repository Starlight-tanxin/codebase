package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * <p>description</p>
 *
 * @author TanXin
 * @date 2020/11/10 17:25
 */
@SpringBootApplication
@MapperScan("com.navinfo.truck.**.**.dao")
public class OldApplication {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }

    public static void main(String[] args) {
        SpringApplication.run(OldApplication.class, args);
    }
}
