package com.nongyeos.loan.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.nongyeos.loan.app.entity.DecisionPolicybase;
import com.nongyeos.loan.app.mapper.DecisionPolicybaseMapper;
import com.nongyeos.loan.app.service.IDecisionPolicybaseService;

@Service("dePoliBaseService")
public class DecisionPolicybaseServiceImpl implements IDecisionPolicybaseService{

	@Autowired
	private DecisionPolicybaseMapper dePoliBaseMapper;
	
	@Override
	public List<DecisionPolicybase> getlistByAppId(String appId,String value)throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			if(!appId.equals("") && !value.equals("")){
				map.put("appId", appId);
				map.put("categoryId", value);
				List<DecisionPolicybase> list= dePoliBaseMapper.selectByAppId(map);
			    return list;
			}else{
				throw new Exception ("appId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<DecisionPolicybase> getAllByAppId(String appId)throws Exception{
		try{
			if(appId != null && !appId.equals("")){
				List<DecisionPolicybase> list = dePoliBaseMapper.selectAllByAppId(appId);
			    return list;
			}else{
				throw new Exception ("appId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addBase(DecisionPolicybase base)throws Exception{
		try{
			if(base != null){
				dePoliBaseMapper.insertSelective(base);
			}else{
				throw new Exception ("对象为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delBase(String baseId)throws Exception{
		try{
			if(baseId != null && !baseId.equals("")){
				dePoliBaseMapper.deleteByPrimaryKey(baseId);
			}else{
				throw new Exception ("主键为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateBase(DecisionPolicybase base)throws Exception{
		try{
			if(base != null){
				dePoliBaseMapper.updateByPrimaryKey(base);
			}else{
				throw new Exception ("对象为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public DecisionPolicybase getBase(String baseId)throws Exception{
		try{
			if(baseId != null && !baseId.equals("")){
				DecisionPolicybase bean = dePoliBaseMapper.selectByPrimaryKey(baseId);
			    return bean;
			}else{
				throw new Exception ("Id为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public DecisionPolicybase queryByPrimaryKeyAndDesc(String baseId,
			String miaoshu) throws Exception{
		try {
			if(StringUtils.isEmpty(baseId)){
				throw new Exception("baseId为空");
			}
			if(StringUtils.isEmpty(miaoshu)){
				throw new Exception("miaoshu为空");
			}
			
			return dePoliBaseMapper.queryByPrimaryKeyAndDesc(baseId, miaoshu);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	
	
}
