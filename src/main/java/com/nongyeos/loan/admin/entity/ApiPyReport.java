package com.nongyeos.loan.admin.entity;

import java.util.Date;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-07-23
 */
public class ApiPyReport {
    /**
     * 主键id
     */
    private String id;

    /**
     * 进件id
     */
    private String intoPieceId;

    /**
     * 身份证号
     */
    private String idCardNo;

    /**
     * 鹏元report的json格式
     */
    private String content;

    /**
     * 鹏元report的html格式
     */
    private String contentHtml;
    
    /**
     * 被拒绝标识符
     */
    private Integer rejectFlag;

    /**
     * 创建时间
     */
    private Date createtime;

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

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo == null ? null : idCardNo.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getContentHtml() {
        return contentHtml;
    }

    public void setContentHtml(String contentHtml) {
        this.contentHtml = contentHtml == null ? null : contentHtml.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

	public Integer getRejectFlag() {
		return rejectFlag;
	}

	public void setRejectFlag(Integer rejectFlag) {
		this.rejectFlag = rejectFlag;
	}
}