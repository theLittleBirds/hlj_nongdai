package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusApplySignFile;

public interface IApplySignFileService {
	
	int insert(BusApplySignFile record) throws Exception;
	
	BusApplySignFile selectByPrimaryKey(String id) throws Exception;

    int updateByPrimaryKeySelective(BusApplySignFile record) throws Exception;

    int updateByPrimaryKey(BusApplySignFile record) throws Exception;
    
    List<BusApplySignFile> selectByLoanId(String loanId) throws Exception;
    
    List<BusApplySignFile> waitSign(String loanId) throws Exception;
    
    List<BusApplySignFile> finishSign(String loanId) throws Exception;
}
