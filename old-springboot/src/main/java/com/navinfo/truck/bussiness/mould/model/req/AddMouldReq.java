package com.navinfo.truck.bussiness.mould.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>description</p>
 *
 * @author TanXin
 * @date 2020/11/4 17:10
 */
@Data
@ApiModel("新增模具请求对象")
@Validated
public class AddMouldReq {


    @ApiModelProperty(value = "是否绑定供应商\n" +
            "0 - 否\n" +
            "1 - 是", required = true)
    @NotNull
    private Integer bindStatus;

    @ApiModelProperty(value = "模具名称", required = true)
    @NotBlank
    private String mouldName;

    @ApiModelProperty(value = "模具状态 \t 1 - 在使用\n" +
            "\t2 - 未使用\n" +
            "\t3 - 废弃", required = true)
    @NotNull
    private Integer mouldStatus;

    @ApiModelProperty(value = "模具价格（金额）保留4位小数")
    @NotNull
    private BigDecimal mouldPrice;

    @ApiModelProperty(value = "供应商编码")
    private String supplierNo;

    @ApiModelProperty(value = "供应商名称(公司名称)")
    private String supplierName;

    @ApiModelProperty(value = "供应商联系人")
    private String supplierContacts;

    @ApiModelProperty(value = "供应商手机号码")
    private String supplierPhone;

    @ApiModelProperty(value = "备注", required = true)
    @NotNull
    private String remarks;
    // 当前登录用户的名字和id
    @ApiModelProperty(hidden = true)
    private String username;
    @ApiModelProperty(hidden = true)
    private Long userId;

    //    @ApiModelProperty(value = "上传后的附件地址  多个使用,号（英文逗号）分割")
//    private String fileUrls;
    @ApiModelProperty(value = "上传后的附件地址，集合")
    private List<FileReq> fileReqList;
}
