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
public class ProBackParam implements Serializable {
    private static final long serialVersionUID = -5570362346371992272L;

    @ApiModelProperty(value = "鉴赏单得id", example = Mock.NUMBER, required = true)
    @NotNull
    private Long id;

    @ApiModelProperty(value = "退回得理由", required = true)
    @NotBlank
    private String proBackMsg;

    @ApiModelProperty(value = "退回得其他原因", required = true)
    @NotBlank
    private String proBackDetail;
}
