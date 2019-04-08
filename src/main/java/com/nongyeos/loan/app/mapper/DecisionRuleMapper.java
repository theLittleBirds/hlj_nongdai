package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.DecisionRule;

public interface DecisionRuleMapper {
    int deleteByPrimaryKey(String ruleId);

    int insert(DecisionRule record);

    int insertSelective(DecisionRule record);

    DecisionRule selectByPrimaryKey(String ruleId);

    int updateByPrimaryKeySelective(DecisionRule record);

    int updateByPrimaryKey(DecisionRule record);
    
    int countByAppIdAndPara(String appId, String category);

	List<DecisionRule> selectListByAppIdAndPara(String appId, String category);
	
	List<DecisionRule> selectByAppId(String appId);
}