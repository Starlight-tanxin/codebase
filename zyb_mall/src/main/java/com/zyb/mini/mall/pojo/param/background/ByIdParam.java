package com.zyb.mini.mall.pojo.param.background;

import com.zyb.mini.mall.constant.Mock;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author Jpping
 * @date 2019/11/8
 */
@ApiModel("按id操作数据")
@Data
@Accessors
public class ByIdParam {
    private static final long serialVersionUID = 8800993860253556075L;


    @ApiModelProperty(value = "主键id", example = Mock.NUMBER, required = true)
    @NotNull(message = "主键id不能为空")
    private Long id;


}
