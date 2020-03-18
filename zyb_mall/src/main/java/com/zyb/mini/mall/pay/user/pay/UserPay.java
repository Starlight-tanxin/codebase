package com.zyb.mini.mall.pay.user.pay;

import com.alibaba.fastjson.JSON;
import com.zyb.mini.mall.pay.plugin.weixinpay.SimpleWXPay;
import com.zyb.mini.mall.pay.plugin.weixinpay.object.JSAPISign;
import com.zyb.mini.mall.pay.plugin.weixinpay.object.UnifiedOrderParam;
import com.zyb.mini.mall.utils.NetUtils;
import com.zyb.mini.mall.utils.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.weixin4j.WeixinException;
import org.weixin4j.component.PayComponent;
import org.weixin4j.model.pay.UnifiedOrder;
import org.weixin4j.model.pay.UnifiedOrderResult;
import org.weixin4j.spring.WeixinTemplate;
import org.weixin4j.util.PayUtil;
import org.weixin4j.util.SignUtil;

import java.time.Clock;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/10/29 星期二 22:50.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */
@Slf4j
@Component
public class UserPay {

    private final WeixinTemplate template;


    public UserPay(WeixinTemplate template) {
        this.template = template;
    }






    public JSAPISign payPlugin(int finalTotalFee,String userRealIp,String openId,String orderNumber) throws WeixinException {
        String notifyUrl = template.getWeixinPayConfig().getNotifyUrl();
        UnifiedOrderParam param = SimpleWXPay.newJSAPIParameter("商品描述", finalTotalFee, userRealIp, openId, "回传参数", notifyUrl, orderNumber);
        if (log.isInfoEnabled()){
            log.info("预下单，下单参数：{}", param);
        }
        JSAPISign paySign = SimpleWXPay.unifiedOrder(param);
        if (paySign != null) {
            log.info("创建支付，返回JSAPI调用参数：{} ", paySign);
            return paySign;
        } else {
            throw new WeixinException("发起支付失败");
        }
    }



    /**
     * 发起支付
     *
     * @throws WeixinException
     */
    public PaySign pay(UserPayUnifiedOrderParam param) throws WeixinException {
        PayComponent component = new PayComponent(template);
        UnifiedOrder order = new UnifiedOrder();
        order.setBody(param.getBody());
        order.setTotal_fee(String.valueOf(param.getTotalFee()));
        order.setSpbill_create_ip(param.getUserRealIp());
        order.setOpenid(param.getOpenId());
        order.setNotify_url(param.getNotifyUrl());
        order.setOut_trade_no(param.getOrderNumber());


        String uniqueId = UUID.absolutelyUniqueId();
        String timestamp = String.valueOf(Clock.systemUTC().millis() / 1000);
        order.setAppid(template.getAppId());
        order.setMch_id(template.getWeixinPayConfig().getPartnerId());
        order.setNonce_str(uniqueId);
        order.setNotify_url("https://zyb.hnsxyts.com/zyb/order/pay/callback");
        order.setTrade_type("JSAPI");

        String signature = SignUtil.getSignature(template.getJsApiTicket().getTicket(), uniqueId, timestamp, "pages/index/index");
        order.setSign(signature.toUpperCase());

        UnifiedOrderResult result = component.payUnifiedOrder(order);
        PaySign sign = new PaySign();
        if (result.isSuccess()) {
            if (log.isInfoEnabled()) {
                log.info("--------------------微信统一预下单成功----------------------");
            }
            sign.setNonceStr(result.getNonce_str());
            sign.setTimestamp(timestamp);
            sign.setPackageInfo(result.getPrepay_id());
            sign.setPaySign(result.getSign());
            sign.setSignType("MD5");
            return sign;
        }
        if (log.isInfoEnabled()){
            log.info("getReturn_code:{}",result.getReturn_code());
            log.info("getReturn_msg:{}",result.getReturn_msg());
            log.info("getAppid:{}",result.getAppid());
            log.info("getMch_id:{}",result.getMch_id());
            log.info("getDevice_info:{}",result.getDevice_info());
            log.info("getNonce_str:{}",result.getNonce_str());
            log.info("getSign:{}",result.getSign());
            log.info("getResult_code:{}",result.getResult_code());
            log.info("getErr_code:{}",result.getErr_code());
            log.info("getErr_code_des:{}",result.getErr_code_des());
            log.info("getTrade_type:{}",result.getTrade_type());
            log.info("getPrepay_id:{}",result.getPrepay_id());
            log.info("getCode_url:{}",result.getCode_url());
        }
        throw new WeixinException("预下单失败:" + result.toString());
    }


}
