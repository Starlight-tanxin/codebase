package com.zyb.mini.mall.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyb.mini.mall.constant.CommonConstant;
import com.zyb.mini.mall.constant.DateFormatter;
import com.zyb.mini.mall.constant.Mock;
import com.zyb.mini.mall.constant.ShopOrderState;
import com.zyb.mini.mall.core.R;
import com.zyb.mini.mall.core.Status;
import com.zyb.mini.mall.exception.ApiException;
import com.zyb.mini.mall.framework.component.NotifyComponent;
import com.zyb.mini.mall.framework.interceptor.user.UpdateUser;
import com.zyb.mini.mall.pay.plugin.weixinpay.object.JSAPISign;
import com.zyb.mini.mall.pay.user.pay.NotifyField;
import com.zyb.mini.mall.pay.user.pay.UserPay;
import com.zyb.mini.mall.pojo.dto.BeforePayDTO;
import com.zyb.mini.mall.pojo.dto.ExpressDTO;
import com.zyb.mini.mall.pojo.entity.*;
import com.zyb.mini.mall.pojo.param.BasePageParam;
import com.zyb.mini.mall.pojo.param.order.OrderIndexParam;
import com.zyb.mini.mall.pojo.param.order.OrderParam;
import com.zyb.mini.mall.pojo.param.order.PreOrderParam;
import com.zyb.mini.mall.pojo.param.pay.AccountPayParam;
import com.zyb.mini.mall.pojo.param.pay.CheckPayParam;
import com.zyb.mini.mall.pojo.param.pay.PayParam;
import com.zyb.mini.mall.service.order.OrderExpressService;
import com.zyb.mini.mall.service.order.OrderService;
import com.zyb.mini.mall.service.order.ShopOrderDetailService;
import com.zyb.mini.mall.service.shop.GoodsService;
import com.zyb.mini.mall.service.shop.ShopService;
import com.zyb.mini.mall.service.trade.PayOrderService;
import com.zyb.mini.mall.utils.CodeUtils;
import com.zyb.mini.mall.utils.NetUtils;
import com.zyb.mini.mall.utils.sign.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.weixin4j.WeixinException;
import org.weixin4j.spring.WeixinTemplate;
import org.weixin4j.util.PayUtil;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/10/27 星期日 11:04.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */
@Validated
@Api(tags = {"订单"})
@RequestMapping("/zyb/order")
@RestController
public class OrderController extends BaseController {


    private final UserPay userPay;
    private final WeixinTemplate template;
    private final OrderService orderService;
    private final GoodsService goodsService;
    private final ShopOrderDetailService shopOrderDetailService;
    private final PayOrderService payOrderService;
    private final NotifyComponent notifyWx;
    private final OrderExpressService orderExpressService;
    private final ShopService shopService;

    public OrderController(UserPay userPay, WeixinTemplate template, OrderService orderService, GoodsService goodsService, ShopOrderDetailService shopOrderDetailService, PayOrderService payOrderService, NotifyComponent notifyWx, OrderExpressService orderExpressService, ShopService shopService) {
        this.userPay = userPay;
        this.template = template;
        this.orderService = orderService;
        this.goodsService = goodsService;
        this.shopOrderDetailService = shopOrderDetailService;
        this.payOrderService = payOrderService;
        this.notifyWx = notifyWx;
        this.orderExpressService = orderExpressService;
        this.shopService = shopService;
    }

    @ApiOperation("订单列表")
    @GetMapping("/list")
    R<Page<ShopOrder>> list(OrderIndexParam param) {
        User user = currentUser();
        param.setUserId(user.getId());
        Page<ShopOrder> page = orderService.selectPageByParam(param);
        if (page.getRecords() != null) {
            List<ShopOrder> orderList = page.getRecords();
            List<Long> ids = orderList.stream().map(ShopOrder::getId).collect(Collectors.toList());
            List<ShopOrderDetail> orderDetailList = shopOrderDetailService.selectAllByOrderIds(ids);
            for (ShopOrder order : orderList) {
                List<ShopOrderDetail> details = order.getOrderDetails();
                details = (details == null) ? new ArrayList<>() : details;
                for (ShopOrderDetail detail : orderDetailList) {
                    if (detail.getShopOrderId().equals(order.getId())) {
                        if (details.size() == 0) {
                            details.add(detail);
                            order.setOrderDetails(details);
                        }
                        if (details.size() > 0) {
                            break;
                        }
                    }
                }
            }
            page.setRecords(orderList);
        }
        return R.success(page);
    }


    @ApiOperation("下单")
    @PostMapping("/pre")
    R<Object> pre(@Validated OrderParam param) {
        if (param.getPerGoodsArray() == null) {
            return R.error(Status.PARAM_LOST);
        }
        User user = currentUser();
        LocalDateTime now = LocalDateTime.now();
        String nowStr = DateFormatter.DATE_TIME_ORDER.format(now);
        //快递费 暂时默认20元
        String payOrderNo = nowStr + CodeUtils.createRandomCharData(16);
        ShopOrder order = new ShopOrder()
                .setOrderNo(payOrderNo)
                .setUserId(user.getId())
                .setExpressPrice(CommonConstant.DEFAULT_EXPRESS_PRICE)
                .setCretaedTime(now)
                .setOrderState(ShopOrderState.NOT_PAY);
        orderService.save(order);
        List<ShopOrderDetail> list = new ArrayList<>();
        //检查商品库存等
        BigDecimal orderPrice = new BigDecimal(0);
        synchronized (this) {
            for (PreOrderParam per : param.getPerGoodsArray()) {
                GoodsBook goodsBook = goodsService.getGoodsById(per.getGoodsId());
                boolean isExit = (goodsBook != null && (goodsBook.getBookNum() >= per.getNum()));
                // 删除购物车商品
                shopService.removeCart(user.getId(), per.getGoodsId());
                if (!isExit) {
                    return R.error(Status.GOODS_NO_NUM);
                }
                ShopOrderDetail detail = new ShopOrderDetail()
                        .setNum(per.getNum())
                        .setGoodsBookId(per.getGoodsId())
                        .setBookDesc(goodsBook.getBookDesc())
                        .setMainImg(goodsBook.getMainImg())
                        .setGoodsPrice(goodsBook.getGoodsPrice())
                        .setGoodsName(goodsBook.getGoodsName())
                        .setShopOrderId(order.getId());
                list.add(detail);
                // 修复一个没计算数量得bug
                orderPrice = orderPrice.add(goodsBook.getGoodsPrice().multiply(BigDecimal.valueOf(per.getNum())));
            }
            list.forEach(od -> {
                goodsService.updateGoodsNum(od.getGoodsBookId(), od.getNum());
            });
        }
        if (list.size() <= 0) {
            orderService.removeById(order.getId());
        } else {
            shopOrderDetailService.saveBatch(list);
            order.setOrderPrice(orderPrice);
            orderService.updateById(order);
        }
        return R.success(order);
    }


    @ApiOperation("支付")
    @PostMapping("/pay")
    @UpdateUser(msg = "发起微信支付更新")
    R<JSAPISign> pay(@Validated PayParam payParam) {
        User user = currentUser();
        // 获取支付前的订单数据模型
        BeforePayDTO beforePayDTO = orderService.getBeforePayDTO(user.getId(), payParam);
        // 创建支付订单
        PayOrder payOrder = payOrderService.createPayOrder(beforePayDTO);
//        String remoteIp = NetUtils.getRemoteIp(request);
//        UserPayUnifiedOrderParam param = new UserPayUnifiedOrderParam();
//        param.setOrderNumber(payOrder.getPayOrderNo());
//        param.setUserRealIp(remoteIp);
//        param.setNotifyUrl(template.getWeixinPayConfig().getNotifyUrl());
//        param.setOpenId(user.getWxOpenId());
//        param.setTotalFee(beforePayDTO.getAmount());
        // TODO 下面的根据实际情况放到service 里面去处理 这里是示例
        //     其它的都封装好了，只需要知道卖的是个啥，卖多少钱就行了
//        param.setBody("藏一本商城，精选！");

        int finalTotalFee = beforePayDTO.getAmount().multiply(CommonConstant.FEN_2_YUAN).intValue();
        String userRealIp = NetUtils.getRemoteIp(request);
        String openId = user.getWxOpenId();
        String orderNumber = payOrder.getPayOrderNo();

        try {

            // 把支付签名发给前端，前端就可以发起支付了
            return R.success(userPay.payPlugin(finalTotalFee, userRealIp, openId, orderNumber));
        } catch (WeixinException e) {
            e.printStackTrace();
        }
        return R.error(Status.SYS_ERROR);
    }

    @ApiOperation("支付详情页面")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", example = Mock.NUMBER, paramType = "query", required = true),
    })
    @GetMapping("/pay/detail")
    R<ShopOrder> detail(@NotNull Long orderId) {
        ShopOrder shopOrder = orderService.detailOrder(orderId);
        return R.success(shopOrder);
    }

    @ApiOperation("支付回调")
    @RequestMapping("/pay/callback")
    synchronized void callback(HttpServletResponse response) throws Exception {
        String xmlStr = notifyWx.getXmlStr(request.getInputStream());
        boolean b = PayUtil.verifySign(xmlStr, template.getWeixinPayConfig().getPartnerKey());
        if (b) {
            // 验签成功: 得到微信的回传信息
            NotifyField notifyField = notifyWx.getNotifyField(xmlStr);
            boolean isTrue = orderService.callbackProcess(notifyField, xmlStr);
            if (isTrue) {
                // 通知微信：我们这里处理回调成了
                notifyWx.notifyWxSuccess(response.getWriter());
            }
        }
        notifyWx.notifyWxFail(response.getWriter(), "订单不存在啊 什么七七八八的逻辑处理");
    }

    @ApiOperation("发布评价")
    @PostMapping("/evaluate/add")
    R add(@Validated ShopOrderEvaluate shopOrderEvaluate) {
        User user = currentUser();
        shopOrderEvaluate.setUserId(user.getId());
        return success(orderService.addEvaluate(shopOrderEvaluate));
    }

    @ApiOperation("查询订单评价")
    @PostMapping("/selectOrderEvaluate")
    R<IPage<ShopOrderEvaluate>> selectOrderEvaluate(@Validated BasePageParam param) {
        return R.success(orderService.selectPageEvaluate(param));
    }

    @ApiOperation("取消订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", example = Mock.NUMBER, paramType = "query", required = true),
    })
    @PostMapping("/cancelOrder")
    R<?> cancelOrder(@NotNull Long orderId) {
        orderService.cancelOrder(orderId);
        return R.success();
    }

    @ApiOperation("申请退款")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", example = Mock.NUMBER, paramType = "query", required = true),
    })
    @PostMapping("/applyRefund")
    R<?> applyRefund(@NotNull Long orderId) {
        orderService.applyRefund(orderId);
        return R.success();
    }

    @ApiOperation("确认收货")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", example = Mock.NUMBER, paramType = "query", required = true),
    })
    @PostMapping("/receipt")
    R<?> receipt(@NotNull Long orderId) {
        ShopOrder shopOrder = orderService.getById(orderId);
        if (shopOrder == null || !shopOrder.getOrderState().equals(ShopOrderState.NOT_PULL_GOODS)) {
            throw new ApiException(Status.ORDER_NO_EXIST, "订单不存在或不是可以发起收货的状态");
        }
        shopOrder.setOrderState(ShopOrderState.NOT_EVA);
        orderService.updateById(shopOrder);
        return R.success();
    }

    @ApiOperation("个人账户支付")
    @PostMapping("/accountPay")
    @UpdateUser(msg = "发起账户支付更新")
    R<?> accountPay(@Validated AccountPayParam param) {
        User user = currentUser();
        String md5Pwd = MD5Utils.MD5Encode(param.getUserPayPwd());
        if (!md5Pwd.equals(user.getPayPwd())) {
            return R.error(Status.INPUT_PWD_ERROR);
        }
        orderService.accountPay(user.getId(), param);
        return R.success();
    }

    @ApiOperation("查询订单的物流信息 -- 不在详情查询")
    @PostMapping("/queryExpress")
    R<ExpressDTO> queryExpress(@NotNull Long orderId) {
        ExpressDTO dto = orderExpressService.queryExpressInfo(orderId);
        return R.success(dto);
    }


    @ApiOperation("检查订单是否支付成功")
    @PostMapping("/checkPaySuccess")
    R checkPaySuccess(@Validated CheckPayParam param) {
        return success(orderService.checkPaySuccess(param));
    }
}
