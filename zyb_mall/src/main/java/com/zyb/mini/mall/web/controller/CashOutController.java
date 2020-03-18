package com.zyb.mini.mall.web.controller;

import com.alibaba.fastjson.JSON;
import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.framework.interceptor.user.UpdateUser;
import com.zyb.mini.mall.pojo.entity.PayOrder;
import com.zyb.mini.mall.pojo.entity.User;
import com.zyb.mini.mall.pojo.param.cash.CashParam;
import com.zyb.mini.mall.service.trade.PayOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/10/27 星期日 11:06.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */

@Slf4j
@Validated
@Api(tags = {"提现"})
@RequestMapping("/zyb/cash-out")
@RestController
public class CashOutController extends BaseController {

    private final PayOrderService payOrderService;

    public CashOutController(PayOrderService payOrderService) {
        this.payOrderService = payOrderService;
    }

    // TODO 参数
    @ApiOperation("发起提现")
    @PostMapping("/add")
    @UpdateUser(msg = "发起提现更新")
    R add(@Validated CashParam param) {
        log.info("发起提现参数 ------> {}", JSON.toJSONString(param));
        User user = currentUser();
        PayOrder order = payOrderService.createCash(user.getId(), param.getAmount());
        log.info("提现的订单 =====》 {}", JSON.toJSONString(order));
        return success();
    }

}
