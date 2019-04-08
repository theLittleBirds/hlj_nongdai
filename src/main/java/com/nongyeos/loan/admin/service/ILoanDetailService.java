package com.nongyeos.loan.admin.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusLoan;
import com.nongyeos.loan.admin.entity.BusLoanDetail;
import com.nongyeos.loan.admin.entity.BusProduct;
import com.nongyeos.loan.admin.model.RepayXlsx;

public interface ILoanDetailService {
	
	int updateByPrimaryKeySelective(BusLoanDetail record) throws Exception;
	
	List<BusLoanDetail> selectByLoanId(String loanId) throws Exception;
	
	BusLoanDetail selectByPrimaryKey(String id) throws Exception;
	
	BusLoanDetail selectNextRepay(BusLoanDetail record) throws Exception;
	
	JSONObject underLineRepay(String id,String pId) throws Exception;

	JSONObject onLineRepay(String id,String pId) throws Exception;

	BusLoanDetail selectLastComplete(String id)throws Exception;
	
	List<Map<String, Object>> queryLoanDetail(BusLoanDetail record) throws Exception;
	
	void creatDetail(BusLoan loan,BusProduct product,Date loanTime) throws Exception;
	
	void importRepayXlse(List<RepayXlsx> list,String personId) throws Exception;
	
	void importFinishXlse(List<RepayXlsx> list,String personId) throws Exception;
	
}
