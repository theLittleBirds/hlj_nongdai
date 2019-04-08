package com.nongyeos.loan.admin.model;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;

@Component
//@ConfigurationProperties(prefix="fileupload")
//@PropertySource( "classpath:busintopiece.properties")
public class FileConfig {
	
	//图片上传路径
	@Value("${fileupload.uploadBaseDir}")
	private String uploadBaseDir;
	
	@Value("${fileupload.baseDir}")
	private String baseDir;
	
	
	public String getBaseDir() {
		return baseDir;
	}

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

	public String getUploadBaseDir() {
		return uploadBaseDir;
	}

	public void setUploadBaseDir(String uploadBaseDir) {
		this.uploadBaseDir = uploadBaseDir;
	}

}
