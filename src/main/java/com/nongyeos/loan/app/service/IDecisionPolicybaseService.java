package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.DecisionPolicybase;

public interface IDecisionPolicybaseService {
	
	List<DecisionPolicybase> getlistByAppId(String appId,String value)throws Exception;

	void addBase(DecisionPolicybase base) throws Exception;

	void delBase(String baseId) throws Exception;

	void updateBase(DecisionPolicybase base) throws Exception;

	DecisionPolicybase getBase(String baseId) throws Exception;

	DecisionPolicybase queryByPrimaryKeyAndDesc(String baseId, String miaoshu) throws Exception;
	
	List<DecisionPolicybase> getAllByAppId(String appId) throws Exception;

}
