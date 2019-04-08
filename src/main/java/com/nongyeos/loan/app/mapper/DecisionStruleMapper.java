package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.DecisionStrule;


public interface DecisionStruleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DecisionStrule record);

    int insertSelective(DecisionStrule record);

    DecisionStrule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DecisionStrule record);

    int updateByPrimaryKey(DecisionStrule record);
    
    List<DecisionStrule> selectByStrategyId(String strategyId);
    
    int count(String strategyId);
    
    void deleteByStrategyId(String strategyId);
}