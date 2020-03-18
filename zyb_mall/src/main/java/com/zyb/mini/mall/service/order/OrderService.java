package com.zyb.mini.mall.service.order;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyb.mini.mall.pay.user.pay.NotifyField;
import com.zyb.mini.mall.pojo.dto.BeforePayDTO;
import com.zyb.mini.mall.pojo.entity.ShopOrder;
import com.zyb.mini.mall.pojo.entity.ShopOrderEvaluate;
import com.zyb.mini.mall.pojo.param.BasePageParam;
import com.zyb.mini.mall.pojo.param.order.OrderExpressParam;
import com.zyb.mini.mall.pojo.param.order.OrderIndexParam;
import com.zyb.mini.mall.pojo.param.pay.AccountPayParam;
import com.zyb.mini.mall.pojo.param.pay.CheckPayParam;
import com.zyb.mini.mall.pojo.param.pay.PayParam;
import com.zyb.mini.mall.pojo.vo.MngOrderVo;
import com.zyb.mini.mall.service.BaseService;

import java.util.List;

/**
 * @author tanxin
 * @date 2019/10/30
 */
public interface OrderService extends BaseService<ShopOrder> {

    ShopOrder detailOrder(Long orderId);

    ShopOrderEvaluate addEvaluate(ShopOrderEvaluate shopOrderEvaluate);

    IPage<ShopOrderEvaluate> selectPageEvaluate(BasePageParam pageParam);

    ShopOrder sendGoods(OrderExpressParam param);

    ShopOrder cancelOrder(Long orderId);

    ShopOrder applyRefund(Long orderId);

    ShopOrder refund(Long orderId);

    BeforePayDTO getBeforePayDTO(Long userId, PayParam payParam);

    boolean callbackProcess(NotifyField notifyField, String xmlStr);


    List<MngOrderVo> listByState(int orderState);

    /**
     * 账户金额支付
     *
     * @param userId 用户id
     * @param param  参数
     */
    void accountPay(Long userId, AccountPayParam param);

    /**
     * 检查是否支付成功
     *
     * @param param 参数
     * @return 是否成功
     */
    Boolean checkPaySuccess(CheckPayParam param);

    Page<ShopOrder> selectPageByParam(OrderIndexParam param);

}
