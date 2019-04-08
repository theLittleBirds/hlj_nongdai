package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.DecisionCasebase;
import com.nongyeos.loan.app.entity.DecisionPolicycase;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IDecisionPolicycaseService {

	List<DecisionPolicycase> getlistByAppId(String appId, String value)throws Exception;

	void addCase(DecisionPolicycase Pocase) throws Exception;

	void delCase(String caseId) throws Exception;

	void updateCase(DecisionPolicycase Pocase) throws Exception;

	DecisionPolicycase getCase(String caseId) throws Exception;

	PageBeanUtil<DecisionCasebase> selectByPage(int currentPage, int pageSize, String caseId, int type) throws Exception;

	void delCaseAndBase(String caseId,int type) throws Exception;

	void addCaseAndBase(DecisionCasebase caba) throws Exception;

	void delCaba(int csbaseId) throws Exception;

	List<DecisionCasebase> getCabaByType(String caseId, int type) throws Exception;

	List<DecisionPolicycase> getList() throws Exception;

	List<DecisionPolicycase> getListByAppId2(String appId) throws Exception;

}

