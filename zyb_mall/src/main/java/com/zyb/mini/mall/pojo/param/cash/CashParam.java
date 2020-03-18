package com.zyb.mini.mall.pojo.param.cash;

import com.zyb.mini.mall.constant.Mock;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: Tx
 * @date: 2019/11/13
 */
@Data
public class CashParam implements Serializable {
    private static final long serialVersionUID = -4192728514252927239L;

    @ApiModelProperty(value = "提现金额 单位元", example = Mock.NUMBER, required = true)
    @NotNull
    private BigDecimal amount;
}
