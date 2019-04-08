package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.FlowData;

public interface IFlowDataService {

	void addFlowData(FlowData flowData) throws Exception;

	void updateFlowDataz(FlowData flowData) throws Exception;

	void deleteById(Integer dataid) throws Exception;

	List<FlowData> selectByNodeIdAndType(String nodeId, Integer type) throws Exception;

	boolean existenceObject(Short controlType, String objectId,Short objectType, String nodeId) throws Exception;

}
