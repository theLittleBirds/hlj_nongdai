package com.nongyeos.loan.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.SysActLog;
import com.nongyeos.loan.app.entity.DecisionAction;
import com.nongyeos.loan.app.mapper.DecisionActionMapper;
import com.nongyeos.loan.app.service.IDecisionActionService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("actionService")
public class DecisionActionServiceImpl implements IDecisionActionService {

	@Autowired
	private DecisionActionMapper actionMapper;

	@Override
	public List<DecisionAction> selectByAppIdAndCategory(String appId,Short value) throws Exception {
		Map<String,Object> map=new HashMap<String, Object>();
		List<DecisionAction> list=null;
		try{
			if(appId!=null && value!=null){
				map.put("appId", appId);
				map.put("value", value);
				list=actionMapper.selectByAppIdAndCategory(map);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("value转换异常");
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addAppScore(DecisionAction action) throws Exception {
		try{
			if(action!=null){
			actionMapper.insertSelective(action);	
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("action为空");
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateAppScore(DecisionAction action) throws Exception {
		try{
			if(action!=null){
				actionMapper.updateByPrimaryKey(action);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("action为空");
		}
		
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delAction(String actionId) throws Exception {
		try{
			if(actionId!=null && !actionId.equals("")){
				actionMapper.deleteByPrimaryKey(actionId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("actionId为空");
		}
		
	}

	@Override
	public List<DecisionAction> selectAll() {
		List<DecisionAction> list = actionMapper.selectAll();
		return list;
	}
	
	@Override
	public PageBeanUtil<DecisionAction> actionPage(int currentPage, int pageSize, Map<String,Object> map) throws Exception{
		try {
			//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			PageHelper.startPage(currentPage, pageSize, false);
			List<DecisionAction> allItems = actionMapper.selectByAppIdAndCategory(map);
			int countNums = actionMapper.countByAppIdAndCategory(map);//总记录数
			PageBeanUtil<DecisionAction> pageData = new PageBeanUtil<DecisionAction>(currentPage, pageSize, countNums);
			pageData.setItems(allItems);
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("pagebean为空");
		}
	}
}
