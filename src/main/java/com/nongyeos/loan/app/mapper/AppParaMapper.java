package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.AppPara;


public interface AppParaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AppPara record);

    int insertSelective(AppPara record);

    AppPara selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppPara record);

    int updateByPrimaryKey(AppPara record);

	List<AppPara> selectAll(String appId);
	
	List<AppPara> selectByGroupName(AppPara record);
	
	List<AppPara> selectByOne(String appId);

	List<AppPara> selectByName(String appParaGroupName,String appId);

	List<AppPara> selectAllDS();

	int count(String appId);
}