package com.dome.mp.server.framework.startset;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DefaultStartSet implements CommandLineRunner, Ordered {

    @Override
    public void run(String... args) throws Exception {
        log.info("com.xh.server.framework.startset.DefaultStartSet : 默认的启动配置----在这里可以配置服务启动缓存，服务启动定时任务等等");
    }

    @Override
    public int getOrder() {
        // 启动顺序 可以用 Order注解代替
        return 10;
    }
}
