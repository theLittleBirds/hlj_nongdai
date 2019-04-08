package com.nongyeos.loan.core.service;

import org.apache.ibatis.session.SqlSession;

import com.nongyeos.loan.core.model.BusinessObject;

public interface DecisionStrategyMgr
{

	public boolean doStrategy(BusinessObject bo, String strategyId)throws Exception;

	boolean doStrategy2(BusinessObject bo, String strategyId, SqlSession session) throws Exception;
	
}
