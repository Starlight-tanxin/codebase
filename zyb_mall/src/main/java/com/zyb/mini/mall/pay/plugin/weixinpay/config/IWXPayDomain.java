package com.zyb.mini.mall.pay.plugin.weixinpay.config;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 域名管理，实现主备域名自动切换
 */
public interface IWXPayDomain {
    /**
     * 上报域名网络状况
     *
     * @param domain            域名。 比如：api.mch.weixin.qq.com
     * @param elapsedTimeMillis 耗时
     * @param ex                网络请求中出现的异常。
     *                          null表示没有异常
     *                          ConnectTimeoutException，表示建立网络连接异常
     *                          UnknownHostException， 表示dns解析异常
     */
    void report(final String domain, long elapsedTimeMillis, final Exception ex);

    /**
     * 获取域名
     *
     * @param config 配置
     * @return 域名
     */
    DomainInfo getDomain(final WXPayConfig config);

    @Data
    @AllArgsConstructor
    class DomainInfo {
        // 域名
        public String domain;
        // 该域名是否为主域名。例如:api.mch.weixin.qq.com为主域名
        public boolean primaryDomain;
    }

}