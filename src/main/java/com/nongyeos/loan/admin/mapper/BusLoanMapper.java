package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusLoan;
import com.nongyeos.loan.admin.resultMap.LoanMap;

public interface BusLoanMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusLoan record);

    int insertSelective(BusLoan record);

    BusLoan selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusLoan record);

    int updateByPrimaryKey(BusLoan record);
    
    BusLoan selectByIpId(String intoPieceId);
    
    int selectLoanCountByCondition(LoanMap loanMap);
    
    List<LoanMap> selectLoanByCondition(LoanMap loanMap);
    
    int selectCountByCondition(LoanMap loanMap);
    
    List<LoanMap> selectByCondition(LoanMap loanMap);
    
    int loanFinishCount(LoanMap loanMap);
    
    List<LoanMap> loanFinish(LoanMap loanMap);
    
    List<LoanMap> selectContactRecord(LoanMap loanMap);
    
    int selectContactRecordCount(LoanMap loanMap);
}