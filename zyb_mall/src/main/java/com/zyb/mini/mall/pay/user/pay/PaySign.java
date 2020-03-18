package com.zyb.mini.mall.pay.user.pay;

import lombok.Data;

/**
 * @author Created by 谭健 on 2019/10/30. 星期三. 11:40.
 * © All Rights Reserved.
 *
 * 这是回传给前端发起支付的类
 */

@Data
public class PaySign {

    /**
     * 时间戳
     */
    private String timestamp;
    /**
     * 打包信息
     */
    private String packageInfo;
    /**
     * 签名类型
     */
    private String signType;
    /**
     * 加密的支付串
     */
    private String paySign;
    /**
     * 随机码
     */
    private String nonceStr;

}
