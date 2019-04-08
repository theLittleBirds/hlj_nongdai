package com.nongyeos.loan.core.service;

import com.nongyeos.loan.app.entity.DecisionPolicybase;
import com.nongyeos.loan.core.model.BusinessObject;

public interface DecisionBaseMgr
{
	
	public boolean logicExpress(BusinessObject bo, String baseId, short mode) throws Exception;
	public boolean logicExpress(BusinessObject bo, DecisionPolicybase base, short mode) throws Exception;
	
}
