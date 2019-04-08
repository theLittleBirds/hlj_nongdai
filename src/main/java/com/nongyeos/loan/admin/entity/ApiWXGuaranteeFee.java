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
public class ApiWXGuaranteeFee {
    /**
     * 主键
     */
    private String id;

    /**
     * 服务费订单主键id
     */
    private String guaranteeId;

    /**
     * 进件id
     */
    private String intoPieceId;

    /**
     * 客户姓名
     */
    private String accountName;

    /**
     * 身份证号
     */
    private String certificateNo;

    /**
     * 手机号
     */
    private String mobileNo;

    /**
     * 订单号
     */
    private String merchantNo;

    /**
     * 自助还款的总金额（包括滞纳金）
     */
    private BigDecimal totalAmount;

    /**
     * 实际还款时间
     */
    private Date realRepayTime;

    /**
     * SUCCESS：交易成功，  FAIL：交易失败
     */
    private String status;

    /**
     * 公司类型
     */
    private String companyType;

    /**
     * 实际结算时间
     */
    private Date realSettleTime;

    /**
     * 异步回调返回的resMessage
     */
    private String settleStatus;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getGuaranteeId() {
        return guaranteeId;
    }

    public void setGuaranteeId(String guaranteeId) {
        this.guaranteeId = guaranteeId == null ? null : guaranteeId.trim();
    }

    public String getIntoPieceId() {
        return intoPieceId;
    }

    public void setIntoPieceId(String intoPieceId) {
        this.intoPieceId = intoPieceId == null ? null : intoPieceId.trim();
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

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo == null ? null : mobileNo.trim();
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo == null ? null : merchantNo.trim();
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getRealRepayTime() {
        return realRepayTime;
    }

    public void setRealRepayTime(Date realRepayTime) {
        this.realRepayTime = realRepayTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType == null ? null : companyType.trim();
    }

    public Date getRealSettleTime() {
        return realSettleTime;
    }

    public void setRealSettleTime(Date realSettleTime) {
        this.realSettleTime = realSettleTime;
    }

    public String getSettleStatus() {
        return settleStatus;
    }

    public void setSettleStatus(String settleStatus) {
        this.settleStatus = settleStatus == null ? null : settleStatus.trim();
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
}