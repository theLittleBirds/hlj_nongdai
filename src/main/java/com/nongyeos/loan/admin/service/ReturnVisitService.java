package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusReturnVisit;

public interface ReturnVisitService {
	
	int insert(BusReturnVisit record) throws Exception;
	
	List<BusReturnVisit> selectByLoanId(String LoanId) throws Exception;
}
