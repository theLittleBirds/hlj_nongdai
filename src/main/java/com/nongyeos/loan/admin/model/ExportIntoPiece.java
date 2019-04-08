package com.nongyeos.loan.admin.model;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class ExportIntoPiece {
	
	@Excel(name = "金融机构", height = 10, width = 20)
	private String fins;
	
	@Excel(name = "服务站", height = 10, width = 20)
	private String org;
	
	@Excel(name = "姓名", height = 10, width = 20)
	private String name;
	
	@Excel(name = "身份证号", height = 10, width = 20)
	private String idCard;
	
	@Excel(name = "手机号", height = 10, width = 20)
	private String phone;
	
	@Excel(name = "家庭住址", height = 10, width = 20)
	private String address;
	
	@Excel(name = "客户类型", replace = { "新增_1", "转贷_2"}, height = 10, width = 20)
	private String type;
	
	@Excel(name = "贷款金额", height = 10, width = 20)
	private String capital;
	
	@Excel(name = "在贷余额", height = 10, width = 20)
	private String balance;
	
	@Excel(name = "开始日期", height = 10, width = 20)
	private String startDate;
	
	@Excel(name = "结束日期", height = 10, width = 20)
	private String endDate;

	public String getFins() {
		return fins;
	}

	public void setFins(String fins) {
		this.fins = fins;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
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
	
	
}
