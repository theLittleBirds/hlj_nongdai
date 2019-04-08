package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusFollowType;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IFollowTypeService {
	
	int insert(BusFollowType record) throws Exception;
	
	BusFollowType selectByPrimaryKey(String id) throws Exception;
	
	int updateByPrimaryKeySelective(BusFollowType record) throws Exception;
	
	List<BusFollowType> selectAll(String creOrgId) throws Exception;
	
	PageBeanUtil<BusFollowType> selectByPage(int currentPage,int pageSize,String creOrgId) throws Exception;
}
