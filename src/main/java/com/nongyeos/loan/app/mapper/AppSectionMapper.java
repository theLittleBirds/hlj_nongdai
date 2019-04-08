package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.AppSection;


public interface AppSectionMapper {
    int deleteByPrimaryKey(String sectionId);

    int insert(AppSection record);

    int insertSelective(AppSection record);

    AppSection selectByPrimaryKey(String sectionId);

    int updateByPrimaryKeySelective(AppSection record);

    int updateByPrimaryKey(AppSection record);

	List<AppSection> selectAll(String appId);

	int count(String appId);
}