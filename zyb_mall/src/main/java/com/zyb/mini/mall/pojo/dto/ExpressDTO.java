package com.zyb.mini.mall.pojo.dto;

import com.zyb.mini.mall.constant.Mock;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Tx
 * @date: 2019/11/3
 */
@ApiModel("物流对象DTO")
@Data
public class ExpressDTO implements Serializable {
    private static final long serialVersionUID = 5334770078688045453L;

    @ApiModelProperty(value = "消息体，请忽略")
    String message;

    @ApiModelProperty(value = "快递单当前状态，包括0在途，1揽收，2疑难，3签收，4退签，5派件，6退回，7转投 等8个状态", example = Mock.NUMBER)
    String state;

    @ApiModelProperty(value = "通讯状态，请忽略", example = Mock.NUMBER)
    String status;

    @ApiModelProperty(value = "快递单明细状态标记，暂未实现，请忽略")
    String condition;

    /**
     * 是否签收标记 0:未签收  1：1签收
     */
    @ApiModelProperty(value = "是否签收标记 0:未签收 \n 1：签收", example = Mock.NUMBER)
    String ischeck;

    @ApiModelProperty(value = "快递公司编码,一律用小写字母")
    String com;

    @ApiModelProperty(value = "快递单号")
    String nu;

    @ApiModelProperty(value = "物流对象 数组，包含多个对象，每个对象字段如展开所示")
    List<ExpressLogisticsDTO> data;

}
