package com.nongyeos.loan.admin.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-11-14
 */
public class BusRefund {
    /**
     * 退费详情id
     */
    private String id;

    /**
     * 改部门id
     */
    private String orgId;

    /**
     * 组织机构名称
     */
    private String orgName;

    private String intoPieceId;

    /**
     * 客户类型
     */
    private Integer use;

    /**
     * 客户姓名
     */
    private String accountName;

    /**
     * 身份证号
     */
    private String certificateNo;

    /**
     * 贷款总金额
     */
    private String accountNo;

    /**
     * 手机号
     */
    private String mobileNo;

    /**
     * 服务费或担保费总金额
     */
    private BigDecimal totalAmount;

    /**
     * 付款人
     */
    private String payer;

    /**
     * 支付人姓名
     */
    private String payerIdcard;

    /**
     * 支付方式(1，站长代付 2，微信支付)
     */
    private String payWay;

    /**
     * 退款状态：I（退款处理中），S（退款成功），F（退款失败）
     */
    private String status;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 更新日期
     */
    private Date updateDate;

    /**
     * 退款类型（1，服务费 2，农资担保金）
     */
    private Integer refundType;

    /**
     * 支付成功的微信订单号
     */
    private String paymentMerid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getIntoPieceId() {
        return intoPieceId;
    }

    public void setIntoPieceId(String intoPieceId) {
        this.intoPieceId = intoPieceId == null ? null : intoPieceId.trim();
    }

    public Integer getUse() {
        return use;
    }

    public void setUse(Integer use) {
        this.use = use;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo == null ? null : certificateNo.trim();
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo == null ? null : mobileNo.trim();
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer == null ? null : payer.trim();
    }

    public String getPayerIdcard() {
        return payerIdcard;
    }

    public void setPayerIdcard(String payerIdcard) {
        this.payerIdcard = payerIdcard == null ? null : payerIdcard.trim();
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay == null ? null : payWay.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getRefundType() {
        return refundType;
    }

    public void setRefundType(Integer refundType) {
        this.refundType = refundType;
    }

    public String getPaymentMerid() {
        return paymentMerid;
    }

    public void setPaymentMerid(String paymentMerid) {
        this.paymentMerid = paymentMerid == null ? null : paymentMerid.trim();
    }
}