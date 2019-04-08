package com.nongyeos.loan.admin.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资金变动记录
 * 
 * @author wcyong
 * 
 * @date 2018-05-30
 */
public class BusMoneyLog {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 客户ID
     */
    private String loanId;

    /**
     * 资金变动类型（1：放款  2：还款）
     */
    private Integer type;

    /**
     * 金额
     */
    private BigDecimal capital;

    /**
     * 利息
     */
    private BigDecimal interest;

    /**
     * 逾期费用
     */
    private BigDecimal overdue;

    /**
     * 备注
     */
    private String info;

    /**
     * 是否删除
     */
    private Short isDeleted;

    /**
     * 更新者ID
     */
    private String updOperId;

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

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId == null ? null : loanId.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getOverdue() {
        return overdue;
    }

    public void setOverdue(BigDecimal overdue) {
        this.overdue = overdue;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getUpdOperId() {
        return updOperId;
    }

    public void setUpdOperId(String updOperId) {
        this.updOperId = updOperId == null ? null : updOperId.trim();
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }
}