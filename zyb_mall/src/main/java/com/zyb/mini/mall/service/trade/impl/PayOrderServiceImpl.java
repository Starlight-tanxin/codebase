package com.zyb.mini.mall.service.trade.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyb.mini.mall.constant.CommonConstant;
import com.zyb.mini.mall.constant.DateFormatter;
import com.zyb.mini.mall.constant.PayOrderState;
import com.zyb.mini.mall.constant.PayOrderType;
import com.zyb.mini.mall.core.Status;
import com.zyb.mini.mall.dao.PayOrderMapper;
import com.zyb.mini.mall.exception.ApiException;
import com.zyb.mini.mall.pay.pay.user.PayUser;
import com.zyb.mini.mall.pay.pay.user.PayUserParam;
import com.zyb.mini.mall.pojo.dto.BeforePayDTO;
import com.zyb.mini.mall.pojo.entity.PayOrder;
import com.zyb.mini.mall.pojo.entity.User;
import com.zyb.mini.mall.service.trade.PayOrderService;
import com.zyb.mini.mall.service.user.UserService;
import com.zyb.mini.mall.utils.CodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weixin4j.pay.WeixinPayException;
import org.weixin4j.pay.model.paywallet.TransPayWalletResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Dream what you want to dream;
 * go where you want to go;
 * be what you want to be;
 * because you have only one life and one chance to do all the things you want to do．
 * - - Online zuozuo / Frank / TANJIAN
 *
 * @author Created by 谭健 on 2019/10/29 星期二 22:09.
 * @link <a href="http://qm.qq.com/cgi-bin/qm/qr?k=FJVK7slBx7qC5tKm_KdFTbwWOFHq1ASt">Join me</a>
 * @link <a href="http://blog.csdn.net/qq_15071263">CSDN Home Page</a>
 * <p>
 * <p>
 * © All Rights Reserved.
 */
@Slf4j
@Service
public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrder> implements PayOrderService {


    @Autowired
    private UserService userService;

    @Autowired
    private PayUser payUser;

    /**
     * 运行环境
     */
    @Value("${spring.profiles.active}")
    private String profiles;


    private PayOrder createPay(BeforePayDTO dto) {
        String payOrderNo;
        LocalDateTime now = LocalDateTime.now();
        // 确保单节点唯一
        synchronized (this) {
            payOrderNo = now.format(DateFormatter.DATE_TIME_ORDER) + CodeUtils.createRandomCharData(16);
        }
        BigDecimal amount = dto.getAmount();
        PayOrder payOrder = new PayOrder()
                .setPayOrderNo(payOrderNo)
                .setCreatedTime(now)
                .setIsIncome(dto.getIsIncome())
                .setPayType(dto.getPayType())
                .setUserId(dto.getUserId())
                .setOtherId(dto.getOtherId())
                .setAmont(amount)
                .setRealAmont(amount)
                .setPayRequestStr("")
                .setRepairType(dto.getRepairType())
                .setState(PayOrderState.PAYING);
        return payOrder;
    }

    @Override
    public PayOrder createPayOrder(BeforePayDTO dto) {
        PayOrder payOrder = createPay(dto);
        //TODO 普通支付 支付中状态订
        payOrder.setState(PayOrderState.PAYING);
        save(payOrder);
        return payOrder;
    }


    @Override
    public PayOrder createPayOrderByAccountPay(BeforePayDTO dto) {
        PayOrder payOrder = createPay(dto);
        // TODO 账户金额支付直接完成
        payOrder.setIsAccountPay(true);
        payOrder.setState(PayOrderState.PAY_FINAL);
        save(payOrder);
        return payOrder;
    }


    /**
     * 提现操作
     * 此处发送错误不会滚
     *
     * @param userId     用户id
     * @param cashAmount 金额
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayOrder createCash(Long userId, BigDecimal cashAmount) {
        synchronized (this) {
            try {
                User user = userService.getById(userId);
                if (user.getActualAmount().doubleValue() >= cashAmount.doubleValue()) {
                    BigDecimal accountAmount = user.getActualAmount().subtract(cashAmount);
                    user.setActualAmount(accountAmount);
                    if (userService.updateById(user)) {
                        LocalDateTime now = LocalDateTime.now();
                        // 提现订单号不能又其他的符合
                        String payOrderNo = now.format(DateFormatter.DATE_TIME_ORDER) + CodeUtils.createRandomCharData(16);
                        log.info("用户 ----》 {} 的提现单号 ----》 {}", user.getWxOpenId(), payOrderNo);
                        PayOrder payOrder = new PayOrder()
                                .setPayOrderNo(payOrderNo)
                                .setCreatedTime(LocalDateTime.now())
                                .setIsIncome(true)
                                .setPayType(PayOrderType.TX_PAY)
                                .setUserId(userId)
                                .setOtherId(0L)
                                .setAmont(cashAmount)
                                .setRealAmont(cashAmount)
                                .setPayRequestStr(CommonConstant.CASH_MAKE)
                                .setState(PayOrderState.PAYING);
                        save(payOrder);
                        PayUserParam param = new PayUserParam();
                        if (CommonConstant.PROD.equals(profiles)) {
                            param.setAmount(cashAmount.multiply(CommonConstant.FEN_2_YUAN).intValue());
                        } else {
                            // 最低提现金额 单位分
                            param.setAmount(30);
                        }
                        param.setOrderNumber(payOrderNo);
                        param.setOpenId(user.getWxOpenId());
                        TransPayWalletResult result = payUser.pay(param);
                        if (CommonConstant.SUCCESS.equalsIgnoreCase(result.getResult_code()) && CommonConstant.SUCCESS.equalsIgnoreCase(result.getReturn_code())) {
                            //TODO 业务成功
                            payOrder.setState(PayOrderState.PAY_FINAL);
                            payOrder.setPayCompleteTime(LocalDateTime.now());
                            payOrder.setPayCallbackStr(JSON.toJSONString(result));
                            payOrder.setThirdOrderNo(result.getPayment_no());
                            updateById(payOrder);
                            return payOrder;
                        }
                    }
                    throw new ApiException(Status.SYS_ERROR, "提现失败！错误未知");
                } else {
                    throw new ApiException(Status.CASH_ERROR_AMOUNT);
                }
            } catch (WeixinPayException e) {
                log.error("微信提现出现错误 错误 ： {} ; \t  错误描述 ： {}", e, e.getMessage());
                throw new ApiException(Status.SYS_ERROR, "提现失败！错误未知");
            }

        }
    }
}
