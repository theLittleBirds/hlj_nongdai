package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusReturnVisit;

public interface BusReturnVisitMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusReturnVisit record);

    int insertSelective(BusReturnVisit record);

    BusReturnVisit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusReturnVisit record);

    int updateByPrimaryKey(BusReturnVisit record);
    
    List<BusReturnVisit> selectByLoanId(String LoanId);
}