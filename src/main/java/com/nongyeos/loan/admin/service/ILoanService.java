package com.nongyeos.loan.admin.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusLoan;
import com.nongyeos.loan.admin.resultMap.LoanMap;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface ILoanService {
	
	int insert(BusLoan record) throws Exception;
	
	BusLoan selectByPrimaryKey(String id) throws Exception;
	
	int updateByPrimaryKeySelective(BusLoan record) throws Exception;

    int updateByPrimaryKey(BusLoan record) throws Exception;
    
    PageBeanUtil<LoanMap> selectLoanByPage(int currentPage,int pageSize, LoanMap loanMap) throws Exception;
    
    PageBeanUtil<LoanMap> selectByPage(int currentPage,int pageSize, LoanMap loanMap) throws Exception;
    
    PageBeanUtil<LoanMap> loanFinishPage(int currentPage,int pageSize, LoanMap loanMap) throws Exception;
    
    PageBeanUtil<LoanMap> contactRecordPage(int currentPage,int pageSize, LoanMap loanMap) throws Exception;
    
    BusLoan selectByIpId(String intoPieceId) throws Exception;
    
    JSONObject examineSave(String id,String loanId,String nextNodeId,String pcId,String personId) throws Exception;
    
    List<LoanMap> alreadyLoanExport(LoanMap loanMap) throws Exception;
}
