package com.zyb.mini.mall.service.trade;

import com.zyb.mini.mall.pojo.dto.BeforePayDTO;
import com.zyb.mini.mall.pojo.entity.PayOrder;
import com.zyb.mini.mall.service.BaseService;

import java.math.BigDecimal;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/10/29 星期二 22:08.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */
public interface PayOrderService extends BaseService<PayOrder> {

    /**
     * 创建支付订单
     *
     * @param dto 支付单的数据模型
     * @return 支付单
     */
    PayOrder createPayOrder(BeforePayDTO dto);

    /**
     * 创建账户支付的订单
     * @param dto 支付单的数据模型
     * @return 支付单
     */
    PayOrder createPayOrderByAccountPay(BeforePayDTO dto);

    /**
     * 创建提现
     * @param userId 用户id
     * @param cashAmount 提现金额
     * @return
     */
    PayOrder createCash(Long userId, BigDecimal cashAmount);
}