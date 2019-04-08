package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.FlowNevent;

public interface IFlowNeventService {

	List<FlowNevent> selectByNodeId(String nodeId) throws Exception;

	void addNevent(FlowNevent flowNevent) throws Exception;

	void updateNevent(FlowNevent flowNevent) throws Exception;

	void deleteNevent(String neventId) throws Exception;

	FlowNevent selectByNeventId(String neventId)throws Exception;

}
