package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.app.entity.DecisionRuleact;
import com.nongyeos.loan.app.mapper.DecisionRuleactMapper;
import com.nongyeos.loan.app.service.IRuleactService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("actService")
public class RuleactServiceImpl implements IRuleactService{
	
	@Autowired
	private DecisionRuleactMapper actMapper;
	
	@Override
	public PageBeanUtil<DecisionRuleact> selectByPage(int limit, int offset,
			String code, int type) throws Exception {
		try {
			//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			PageHelper.startPage(offset, limit);
			List<DecisionRuleact> allItems = actMapper.selectAll(code,type);
			int countNums = actMapper.count(code,type);//总记录数
			PageBeanUtil<DecisionRuleact> pageData = new PageBeanUtil<DecisionRuleact>(offset, limit, countNums);
			pageData.setItems(allItems);
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<DecisionRuleact> getList(String code, int type) {
		List<DecisionRuleact> list = actMapper.selectAll(code,type);
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void add(DecisionRuleact act) throws Exception{
		try{
			actMapper.insertSelective(act);
		}catch(Exception e){
			throw new Exception(e);
		}
	}

	@Override
	public DecisionRuleact selectByActionId(String actionId) {
		DecisionRuleact act = actMapper.selectByActionId(actionId);
		return act;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteByPrimaryKey(Integer id) throws Exception {
		try{
			actMapper.deleteByPrimaryKey(id);
		}catch(Exception e){
			throw new Exception(e);
		}
	}

	@Override
	public List<DecisionRuleact> getLeftListByRuleId(String ruleId, Short type) throws Exception{
		try {
			List<DecisionRuleact> list = actMapper.getLeftListByRuleId(ruleId,type);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteByRuleId(String ruleId, Short type) throws Exception {
		try {
			actMapper.deleteByRuleId(ruleId,type);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<DecisionRuleact> getRightListByRuleId(String ruleId, Short type) throws Exception{
		try {
			List<DecisionRuleact> list = actMapper.getRightListByRuleId(ruleId,type);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
