package com.nongyeos.loan.app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.mapper.AppEntryMapper;
import com.nongyeos.loan.app.service.IAppEntryService;

@Service("appEntryService")
public class AppEntryServiceImpl implements IAppEntryService{

	@Autowired
	private AppEntryMapper appEntryMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByAppEntrySelective(AppEntry record) throws Exception {
		try {
			if(record == null){
				throw new Exception("appEntry对象为空");
			}
			if(record.getEntryId() == null){
				throw new Exception("entryId为空");
			}
			int selective = appEntryMapper.updateByAppEntrySelective(record);
			return selective;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}	
	
	@Override
	public AppEntry queryByAppModeId(String modeId) throws Exception {
		try {
			if(StringUtils.isEmpty(modeId)){
				throw new Exception("modeId为空");
			}
			AppEntry appModeId = appEntryMapper.queryByAppModeId(modeId);
			return appModeId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addAppEntrySelective(AppEntry record) throws Exception {
		try {
			if(record==null){
				throw new Exception("AppEntry对象为空");
			}
			int entry = appEntryMapper.addAppEntrySelective(record);
			return entry;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	public int deleteByPrimaryKey(String entryId) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(AppEntry record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AppEntry selectByPrimaryKey(String entryId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKey(AppEntry record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<AppEntry> todayStatistics(Map<String, Object> map)
			throws Exception {
		try {
			return appEntryMapper.todayStatistics(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public int todayRefuse(Map<String, Object> map) throws Exception {
		try {
			return appEntryMapper.todayRefuse(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

}