package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusExamine;

public interface BusExamineMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusExamine record);

    int insertSelective(BusExamine record);

    BusExamine selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusExamine record);

    int updateByPrimaryKey(BusExamine record);
    
    List<BusExamine> historyByIpId(String intoPieceId);
    
    BusExamine selectByIpIdNode(BusExamine record);

	BusExamine selectByIpIdLast(String intoPieceId);
}