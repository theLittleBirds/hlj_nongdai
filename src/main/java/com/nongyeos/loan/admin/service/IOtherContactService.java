package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusOtherContact;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IOtherContactService {
	
	int insert(BusOtherContact record) throws Exception;
	
	int updateByPrimaryKey(BusOtherContact record) throws Exception;
	
	int updateByPrimaryKeySelective(BusOtherContact record) throws Exception;
	
	BusOtherContact selectByPrimaryKey(String id) throws Exception;
	
	PageBeanUtil<BusOtherContact> selectByPage(int currentPage,
			int pageSize, BusOtherContact record) throws Exception;

	List<BusOtherContact> selectByIntpId(BusOtherContact oc) throws Exception;
}
