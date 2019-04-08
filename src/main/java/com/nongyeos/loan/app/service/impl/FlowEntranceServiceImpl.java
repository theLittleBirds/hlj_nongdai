package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.app.entity.FlowEntrance;
import com.nongyeos.loan.app.mapper.FlowEntranceMapper;
import com.nongyeos.loan.app.service.IFlowEntranceService;

@Service("flowEntranceService")
public class FlowEntranceServiceImpl implements IFlowEntranceService{

	@Autowired
	private FlowEntranceMapper flowEntranceMapper;


	//根据appid查找
	@Override
	public List<FlowEntrance> selectByAppId(String appId) throws Exception {
		List<FlowEntrance> ls=null;
		try{
			if(appId != null && !appId.equals("")){
				ls=flowEntranceMapper.selectByAppId(appId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("appId为空");
		}
		return ls;
	}
	
	//添加
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addEntrance(FlowEntrance entrance) throws Exception {
		try {
			if(entrance != null){
				flowEntranceMapper.insertSelective(entrance);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("entrance为空");
		}
	}

	//更新
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateEntrance(FlowEntrance entrance) throws Exception {
		try {
			if(entrance != null){
				flowEntranceMapper.updateByPrimaryKey(entrance);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("entrance为空");
		}
	}

	//删除
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delEntranceById(Integer entranceId) throws Exception {
		try {
			if(entranceId != null){
				flowEntranceMapper.deleteByPrimaryKey(entranceId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("entranceId为空");
		}
	}
}
