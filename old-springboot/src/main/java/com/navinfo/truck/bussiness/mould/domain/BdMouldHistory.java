package com.navinfo.truck.bussiness.mould.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("模具操作历史")
public class BdMouldHistory {

    private Long id;

    @ApiModelProperty(value = "模具id")
    private Long tbBdMouldId;

    @ApiModelProperty(value = "操作人名字")
    private String operationName;

    @ApiModelProperty(value = "操作人id")
    private Long operationId;

    @ApiModelProperty(value = "操作时间")
    private Date operationTime;

    @ApiModelProperty(value = "操作名称")
    private String operationItem;

    @ApiModelProperty(value = "操作内容（供应商）")
    private String operationInfo;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(hidden = true)
    private String operationDetailData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTbBdMouldId() {
        return tbBdMouldId;
    }

    public void setTbBdMouldId(Long tbBdMouldId) {
        this.tbBdMouldId = tbBdMouldId;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName == null ? null : operationName.trim();
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public String getOperationItem() {
        return operationItem;
    }

    public void setOperationItem(String operationItem) {
        this.operationItem = operationItem == null ? null : operationItem.trim();
    }

    public String getOperationInfo() {
        return operationInfo;
    }

    public void setOperationInfo(String operationInfo) {
        this.operationInfo = operationInfo == null ? null : operationInfo.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getOperationDetailData() {
        return operationDetailData;
    }

    public void setOperationDetailData(String operationDetailData) {
        this.operationDetailData = operationDetailData == null ? null : operationDetailData.trim();
    }
}