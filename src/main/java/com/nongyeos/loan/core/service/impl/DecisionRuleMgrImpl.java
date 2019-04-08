package com.nongyeos.loan.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nongyeos.loan.app.entity.DecisionRule;
import com.nongyeos.loan.app.entity.DecisionRuleact;
import com.nongyeos.loan.app.mapper.DecisionRuleMapper;
import com.nongyeos.loan.app.mapper.DecisionRuleactMapper;
import com.nongyeos.loan.core.model.BusinessObject;
import com.nongyeos.loan.core.service.DecisionActionMgr;
import com.nongyeos.loan.core.service.DecisionCaseMgr;
import com.nongyeos.loan.core.service.DecisionRuleMgr;

@Service("RuleMgrImpl")
public class DecisionRuleMgrImpl implements DecisionRuleMgr{

	@Autowired
	private DecisionRuleMapper ruleMapper;
	@Autowired
	private DecisionCaseMgr caseMgrService;
	@Autowired
	private DecisionRuleactMapper actMapper;
	@Autowired
	private DecisionActionMgr actionMgrService;
	
	@Override
	public boolean doRule(BusinessObject bo, String ruleId) throws Exception{
		boolean flag = false;
		boolean action = false;
		try {
			DecisionRule rule = ruleMapper.selectByPrimaryKey(ruleId);
			String caseId = rule.getCaseId();
			flag = caseMgrService.logicExpress(bo, caseId);
			int type;
			if (flag) {
				type = 1;
			}else{
				type = 0;
			}
			List<DecisionRuleact> list = actMapper.selectAll(ruleId, type);
			for (int i = 0; i < list.size(); i++) {
					action = actionMgrService.doAction(bo, list.get(i).getActionId());
			}
			if(list.size() == 0){
				action = true;
			}
			return action;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception (e);
		}
	}
	
}
