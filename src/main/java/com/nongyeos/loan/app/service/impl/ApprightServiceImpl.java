package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.app.entity.AppAppright;
import com.nongyeos.loan.app.mapper.AppApprightMapper;
import com.nongyeos.loan.app.service.IApprightService;


@Service("apprightService")
public class ApprightServiceImpl implements IApprightService{
	
	@Autowired
	private AppApprightMapper apprightMapper;
	
	@Override
	public List<AppAppright> getApprightByEntityId(String entityId)throws Exception{
		try{
			List<AppAppright> list = apprightMapper.selectByEntityId(entityId);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("查询错误");
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void insertRole(AppAppright appright){
		try{
			if(appright != null){
				apprightMapper.insertSelective(appright);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteByEntityId(String entityId)throws Exception{
		try{
			if(entityId != null && !entityId.equals("")){
				apprightMapper.deleteByEntityId(entityId);
			}else{
				throw new Exception("Id为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

}
