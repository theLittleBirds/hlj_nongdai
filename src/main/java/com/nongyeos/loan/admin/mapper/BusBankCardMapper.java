package com.nongyeos.loan.admin.mapper;

import com.nongyeos.loan.admin.entity.BusBankCard;

public interface BusBankCardMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusBankCard record);

    int insertSelective(BusBankCard record);

    BusBankCard selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusBankCard record);

    int updateByPrimaryKey(BusBankCard record);
    
    BusBankCard queryByLoanId(String loanId);
}