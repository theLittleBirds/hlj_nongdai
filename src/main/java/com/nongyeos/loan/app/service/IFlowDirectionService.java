package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.FlowDirection;


public interface IFlowDirectionService {

	List<FlowDirection> selectByNodeId(String nodeId) throws Exception;

	void delDirectionsById(Integer directionid) throws Exception;

	void updateDirction(FlowDirection dirction) throws Exception;

	void addDirction(FlowDirection dirction) throws Exception;

}
