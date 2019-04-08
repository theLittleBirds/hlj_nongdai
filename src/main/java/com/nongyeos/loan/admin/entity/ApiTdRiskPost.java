package com.nongyeos.loan.admin.entity;

import java.util.Date;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-05-23
 */
public class ApiTdRiskPost {
    /**
     * 主键
     */
    private String id;

    /**
     * 同盾report_id
     */
    private String reportId;
    
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
     * 放款日期
     */
    private String loanDate;

    /**
     * 放款期限
     */
    private String loanTerm;

    /**
     * 放款期限单位
     */
    private String loanTermUnit;

    /**
     * 逾期风险等级    低，较低，中等，较高，高；放款期限大于3个月，才会有此数据输出
     */
    private String overdueLevel;

    /**
     * "坏"客户占比
     */
    private String badRate;

    /**
     * 用户行为分
     */
    private String score;

    /**
     * 进件id
     */
    private String intoPieceId;

    /**
     * 调用完成状态码
     */
    private String reasonCode;

    /**
     * 状态描述
     */
    private String reasonDesc;

    /**
     * 风险列表
     */
    private String data;

    /**
     * false:调用失败；true:调用成功
     */
    private String result;

    /**
     * 创建时间
     */
    private Date createtime;
    
    /**
     * 是否获的风险
     * 
     * 1：获的风险，2：未获的风险
     */
    private String obtained;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
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

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate == null ? null : loanDate.trim();
    }

    public String getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(String loanTerm) {
        this.loanTerm = loanTerm == null ? null : loanTerm.trim();
    }

    public String getLoanTermUnit() {
        return loanTermUnit;
    }

    public void setLoanTermUnit(String loanTermUnit) {
        this.loanTermUnit = loanTermUnit == null ? null : loanTermUnit.trim();
    }

    public String getOverdueLevel() {
        return overdueLevel;
    }

    public void setOverdueLevel(String overdueLevel) {
        this.overdueLevel = overdueLevel == null ? null : overdueLevel.trim();
    }

    public String getBadRate() {
        return badRate;
    }

    public void setBadRate(String badRate) {
        this.badRate = badRate == null ? null : badRate.trim();
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score == null ? null : score.trim();
    }

    public String getIntoPieceId() {
        return intoPieceId;
    }

    public void setIntoPieceId(String intoPieceId) {
        this.intoPieceId = intoPieceId == null ? null : intoPieceId.trim();
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode == null ? null : reasonCode.trim();
    }

    public String getReasonDesc() {
        return reasonDesc;
    }

    public void setReasonDesc(String reasonDesc) {
        this.reasonDesc = reasonDesc == null ? null : reasonDesc.trim();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

	public String getObtained() {
		return obtained;
	}

	public void setObtained(String obtained) {
		this.obtained = obtained;
	}
    
}