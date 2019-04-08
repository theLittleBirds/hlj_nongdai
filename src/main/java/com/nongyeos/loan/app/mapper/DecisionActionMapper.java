package com.nongyeos.loan.app.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.app.entity.DecisionAction;


public interface DecisionActionMapper {
    int deleteByPrimaryKey(String actionId);

    int insert(DecisionAction record);

    int insertSelective(DecisionAction record);

    DecisionAction selectByPrimaryKey(String actionId);

    int updateByPrimaryKeySelective(DecisionAction record);

    int updateByPrimaryKey(DecisionAction record);

	List<DecisionAction> selectByAppIdAndCategory(Map<String, Object> map);
	
	int countByAppIdAndCategory(Map<String, Object> map);
	
	List<DecisionAction> selectByAppId(String appId);

	List<DecisionAction> selectAll();
}