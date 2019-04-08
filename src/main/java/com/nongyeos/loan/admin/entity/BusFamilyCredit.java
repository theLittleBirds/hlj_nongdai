package com.nongyeos.loan.admin.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 家庭征信情况
 * 
 * @author wcyong
 * 
 * @date 2018-05-17
 */
public class BusFamilyCredit {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 进件ID
     */
    private String intoPieceId;

    /**
     * 贷款白户（0：否  1：是）
     */
    private Integer isWhite;

    /**
     * 5年内贷款总次数
     */
    private Integer loanTimesWithFiveYear;

    /**
     * 夫妻征信逾期总次数
     */
    private Integer overdueTimes;

    /**
     * 夫妻征信当前逾期（1：有  2：无）
     */
    private Integer hasOverdueCurrent;

    /**
     * 夫妻征信逾期超过90天以上（1：有  2：无）
     */
    private Integer hasOverdueOutNinetyDay;

    /**
     * 夫妻征信机构一年查询次数
     */
    private Integer orgSearchTimesWithCredit;

    /**
     * 夫妻征信本人一年查询次数
     */
    private Integer selSearchTimesWithCredit;

    /**
     * 夫妻征信逾期90天的金额
     */
    private BigDecimal ninetyDayOverdueMoney;

    /**
     * 征信负面信息（税务、公检法等）(1,有 2，无)
     */
    private Integer negativeInformation;

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

    public Integer getIsWhite() {
        return isWhite;
    }

    public void setIsWhite(Integer isWhite) {
        this.isWhite = isWhite;
    }

    public Integer getLoanTimesWithFiveYear() {
        return loanTimesWithFiveYear;
    }

    public void setLoanTimesWithFiveYear(Integer loanTimesWithFiveYear) {
        this.loanTimesWithFiveYear = loanTimesWithFiveYear;
    }

    public Integer getOverdueTimes() {
        return overdueTimes;
    }

    public void setOverdueTimes(Integer overdueTimes) {
        this.overdueTimes = overdueTimes;
    }

    public Integer getHasOverdueCurrent() {
        return hasOverdueCurrent;
    }

    public void setHasOverdueCurrent(Integer hasOverdueCurrent) {
        this.hasOverdueCurrent = hasOverdueCurrent;
    }

    public Integer getHasOverdueOutNinetyDay() {
        return hasOverdueOutNinetyDay;
    }

    public void setHasOverdueOutNinetyDay(Integer hasOverdueOutNinetyDay) {
        this.hasOverdueOutNinetyDay = hasOverdueOutNinetyDay;
    }

    public Integer getOrgSearchTimesWithCredit() {
        return orgSearchTimesWithCredit;
    }

    public void setOrgSearchTimesWithCredit(Integer orgSearchTimesWithCredit) {
        this.orgSearchTimesWithCredit = orgSearchTimesWithCredit;
    }

    public Integer getSelSearchTimesWithCredit() {
        return selSearchTimesWithCredit;
    }

    public void setSelSearchTimesWithCredit(Integer selSearchTimesWithCredit) {
        this.selSearchTimesWithCredit = selSearchTimesWithCredit;
    }

    public BigDecimal getNinetyDayOverdueMoney() {
        return ninetyDayOverdueMoney;
    }

    public void setNinetyDayOverdueMoney(BigDecimal ninetyDayOverdueMoney) {
        this.ninetyDayOverdueMoney = ninetyDayOverdueMoney;
    }

    public Integer getNegativeInformation() {
        return negativeInformation;
    }

    public void setNegativeInformation(Integer negativeInformation) {
        this.negativeInformation = negativeInformation;
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