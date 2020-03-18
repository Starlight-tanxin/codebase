package com.zyb.mini.mall.pojo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author tanxin
 * @date 2019/8/16
 */
@ApiModel("基础分页参数对象")
@Data
public class BasePageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "页数", example = "1", required = true)
    @NotNull(message = "页数不能为空")
    private Integer page;

    @ApiModelProperty(value = "每页大小", example = "10", required = true)
    @NotNull(message = "每页大小不能为空")
    private Integer pageSize;
}
