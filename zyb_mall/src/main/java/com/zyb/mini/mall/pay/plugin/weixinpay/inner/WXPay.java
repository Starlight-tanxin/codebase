package com.zyb.mini.mall.pay.plugin.weixinpay.inner;


import com.google.common.collect.Maps;
import com.zyb.mini.mall.pay.plugin.weixinpay.config.WXPayConfig;
import com.zyb.mini.mall.pay.plugin.weixinpay.config.WXPayConstants;
import com.zyb.mini.mall.pay.plugin.weixinpay.object.JSAPISign;
import com.zyb.mini.mall.pay.plugin.weixinpay.object.TransfersResult;
import com.zyb.mini.mall.pay.plugin.weixinpay.object.UnifiedOrderResult;
import com.zyb.mini.mall.pay.plugin.weixinpay.util.WXPayUtil;
import com.zyb.mini.mall.utils.UUID;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;


@Slf4j
public class WXPay {


    private WXPayConfig config;
    private WXPayConstants.SignType signType;
    private boolean autoReport;
    private boolean useSandbox;
    private String notifyUrl;
    private WXPayRequest wxPayRequest;

    public WXPay(final WXPayConfig config) throws Exception {
        this(config, config.defaultNotifyUrl(), true, false);
    }

    public WXPay(final WXPayConfig config, final boolean autoReport) throws Exception {
        this(config, config.defaultNotifyUrl(), autoReport, false);
    }


    public WXPay(final WXPayConfig config, final boolean autoReport, final boolean useSandbox) throws Exception {
        this(config, config.defaultNotifyUrl(), autoReport, useSandbox);
    }

    public WXPay(final WXPayConfig config, final String notifyUrl) throws Exception {
        this(config, notifyUrl, true, false);
    }

    public WXPay(final WXPayConfig config, final String notifyUrl, final boolean autoReport) throws Exception {
        this(config, notifyUrl, autoReport, false);
    }

    public WXPay(final WXPayConfig config, final String notifyUrl, final boolean autoReport, final boolean useSandbox) throws Exception {
        this.config = config;
        this.notifyUrl = notifyUrl;
        this.autoReport = autoReport;
        this.useSandbox = useSandbox;
        if (useSandbox) {
            // 沙箱环境下即使设置了 HMACSHA256 也是无效的，沙箱环境默认是MD5
            this.signType = WXPayConstants.SignType.MD5;
        } else {
            this.signType = WXPayConstants.SignType.MD5;
        }
        this.wxPayRequest = new WXPayRequest(config);
    }

    private void checkWXPayConfig() throws Exception {
        if (this.config == null) {
            throw new Exception("config is null");
        }
        if (this.config.getAppID() == null || this.config.getAppID().trim().length() == 0) {
            throw new Exception("appid in config is empty");
        }
        if (this.config.getMchID() == null || this.config.getMchID().trim().length() == 0) {
            throw new Exception("appid in config is empty");
        }
        if (this.config.getCertStream() == null) {
            throw new Exception("cert stream in config is empty");
        }
        if (this.config.getWXPayDomain() == null) {
            throw new Exception("config.getWXPayDomain() is null");
        }

        if (this.config.getHttpConnectTimeoutMs() < 10) {
            throw new Exception("http connect timeout is too small");
        }
        if (this.config.getHttpReadTimeoutMs() < 10) {
            throw new Exception("http read timeout is too small");
        }

    }


    /**
     * 把
     *
     * @param responseData 预下单接口返回的数据
     * @see WXPay {@link #unifiedOrder(Map)}
     * 返回的结果组装成Java 对象
     * 该方法应尽量在，不是一定
     * @see WXPayUtil#isSuccessResult(Map)
     * 返回true 后调用
     */
    public UnifiedOrderResult responseDataToResult(Map<String, String> responseData) {
        UnifiedOrderResult result = new UnifiedOrderResult();
        if (responseData.containsKey("appid")) {
            result.setAppid(responseData.get("appid"));
        }
        if (responseData.containsKey("mch_id")) {
            result.setMchId(responseData.get("mch_id"));
        }
        if (responseData.containsKey("device_info")) {
            result.setDeviceInfo(responseData.get("device_info"));
        }
        if (responseData.containsKey("nonce_str")) {
            result.setNonceStr(responseData.get("nonce_str"));
        }
        if (responseData.containsKey("sign")) {
            result.setSign(responseData.get("sign"));
        }
        if (responseData.containsKey("result_code")) {
            result.setResultCode(responseData.get("result_code"));
        }
        if (responseData.containsKey("err_code")) {
            result.setErrCode(responseData.get("err_code"));
        }
        if (responseData.containsKey("err_code_des")) {
            result.setErrCodeDes(responseData.get("err_code_des"));
        }
        if (responseData.containsKey("trade_type")) {
            result.setTradeType(responseData.get("trade_type"));
        }
        if (responseData.containsKey("prepay_id")) {
            result.setPrepayId(responseData.get("prepay_id"));
        }
        if (responseData.containsKey("code_url")) {
            result.setCodeUrl(responseData.get("code_url"));
        }
        if (responseData.containsKey("return_code")) {
            result.setReturnCode(responseData.get("return_code"));
        }
        if (responseData.containsKey("return_msg")) {
            result.setReturnMsg(responseData.get("return_msg"));
        }
        return result;
    }


    /**
     * 把
     *
     * @param responseData 企业付款到个人的数据
     * @see WXPay {@link #unifiedOrder(Map)}
     * 返回的结果组装成Java 对象
     * 该方法应尽量在，不是一定
     * @see WXPayUtil#isSuccessResult(Map)
     * 返回true 后调用
     */
    public TransfersResult transfersDataToResult(Map<String, String> responseData) {
        TransfersResult result = new TransfersResult();
        if (responseData.containsKey("return_code")) {
            result.setReturnCode(responseData.get("return_code"));
        }
        if (responseData.containsKey("return_msg")) {
            result.setReturnMsg(responseData.get("return_msg"));
        }
        if (responseData.containsKey("mch_appid")) {
            result.setMchAppId(responseData.get("mch_appid"));
        }
        if (responseData.containsKey("mch_id")) {
            result.setMchId(responseData.get("mch_id"));
        }
        if (responseData.containsKey("device_info")) {
            result.setDeviceInfo(responseData.get("device_info"));
        }
        if (responseData.containsKey("nonce_str")) {
            result.setNonceStr(responseData.get("nonce_str"));
        }
        if (responseData.containsKey("result_code")) {
            result.setResultCode(responseData.get("result_code"));
        }
        if (responseData.containsKey("err_code")) {
            result.setErrCode(responseData.get("err_code"));
        }
        if (responseData.containsKey("err_code_des")) {
            result.setErrCodeDes(responseData.get("err_code_des"));
        }
        if (responseData.containsKey("partner_trade_no")) {
            result.setPartnerTradeNo(responseData.get("partner_trade_no"));
        }
        if (responseData.containsKey("payment_no")) {
            result.setPaymentNo(responseData.get("payment_no"));
        }
        if (responseData.containsKey("payment_time")) {
            result.setPaymentTime(responseData.get("payment_time"));
        }
        return result;
    }


    public JSAPISign signJSAPI(UnifiedOrderResult result) {
        JSAPISign sign = new JSAPISign();
        sign.setNonceStr(UUID.absolutelyUniqueId());
        sign.setPackageInfo("prepay_id=" + result.getPrepayId());
        sign.setTimestamp(String.valueOf(WXPayUtil.getCurrentTimestamp()));
        sign.setSignType(this.signType.name());
        Map<String, String> stringMap = Maps.newHashMap();
        stringMap.put("appId", config.getAppID());
        stringMap.put("timeStamp", sign.getTimestamp());
        stringMap.put("nonceStr", sign.getNonceStr());
        stringMap.put("package", sign.getPackageInfo());
        stringMap.put("signType", sign.getSignType());
        try {
            sign.setPaySign(WXPayUtil.generateSignature(stringMap, config.getKey(), this.signType));
        } catch (Exception e) {
            log.error("代码执行错误 ", e);
        }
        return sign;
    }


    /**
     * 向 Map 中添加 appid、mch_id、nonce_str、sign_type、sign <br>
     * 该函数适用于商户适用于统一下单等接口，不适用于红包、代金券接口
     */
    public Map<String, String> fillRequestData(Map<String, String> reqData) throws Exception {
        reqData.put("appid", config.getAppID());
        reqData.put("mch_id", config.getMchID());
        reqData.put("nonce_str", WXPayUtil.generateNonceStr());
        if (WXPayConstants.SignType.MD5.equals(this.signType)) {
            reqData.put("sign_type", WXPayConstants.MD5);
        } else if (WXPayConstants.SignType.HMACSHA256.equals(this.signType)) {
            reqData.put("sign_type", WXPayConstants.HMACSHA256);
        }
        String sign = WXPayUtil.generateSignature(reqData, config.getKey(), this.signType);
        reqData.put(WXPayConstants.FIELD_SIGN, sign);
        return reqData;
    }

    /**
     * 向 Map 中添加 mch_appid、mch_id、nonce_str、sign_type、sign <br>
     * 使用于企业付款到零钱
     */
    public Map<String, String> fillRequestDataPayUser(Map<String, String> reqData) throws Exception {
        reqData.put("mch_appid", config.getMchID());
        reqData.put("mch_id", config.getMchID());
        reqData.put("nonce_str", WXPayUtil.generateNonceStr());
        if (WXPayConstants.SignType.MD5.equals(this.signType)) {
            reqData.put("sign_type", WXPayConstants.MD5);
        } else if (WXPayConstants.SignType.HMACSHA256.equals(this.signType)) {
            reqData.put("sign_type", WXPayConstants.HMACSHA256);
        }
        String sign = WXPayUtil.generateSignature(reqData, config.getKey(), this.signType);
        reqData.put(WXPayConstants.FIELD_SIGN, sign);
        return reqData;
    }

    /**
     * 判断xml数据的sign是否有效，必须包含sign字段，否则返回false。
     *
     * @param reqData 向wxpay post的请求数据
     * @return 签名是否有效
     */
    public boolean isResponseSignatureValid(Map<String, String> reqData) throws Exception {
        // 返回数据的签名方式和请求中给定的签名方式是一致的
        return WXPayUtil.isSignatureValid(reqData, this.config.getKey(), this.signType);
    }


    /**
     * 不需要证书的请求
     *
     * @param urlSuffix        String
     * @param reqData          向wxpay post的请求数据
     * @param connectTimeoutMs 超时时间，单位是毫秒
     * @param readTimeoutMs    超时时间，单位是毫秒
     * @return API返回数据
     */
    public String requestWithoutCert(String urlSuffix, Map<String, String> reqData,
                                     int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String msgUUID = reqData.get("nonce_str");
        String reqBody = WXPayUtil.mapToXml(reqData);

        return this.wxPayRequest.requestWithoutCert(urlSuffix, msgUUID, reqBody, connectTimeoutMs, readTimeoutMs, autoReport);
    }


    /**
     * 需要证书的请求
     *
     * @param urlSuffix        String
     * @param reqData          向wxpay post的请求数据  Map
     * @param connectTimeoutMs 超时时间，单位是毫秒
     * @param readTimeoutMs    超时时间，单位是毫秒
     * @return API返回数据
     */
    public String requestWithCert(String urlSuffix, Map<String, String> reqData,
                                  int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String msgUUID = reqData.get("nonce_str");
        String reqBody = WXPayUtil.mapToXml(reqData);

        return this.wxPayRequest.requestWithCert(urlSuffix, msgUUID, reqBody, connectTimeoutMs, readTimeoutMs, this.autoReport);
    }

    /**
     * 处理 HTTPS API返回数据，转换成Map对象。return_code为SUCCESS时，验证签名。
     *
     * @param xmlStr API返回的XML格式数据
     * @return Map类型数据
     */
    public Map<String, String> processResponseXml(String xmlStr) throws Exception {
        String returnCodeName = "return_code";
        String returnCode;
        Map<String, String> respData = WXPayUtil.xmlToMap(xmlStr);
        if (respData.containsKey(returnCodeName)) {
            returnCode = respData.get(returnCodeName);
        } else {
            throw new Exception(String.format("No `return_code` in XML: %s", xmlStr));
        }

        switch (returnCode) {
            case WXPayConstants.FAIL:
                return respData;
            case WXPayConstants.SUCCESS:
                if (this.isResponseSignatureValid(respData)) {
                    return respData;
                } else {
                    throw new Exception(String.format("签名值 [sign] 无效: %s", xmlStr));
                }
            default:
                throw new Exception(String.format("return_code value %s is invalid in XML: %s", returnCode, xmlStr));
        }
    }

    /**
     * 作用：提交刷卡支付<br>
     * 场景：刷卡支付
     *
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     */
    public Map<String, String> microPay(Map<String, String> reqData) throws Exception {
        return this.microPay(reqData, this.config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }


    /**
     * 作用：提交刷卡支付<br>
     * 场景：刷卡支付
     *
     * @param reqData          向wxpay post的请求数据
     * @param connectTimeoutMs 连接超时时间，单位是毫秒
     * @param readTimeoutMs    读超时时间，单位是毫秒
     * @return API返回数据
     */
    public Map<String, String> microPay(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WXPayConstants.SANDBOX_MICROPAY_URL_SUFFIX;
        } else {
            url = WXPayConstants.MICROPAY_URL_SUFFIX;
        }
        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData), connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }

    /**
     * 提交刷卡支付，针对软POS，尽可能做成功
     * 内置重试机制，最多60s
     */
    public Map<String, String> microPayWithPos(Map<String, String> reqData) throws Exception {
        return this.microPayWithPos(reqData, this.config.getHttpConnectTimeoutMs());
    }

    /**
     * 提交刷卡支付，针对软POS，尽可能做成功
     * 内置重试机制，最多60s
     */
    public Map<String, String> microPayWithPos(Map<String, String> reqData, int connectTimeoutMs) throws Exception {
        int remainingTimeMs = 60 * WXPayConstants.SECOND;
        long startTimestampMs = 0;
        Map<String, String> lastResult = null;
        Exception lastException = null;

        while (true) {
            startTimestampMs = WXPayUtil.getCurrentTimestampMs();
            int readTimeoutMs = remainingTimeMs - connectTimeoutMs;
            if (readTimeoutMs > WXPayConstants.SECOND) {
                try {
                    lastResult = this.microPay(reqData, connectTimeoutMs, readTimeoutMs);
                    String returnCode = lastResult.get("return_code");
                    if (WXPayConstants.SUCCESS.equals(returnCode)) {
                        String resultCode = lastResult.get("result_code");
                        String errCode = lastResult.get("err_code");
                        if (WXPayConstants.SUCCESS.equals(resultCode)) {
                            break;
                        } else {
                            // 看错误码，若支付结果未知，则重试提交刷卡支付
                            if ("SYSTEMERROR".equals(errCode) || "BANKERROR".equals(errCode) || "USERPAYING".equals(errCode)) {
                                remainingTimeMs = remainingTimeMs - (int) (WXPayUtil.getCurrentTimestampMs() - startTimestampMs);
                                if (remainingTimeMs <= 100) {
                                    break;
                                } else {
                                    WXPayUtil.getLogger().info("microPayWithPos: try micropay again");
                                    if (remainingTimeMs > 5L * WXPayConstants.SECOND) {
                                        Thread.sleep(5L * WXPayConstants.SECOND);
                                    } else {
                                        Thread.sleep(WXPayConstants.SECOND);
                                    }
                                }
                            } else {
                                break;
                            }
                        }
                    } else {
                        break;
                    }
                } catch (Exception ex) {
                    lastResult = null;
                    lastException = ex;
                }
            } else {
                break;
            }
        }

        if (lastResult == null) {
            throw lastException;
        } else {
            return lastResult;
        }
    }


    /**
     * 作用：统一下单<br>
     * 场景：公共号支付、扫码支付、APP支付
     *
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     */
    public Map<String, String> unifiedOrder(Map<String, String> reqData) throws Exception {
        return this.unifiedOrder(reqData, config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }


    /**
     * 作用：统一下单<br>
     * 场景：公共号支付、扫码支付、APP支付
     *
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     */
    public Map<String, String> transfers(Map<String, String> reqData) throws Exception {
        String url = WXPayConstants.PROMOTION_TRANSFERS;
        String respXml = this.requestWithCert(url, this.fillRequestDataPayUser(reqData), config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
        return this.processResponseXml(respXml);
    }


    /**
     * 作用：统一下单<br>
     * 场景：公共号支付、扫码支付、APP支付
     *
     * @param reqData          向wxpay post的请求数据
     * @param connectTimeoutMs 连接超时时间，单位是毫秒
     * @param readTimeoutMs    读超时时间，单位是毫秒
     * @return API返回数据
     */
    public Map<String, String> unifiedOrder(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WXPayConstants.SANDBOX_UNIFIEDORDER_URL_SUFFIX;
        } else {
            url = WXPayConstants.UNIFIEDORDER_URL_SUFFIX;
        }
        if (this.notifyUrl != null) {
            reqData.put("notify_url", this.notifyUrl);
        }
        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData), connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }


    /**
     * 作用：查询订单<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     *
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     */
    public Map<String, String> orderQuery(Map<String, String> reqData) throws Exception {
        return this.orderQuery(reqData, config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }


    /**
     * 作用：查询订单<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     *
     * @param reqData          向wxpay post的请求数据 int
     * @param connectTimeoutMs 连接超时时间，单位是毫秒
     * @param readTimeoutMs    读超时时间，单位是毫秒
     * @return API返回数据
     */
    public Map<String, String> orderQuery(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WXPayConstants.SANDBOX_ORDERQUERY_URL_SUFFIX;
        } else {
            url = WXPayConstants.ORDERQUERY_URL_SUFFIX;
        }
        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData), connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }


    /**
     * 作用：撤销订单<br>
     * 场景：刷卡支付
     *
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     */
    public Map<String, String> reverse(Map<String, String> reqData) throws Exception {
        return this.reverse(reqData, config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }


    /**
     * 作用：撤销订单<br>
     * 场景：刷卡支付<br>
     * 其他：需要证书
     *
     * @param reqData          向wxpay post的请求数据
     * @param connectTimeoutMs 连接超时时间，单位是毫秒
     * @param readTimeoutMs    读超时时间，单位是毫秒
     * @return API返回数据
     */
    public Map<String, String> reverse(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WXPayConstants.SANDBOX_REVERSE_URL_SUFFIX;
        } else {
            url = WXPayConstants.REVERSE_URL_SUFFIX;
        }
        String respXml = this.requestWithCert(url, this.fillRequestData(reqData), connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }


    /**
     * 作用：关闭订单<br>
     * 场景：公共号支付、扫码支付、APP支付
     *
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     */
    public Map<String, String> closeOrder(Map<String, String> reqData) throws Exception {
        return this.closeOrder(reqData, config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }


    /**
     * 作用：关闭订单<br>
     * 场景：公共号支付、扫码支付、APP支付
     *
     * @param reqData          向wxpay post的请求数据
     * @param connectTimeoutMs 连接超时时间，单位是毫秒
     * @param readTimeoutMs    读超时时间，单位是毫秒
     * @return API返回数据
     */
    public Map<String, String> closeOrder(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WXPayConstants.SANDBOX_CLOSEORDER_URL_SUFFIX;
        } else {
            url = WXPayConstants.CLOSEORDER_URL_SUFFIX;
        }
        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData), connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }


    /**
     * 作用：申请退款<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     *
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     */
    public Map<String, String> refund(Map<String, String> reqData) throws Exception {
        return this.refund(reqData, this.config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }


    /**
     * 作用：申请退款<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付<br>
     * 其他：需要证书
     *
     * @param reqData          向wxpay post的请求数据
     * @param connectTimeoutMs 连接超时时间，单位是毫秒
     * @param readTimeoutMs    读超时时间，单位是毫秒
     * @return API返回数据
     */
    public Map<String, String> refund(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WXPayConstants.SANDBOX_REFUND_URL_SUFFIX;
        } else {
            url = WXPayConstants.REFUND_URL_SUFFIX;
        }
        String respXml = this.requestWithCert(url, this.fillRequestData(reqData), connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }


    /**
     * 作用：退款查询<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     *
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     */
    public Map<String, String> refundQuery(Map<String, String> reqData) throws Exception {
        return this.refundQuery(reqData, this.config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }


    /**
     * 作用：退款查询<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     *
     * @param reqData          向wxpay post的请求数据
     * @param connectTimeoutMs 连接超时时间，单位是毫秒
     * @param readTimeoutMs    读超时时间，单位是毫秒
     * @return API返回数据
     */
    public Map<String, String> refundQuery(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WXPayConstants.SANDBOX_REFUNDQUERY_URL_SUFFIX;
        } else {
            url = WXPayConstants.REFUNDQUERY_URL_SUFFIX;
        }
        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData), connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }


    /**
     * 作用：对账单下载（成功时返回对账单数据，失败时返回XML格式数据）<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     *
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     */
    public Map<String, String> downloadBill(Map<String, String> reqData) throws Exception {
        return this.downloadBill(reqData, this.config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }


    /**
     * 作用：对账单下载<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付<br>
     * 其他：无论是否成功都返回Map。若成功，返回的Map中含有return_code、return_msg、data，
     * 其中return_code为`SUCCESS`，data为对账单数据。
     *
     * @param reqData          向wxpay post的请求数据
     * @param connectTimeoutMs 连接超时时间，单位是毫秒
     * @param readTimeoutMs    读超时时间，单位是毫秒
     * @return 经过封装的API返回数据
     */
    public Map<String, String> downloadBill(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WXPayConstants.SANDBOX_DOWNLOADBILL_URL_SUFFIX;
        } else {
            url = WXPayConstants.DOWNLOADBILL_URL_SUFFIX;
        }
        String respStr = this.requestWithoutCert(url, this.fillRequestData(reqData), connectTimeoutMs, readTimeoutMs).trim();
        Map<String, String> ret;
        // 出现错误，返回XML数据
        if (respStr.indexOf("<") == 0) {
            ret = WXPayUtil.xmlToMap(respStr);
        } else {
            // 正常返回csv数据
            ret = new HashMap<>();
            ret.put("return_code", WXPayConstants.SUCCESS);
            ret.put("return_msg", "ok");
            ret.put("data", respStr);
        }
        return ret;
    }


    /**
     * 作用：交易保障<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     *
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     */
    public Map<String, String> report(Map<String, String> reqData) throws Exception {
        return this.report(reqData, this.config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }


    /**
     * 作用：交易保障<br>
     * 场景：刷卡支付、公共号支付、扫码支付、APP支付
     *
     * @param reqData          向wxpay post的请求数据
     * @param connectTimeoutMs 连接超时时间，单位是毫秒
     * @param readTimeoutMs    读超时时间，单位是毫秒
     * @return API返回数据
     */
    public Map<String, String> report(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WXPayConstants.SANDBOX_REPORT_URL_SUFFIX;
        } else {
            url = WXPayConstants.REPORT_URL_SUFFIX;
        }
        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData), connectTimeoutMs, readTimeoutMs);
        return WXPayUtil.xmlToMap(respXml);
    }


    /**
     * 作用：转换短链接<br>
     * 场景：刷卡支付、扫码支付
     *
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     */
    public Map<String, String> shortUrl(Map<String, String> reqData) throws Exception {
        return this.shortUrl(reqData, this.config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }


    /**
     * 作用：转换短链接<br>
     * 场景：刷卡支付、扫码支付
     *
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     */
    public Map<String, String> shortUrl(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WXPayConstants.SANDBOX_SHORTURL_URL_SUFFIX;
        } else {
            url = WXPayConstants.SHORTURL_URL_SUFFIX;
        }
        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData), connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }


    /**
     * 作用：授权码查询OPENID接口<br>
     * 场景：刷卡支付
     *
     * @param reqData 向wxpay post的请求数据
     * @return API返回数据
     */
    public Map<String, String> authCodeToOpenid(Map<String, String> reqData) throws Exception {
        return this.authCodeToOpenid(reqData, this.config.getHttpConnectTimeoutMs(), this.config.getHttpReadTimeoutMs());
    }


    /**
     * 作用：授权码查询OPENID接口<br>
     * 场景：刷卡支付
     *
     * @param reqData          向wxpay post的请求数据
     * @param connectTimeoutMs 连接超时时间，单位是毫秒
     * @param readTimeoutMs    读超时时间，单位是毫秒
     * @return API返回数据
     */
    public Map<String, String> authCodeToOpenid(Map<String, String> reqData, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String url;
        if (this.useSandbox) {
            url = WXPayConstants.SANDBOX_AUTHCODETOOPENID_URL_SUFFIX;
        } else {
            url = WXPayConstants.AUTHCODETOOPENID_URL_SUFFIX;
        }
        String respXml = this.requestWithoutCert(url, this.fillRequestData(reqData), connectTimeoutMs, readTimeoutMs);
        return this.processResponseXml(respXml);
    }


}
