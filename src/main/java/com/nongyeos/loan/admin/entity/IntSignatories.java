package com.nongyeos.loan.admin.entity;

import java.util.Date;

/**
 * JZQ合同签约人信息
 * 
 * @author wcyong
 * 
 * @date 2018-08-09
 */
public class IntSignatories {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 放款ID
     */
    private String loanId;

    /**
     * 签约编号
     */
    private String applyNo;

    /**
     * 上传合同到签约室ID
     */
    private String applySignFileId;

    /**
     * 签约人姓名
     */
    private String fullName;

    /**
     * 证件类型(1：身份证  11：企业营业执照）
     */
    private Integer identityType;

    /**
     * 签约人身份证号
     */
    private String idCard;

    /**
     * 签约人手机号码
     */
    private String mobile;

    /**
     * 短信发送状态（0：待签  1：已签  2：拒签  3：发送失败）
     */
    private Integer status;

    /**
     * 错误消息
     */
    private String message;

    /**
     * 解决方案
     */
    private String solution;

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

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId == null ? null : loanId.trim();
    }

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo == null ? null : applyNo.trim();
    }

    public String getApplySignFileId() {
        return applySignFileId;
    }

    public void setApplySignFileId(String applySignFileId) {
        this.applySignFileId = applySignFileId == null ? null : applySignFileId.trim();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    public Integer getIdentityType() {
        return identityType;
    }

    public void setIdentityType(Integer identityType) {
        this.identityType = identityType;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
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