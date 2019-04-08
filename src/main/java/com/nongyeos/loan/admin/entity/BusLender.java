package com.nongyeos.loan.admin.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 出借人信息表
 * 
 * @author wcyong
 * 
 * @date 2018-04-18
 */
public class BusLender {
    /**
     * 主键ID
     */
    private String lenderId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 收款银行名
     */
    private String lenderBank;

    /**
     * 收款银行开户名
     */
    private String lenderName;

    /**
     * 收款银行账号
     */
    private String lenderCardNo;

    /**
     * 收款微信号
     */
    private String lenderWechat;

    /**
     * 收款支付宝账号
     */
    private String lenderAlipay;

    /**
     * 地址
     */
    private String address;

    /**
     * 年利率%
     */
    private BigDecimal rate;

    /**
     * 标记出借人类型（1，出借人 2出借银行）
     */
    private Short type;

    /**
     * 是否删除
     */
    private Short isDeleted;

    /**
     * 创建者ID
     */
    private String creOperId;

    /**
     * 创建者名字
     */
    private String creOperName;

    /**
     * 创建者机构名称
     */
    private String creOrgId;

    /**
     * 创建时间
     */
    private Date creDate;

    /**
     * 更新者ID
     */
    private String updOperId;

    /**
     * 更新者名称
     */
    private String updOperName;

    /**
     * 更新者机构ID
     */
    private String updOrgId;

    /**
     * 更新时间
     */
    private Date updDate;

    public String getLenderId() {
        return lenderId;
    }

    public void setLenderId(String lenderId) {
        this.lenderId = lenderId == null ? null : lenderId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getLenderBank() {
        return lenderBank;
    }

    public void setLenderBank(String lenderBank) {
        this.lenderBank = lenderBank == null ? null : lenderBank.trim();
    }

    public String getLenderName() {
        return lenderName;
    }

    public void setLenderName(String lenderName) {
        this.lenderName = lenderName == null ? null : lenderName.trim();
    }

    public String getLenderCardNo() {
        return lenderCardNo;
    }

    public void setLenderCardNo(String lenderCardNo) {
        this.lenderCardNo = lenderCardNo == null ? null : lenderCardNo.trim();
    }

    public String getLenderWechat() {
        return lenderWechat;
    }

    public void setLenderWechat(String lenderWechat) {
        this.lenderWechat = lenderWechat == null ? null : lenderWechat.trim();
    }

    public String getLenderAlipay() {
        return lenderAlipay;
    }

    public void setLenderAlipay(String lenderAlipay) {
        this.lenderAlipay = lenderAlipay == null ? null : lenderAlipay.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCreOperId() {
        return creOperId;
    }

    public void setCreOperId(String creOperId) {
        this.creOperId = creOperId == null ? null : creOperId.trim();
    }

    public String getCreOperName() {
        return creOperName;
    }

    public void setCreOperName(String creOperName) {
        this.creOperName = creOperName == null ? null : creOperName.trim();
    }

    public String getCreOrgId() {
        return creOrgId;
    }

    public void setCreOrgId(String creOrgId) {
        this.creOrgId = creOrgId == null ? null : creOrgId.trim();
    }

    public Date getCreDate() {
        return creDate;
    }

    public void setCreDate(Date creDate) {
        this.creDate = creDate;
    }

    public String getUpdOperId() {
        return updOperId;
    }

    public void setUpdOperId(String updOperId) {
        this.updOperId = updOperId == null ? null : updOperId.trim();
    }

    public String getUpdOperName() {
        return updOperName;
    }

    public void setUpdOperName(String updOperName) {
        this.updOperName = updOperName == null ? null : updOperName.trim();
    }

    public String getUpdOrgId() {
        return updOrgId;
    }

    public void setUpdOrgId(String updOrgId) {
        this.updOrgId = updOrgId == null ? null : updOrgId.trim();
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }
}