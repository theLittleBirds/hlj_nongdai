package com.nongyeos.loan.admin.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.admin.entity.BusFollowData;
import com.nongyeos.loan.admin.mapper.BusFollowDataMapper;
import com.nongyeos.loan.admin.service.IFollowDataService;
@Service("followDataService")
public class FollowDataServiceImpl implements IFollowDataService {
	
	@Autowired
	private BusFollowDataMapper followDataMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insert(BusFollowData record) throws Exception {
		if(record == null)
			throw new Exception("数据为空");
		try {
			return followDataMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int deleteBeforeSave(Map<String, String> map) throws Exception {
		if(map == null)
			throw new Exception("删除条件为空为空");
		try {
			return followDataMapper.deleteBeforeSave(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

}
