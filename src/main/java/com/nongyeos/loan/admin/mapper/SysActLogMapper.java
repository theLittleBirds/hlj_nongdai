package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.SysActLog;

public interface SysActLogMapper {

	List<SysActLog> queryList();
	
	int count();
	
	int insert(SysActLog actLog);
	
	int deleteById(Integer logId);
	
	int update(SysActLog actLog);
}
