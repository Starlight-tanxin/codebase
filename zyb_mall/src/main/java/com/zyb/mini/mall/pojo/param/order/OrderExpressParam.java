package com.zyb.mini.mall.pojo.param.order;

import com.zyb.mini.mall.constant.Mock;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: Tx
 * @date: 2019/11/2
 */
@Data
public class OrderExpressParam implements Serializable {
    private static final long serialVersionUID = 1317909497649272506L;

    @ApiModelProperty(value = "订单id",example = Mock.NUMBER,required = true)
    @NotNull
    private Long orderId;

    @ApiModelProperty(value = "快递单号")
    @NotNull
    private String expressNo;

    @ApiModelProperty(value = "快递公司代码 简称",example = "EMS")
    @NotNull
    private String expressCompanyNo;
}
