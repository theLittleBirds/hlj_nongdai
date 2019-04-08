package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.AppEntity;

public interface IEntityService {
	
	List<AppEntity> selectAll(String appId) throws Exception;

	void addEntity(AppEntity appEntity) throws Exception;

	void updateEntity(AppEntity appEntity) throws Exception;

	void deleteEntity(String enityId) throws Exception;

	List<AppEntity> getMainEntity(String appId);

	AppEntity getAppEntityByEntityId(String entityId) throws Exception;



}
