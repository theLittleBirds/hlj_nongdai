package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.DecisionRuleact;

public interface DecisionRuleactMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DecisionRuleact record);

    int insertSelective(DecisionRuleact record);

    DecisionRuleact selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DecisionRuleact record);

    int updateByPrimaryKey(DecisionRuleact record);

	List<DecisionRuleact> selectAll(String code, int type);

	int count(String code, int type);

	DecisionRuleact selectByActionId(String actionId);
	
	List<DecisionRuleact> selectAllByRuleId(String ruleId);

	List<DecisionRuleact> getLeftListByRuleId(String ruleId, Short type);

	void deleteByRuleId(String ruleId, Short type);

	List<DecisionRuleact> getRightListByRuleId(String ruleId, Short type);
}