package com.zyb.mini.mall.pay.plugin.weixinpay.config;


import com.zyb.mini.mall.pay.plugin.weixinpay.util.WXPayUtil;

import java.io.InputStream;


public interface WXPayConfig {


    /**
     * 获取 App ID
     *
     * @return App ID
     */
    default String getAppID() {
        return "wxc7f2f7a96928c55f";
    }


    /**
     * 获取 Mch ID
     *
     * @return Mch ID
     */
    default String getMchID() {
        return "1558652881";
    }

    /**
     * 获取mch_appid
     * @return mch_appid
     */
    default String getMchAppId() {
        return "ww1fb44d79434801ae";
    }

    /**
     * 获取 API 密钥
     * <p>
     * API 密钥不是公众号的密钥，是通过微信商户平台，用户自行设置的密钥
     *
     * @return API密钥
     */
    default String getKey() {
        return "lMxGOtZw90pg3nSZLi3PnfMsxu0X3RKU";
    }


    /**
     * 默认回调地址
     * 发起支付时，如果回调地址为空，则自动转换到默认回调地址
     * <p>
     * 默认回调地址请设置拦截器不要拦截，属于开放接口
     */
    default String defaultNotifyUrl() {
        return "https://zyb.hnsxyts.com/zyb/order/pay/callback";
    }

    /**
     * 获取商户证书内容
     *
     * @return 商户证书内容
     */
    default InputStream getCertStream() {
        return null;
    }


    /**
     * HTTP(S) 连接超时时间，单位毫秒
     */
    default int getHttpConnectTimeoutMs() {
        return 6 * WXPayConstants.SECOND;
    }

    /**
     * HTTP(S) 读数据超时时间，单位毫秒
     */
    default int getHttpReadTimeoutMs() {
        return 8 * WXPayConstants.SECOND;
    }


    /**
     * 获取WXPayDomain, 用于多域名容灾自动切换
     */
    default IWXPayDomain getWXPayDomain() {
        return new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, final Exception ex) {
                WXPayUtil.getLogger().info("WXPayConfig : 上报域名网络状况");
                WXPayUtil.getLogger().info("WXPayConfig : 域名 ： {}", domain);
                WXPayUtil.getLogger().info("WXPayConfig : 时间 ： {}", elapsedTimeMillis);
            }

            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new DomainInfo(
                        "api.mch.weixin.qq.com",
                        true
                );
            }
        };
    }


    /**
     * 是否自动上报。
     * 若要关闭自动上报，子类中实现该函数返回 false 即可。
     */
    default boolean shouldAutoReport() {
        return false;
    }

    /**
     * 进行健康上报的线程的数量
     */
    default int getReportWorkerNum() {
        return 6;
    }


    /**
     * 健康上报缓存消息的最大数量。会有线程去独立上报
     * 粗略计算：加入一条消息200B，10000消息占用空间 2000 KB，约为2MB，可以接受
     */
    default int getReportQueueMaxSize() {
        return 10000;
    }

    /**
     * 批量上报，一次最多上报多个数据
     */
    default int getReportBatchSize() {
        return 10;
    }

}
