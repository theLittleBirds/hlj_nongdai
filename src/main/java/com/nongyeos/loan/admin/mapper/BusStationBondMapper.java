package com.nongyeos.loan.admin.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.BusStationBond;

public interface BusStationBondMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusStationBond record);

    int insertSelective(BusStationBond record);

    BusStationBond selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusStationBond record);

    int updateByPrimaryKey(BusStationBond record);
    
    List<BusStationBond> queryAll(Map<String, String> map);
    
    int queryAllCount(Map<String, String> map);
    
    BusStationBond selectByIpId(String intoPieceId);
}