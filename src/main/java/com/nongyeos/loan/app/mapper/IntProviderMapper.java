package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.IntProvider;

public interface IntProviderMapper {
    int deleteByPrimaryKey(String providerCode);

    int insert(IntProvider record);

    int insertSelective(IntProvider record);

    IntProvider selectByPrimaryKey(String providerCode);

    int updateByPrimaryKeySelective(IntProvider record);

    int updateByPrimaryKey(IntProvider record);
    
	List<IntProvider> selectAll();

	int count();

	IntProvider selectByName(String name);
	
}