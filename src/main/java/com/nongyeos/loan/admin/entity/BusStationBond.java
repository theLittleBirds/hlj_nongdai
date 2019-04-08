package com.nongyeos.loan.admin.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-10-26
 */
public class BusStationBond {
    private String id;

    /**
     * 进件id
     */
    private String intoPieceId;

    /**
     * 组织机构id
     */
    private String orgId;

    /**
     * 姓名
     */
    private String memberName;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 借款金额（元）
     */
    private BigDecimal capital;

    /**
     * 农资金额（元）
     */
    private BigDecimal recieveNongZi;

    /**
     * 保证金
     */
    private BigDecimal bond;

    /**
     * 支付人
     */
    private String payer;

    /**
     * 支付方式(1，站长代付 2，微信支付  3，暂不支付（针对黑龙江首信）)
     */
    private String payWay;

    /**
     * 支付状态
     */
    private String status;

    /**
     * 支付时间
     */
    private String payDate;

    /**
     * 更新时间
     */
    private Date updDate;

    private String updOperId;

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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
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

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getRecieveNongZi() {
        return recieveNongZi;
    }

    public void setRecieveNongZi(BigDecimal recieveNongZi) {
        this.recieveNongZi = recieveNongZi;
    }

    public BigDecimal getBond() {
        return bond;
    }

    public void setBond(BigDecimal bond) {
        this.bond = bond;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer == null ? null : payer.trim();
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

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }

    public String getUpdOperId() {
        return updOperId;
    }

    public void setUpdOperId(String updOperId) {
        this.updOperId = updOperId == null ? null : updOperId.trim();
    }
}