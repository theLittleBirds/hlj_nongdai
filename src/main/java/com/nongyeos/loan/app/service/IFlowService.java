package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.FleFlow;

public interface IFlowService {

	List<FleFlow> selectByFinsCode(String finsCode) throws Exception;

	void add(FleFlow flow);

	void update(FleFlow flow);

	void deleteById(String flowId) throws Exception;

	FleFlow getFlowById(String flowId) throws Exception;

}
