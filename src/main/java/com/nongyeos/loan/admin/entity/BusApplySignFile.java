package com.nongyeos.loan.admin.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * JZQ上传合同文件进入签约室
 * 
 * @author wcyong
 * 
 * @date 2018-08-17
 */
public class BusApplySignFile {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 连续签合同唯一id
     */
    private String businessNo;

    /**
     * 第几个签
     */
    private Integer sequenceOrder;

    /**
     * 总的合同份数
     */
    private Integer totalNum;

    /**
     * 请求时间戳
     */
    private String reqTime;

    /**
     * 合同序号
     */
    private Integer no;

    /**
     * 签约编号
     */
    private String applyNo;

    /**
     * 放款ID
     */
    private String loanId;

    /**
     * 合同名称
     */
    private String name;

    /**
     * 合同金额
     */
    private BigDecimal amount;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 下载合同保存的路径
     */
    private String fileDownpath;
    
    /**
     * 不同app权限标识（公司类型）
     */
    private String channel;

    /**
     * 签约方
     */
    private String signatories;

    /**
     * 浮层备注提示信息
     */
    private String remark;

    /**
     * 状态（0：上传失败  1:上传中  2：签约中  3：签约完成）
     */
    private Integer status;

    /**
     * 主要错误消息
     */
    private String message;

    /**
     * 解决方案
     */
    private String solution;

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

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo == null ? null : businessNo.trim();
    }

    public Integer getSequenceOrder() {
        return sequenceOrder;
    }

    public void setSequenceOrder(Integer sequenceOrder) {
        this.sequenceOrder = sequenceOrder;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime == null ? null : reqTime.trim();
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo == null ? null : applyNo.trim();
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId == null ? null : loanId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getFileDownpath() {
        return fileDownpath;
    }

    public void setFileDownpath(String fileDownpath) {
        this.fileDownpath = fileDownpath == null ? null : fileDownpath.trim();
    }

    public String getSignatories() {
        return signatories;
    }

    public void setSignatories(String signatories) {
        this.signatories = signatories == null ? null : signatories.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution == null ? null : solution.trim();
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

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
}