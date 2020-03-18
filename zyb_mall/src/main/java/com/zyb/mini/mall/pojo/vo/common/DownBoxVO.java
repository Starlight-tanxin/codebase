package com.zyb.mini.mall.pojo.vo.common;

import com.zyb.mini.mall.constant.Mock;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用下拉框vo
 *
 * @author: Tx
 * @date: 2019/10/27
 */
@ApiModel(value = "通用下拉框VO")
@Data
public class DownBoxVO implements Serializable {

    private static final long serialVersionUID = -5980407479641057494L;

    @ApiModelProperty(value = "id", example = Mock.NUMBER)
    private Integer id;

    @ApiModelProperty(value = "类型得编号", example = Mock.NUMBER)
    private String typeNo;

    @ApiModelProperty(value = "类型名字")
    private String typeName;

    @ApiModelProperty(value = "图片")
    private String typeIcon;
}
