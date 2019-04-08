package com.nongyeos.loan.admin.mapper;

import com.nongyeos.loan.admin.entity.BusContractData;

public interface BusContractDataMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusContractData record);

    int insertSelective(BusContractData record);

    BusContractData selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusContractData record);

    int updateByPrimaryKey(BusContractData record);
    
    BusContractData selectByloanId(String loanId);
    
    int deleteByLoanId(String loanId);
}