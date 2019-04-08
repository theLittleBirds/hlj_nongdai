package com.nongyeos.loan.admin.entity;

import java.util.Date;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-08-07
 */
public class BusBankCard {
    /**
     * 主键
     */
    private String id;

    /**
     * 进件id
     */
    private String intoPieceId;

    /**
     * 贷款id
     */
    private String loanId;

    /**
     * 客户姓名
     */
    private String accountName;

    /**
     * 客户银行卡号
     */
    private String accountNo;

    /**
     * 身份证号
     */
    private String idcard;

    /**
     * 银行卡预留手机号
     */
    private String phone;

    /**
     * 创建时间
     */
    private Date creDate;

    /**
     * 修改时间
     */
    private Date updDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getIntoPieceId() {
        return intoPieceId;
    }

    public void setIntoPieceId(String intoPieceId) {
        this.intoPieceId = intoPieceId == null ? null : intoPieceId.trim();
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId == null ? null : loanId.trim();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Date getCreDate() {
        return creDate;
    }

    public void setCreDate(Date creDate) {
        this.creDate = creDate;
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }
}