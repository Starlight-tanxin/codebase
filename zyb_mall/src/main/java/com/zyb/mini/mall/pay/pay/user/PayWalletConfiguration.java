package com.zyb.mini.mall.pay.pay.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.weixin4j.pay.WeixinPay;
import org.weixin4j.pay.component.PayWalletComponment;

/**
 * @author Created by 谭健 on 2019/10/30. 星期三. 15:05.
 * © All Rights Reserved.
 * <p>
 * 交给spring 管理，保证这2个类是单例
 */
@Configuration
public class PayWalletConfiguration {


    @Bean
    WeixinPay weixinPay() {
        return new WeixinPay();
    }

    @Bean
    PayWalletComponment payWalletComponment(WeixinPay weixinPay) {
        return new PayWalletComponment(weixinPay);
    }
}
