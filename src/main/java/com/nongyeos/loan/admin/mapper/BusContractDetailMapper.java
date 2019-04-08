package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusContractDetail;

public interface BusContractDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusContractDetail record);

    int insertSelective(BusContractDetail record);

    BusContractDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusContractDetail record);

    int updateByPrimaryKey(BusContractDetail record);
    
    int selectCount(BusContractDetail record);
    
    List<BusContractDetail> selectByFinsId(BusContractDetail record);
    
    List<BusContractDetail> contractList(String lenderId);
}