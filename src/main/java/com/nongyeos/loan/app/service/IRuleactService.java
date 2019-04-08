package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.DecisionRuleact;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IRuleactService {

	PageBeanUtil<DecisionRuleact> selectByPage(int limit, int offset,
			String code, int type) throws Exception;

	List<DecisionRuleact> getList(String code, int type);

	void add(DecisionRuleact act) throws Exception;

	DecisionRuleact selectByActionId(String actionId);

	void deleteByPrimaryKey(Integer id) throws Exception;

	List<DecisionRuleact> getLeftListByRuleId(String ruleId, Short type) throws Exception;

	void deleteByRuleId(String ruleId, Short type) throws Exception;

	List<DecisionRuleact> getRightListByRuleId(String ruleId, Short type)throws Exception;

}
