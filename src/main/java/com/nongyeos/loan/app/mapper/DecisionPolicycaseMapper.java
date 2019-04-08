package com.nongyeos.loan.app.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.app.entity.DecisionPolicycase;


public interface DecisionPolicycaseMapper {
    int deleteByPrimaryKey(String caseId);

    int insert(DecisionPolicycase record);

    int insertSelective(DecisionPolicycase record);

    DecisionPolicycase selectByPrimaryKey(String caseId);
    
    List<DecisionPolicycase> selectByAppId(Map<String,Object> map);

    int updateByPrimaryKeySelective(DecisionPolicycase record);

    int updateByPrimaryKey(DecisionPolicycase record);

	List<DecisionPolicycase> getList();
	
	List<DecisionPolicycase> selectByAppId2(String appId);

}