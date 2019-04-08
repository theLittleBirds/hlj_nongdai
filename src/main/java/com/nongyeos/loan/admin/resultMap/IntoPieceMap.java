package com.nongyeos.loan.admin.resultMap;

import java.util.Date;

import com.nongyeos.loan.admin.entity.BusIntoPiece;


public class IntoPieceMap extends BusIntoPiece{
    
	/**
	 * 进件状态
	 */
	private String status;
	
	/**
	 * 产业链全名
	 */
	private String fullCname;

    /**
     * 状态更新时间
     */
    private Date statusUpdate;
    
    /**
     * 年月日
     */
    private String yyyymmdd;
    
    /**
     * 总次数
     */
    private Integer total;
    
    /**
     * 用户id
     */
    private String personId;
    
    /**
     * 操作人员姓名
     */
    private String operName;
    
    /**
     * 产品名称
     */
    private String appName;
    
    /**
     * 进件开始时间
     */
    private String startDate;
    
    
    private String serviceStatus;
    
    /**
     * 进件结束时间
     */
    private String endDate; 
    
    
    private String finsId;
    

	public String getFinsId() {
		return finsId;
	}

	public void setFinsId(String finsId) {
		this.finsId = finsId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getYyyymmdd() {
		return yyyymmdd;
	}

	public void setYyyymmdd(String yyyymmdd) {
		this.yyyymmdd = yyyymmdd;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getFullCname() {
		return fullCname;
	}

	public void setFullCname(String fullCname) {
		this.fullCname = fullCname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStatusUpdate() {
		return statusUpdate;
	}

	public void setStatusUpdate(Date statusUpdate) {
		this.statusUpdate = statusUpdate;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
}