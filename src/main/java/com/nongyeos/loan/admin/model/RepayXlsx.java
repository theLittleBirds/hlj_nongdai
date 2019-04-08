package com.nongyeos.loan.admin.model;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class RepayXlsx {
	
	
	@Excel(name = "金融机构", height = 10, width = 20)
	private String fins;
	
	
	@Excel(name = "姓名", height = 10, width = 20)
	private String name;
	
	
	@Excel(name = "身份证号", height = 10, width = 20)
	private String idCard;
	
	
	@Excel(name = "还款状态", replace = { "还款完成_1", "逾期_2"}, height = 10, width = 20)
	private String status;
	
	@Excel(name = "在贷余额", height = 10, width = 20)
	private String money;
	
	public String getMoney() {
		return money;
	}


	public void setMoney(String money) {
		this.money = money;
	}


	public String getFins() {
		return fins;
	}


	public void setFins(String fins) {
		this.fins = fins;
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


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
