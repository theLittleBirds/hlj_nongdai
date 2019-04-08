package com.nongyeos.loan.admin.resultMap;

import java.util.Date;

import com.nongyeos.loan.admin.entity.BusLoan;

public class LoanMap extends BusLoan{
	
	/**
	 * 产业链全名
	 */
	private String fullCname;
	
	
	/**
	 * 进件状态
	 */
	private String ipStatus;
	
	/**
	 * 本月是否已还款
	 */
	private String monthStatus;
	
	/**
	 * 进件id
	 */
	private String intoPieceId;
	
	/**
     * 用户id
     */
    private String personId;
    //身份证号
    private String idCard;
    //手机号
    private String phone;
    
    //银行卡号
    private String bankCardNo;
    
    //家庭住址
    private String address;
    
    private String finsName;
    
    private Date applyTime;

	public String getFinsName() {
		return finsName;
	}

	public void setFinsName(String finsName) {
		this.finsName = finsName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getIntoPieceId() {
		return intoPieceId;
	}

	public void setIntoPieceId(String intoPieceId) {
		this.intoPieceId = intoPieceId;
	}

	/**
	 * 时间
	 */
	private String time;
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMonthStatus() {
		return monthStatus;
	}

	public void setMonthStatus(String monthStatus) {
		this.monthStatus = monthStatus;
	}

	public String getIpStatus() {
		return ipStatus;
	}

	public void setIpStatus(String ipStatus) {
		this.ipStatus = ipStatus;
	}

	public String getFullCname() {
		return fullCname;
	}

	public void setFullCname(String fullCname) {
		this.fullCname = fullCname;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
}
