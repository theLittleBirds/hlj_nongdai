package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.AppApplication;


public interface AppApplicationMapper {
    int deleteByPrimaryKey(String appId);

    int insert(AppApplication record);

    int insertSelective(AppApplication record);

    AppApplication selectByPrimaryKey(String appId);

    int updateByPrimaryKeySelective(AppApplication record);

    int updateByPrimaryKey(AppApplication record);
    
    List<AppApplication> selectByFinsCode(String finsCode);

	List<AppApplication> selectAllApplications();
	
	List<AppApplication> selectByGroupId(String groupId);
	
	int count(String groupId);
	
	List<AppApplication> selectApplicationsByOrg(String orgId);
	
	List<AppApplication> quetyByFinsIdType(AppApplication record);
}