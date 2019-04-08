package com.nongyeos.loan.core.service;

import com.nongyeos.loan.core.model.BusinessObject;

public interface DecisionRuleMgr
{

	public boolean doRule(BusinessObject bo, String ruleId) throws Exception;
	
}
