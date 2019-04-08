package com.nongyeos.loan.admin.service;

import com.nongyeos.loan.admin.entity.BusContractData;

public interface IContractDataService {
	
	int insert(BusContractData record) throws Exception;
	
	BusContractData selectByPrimaryKey(String id) throws Exception;
	
	int updateByPrimaryKeySelective(BusContractData record) throws Exception;
	
	BusContractData selectByloanId(String loanId) throws Exception;
	
	int deleteByLoan(String loanId) throws Exception;
}
