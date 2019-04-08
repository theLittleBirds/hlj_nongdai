package com.nongyeos.loan.admin.mapper;

import java.util.Map;

import com.nongyeos.loan.admin.entity.BusFollowData;

public interface BusFollowDataMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusFollowData record);

    int insertSelective(BusFollowData record);

    BusFollowData selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusFollowData record);

    int updateByPrimaryKey(BusFollowData record);
    
    int deleteBeforeSave(Map<String, String> map);
}