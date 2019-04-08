package com.nongyeos.loan.admin.service;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.BusLender;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface ILenderService {

	PageBeanUtil<BusLender> selectByPage(int startIndex, int pageSize, BusLender lender) throws Exception;

	boolean existedSameName(BusLender lender)throws Exception;

	void addLender(BusLender lender, String chooseOrgIds)throws Exception;

	void updateLender(BusLender lender, String chooseOrgIds)throws Exception;

	void delLender(String lenderId)throws Exception;

	Map<String, Object> getOrgTree(String lenderId)throws Exception;
	
	List<BusLender> selectByOrgId(String orgId) throws Exception;
	
	BusLender selectByPrimaryKey(String lenderId) throws Exception;
	
	List<BusLender> selectAll() throws Exception;
}
