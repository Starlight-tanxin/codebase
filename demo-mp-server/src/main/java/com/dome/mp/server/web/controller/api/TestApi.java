package com.dome.mp.server.web.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * <p>description</p>
 *
 * @author TanXin
 * @date 2020/11/4 9:24
 */
public interface TestApi {

    @GetMapping("/testApi/{msg}")
    Object testApi(@PathVariable("msg") String msg);
}
