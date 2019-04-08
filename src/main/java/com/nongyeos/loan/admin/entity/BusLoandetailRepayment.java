package com.nongyeos.loan.admin.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 还款记录表
 * 
 * @author wcyong
 * 
 * @date 2018-08-02
 */
public class BusLoandetailRepayment {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 放款详细ID
     */
    private String loanDetailId;

    /**
     * 还款本金（单位：元）
     */
    private BigDecimal receiveCapital;

    /**
     * 还款利息（单位：元）
     */
    private BigDecimal receiveInterest;

    /**
     * 还款逾期费用（单位：元）
     */
    private BigDecimal receiveOverdue;

    /**
     * 还款方式（1：本系统  2：代扣）
     */
    private Integer repaymentWay;

    /**
     * 还款时间
     */
    private Date repaymentDate;

    /**
     * 状态（1：还款中  2：复审中  3：还款失败  4：还款完成）
     */
    private Integer status;

    /**
     * 备注
     */
    private String info;

    /**
     * 代扣返回信息
     */
    private String intMes;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新日期
     */
    private Date updateDate;

    /**
     * 删除标识符
     */
    private Integer delFlag;

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

    public String getLoanDetailId() {
        return loanDetailId;
    }

    public void setLoanDetailId(String loanDetailId) {
        this.loanDetailId = loanDetailId == null ? null : loanDetailId.trim();
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

    public BigDecimal getReceiveOverdue() {
        return receiveOverdue;
    }

    public void setReceiveOverdue(BigDecimal receiveOverdue) {
        this.receiveOverdue = receiveOverdue;
    }

    public Integer getRepaymentWay() {
        return repaymentWay;
    }

    public void setRepaymentWay(Integer repaymentWay) {
        this.repaymentWay = repaymentWay;
    }

    public Date getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(Date repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public String getIntMes() {
        return intMes;
    }

    public void setIntMes(String intMes) {
        this.intMes = intMes == null ? null : intMes.trim();
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

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}