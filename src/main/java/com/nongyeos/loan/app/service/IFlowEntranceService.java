package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.FlowEntrance;


public interface IFlowEntranceService {

	void addEntrance(FlowEntrance entrance)throws Exception;

	void updateEntrance(FlowEntrance entrance) throws Exception;

	List<FlowEntrance> selectByAppId(String appId) throws Exception;

	void delEntranceById(Integer entranceId) throws Exception;
	

}
