package com.nongyeos.loan.admin.entity;

import java.util.Date;

/**
 * 消费者
 * 
 * @author wcyong
 * 
 * @date 2018-06-05
 */
public class BusMember {
    /**
     * 消费者ID
     */
    private String memberId;

    /**
     * 证件类型
     */
    private Short idCardType;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 证件号
     */
    private String idCard;

    /**
     * 消费者姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Short age;

    /**
     * 性别（1：男  2：女）
     */
    private Short sex;

    /**
     * 婚姻状况
     */
    private Short maritalStatus;

    /**
     * 文化程度（1：初中以下  2：中专/高中  3：大专  4：本科及以上）
     */
    private Short educationLevel;

    /**
     * 银行预留手机号
     */
    private String bankPhone;

    /**
     * 开户银行
     */
    private String bank;

    /**
     * 银行卡号
     */
    private String bankCardNo;

    /**
     * 从事职业（1：在家务农  2：从事非农职业）--多选
     */
    private String duty;

    /**
     * 从事非农职业单位名称
     */
    private String nonfarmComName;

    /**
     * 从事非农职业地址
     */
    private String nonfarmComAddress;

    /**
     * 从事非农职业电话
     */
    private String nonfarmComPhone;

    private Short isDeleted;

    private String creOperId;

    private String creOperName;

    private String creOrgId;

    private Date creDate;

    private String updOperId;

    private String updOperName;

    private String updOrgId;

    private Date updDate;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    public Short getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(Short idCardType) {
        this.idCardType = idCardType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Short getAge() {
        return age;
    }

    public void setAge(Short age) {
        this.age = age;
    }

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }

    public Short getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Short maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Short getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(Short educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getBankPhone() {
        return bankPhone;
    }

    public void setBankPhone(String bankPhone) {
        this.bankPhone = bankPhone == null ? null : bankPhone.trim();
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo == null ? null : bankCardNo.trim();
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty == null ? null : duty.trim();
    }

    public String getNonfarmComName() {
        return nonfarmComName;
    }

    public void setNonfarmComName(String nonfarmComName) {
        this.nonfarmComName = nonfarmComName == null ? null : nonfarmComName.trim();
    }

    public String getNonfarmComAddress() {
        return nonfarmComAddress;
    }

    public void setNonfarmComAddress(String nonfarmComAddress) {
        this.nonfarmComAddress = nonfarmComAddress == null ? null : nonfarmComAddress.trim();
    }

    public String getNonfarmComPhone() {
        return nonfarmComPhone;
    }

    public void setNonfarmComPhone(String nonfarmComPhone) {
        this.nonfarmComPhone = nonfarmComPhone == null ? null : nonfarmComPhone.trim();
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