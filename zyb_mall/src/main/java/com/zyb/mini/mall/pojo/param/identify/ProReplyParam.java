package com.zyb.mini.mall.pojo.param.identify;

import com.zyb.mini.mall.constant.Mock;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: Tx
 * @date: 2019/11/23
 */
@Data
public class ProReplyParam implements Serializable {
    private static final long serialVersionUID = 7555035598172150143L;

    @ApiModelProperty(value = "鉴赏单得id", example = Mock.NUMBER, required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "真伪状态 0-假  1-真  2-存疑", example = Mock.NUMBER, required = true)
    @NotNull
    private Integer checkState;

    @ApiModelProperty(value = "鉴赏得年代得id", example = Mock.NUMBER, required = true)
    @NotNull
    private Integer proYearId;

    @ApiModelProperty(value = "专家回复得消息", example = Mock.NUMBER, required = true)
    @NotBlank
    private String proReplyMsg;
}
