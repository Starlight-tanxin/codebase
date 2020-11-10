package com.navinfo.truck.bussiness.mould.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel
public class BdMould {

    private Long id;

    @ApiModelProperty(value = "是否绑定供应商\n" +
            "0 - 否\n" +
            "1 - 是")
    private Integer bindStatus;

    @ApiModelProperty(value = "模具编号-唯一")
    private String mouldNo;

    @ApiModelProperty(value = "模具名称")
    private String mouldName;

    @ApiModelProperty(value = "模具状态 \t 1 - 在使用\n" +
            "\t2 - 未使用\n" +
            "\t3 - 废弃")
    private Integer mouldStatus;

    @ApiModelProperty(value = "模具价格（金额）保留4位小数")
    private BigDecimal mouldPrice;

    @ApiModelProperty(value = "供应商编码")
    private String supplierNo;

    @ApiModelProperty(value = "供应商名称(公司名称)")
    private String supplierName;

    @ApiModelProperty(value = "供应商联系人")
    private String supplierContacts;

    @ApiModelProperty(value = "供应商手机号码")
    private String supplierPhone;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人ID")
    private Long createBy;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "修改人id")
    private Long updateBy;

    @ApiModelProperty(value = "创建人名字")
    private String createName;

    @ApiModelProperty(value = "修改人名字")
    private String updateName;

    @ApiModelProperty(value = "历史操作集合")
    private List<BdMouldHistory> historyList;

    @ApiModelProperty(value = "附件对象集合")
    private List<BdMouldFile> fileList;

    public List<BdMouldHistory> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<BdMouldHistory> historyList) {
        this.historyList = historyList;
    }

    public List<BdMouldFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<BdMouldFile> fileList) {
        this.fileList = fileList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(Integer bindStatus) {
        this.bindStatus = bindStatus;
    }

    public String getMouldNo() {
        return mouldNo;
    }

    public void setMouldNo(String mouldNo) {
        this.mouldNo = mouldNo == null ? null : mouldNo.trim();
    }

    public String getMouldName() {
        return mouldName;
    }

    public void setMouldName(String mouldName) {
        this.mouldName = mouldName == null ? null : mouldName.trim();
    }

    public Integer getMouldStatus() {
        return mouldStatus;
    }

    public void setMouldStatus(Integer mouldStatus) {
        this.mouldStatus = mouldStatus;
    }

    public BigDecimal getMouldPrice() {
        return mouldPrice;
    }

    public void setMouldPrice(BigDecimal mouldPrice) {
        this.mouldPrice = mouldPrice;
    }

    public String getSupplierNo() {
        return supplierNo;
    }

    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo == null ? null : supplierNo.trim();
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public String getSupplierContacts() {
        return supplierContacts;
    }

    public void setSupplierContacts(String supplierContacts) {
        this.supplierContacts = supplierContacts == null ? null : supplierContacts.trim();
    }

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone == null ? null : supplierPhone.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName == null ? null : updateName.trim();
    }
}