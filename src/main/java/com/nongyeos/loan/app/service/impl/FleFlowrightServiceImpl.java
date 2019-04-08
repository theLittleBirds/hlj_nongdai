package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.app.entity.FleFlowright;
import com.nongyeos.loan.app.mapper.FleFlowrightMapper;
import com.nongyeos.loan.app.service.IFleFlowrightService;

@Service("flowrightService")
public class FleFlowrightServiceImpl implements IFleFlowrightService{

	@Autowired
	private FleFlowrightMapper flowrightMapper;
	
	@Override
	public List<FleFlowright> selectByFlowCode(String flowCode)throws Exception{
		try{
			if(flowCode != null && !flowCode.equals("")){
				List<FleFlowright> list = flowrightMapper.selectByFlowCode(flowCode);
			    return list;
			}else{
				throw new Exception("flowCode为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delByFlowCode(String flowCode)throws Exception{
		try{
			if(flowCode != null && !flowCode.equals("")){
				flowrightMapper.deleteByFlowCode(flowCode);
			}else{
				throw new Exception("flowCode为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void insertRole(FleFlowright flowright)throws Exception{
		try{
			if(flowrightMapper != null){
				flowrightMapper.insertSelective(flowright);
			}else{
				throw new Exception("对象为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
}
