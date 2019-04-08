package com.nongyeos.loan.admin.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 放款详细表
 * 
 * @author wcyong
 * 
 * @date 2018-08-24
 */
public class BusLoanDetail {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 借款ID
     */
    private String loanId;

    /**
     * 计划还款本金（单位：元）
     */
    private BigDecimal capital;

    /**
     * 计划还款利息（单位：元）
     */
    private BigDecimal intrest;

    /**
     * 服务费
     */
    private BigDecimal serviceFee;

    /**
     * 剩余本金
     */
    private BigDecimal overplusMoney;

    /**
     * 实收本金（单位：元）
     */
    private BigDecimal receiveCapital;

    /**
     * 实收利息（单位：元）
     */
    private BigDecimal receiveInterest;

    /**
     * 实收服务费
     */
    private BigDecimal receiveServiceFee;

    /**
     * 实收逾期费用（单位：元）
     */
    private BigDecimal receiveOverdue;

    /**
     * 到期时间
     */
    private Date deadlineDate;

    /**
     * 状态（1：待还款  2：还款中  3：还款失败  2：还款完成）
     */
    private Integer status;

    /**
     * 是否是最新的一笔还款(0：否  1：是）
     */
    private Integer recentWaitRep;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 总期数
     */
    private Integer total;

    /**
     * 备注信息
     */
    private String remarks;

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId == null ? null : loanId.trim();
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getIntrest() {
        return intrest;
    }

    public void setIntrest(BigDecimal intrest) {
        this.intrest = intrest;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public BigDecimal getOverplusMoney() {
        return overplusMoney;
    }

    public void setOverplusMoney(BigDecimal overplusMoney) {
        this.overplusMoney = overplusMoney;
    }

    public BigDecimal getReceiveCapital() {
        return receiveCapital;
    }

    public void setReceiveCapital(BigDecimal receiveCapital) {
        this.receiveCapital = receiveCapital;
    }

    public BigDecimal getReceiveInterest() {
        return receiveInterest;
    }

    public void setReceiveInterest(BigDecimal receiveInterest) {
        this.receiveInterest = receiveInterest;
    }

    public BigDecimal getReceiveServiceFee() {
        return receiveServiceFee;
    }

    public void setReceiveServiceFee(BigDecimal receiveServiceFee) {
        this.receiveServiceFee = receiveServiceFee;
    }

    public BigDecimal getReceiveOverdue() {
        return receiveOverdue;
    }

    public void setReceiveOverdue(BigDecimal receiveOverdue) {
        this.receiveOverdue = receiveOverdue;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRecentWaitRep() {
        return recentWaitRep;
    }

    public void setRecentWaitRep(Integer recentWaitRep) {
        this.recentWaitRep = recentWaitRep;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
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