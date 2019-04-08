package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.FlowDirection;

public interface FlowDirectionMapper {
    int deleteByPrimaryKey(Integer directionId);

    int insert(FlowDirection record);

    int insertSelective(FlowDirection record);

    FlowDirection selectByPrimaryKey(Integer directionId);

    int updateByPrimaryKeySelective(FlowDirection record);

    int updateByPrimaryKey(FlowDirection record);

	List<FlowDirection> selectByNodeId(String nodeId);
	
	FlowDirection selectByNodeIdAndType(FlowDirection record);
}