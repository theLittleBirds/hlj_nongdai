package com.nongyeos.loan.admin.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="jpush")
@PropertySource("classpath:busintopiece.properties")
public class JpushConfig {
	private String APPKEY;
	private String MASTERSECRET;
	private String TYPECPNTENT;
	public String getAPPKEY() {
		return APPKEY;
	}
	public void setAPPKEY(String aPPKEY) {
		APPKEY = aPPKEY;
	}
	public String getMASTERSECRET() {
		return MASTERSECRET;
	}
	public void setMASTERSECRET(String mASTERSECRET) {
		MASTERSECRET = mASTERSECRET;
	}
	public String getTYPECPNTENT() {
		return TYPECPNTENT;
	}
	public void setTYPECPNTENT(String tYPECPNTENT) {
		TYPECPNTENT = tYPECPNTENT;
	}
}
