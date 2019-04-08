package com.nongyeos.loan.admin.entity;

import java.util.Date;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-04-10
 */
public class ApiThirdMsg {
    /**
     * 贷前风险评估id
     */
    private String id;

    /**
     * 进件关联id
     */
    private String intoPieceId;

    /**
     * 风险评估接口返回信息
     */
    private String content;

    /**
     * 平台类型： 同盾:1，百融:2
     */
    private String platform;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 身份证号
     */
    private String idCardNo;

    /**
     * 类型（1.主借人，2.家庭成员）
     */
    private Integer type;

    /**
     * 被拒绝标识符
     */
    private Integer rejectFlag;
    
    /**
     * 同盾贷前reportId
     */    
    private String reportId;
    
    /**
     * 是否添加贷后监控
     */
    private String postRisk;    

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo == null ? null : idCardNo.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRejectFlag() {
        return rejectFlag;
    }

    public void setRejectFlag(Integer rejectFlag) {
        this.rejectFlag = rejectFlag;
    }

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getPostRisk() {
		return postRisk;
	}

	public void setPostRisk(String postRisk) {
		this.postRisk = postRisk;
	}
    
}