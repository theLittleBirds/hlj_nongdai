package com.nongyeos.loan.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.app.entity.DecisionStrategy;
import com.nongyeos.loan.app.entity.DecisionStrule;
import com.nongyeos.loan.app.mapper.DecisionStrategyMapper;
import com.nongyeos.loan.app.mapper.DecisionStruleMapper;
import com.nongyeos.loan.app.service.IDecisionStrategyService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("strategyService")
public class DecisionStrategyServiceImpl implements IDecisionStrategyService{
	@Autowired
	private DecisionStrategyMapper strategyMapper;
	@Autowired
	private DecisionStruleMapper struleMapper;
	
	@Override
	public List<DecisionStrategy> selectByAppIdAndCategory(String appId,Short value) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		List<DecisionStrategy> list=null;
		try{
			if(appId!=null && value!=null){
				map.put("appId", appId);
				map.put("value", value);
				list=strategyMapper.selectByAppIdAndCategory(map);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("value转换异常");
		}
		return list;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addStrategy(DecisionStrategy strategy) throws Exception{
		try{
			if(strategy != null){
				strategyMapper.insertSelective(strategy);
			}else{
				throw new Exception ("对象为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateStrategy(DecisionStrategy strategy) throws Exception{
		try{
			if(strategy != null){
				strategyMapper.updateByPrimaryKey(strategy);
			}else{
				throw new Exception ("对象为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delStrategy(String strategyId) throws Exception{
		try{
			if(strategyId != null && !strategyId.equals("")){
				strategyMapper.deleteByPrimaryKey(strategyId);
			}else{
				throw new Exception ("id为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public DecisionStrategy getStrategy(String strategyId) throws Exception{
		DecisionStrategy bean = null;
		try{
			if(strategyId != null && !strategyId.equals("")){
				bean = strategyMapper.selectByPrimaryKey(strategyId);
			}else{
				throw new Exception ("id为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return bean;
	}
	
	@Override
	public PageBeanUtil<DecisionStrule> getStruleByStrategyId(int offset, int limit, String strategyId) throws Exception{
		try{
			    PageHelper.startPage(offset, limit);
				List<DecisionStrule> allItems = struleMapper.selectByStrategyId(strategyId);
				int countNums = struleMapper.count(strategyId);
				PageBeanUtil<DecisionStrule> pageData = new PageBeanUtil<DecisionStrule>(offset, limit, countNums);
				pageData.setItems(allItems);
				return pageData;
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("pagebean为空");
		}
	}
	
	@Override
	public List<DecisionStrule> getStrulelist(String strategyId) throws Exception{
		List<DecisionStrule> list = null;
		try{
			if(strategyId != null && !strategyId.equals("")){
				list = struleMapper.selectByStrategyId(strategyId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("pagebean为空");
		}
		return list;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delStrule(String strategyId) throws Exception{
		try{
			if(strategyId != null && !strategyId.equals("")){
				struleMapper.deleteByStrategyId(strategyId);
			}else{
				throw new Exception ("strategyId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addStrule(DecisionStrule strule) throws Exception{
		try{
			if(strule != null){
				struleMapper.insertSelective(strule);
			}else{
				throw new Exception ("对象为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateStrule(DecisionStrule strule) throws Exception{
		try{
			if(strule != null){
				struleMapper.updateByPrimaryKey(strule);
			}else{
				throw new Exception ("对象为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delStruleById(int id) throws Exception{
		try{
			if(id != 0){
				struleMapper.deleteByPrimaryKey(id);
			}else{
				throw new Exception ("id为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public List<DecisionStrategy> selectByAppId(String appId) throws Exception {
		List<DecisionStrategy> list = null;
		try{
			if(appId != null && !appId.equals("")){
				list = strategyMapper.selectByAppId(appId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception ("appId为空");
		}
		return list;
	}

	@Override
	public List<DecisionStrule> selectByStrategyId(String strategyId)throws Exception {
		List<DecisionStrule> list= null;
		try{
			if(strategyId != null && !strategyId.equals("")){
				list = struleMapper.selectByStrategyId(strategyId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception ("strategyId为空");
		}
		return list;
	}
}
