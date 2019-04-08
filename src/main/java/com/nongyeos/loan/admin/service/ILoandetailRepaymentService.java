package com.nongyeos.loan.admin.service;

import com.nongyeos.loan.admin.entity.BusLoandetailRepayment;
import com.nongyeos.loan.admin.resultMap.LoandetailRepaymentMap;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface ILoandetailRepaymentService {
    int deleteByPrimaryKey(String id) throws Exception;

    int insert(BusLoandetailRepayment record) throws Exception;

    int insertSelective(BusLoandetailRepayment record) throws Exception;

    BusLoandetailRepayment selectByPrimaryKey(String id) throws Exception;

    int updateByPrimaryKeySelective(BusLoandetailRepayment record) throws Exception;

    int updateByPrimaryKey(BusLoandetailRepayment record) throws Exception;
    
    PageBeanUtil<LoandetailRepaymentMap> queryWaitVerify(int currentPage,int pageSize, LoandetailRepaymentMap record) throws Exception; 
}