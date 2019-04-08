package com.nongyeos.loan.app.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.app.entity.AppSrvunit;


public interface AppSrvunitMapper {
    int deleteByPrimaryKey(String srvunId);

    int insert(AppSrvunit record);

    int insertSelective(AppSrvunit record);

    AppSrvunit selectByPrimaryKey(String srvunId);

    int updateByPrimaryKeySelective(AppSrvunit record);

    int updateByPrimaryKey(AppSrvunit record);

	List<AppSrvunit> selectByAppid(String appId);
	
	List<AppSrvunit> selectByNodeTime(Map<String,Object> map);
}