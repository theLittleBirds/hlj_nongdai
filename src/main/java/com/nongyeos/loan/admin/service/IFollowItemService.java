package com.nongyeos.loan.admin.service;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.BusFollowItem;
import com.nongyeos.loan.admin.resultMap.FollowItemMap;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IFollowItemService {
	
	int insert(BusFollowItem record) throws Exception;
	
	BusFollowItem selectByPrimaryKey(String id) throws Exception;
	
	int updateByPrimaryKeySelective(BusFollowItem record) throws Exception;

	int updateByPrimaryKey(BusFollowItem record) throws Exception;
	    
	List<FollowItemMap> selectDynamicByType(Map<String, String> map) throws Exception;
	
	PageBeanUtil<BusFollowItem> selectByPage(int currentPage,int pageSize,String type) throws Exception;
	
	int selectCountByType(String type) throws Exception;
}
