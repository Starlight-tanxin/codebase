package com.navinfo.truck.bussiness.mould.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import java.util.Date;

/**
 * <p>description</p>
 *
 * @author TanXin
 * @date 2020/11/4 14:53
 */
@Data
@ApiModel("查询模具请求对象")
@Validated
public class MouldReq {

    @Min(value = 1, message = "页码最小不能小于1")
    @ApiModelProperty(value = "页码【默认1】", required = true)
    private int pageNum;

    @Min(value = 1, message = "每页显示数最小不能小于1")
    @ApiModelProperty(value = "每页显示数【默认10】", required = true)
    private int pageSize;

    @ApiModelProperty(value = "模具编号")
    private String mouldNo;

    @ApiModelProperty(value = "供应商编号")
    private String supplierNo;

    @ApiModelProperty(value = "供应商公司命")
    private String supplierName;

    @ApiModelProperty(value = "供应商联系方式")
    private String supplierPhone;

    @ApiModelProperty(value = "模具状态 \t 1 - 在使用\n" +
            "\t2 - 未使用\n" +
            "\t3 - 废弃")
    private Integer mouldStatus;

    @ApiModelProperty(value = "创建时间--起始 yyyy-MM-dd hh:mm:ss")
    private Date createTimeStart;

    @ApiModelProperty(value = "创建时间--结束 yyyy-MM-dd hh:mm:ss")
    private Date createTimeEnd;

    @ApiModelProperty(value = "修改时间--结束 yyyy-MM-dd hh:mm:ss")
    private Date updateTimeStart;

    @ApiModelProperty(value = "修改时间--结束 yyyy-MM-dd hh:mm:ss")
    private Date updateTimeEnd;


}
