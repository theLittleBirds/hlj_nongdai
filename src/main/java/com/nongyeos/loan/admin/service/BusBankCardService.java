package com.nongyeos.loan.admin.service;

import com.nongyeos.loan.admin.entity.BusBankCard;

public interface BusBankCardService {
    int deleteByPrimaryKey(String id) throws Exception;

    int insert(BusBankCard record) throws Exception;

    int insertSelective(BusBankCard record) throws Exception;

    BusBankCard selectByPrimaryKey(String id) throws Exception;

    int updateByPrimaryKeySelective(BusBankCard record) throws Exception;

    int updateByPrimaryKey(BusBankCard record) throws Exception;
    
    BusBankCard queryByLoanId(String loanId) throws Exception;
}