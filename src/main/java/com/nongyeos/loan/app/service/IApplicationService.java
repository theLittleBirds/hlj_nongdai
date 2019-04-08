package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.AppApplication;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IApplicationService {

	List<AppApplication> selectByFinsCode(String finsCode) throws Exception;

	void addApplication(AppApplication application) throws Exception;

	void updateApplication(AppApplication application) throws Exception;

	void deleteById(String appId) throws Exception;

	AppApplication getApplicationById(String appId) throws Exception;

	List<AppApplication> selectAllApplications()throws Exception;

	PageBeanUtil<AppApplication> selectByPage(int limit, int offset,
			String groupId) throws Exception;

	List<AppApplication> selectByGroupId(String groupId) throws Exception;

	String appTreeString(List<String> orgIdList);

	AppApplication selectByModelId(String modelId)throws Exception;

	List<AppApplication> selectByOrgId(String orgId)throws Exception;
	
	List<AppApplication> quetyByFinsIdType(AppApplication record)throws Exception;

}
