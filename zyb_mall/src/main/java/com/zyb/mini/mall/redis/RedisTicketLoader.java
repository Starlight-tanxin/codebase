package com.zyb.mini.mall.redis;

import com.alibaba.fastjson.JSON;
import com.zyb.mini.mall.redis.RedisKeyNameConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.weixin4j.loader.ITicketLoader;
import org.weixin4j.model.js.Ticket;
import org.weixin4j.model.js.TicketType;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/** @author Created by 谭健 on 2019/10/28. 星期一. 14:19. © All Rights Reserved. */
@Slf4j
@Component
public class RedisTicketLoader implements ITicketLoader {

  @Value("${weixin4j.config.appid}")
  private String appId;

  private final StringRedisTemplate stringRedisTemplate;

  public RedisTicketLoader(StringRedisTemplate stringRedisTemplate) {
    this.stringRedisTemplate = stringRedisTemplate;
  }



  @Override
  public Ticket get(TicketType ticketType) {
    String key = "";
    if (null != ticketType) {
      switch (ticketType) {
        case JSAPI:
          key = RedisKeyNameConstant.WECHAT_TICKET_JSAPI;
          break;
        case WX_CARD:
          key = RedisKeyNameConstant.WECHAT_TICKET_WXCARD;
          break;
        default:
          key = RedisKeyNameConstant.WECHAT_TICKET;
          break;
      }
    }
    String ticket = stringRedisTemplate.opsForValue().get(key);
    return JSON.parseObject(ticket, Ticket.class);
  }

  @Override
  public void refresh(Ticket ticket) {
    String key = "";
    String suffix = "_" + appId;
    if (null != ticket.getTicketType()) {
      switch (ticket.getTicketType()) {
        case JSAPI:
          key = RedisKeyNameConstant.WECHAT_TICKET_JSAPI + suffix;
          break;
        case WX_CARD:
          key = RedisKeyNameConstant.WECHAT_TICKET_WXCARD + suffix;
          break;
        default:
          key = RedisKeyNameConstant.WECHAT_TICKET + suffix;
          break;
      }
    }
    log.info("微信操作JSAPI 票据过期，重新刷新，刷新后的值是 [{}]", ticket);
    String ticketValue = JSON.toJSONString(ticket);
    // 提前过期，防止热点事件
    long timeout = ticket.getExpires_in() - 600L;
    stringRedisTemplate.opsForValue().set(key, ticketValue, timeout, TimeUnit.SECONDS);
  }

  @PostConstruct
  void init() {
    if (log.isInfoEnabled()) {
      log.info("---------------- 把微信的 JSAPI 票据交给spring 管理 ---------------------");
    }
  }
}
