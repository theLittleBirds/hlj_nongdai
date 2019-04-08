package com.nongyeos.loan.admin.service;

import java.util.Map;

import com.nongyeos.loan.admin.entity.BusFollowData;

public interface IFollowDataService {
	
	int insert(BusFollowData record) throws Exception;
	
	int deleteBeforeSave(Map<String, String> map) throws Exception;
}
