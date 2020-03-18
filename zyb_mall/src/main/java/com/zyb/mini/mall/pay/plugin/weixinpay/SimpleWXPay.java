package com.zyb.mini.mall.pay.plugin.weixinpay;

import com.google.common.collect.Maps;
import com.zyb.mini.mall.pay.plugin.weixinpay.config.WXPayConfig;
import com.zyb.mini.mall.pay.plugin.weixinpay.config.WXPayConfigImpl;
import com.zyb.mini.mall.pay.plugin.weixinpay.config.WXPayConstants;
import com.zyb.mini.mall.pay.plugin.weixinpay.inner.WXPay;
import com.zyb.mini.mall.pay.plugin.weixinpay.object.JSAPISign;
import com.zyb.mini.mall.pay.plugin.weixinpay.object.TransfersParam;
import com.zyb.mini.mall.pay.plugin.weixinpay.object.TransfersResult;
import com.zyb.mini.mall.pay.plugin.weixinpay.object.UnifiedOrderParam;
import com.zyb.mini.mall.pay.plugin.weixinpay.util.WXPayUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @author Created by 谭健 on 2018/12/12. 星期三. 14:55.
 * © All Rights Reserved.
 * <p>
 * 微信支付
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class SimpleWXPay {

    /**
     * 创建统一下单
     * 传入必传参数后，即可创建一个简单的支付统一下单[预下单]
     *
     * @param param 统一下单基础参数
     * @return 本方法返回的结果具备完整的结果参数，可以直接通过本方法返回的各项参数在网页端吊起微信支付
     */
    public static JSAPISign unifiedOrder(UnifiedOrderParam param) {
        if (StringUtils.isBlank(param.getBody()) || StringUtils.isBlank(param.getOutTradeNo()) || StringUtils.isBlank(param.getTotalFee()) ||
                StringUtils.isBlank(param.getSpbillCreateIp()) || StringUtils.isBlank(param.getTradeType())) {
            log.error("错误 ： 必传参数不能为空，请检查！");
            return null;
        }
        try {
            WXPay wxPay;
            if (StringUtils.isNotBlank(param.getNotifyUrl())) {
                // 如果用户自定义了回调地址，则按照用户自定义的回调地址
                // 否则，自动回调 WXPayConfig 中设置的回调地址
                wxPay = new WXPay(new WXPayConfigImpl(), param.getNotifyUrl());
            } else {
                wxPay = new WXPay(new WXPayConfigImpl());
            }
            Map<String, String> stringMap = Maps.newHashMap();
            stringMap.put("body", param.getBody());
            stringMap.put("out_trade_no", param.getOutTradeNo());
            stringMap.put("total_fee", param.getTotalFee());
            stringMap.put("spbill_create_ip", param.getSpbillCreateIp());
            stringMap.put("trade_type", param.getTradeType());
            if (WXPayConstants.JSAPI.equals(param.getTradeType())) {
                // 只有JSAPI 才需要openid
                stringMap.put("openid", param.getOpenid());
            }
            stringMap.put("attach", param.getAttach());
            stringMap.put("device_info", param.getDeviceInfo());
            stringMap.put("fee_type", param.getFeeType());
            Map<String, String> responseData = wxPay.unifiedOrder(stringMap);
            if (WXPayUtil.isSuccessResult(responseData)) {
                return wxPay.signJSAPI(wxPay.responseDataToResult(responseData));
            }
        } catch (Exception e) {
            log.error("代码执行错误 ", e);
            log.error("错误 ： 微信支付初始化失败，请检查是否正确实现了 WXPayConfig 接口");
        }
        return null;
    }

    /**
     * 企业付款到个人
     *
     * @param param
     * @return
     */
    public static TransfersResult company2User(TransfersParam param) {
        if (StringUtils.isBlank(param.getPartnerTradeNo()) || StringUtils.isBlank(param.getAmount())
                || StringUtils.isBlank(param.getSpbillCreateIp()) || StringUtils.isBlank(param.getOpenid())
                || StringUtils.isBlank(param.getDesc())) {
            log.error("错误 ： 必传参数不能为空，请检查！");
            return null;
        }
        try {
            WXPay wxPay = new WXPay(new WXPayConfigImpl());
            Map<String, String> stringMap = Maps.newTreeMap();
            stringMap.put("partner_trade_no", param.getPartnerTradeNo());
            stringMap.put("openid", param.getOpenid());
            stringMap.put("check_name", param.getCheckName());
            stringMap.put("amount", param.getAmount());
            stringMap.put("desc", param.getDesc());
            stringMap.put("spbill_create_ip", param.getSpbillCreateIp());
            Map<String, String> responseData = wxPay.transfers(stringMap);
            if (WXPayUtil.isSuccessResult(responseData)) {
                return wxPay.transfersDataToResult(responseData);
            }
        } catch (Exception e) {
            log.error("代码执行错误 ", e);
            log.error("错误 ： 微信支付初始化失败，请检查是否正确实现了 WXPayConfig 接口");
        }
        return null;
    }


    /**
     * 构建支付基本参数
     *
     * @param body        商品描述
     * @param totalFee    总价 单位：分
     * @param userIp      用户IP
     * @param openid      用户openid
     * @param attach      回传参数
     * @param notifyUrl   回调地址，如果已经配置默认回调地址，则该值传null
     * @param orderNumber 自我系统内的订单号
     * @return 返回发起支付的预下单接口的参数
     */
    public static UnifiedOrderParam newJSAPIParameter(String body, int totalFee, String userIp, String openid, String attach, String notifyUrl, String orderNumber) {
        UnifiedOrderParam param = new UnifiedOrderParam();
        param.setBody(body);
        param.setTradeType(WXPayConstants.JSAPI);
        param.setOutTradeNo(orderNumber);
        param.setTotalFee(String.valueOf(totalFee));
        param.setSpbillCreateIp(userIp);
        param.setOpenid(openid);
        param.setAttach(attach);
        // 为了让回调地址参数具备更好的容错性，此处做处理
        // 比如在交流的时候，只说明了传空，但是没有明确告知是什么空的时候，可以进行容错
        if (StringUtils.isNotBlank(notifyUrl)) {
            param.setNotifyUrl(notifyUrl);
        } else {
            param.setNotifyUrl(null);
        }
        return param;
    }


    /**
     * 获取回调通知中的参数
     *
     * @param inputStream 微信请求返回的流
     */
    public static Map<String, String> getNotifyResult(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, WXPayConstants.DEFAULT_CHARSET))) {
            StringBuffer strXml = new StringBuffer();
            String temporaryBuffer;
            while ((temporaryBuffer = reader.readLine()) != null) {
                strXml.append(temporaryBuffer);
            }
            Map<String, String> stringMap = WXPayUtil.xmlToMap(strXml.toString());
            if (WXPayUtil.isSuccessResult(stringMap)) {
                WXPayConfig config = new WXPayConfigImpl();
                String mchId = stringMap.get("mch_id");
                String appid = stringMap.get("appid");
                if (config.getMchID().equals(mchId) && config.getAppID().equals(appid)) {
                    return stringMap;
                }
            }
        } catch (Exception e) {
            log.error("代码执行错误 ", e);
        }
        return null;
    }
}
