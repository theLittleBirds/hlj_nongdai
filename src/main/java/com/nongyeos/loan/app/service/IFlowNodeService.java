package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.FlowNode;

public interface IFlowNodeService {


	void addFleNode(FlowNode fleNode) throws Exception;

	void updateFleNode(FlowNode fleNode) throws Exception;
	
	void updateFleNode1(FlowNode fleNode) throws Exception;

	void deleteFleNode(String fleNodeId) throws Exception;

	List<FlowNode> selectByAppId(String appId) throws Exception;

	FlowNode selectByNodeId(String nodeId) throws Exception;

	FlowNode getApproverIdsByNodeId(String nodeId) throws Exception;

	FlowNode queryByEnameAndModel(FlowNode fleNode) throws Exception;

	List<FlowNode> selectAll();
}
