package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.DecisionRule;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IRuleService {

	void add(DecisionRule score) throws Exception;

	void update(DecisionRule score) throws Exception;

	void deleteById(String ruleId) throws Exception;

	DecisionRule getRuleById(String ruleId) throws Exception;

	List<DecisionRule> selectListByAppIdAndPara(String appId, String category) throws Exception;

	List<DecisionRule> getRuleByAppId(String appId) throws Exception;

	DecisionRule selectById(String ruleId) throws Exception;

	PageBeanUtil<DecisionRule> rulePage(int currentPage, int pageSize,
			String appId, String value) throws Exception;

}
