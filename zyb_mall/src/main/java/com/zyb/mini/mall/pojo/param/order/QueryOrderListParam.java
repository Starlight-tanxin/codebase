package com.zyb.mini.mall.pojo.param.order;

import com.zyb.mini.mall.constant.Mock;
import com.zyb.mini.mall.pojo.param.BasePageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author JPPing
 * @date 2019/11/08
 */
@Data
public class QueryOrderListParam extends BasePageParam implements Serializable {
    private static final long serialVersionUID = -8210338855664012680L;

    @ApiModelProperty(value = "订单编号")
    String orderNo;

    @ApiModelProperty(value = "订单状态", example = Mock.NUMBER)
    Integer orderState;
}
