package com.nongyeos.loan.admin.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nongyeos.loan.admin.entity.SysPara;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IParaService {

	String selectByPGroupId(Integer pgroupId);
	
	String selectByPGroupId2(Integer pgroupId);
	
	void save(SysPara para) throws Exception;
	
	void update(SysPara para) throws Exception;
	
	PageBeanUtil<SysPara> getList(int offset, int limit, int pgroupId) throws Exception;
	
	void delete(String paraIds) throws Exception;
	
	boolean existedSameName(SysPara para);
	
	boolean existedSameDesc(SysPara para);

	List<SysPara> getListByPId(Integer pgroupId);

	List<SysPara> selectListByPGroupId(Integer pgroupId) throws Exception;
	
	SysPara queryParaByGroupIdAndVal(String pgroupId, String value) throws Exception;
}

