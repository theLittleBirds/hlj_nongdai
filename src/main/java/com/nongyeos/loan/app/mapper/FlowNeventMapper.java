package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.FlowNevent;

public interface FlowNeventMapper {
    int deleteByPrimaryKey(String neventId);

    int insert(FlowNevent record);

    int insertSelective(FlowNevent record);

    FlowNevent selectByPrimaryKey(String neventId);

    int updateByPrimaryKeySelective(FlowNevent record);

    int updateByPrimaryKey(FlowNevent record);

	List<FlowNevent> selectByNodeId(String nodeId);
}