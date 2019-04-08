package com.nongyeos.loan.admin.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-04-19
 */
public class BusProduct {
    /**
     * 主键ID
     */
    private String productId;

    /**
     * 金融机构ID
     */
    private String finsId;
    /**
     * 产品名
     */
    private String name;

    /**
     * 利率
     */
    private BigDecimal monthRate;
    
    public Short getMonthRateType() {
		return monthRateType;
	}

	public void setMonthRateType(Short monthRateType) {
		this.monthRateType = monthRateType;
	}

	public Short getServiceRateType() {
		return serviceRateType;
	}

	public void setServiceRateType(Short serviceRateType) {
		this.serviceRateType = serviceRateType;
	}

	/**
     * 利率类型（1，月利率 2，年利率）
     */
    private Short monthRateType;

    /**
     * 服务费利率
     */
    private BigDecimal serviceRate;
    
    /**
     * 服务费利率类型（1，月利率 2，年利率）
     */
    private Short serviceRateType;

    /**
     * 逾期天利率
     */
    private BigDecimal overdueDayRate;

    /**
     * 是否有担保（1，有 2，无）
     */
    private Short haveGuarantee;

    /**
     * 借款方式(1,贷服务费 2,不贷服务费)
     */
    private Short borrowWay;

    /**
     * 还款方式(1,等额本息  2先息后本  3组合贷款  4惠农通)
     */
    private Short repaymentWay;

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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getMonthRate() {
        return monthRate;
    }

    public void setMonthRate(BigDecimal monthRate) {
        this.monthRate = monthRate;
    }

    public BigDecimal getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(BigDecimal serviceRate) {
        this.serviceRate = serviceRate;
    }

    public BigDecimal getOverdueDayRate() {
        return overdueDayRate;
    }

    public void setOverdueDayRate(BigDecimal overdueDayRate) {
        this.overdueDayRate = overdueDayRate;
    }

    public Short getHaveGuarantee() {
        return haveGuarantee;
    }

    public void setHaveGuarantee(Short haveGuarantee) {
        this.haveGuarantee = haveGuarantee;
    }

    public Short getBorrowWay() {
        return borrowWay;
    }

    public void setBorrowWay(Short borrowWay) {
        this.borrowWay = borrowWay;
    }

    public Short getRepaymentWay() {
        return repaymentWay;
    }

    public void setRepaymentWay(Short repaymentWay) {
        this.repaymentWay = repaymentWay;
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

	public String getFinsId() {
		return finsId;
	}

	public void setFinsId(String finsId) {
		this.finsId = finsId;
	}
}