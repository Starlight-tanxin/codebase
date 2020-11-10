package com.navinfo.truck.bussiness.mould.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * <p>description</p>
 *
 * @author TanXin
 * @date 2020/11/4 17:20
 */
@Data
@ApiModel("编辑更新模具请求对象")
@Validated
public class UpdateMouldReq extends AddMouldReq {

    @ApiModelProperty(value = "主键id", required = true)
    @NotNull
    private Long id;
}
