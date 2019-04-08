package com.nongyeos.loan.app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.app.entity.DecisionAction;
import com.nongyeos.loan.app.entity.DecisionRule;
import com.nongyeos.loan.app.mapper.DecisionRuleMapper;
import com.nongyeos.loan.app.service.IRuleService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("ruleService")
public class RuleServiceImpl implements IRuleService{
	
	@Autowired
	private DecisionRuleMapper ruleMapper;
	
	@Override
	public List<DecisionRule> selectListByAppIdAndPara(String appId, String category) throws Exception{
		try{
			if(appId != null && !appId.equals("")){
				List<DecisionRule> list = ruleMapper.selectListByAppIdAndPara(appId,category);
				return list;
			}else{
				throw new Exception("appId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void add(DecisionRule rule) throws Exception{
		try{
			ruleMapper.insertSelective(rule);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(DecisionRule rule) throws Exception{
		try{
			ruleMapper.updateByPrimaryKey(rule);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(String ruleId)throws Exception{
		try{
			if(ruleId != null && !ruleId.equals("")){
				ruleMapper.deleteByPrimaryKey(ruleId);
			}else{
				throw new Exception("规则Id为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	public DecisionRule getRuleById(String ruleId)throws Exception{
		try{
			if(ruleId != null && !ruleId.equals("")){
				DecisionRule rule = ruleMapper.selectByPrimaryKey(ruleId);
		        return rule;
			}else{
				throw new Exception("规则Id为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	public List<DecisionRule> getRuleByAppId(String appId) throws Exception{
		try{
			if(appId != null && !appId.equals("")){
				List <DecisionRule> list = ruleMapper.selectByAppId(appId);
				return list;
			}else{
				throw new Exception("appId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public DecisionRule selectById(String ruleId) throws Exception {
		DecisionRule rule = null;
		try{
			if(ruleId != null && !ruleId.equals("")){
				rule= ruleMapper.selectByPrimaryKey(ruleId);
			}else{
				throw new Exception("appId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
		return rule;
	}
	
	@Override
	public PageBeanUtil<DecisionRule> rulePage(int currentPage, int pageSize, String appId, String value) throws Exception{
		try {
			//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			PageHelper.startPage(currentPage, pageSize, false);
			List<DecisionRule> allItems = ruleMapper.selectListByAppIdAndPara(appId,value);
			int countNums = ruleMapper.countByAppIdAndPara(appId, value);
			PageBeanUtil<DecisionRule> pageData = new PageBeanUtil<DecisionRule>(currentPage, pageSize, countNums);
			pageData.setItems(allItems);
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("pagebean为空");
		}
	}

//	@Override
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
//	public void delByScoreId(String scoreid) throws Exception {
//		try{
//			if(scoreid!=null){
//				scoreRightMapper.deleteByScoreId(scoreid);
//			}else {
//				throw new Exception("scoreid为空");
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//			throw new Exception(e);
//		}
//	}

//	@Override
//	public List<ScoreScoreright> getScoreRightByScoreId(String scoreid) throws Exception{
//		try{
//			if(scoreid != null){
//				List<ScoreScoreright> list = scoreRightMapper.selectByScoreId(scoreid);
//				return list;
//			}else {
//				throw new Exception("scoreid为空");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new Exception(e);
//		}
//	}

//	@Override
//	public List<ScoreScore> selectByCategory(String value) throws Exception {
//		try {
//			List<ScoreScore> list = scoreMapper.selectByCategory(value);
//			return list;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}

//	@Override
//	public List<ScoreScore> selectAll() throws Exception {
//		List<ScoreScore> list=scoreMapper.selectAll();
//		return list;
//	}

}
