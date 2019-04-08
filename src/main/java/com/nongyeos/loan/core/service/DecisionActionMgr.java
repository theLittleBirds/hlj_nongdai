package com.nongyeos.loan.core.service;

import com.nongyeos.loan.core.model.BusinessObject;

public interface DecisionActionMgr
{

	public boolean doAction(BusinessObject bo, String actionId) throws Exception;
	
}
