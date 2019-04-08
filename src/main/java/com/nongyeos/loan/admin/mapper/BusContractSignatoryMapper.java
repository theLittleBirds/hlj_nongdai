package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusContractSignatory;

public interface BusContractSignatoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusContractSignatory record);

    int insertSelective(BusContractSignatory record);

    BusContractSignatory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusContractSignatory record);

    int updateByPrimaryKey(BusContractSignatory record);
    
    List<BusContractSignatory> selectList(BusContractSignatory record);
    
    int selectCount(BusContractSignatory record);
    
    BusContractSignatory selectByEname(String ename);
}