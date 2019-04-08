package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.FlowEntrance;

public interface FlowEntranceMapper {
    int deleteByPrimaryKey(Integer entranceId);

    int insert(FlowEntrance record);

    int insertSelective(FlowEntrance record);

    FlowEntrance selectByPrimaryKey(Integer entranceId);

    int updateByPrimaryKeySelective(FlowEntrance record);

    int updateByPrimaryKey(FlowEntrance record);

	List<FlowEntrance> selectByAppId(String appId);
}