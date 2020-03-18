package com.zyb.mini.mall.pojo.param.pay;

import com.zyb.mini.mall.constant.Mock;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: Tx
 * @date: 2019/11/18
 */
@ApiModel
@Data
public class CheckPayParam implements Serializable {
    private static final long serialVersionUID = 2846375897584888836L;

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
}
