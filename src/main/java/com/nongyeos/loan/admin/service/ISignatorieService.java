package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.IntSignatories;

public interface ISignatorieService {
	
	IntSignatories selectByPrimaryKey(String id) throws Exception;

    int updateByPrimaryKeySelective(IntSignatories record) throws Exception;

    int updateByPrimaryKey(IntSignatories record) throws Exception;
    
    List<IntSignatories> selectIdSendSms(String loanId) throws Exception;
    
    List<IntSignatories> selectByapplySignFileId(String applySignFileId) throws Exception;
}
