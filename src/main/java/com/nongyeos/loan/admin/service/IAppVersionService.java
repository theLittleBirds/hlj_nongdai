package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.SysAppVersion;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IAppVersionService {

	PageBeanUtil<SysAppVersion> selectByPage(int currentPage, int pageSize, SysAppVersion sav) throws Exception;

	SysAppVersion selectById(String id)throws Exception;


	SysAppVersion selectByVersionNumber(SysAppVersion appVersion)throws Exception;

	void saveOrUpdate(SysAppVersion appVersion)throws Exception;

	void delVersion(SysAppVersion appVersion)throws Exception;

	List<SysAppVersion> newForceVersions(SysAppVersion appVersion)throws Exception;

	SysAppVersion selectNewest(SysAppVersion appVersion)throws Exception;
	
}
