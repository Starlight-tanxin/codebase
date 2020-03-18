package com.zyb.mini.mall.pojo.param.order;

import com.zyb.mini.mall.constant.Mock;
import com.zyb.mini.mall.pojo.param.BasePageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: Tx
 * @date: 2019/11/13
 */
@Data
public class OrderIndexParam extends BasePageParam {
    private static final long serialVersionUID = -5938098607518822619L;

    @ApiModelProperty(value = "订单状态 \n 1-待支付 \n 2-待发货 (可申请退款) \n 3-待收货 （可申请退款） \n 4-待评价  \n 5-已评价 \n 6-取消订单 \n 7-申请退款\n 0-已退款", example = Mock.NUMBER)
    private Integer state;

    @ApiModelProperty(hidden = true)
    private Long userId;
}
