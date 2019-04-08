package com.nongyeos.loan.admin.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix="thirdmsg")
@PropertySource("classpath:busintopiece.properties")
public class RejectConfig {
	//第三方数据获取成功
	private String gateResultSuccess;
	//第三方数据获取失败
	private String gateResultFail;
	//同盾标记
	private String tongdun;
	//百融标记
	private String bairong;
	
	public String getGateResultSuccess() {
		return gateResultSuccess;
	}
	public void setGateResultSuccess(String gateResultSuccess) {
		this.gateResultSuccess = gateResultSuccess;
	}
	public String getTongdun() {
		return tongdun;
	}
	public void setTongdun(String tongdun) {
		this.tongdun = tongdun;
	}
	public String getBairong() {
		return bairong;
	}
	public void setBairong(String bairong) {
		this.bairong = bairong;
	}
	public String getGateResultFail() {
		return gateResultFail;
	}
	public void setGateResultFail(String gateResultFail) {
		this.gateResultFail = gateResultFail;
	}
	
	
}
