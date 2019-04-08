package com.nongyeos.loan.admin.entity;

import java.util.Date;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2018-04-27
 */
public class BusRejectReason {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 进件Id
     */
    private String intoPieceId;

    /**
     * 身份证号
     */
    private String idCardNo;

    /**
     * 拒绝原因
     */
    private String rejectReason;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 删除标识符
     */
    private Integer delFlag;

    public String getId() {
        return id;
    }

    
    
    public BusRejectReason() {
		super();
	}



	public BusRejectReason(String id, String intoPieceId, String idCardNo,
			String rejectReason, Date createTime, Integer delFlag) {
		super();
		this.id = id;
		this.intoPieceId = intoPieceId;
		this.idCardNo = idCardNo;
		this.rejectReason = rejectReason;
		this.createTime = createTime;
		this.delFlag = delFlag;
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

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason == null ? null : rejectReason.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}