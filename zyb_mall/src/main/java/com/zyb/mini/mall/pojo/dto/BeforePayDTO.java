package com.zyb.mini.mall.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>生产支付订单之前的数据模型</p>
 *
 * @author: Tx
 * @date: 2019/11/6
 */
@Data
public class BeforePayDTO implements Serializable {
    private static final long serialVersionUID = 182257695847022442L;

    private Long otherId;

    private BigDecimal amount;

    private Integer repairType;

    private Long userId;

    /**
     * 是不是收入(相对用户)
     * 0 - 不是 （本单支出）
     * 1 - 是 （本单收入）
     */
    private Boolean isIncome;

    /**
     * 支付单类型
     */
    private Integer payType;


}
