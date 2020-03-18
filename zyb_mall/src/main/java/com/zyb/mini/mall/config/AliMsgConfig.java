package com.zyb.mini.mall.config;

/**
 * @author: Tx
 * @date: 2019/11/3
 */
public interface AliMsgConfig {

    /**
     * key
     */
    String ACCESS_KEY_ID = "LTAI4FtqtEHwQLezPjggiFrY";

    /**
     * 密钥
     */
    String ACCESS_KEY_SECRET = "YYOXmnMNfJgZ2TvaOGJ3tdBFbE0cJy";

    /**
     * 区域id
     */
    String REGION_ID = "cn-hangzhou";

    /**
     * 公司签名
     */
    String SIGN_NAME = "湘阅";

    /**
     * 请求地址
     */
    String DOMAIN = "dysmsapi.aliyuncs.com";

    /**
     * 版本号
     */
    String VERSIO = "2017-05-25";

    /**
     * 短信验证吗 模板id (身份验证)
     */
    String CODE_TEMPLATE = "SMS_176595646";

    /**
     * 退款通知 模板id
     */
    String REFUND_TEMPLATE = "SMS_176937215";
    /**
     * 修复订单已回复通知
     */
    String MAINTAIN_TEMPLATE = "SMS_176941453";
    /**
     * 订单下单成功通知
     */
    String ORDER_TEMPLATE = "SMS_176936470";
    /**
     * 鉴赏通知
     */
    String IDENTIFY_TEMPLATE = "SMS_176941397";

    /**
     * 调用的接口 （发送信息）
     */
    String API_SEND_SMS = "SendSms";
}
