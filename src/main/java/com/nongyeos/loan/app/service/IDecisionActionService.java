package com.nongyeos.loan.app.service;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.app.entity.DecisionAction;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IDecisionActionService {

	List<DecisionAction> selectByAppIdAndCategory(String appId, Short value) throws Exception;

	void addAppScore(DecisionAction action) throws Exception;

	void delAction(String actionId) throws Exception;

	void updateAppScore(DecisionAction action) throws Exception;

	List<DecisionAction> selectAll();

	PageBeanUtil<DecisionAction> actionPage(int currentPage, int pageSize,
			Map<String, Object> map) throws Exception;

}
