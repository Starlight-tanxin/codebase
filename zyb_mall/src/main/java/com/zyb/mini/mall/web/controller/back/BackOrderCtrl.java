package com.zyb.mini.mall.web.controller.back;

import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.pojo.param.order.OrderExpressParam;
import com.zyb.mini.mall.service.order.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author: Tx
 * @date: 2019/11/9
 */
@Api(tags = {"后台操作的接口：订单"})
@RequestMapping("/zyb/back/order")
@RestController
@Validated
public class BackOrderCtrl {

    private final OrderService orderService;

    public BackOrderCtrl(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation("订单发货")
    @PostMapping("/sendGoods")
    R<?> sendGoods(@Validated OrderExpressParam param) {
        orderService.sendGoods(param);
        return R.success();
    }

    @ApiOperation("退款")
    @PostMapping("/refund")
    R<?> refund(@NotNull Long orderId) {
        orderService.refund(orderId);
        return R.success();
    }
}
