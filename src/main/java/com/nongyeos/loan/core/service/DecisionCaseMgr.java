package com.nongyeos.loan.core.service;

import com.nongyeos.loan.app.entity.DecisionPolicycase;
import com.nongyeos.loan.core.model.BusinessObject;

public interface DecisionCaseMgr
{

	public boolean logicExpress(BusinessObject bo, String caseId) throws Exception;
	
	public boolean logicExpress(BusinessObject bo, DecisionPolicycase pcase) throws Exception;
	
	boolean logicExpress2(BusinessObject bo, String caseId) throws Exception;
	
}
