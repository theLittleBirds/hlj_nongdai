package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.DecisionStrategy;
import com.nongyeos.loan.app.entity.DecisionStrule;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IDecisionStrategyService {

	List<DecisionStrategy> selectByAppIdAndCategory(String appId, Short value)
			throws Exception;

	void addStrategy(DecisionStrategy strategy) throws Exception;

	void updateStrategy(DecisionStrategy strategy) throws Exception;

	void delStrategy(String strategyId) throws Exception;

	DecisionStrategy getStrategy(String strategyId) throws Exception;

	PageBeanUtil<DecisionStrule> getStruleByStrategyId(int offset, int limit,
			String strategyId) throws Exception;

	List<DecisionStrule> getStrulelist(String strategyId) throws Exception;

	void delStrule(String strategyId) throws Exception;

	void addStrule(DecisionStrule strule) throws Exception;

	void delStruleById(int id) throws Exception;

	void updateStrule(DecisionStrule strule) throws Exception;

	List<DecisionStrategy> selectByAppId(String appId) throws Exception;

	List<DecisionStrule> selectByStrategyId(String strategyId) throws Exception;



}
