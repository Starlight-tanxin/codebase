package com.dome.mp.server.web.controller;

import com.dome.mp.server.web.controller.api.TestApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>description</p>
 *
 * @author TanXin
 * @date 2020/11/4 9:24
 */
@RestController
@RequestMapping("/demo/test-api")
public class TestController implements TestApi {

    @Override
    public Object testApi(String msg) {
        Map<String,String> map = new HashMap<>();
        map.put("msg","ok");
        map.put("status","200");
        map.put("body",msg);
        return map;
    }


}
