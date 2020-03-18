package com.zyb.mini.mall.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: Tx
 * @date: 2019/11/3
 */
@ApiModel("物流详情")
@Data
public class ExpressLogisticsDTO implements Serializable {

    private static final long serialVersionUID = -6923071869072191567L;

    @ApiModelProperty(value = "物流轨迹节点内容")
    String context;

    @ApiModelProperty(value = "时间，原始格式")
    String time;

    @ApiModelProperty(value = "格式化后时间")
    String ftime;

    @ApiModelProperty(value = "本数据元对应的签收状态")
    String status;

    @ApiModelProperty(value = "本数据元对应的行政区域的编码")
    String areaCode;

    @ApiModelProperty(value = "本数据元对应的行政区域的名称")
    String areaName;
}
