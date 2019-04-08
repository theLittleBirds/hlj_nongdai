package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.admin.resultMap.IntServiceimplMap;
import com.nongyeos.loan.app.entity.FlowNevent;
import com.nongyeos.loan.app.entity.IntServiceimpl;

public interface IntServiceimplMapper {
    int deleteByPrimaryKey(String servimplCode);

    int insert(IntServiceimpl record);

    int insertSelective(IntServiceimpl record);

    IntServiceimpl selectByPrimaryKey(String servimplCode);

    int updateByPrimaryKeySelective(IntServiceimpl record);

    int updateByPrimaryKey(IntServiceimpl record);

	List<IntServiceimpl> selectAll();

	int count();

	IntServiceimpl selectByName(String name);

	IntServiceimpl selectByLocalPCode(String code);
	
	List<IntServiceimplMap> selectByEventAndRunTime(FlowNevent fn);
}