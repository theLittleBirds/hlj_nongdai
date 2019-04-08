package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.BusFins;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IBusFinsService {

	List<BusFins> selectAll(List<String> orgIdList);
	
	List<BusFins> queryAll();

	void add(BusFins busFins) throws Exception;

	void update(BusFins busFins) throws Exception;

	void deleteBusFins(String busFin) throws Exception;

	PageBeanUtil<BusFins> selectByPage(int limit, int offset, List<String> orgIdList) throws Exception;

	BusFins selectById(String finsId) throws Exception;

	List<BusFins> selectByOrgId(String orgId)throws Exception;
}
