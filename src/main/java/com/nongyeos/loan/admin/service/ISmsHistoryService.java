package com.nongyeos.loan.admin.service;

import com.nongyeos.loan.admin.entity.BusSmsHistory;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface ISmsHistoryService {
	
	int insert(BusSmsHistory record) throws Exception;
	
	int updateByPrimaryKeySelective(BusSmsHistory record) throws Exception;
	
	PageBeanUtil<BusSmsHistory> selectByPage(int currentPage,int pageSize,BusSmsHistory record) throws Exception;
}
