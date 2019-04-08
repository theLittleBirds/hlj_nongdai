package com.nongyeos.loan.app.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.app.entity.DecisionStrategy;


public interface DecisionStrategyMapper {
    int deleteByPrimaryKey(String strategyId);

    int insert(DecisionStrategy record);

    int insertSelective(DecisionStrategy record);

    DecisionStrategy selectByPrimaryKey(String strategyId);

    int updateByPrimaryKeySelective(DecisionStrategy record);

    int updateByPrimaryKey(DecisionStrategy record);
    
    List<DecisionStrategy> selectByAppIdAndCategory(Map<String,Object> map);

	List<DecisionStrategy> selectByAppId(String appId);
}