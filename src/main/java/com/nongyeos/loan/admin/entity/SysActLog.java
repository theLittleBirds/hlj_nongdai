package com.nongyeos.loan.admin.entity;

import java.util.Date;

public class SysActLog {

	private Integer logId;
	private String userCode;
	private String personCode;
	private String personName;
	private String orgCode;
	private String orgName;
	private String ip;
	private String actEvent;
	private String actObject;
	private Date actTime;
	public SysActLog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SysActLog(Integer logId, String userCode, String personCode,
			String personName, String orgCode, String orgName, String ip,
			String actEvent, String actObject, Date actTime) {
		super();
		this.logId = logId;
		this.userCode = userCode;
		this.personCode = personCode;
		this.personName = personName;
		this.orgCode = orgCode;
		this.orgName = orgName;
		this.ip = ip;
		this.actEvent = actEvent;
		this.actObject = actObject;
		this.actTime = actTime;
	}
	public Integer getLogId() {
		return logId;
	}
	public String getUserCode() {
		return userCode;
	}
	public String getPersonCode() {
		return personCode;
	}
	public String getPersonName() {
		return personName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public String getIp() {
		return ip;
	}
	public String getActEvent() {
		return actEvent;
	}
	public String getActObject() {
		return actObject;
	}
	public Date getActTime() {
		return actTime;
	}
	public void setLogId(Integer logId) {
		this.logId = logId;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setActEvent(String actEvent) {
		this.actEvent = actEvent;
	}
	public void setActObject(String actObject) {
		this.actObject = actObject;
	}
	public void setActTime(Date actTime) {
		this.actTime = actTime;
	}
	
	
}
