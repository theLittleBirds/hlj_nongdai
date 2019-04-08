package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.AppPara;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IAppParaService {

	PageBeanUtil<AppPara> selectAll(int limit, int offset, String appId) throws Exception;

	void addAppPara(AppPara appPara) throws Exception;

	void updateAppPara(AppPara appPara) throws Exception;

	void deleteAppPara(Integer appParaId) throws Exception;
	
	List<AppPara> selectByGroupName(AppPara record) throws Exception;

	List<AppPara> selectByOne(String appId) throws Exception;

	List<AppPara> selectByName(String appParaGroupName,String appId) throws Exception;

	List<AppPara> selectAllDS() throws Exception;

}
