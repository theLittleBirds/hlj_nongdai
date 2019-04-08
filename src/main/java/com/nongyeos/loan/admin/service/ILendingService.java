package com.nongyeos.loan.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusLoan;
import com.nongyeos.loan.admin.entity.BusMoneyLog;

public interface ILendingService {
	
	public JSONObject underLineLoan(BusLoan loan,String personId) throws Exception;
	
	public JSONObject onLineLoan(BusLoan loan,String personId) throws Exception;
}
