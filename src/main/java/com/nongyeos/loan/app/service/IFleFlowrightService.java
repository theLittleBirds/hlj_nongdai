package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.FleFlowright;

public interface IFleFlowrightService {

	List<FleFlowright> selectByFlowCode(String flowCode) throws Exception;

	void delByFlowCode(String flowCode) throws Exception;

	void insertRole(FleFlowright flowright) throws Exception;

}
