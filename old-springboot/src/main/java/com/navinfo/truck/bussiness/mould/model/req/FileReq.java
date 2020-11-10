package com.navinfo.truck.bussiness.mould.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@ApiModel("模具附件上传对象")
public class FileReq {

    /**
     * 常见问题附件名称
     */
    @ApiModelProperty("附件名称")
    private String fileName;
    /**
     * 附件地址
     */
    @ApiModelProperty("附件地址")
    private String fileUrl;
}