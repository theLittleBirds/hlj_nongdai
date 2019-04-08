package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.SysParaGroup;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IParaGroupService {

	List<SysParaGroup> getList() throws Exception;
	
	SysParaGroup selectByName(String name) throws Exception;
	
	void save(SysParaGroup paraGroup) throws Exception;
	
	void update(SysParaGroup paraGroup) throws Exception;
	
	void delete(String ids) throws Exception;
	
	boolean existedSameName(SysParaGroup paraGroup);
	
	boolean existedSameDesc(SysParaGroup paraGroup);

	PageBeanUtil<SysParaGroup> selectByPage(int offset,int limit) throws Exception;
	
	String getSelectOption(String name,String value);
}
