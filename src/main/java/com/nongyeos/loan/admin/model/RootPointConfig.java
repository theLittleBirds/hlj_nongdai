package com.nongyeos.loan.admin.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="rootpoint")
@PropertySource("classpath:busintopiece.properties")
public class RootPointConfig {
	//农鲸标识
	private String mark;

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
	 
}
