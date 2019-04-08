package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.AppAppright;

public interface IApprightService {

	List<AppAppright> getApprightByEntityId(String entityId)throws Exception;

	void insertRole(AppAppright appright);

	void deleteByEntityId(String entityId) throws Exception;

}
