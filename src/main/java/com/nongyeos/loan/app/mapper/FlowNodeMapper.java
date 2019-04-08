package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.FlowNode;


public interface FlowNodeMapper {
    int deleteByPrimaryKey(String nodeId);

    int insert(FlowNode record);

    int insertSelective(FlowNode record);

    FlowNode selectByPrimaryKey(String nodeId);

    int updateByPrimaryKeySelective(FlowNode record);

    int updateByPrimaryKey(FlowNode record);

	List<FlowNode> selectByAppId(String appId);

	FlowNode selectByNodeId(String nodeId);
	
	FlowNode queryByEnameAndModel(FlowNode node);

	List<FlowNode> selectAll();
}