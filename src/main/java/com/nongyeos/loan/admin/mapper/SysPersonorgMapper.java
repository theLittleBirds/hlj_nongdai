package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.SysPersonorg;

public interface SysPersonorgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysPersonorg record);

    int insertSelective(SysPersonorg record);

    SysPersonorg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysPersonorg record);

    int updateByPrimaryKey(SysPersonorg record);

	void deleteByPersonId(String personId);
	
	void deleteByOrgId(String orgId);
	
	List<SysPersonorg>selectByPersonId(String personId);
}