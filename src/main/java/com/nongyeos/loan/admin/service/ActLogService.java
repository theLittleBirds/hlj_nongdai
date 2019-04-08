package com.nongyeos.loan.admin.service;


import com.nongyeos.loan.admin.entity.SysActLog;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface ActLogService {

	PageBeanUtil<SysActLog> logPage(int currentPage, int pageSize)throws Exception;
	
	void add(SysActLog actLog)throws Exception;
	
	void delete(Integer logId)throws Exception;
	
	void update(SysActLog actLog) throws Exception;
}
