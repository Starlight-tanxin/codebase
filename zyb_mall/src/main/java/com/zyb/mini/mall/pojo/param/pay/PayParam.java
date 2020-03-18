package com.zyb.mini.mall.pojo.param.pay;

import com.zyb.mini.mall.constant.Mock;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tanxin
 * @date 2019/10/31
 */
@Data
public class PayParam implements Serializable {
    private static final long serialVersionUID = -2908596024316889490L;

    @ApiModelProperty(value = "订单ID ", example = Mock.NUMBER, required = true)
    @NotNull
    private Long orderId;

    @ApiModelProperty(value = "订单的类型 \n" +
            "1 - 鉴赏支付单\n" +
            "2 - 修复得支付单（修复是2次支付）\n" +
            "3 - 书城得支付单\n" +
            "4 - 助力打卡得支付单\n" +
            "5 - 充值得支付单\n" +
            "6 - 提现得支付单", example = Mock.NUMBER, required = true)
    @NotNull
    private Integer orderType;

    @ApiModelProperty(value = "修复订单得类型 \n PS：2-修复 必填参数）\n" +
            "0 - 首款\n" +
            "1 - 尾款", example = Mock.NUMBER)
    private Integer repairType;

    @ApiModelProperty(value = "金额（单位元）\n PS： 4-助力打卡/5-充值/6-提现  必填参数", example = Mock.NUMBER)
    private BigDecimal amount;

    @ApiModelProperty(value = "用户的收获地址 - 订单类型 = 3 时 不能为空", example = Mock.NUMBER)
    private Long userAddressId;
}
