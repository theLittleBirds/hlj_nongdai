package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.AppEntity;


public interface AppEntityMapper {
    int deleteByPrimaryKey(String entityId);

    int insert(AppEntity record);

    int insertSelective(AppEntity record);

    AppEntity selectByPrimaryKey(String entityId);

    int updateByPrimaryKeySelective(AppEntity record);

    int updateByPrimaryKey(AppEntity record);

	List<AppEntity> selectAll(String appId);
	
	List<AppEntity> selectByType();

	List<AppEntity> selectAll();
}