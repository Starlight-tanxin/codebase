package com.zyb.mini.mall.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 支付订单（支付记录）
 * </p>
 *
 * @author tanxin
 * @since 2019-10-27
 */
@Data
@Accessors
@TableName("tb_pay_order")
public class PayOrder extends Model<PayOrder> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属用户id（外键）
     */
    private Long userId;

    /**
     * 订单id（由于订单种类较多）
     * 需要更具type来区分
     */
    private Long otherId;

    /**
     * 1 - 鉴赏支付单
     * 2 - 修复得支付单（修复是2次支付）
     * 3 - 书城得支付单
     * 4 - 助理得支付单
     * 5 - 充值得支付单
     * 6 - 提现得支付单
     */
    private Integer payType;

    /**
     * 支付单单号(我方)
     */
    private String payOrderNo;

    /**
     * 第三方支付订单号(wx方)
     */
    private String thirdOrderNo;

    /**
     * 修复订单得类型
     * 0 - 收款
     * 1 - 尾款
     */
    private Integer repairType;

    /**
     * 支付金额
     */
    private BigDecimal amont;

    /**
     * 实际支付金额(无优惠则通支付金额)
     */
    private BigDecimal realAmont;

    /**
     * 状态
     * 1 - 支付中
     * 2 - 支付完成 (后台发起支付了就算支付完成)
     * 3 - 确认支付完成（回调后确认完成）
     */
    private Integer state;

    /**
     * 支付发起得原始字符串
     */
    private String payRequestStr;

    /**
     * 支付回调得原始字符串
     */
    private String payCallbackStr;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    private LocalDateTime payCompleteTime;

    /**
     * 是不是收入(相对用户)
     * 0 - 不是 （本单支出）
     * 1 - 是 （本单收入）
     */
    private Boolean isIncome;
    /**
     * 是不是账户支付的 0-不是 1-是
     */
    private Boolean isAccountPay;

    /**
     * 退款单对应的原来支付的支付的单号
     */
    private String refundPayOrderNo;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
