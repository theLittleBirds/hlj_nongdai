package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusLoandetailRepayment;
import com.nongyeos.loan.admin.resultMap.LoandetailRepaymentMap;

public interface BusLoandetailRepaymentMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusLoandetailRepayment record);

    int insertSelective(BusLoandetailRepayment record);

    BusLoandetailRepayment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusLoandetailRepayment record);

    int updateByPrimaryKey(BusLoandetailRepayment record);
    
    List<LoandetailRepaymentMap> queryWaitVerify(LoandetailRepaymentMap record);
}