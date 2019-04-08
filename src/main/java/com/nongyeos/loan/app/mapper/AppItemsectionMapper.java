package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.AppItemsection;


public interface AppItemsectionMapper {
    int deleteByPrimaryKey(Integer seitId);

    int insert(AppItemsection record);

    int insertSelective(AppItemsection record);

    AppItemsection selectByPrimaryKey(Integer seitId);

    int updateByPrimaryKeySelective(AppItemsection record);

    int updateByPrimaryKey(AppItemsection record);
    
    List<AppItemsection> selectBySectionId(String sectionId);
    
    void deleteBySectionId(String sectionId);

	int count(String sectionId);
}