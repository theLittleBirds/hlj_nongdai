package com.nongyeos.loan.admin.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="twoelement")
@PropertySource("classpath:busintopiece.properties")
public class TwoElementsConfig {
	private String APPKEY;
	private String APPSECRET;
	private String APPCODE;
	private String HOST;
	private String PATH;
	public String getAPPKEY() {
		return APPKEY;
	}
	public void setAPPKEY(String aPPKEY) {
		APPKEY = aPPKEY;
	}
	public String getAPPSECRET() {
		return APPSECRET;
	}
	public void setAPPSECRET(String aPPSECRET) {
		APPSECRET = aPPSECRET;
	}
	public String getAPPCODE() {
		return APPCODE;
	}
	public void setAPPCODE(String aPPCODE) {
		APPCODE = aPPCODE;
	}
	public String getHOST() {
		return HOST;
	}
	public void setHOST(String hOST) {
		HOST = hOST;
	}
	public String getPATH() {
		return PATH;
	}
	public void setPATH(String pATH) {
		PATH = pATH;
	}
	
}
