package com.nongyeos.loan.admin.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-12-19
 */
public class BusIntopieceReverse {
    /**
     * 进件反担保金id
     */
    private String id;

    /**
     * 组织机构id
     */
    private String orgId;

    /**
     * 进件id
     */
    private String intoPieceId;

    /**
     * 组织机构名称
     */
    private String orgName;

    /**
     * 客户姓名
     */
    private String memberName;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 贷款审核金额
     */
    private BigDecimal capital;

    /**
     * 农户反担保金
     */
    private BigDecimal farmerReverse;

    /**
     * 服务站反担保金
     */
    private BigDecimal stationReverse;

    /**
     * 进件反担保金状态(1,已收取 2，退回中 3，已退回)
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

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

    public String getIntoPieceId() {
        return intoPieceId;
    }

    public void setIntoPieceId(String intoPieceId) {
        this.intoPieceId = intoPieceId == null ? null : intoPieceId.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
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

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getFarmerReverse() {
        return farmerReverse;
    }

    public void setFarmerReverse(BigDecimal farmerReverse) {
        this.farmerReverse = farmerReverse;
    }

    public BigDecimal getStationReverse() {
        return stationReverse;
    }

    public void setStationReverse(BigDecimal stationReverse) {
        this.stationReverse = stationReverse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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