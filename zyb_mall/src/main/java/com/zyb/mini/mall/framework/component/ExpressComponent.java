package com.zyb.mini.mall.framework.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Strings;
import com.zyb.mini.mall.config.ExpressConfig;
import com.zyb.mini.mall.core.RedisCache;
import com.zyb.mini.mall.pojo.dto.ExpressDTO;
import com.zyb.mini.mall.pojo.entity.OrderExpress;
import com.zyb.mini.mall.redis.RedisKeyNameConstant;
import com.zyb.mini.mall.service.order.OrderExpressService;
import com.zyb.mini.mall.utils.sign.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>快递100查询组件</p>
 *
 * @author: Tx
 * @date: 2019/11/3
 */
@Slf4j
@Component
public class ExpressComponent {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private OrderExpressService orderExpressService;

    // 签收
    private final static String TRUE_CHECK = "1";
    // 签收
    private final static String EXPRESS_QS = "3";


    /**
     * 实施查询快递
     *
     * @param expressNo        快递单号
     * @param expressCompanyNo 快递公司编号
     * @param mobile           收货人或者寄件人 手机号
     * @return 查询的信息
     */
    public ExpressDTO queryExpress(String expressNo, String expressCompanyNo, String mobile) {
        // 先查询缓存得快递数据
        String key = RedisKeyNameConstant.EXPRESS_DATA_NO + expressNo;
        String str = redisCache.getStrCache(key);
        if (!Strings.isNullOrEmpty(str)) {
            return JSONObject.parseObject(str, ExpressDTO.class);
        }
        // 缓存没找的就找数据库得
        LambdaQueryWrapper<OrderExpress> qw = new QueryWrapper<OrderExpress>().lambda().eq(OrderExpress::getExpressNo, expressNo).eq(OrderExpress::getExpressCompanyNo, expressCompanyNo);
        OrderExpress dbExpressInfo = orderExpressService.getOne(qw);
        if (dbExpressInfo != null) {
            redisCache.refreshStrCacheByTime(key, dbExpressInfo.getExpressInfoJson(), 60 * 60);
            return JSONObject.parseObject(dbExpressInfo.getExpressInfoJson(), ExpressDTO.class);
        }
        // 调用外部接口获取物流信息
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 业务参数
        Map<String, String> param = new LinkedHashMap<>();
        param.put("com", expressCompanyNo.toLowerCase());
        param.put("num", expressNo);
        param.put("phone", mobile);
        param.put("resultv2", "2");
        param.put("show", "0");
        param.put("order", "desc");
        String paramJson = JSON.toJSONString(param);
        // 参数
//        Map<String, String> map = new HashMap<>();
//        map.put("customer", ExpressConfig.CUSTOMER);
//        map.put("sign", MD5Utils.MD5Encode(JSON.toJSONString(param) + ExpressConfig.KEY + ExpressConfig.CUSTOMER).toUpperCase());
//        map.put("param", JSON.toJSONString(param));
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("customer", ExpressConfig.CUSTOMER);
        map.add("sign", MD5Utils.MD5Encode(paramJson + ExpressConfig.KEY + ExpressConfig.CUSTOMER).toUpperCase());
        map.add("param", paramJson);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(ExpressConfig.QUERY_API, request, String.class);
        String respBodyStr = responseEntity.getBody();
        log.info("物流信息 : {}", respBodyStr);
        redisCache.refreshStrCacheByTime(key, respBodyStr, 60 * 60);
        ExpressDTO expressDTO = JSONObject.parseObject(respBodyStr, ExpressDTO.class);
        // 物流信息签收了直接入库
        if (expressDTO != null && (TRUE_CHECK.equals(expressDTO.getIscheck()) || EXPRESS_QS.equals(expressDTO.getState()))) {
            OrderExpress orderExpress = new OrderExpress().setExpressCompanyNo(expressCompanyNo).setExpressNo(expressNo).setExpressInfoJson(respBodyStr);
            orderExpressService.save(orderExpress);
        }
        return expressDTO;
//        String body = HttpClient.sendPostRequest(ExpressConfig.QUERY_API, map);
//        log.info(body);
//        return body;
    }
}
