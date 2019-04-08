package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.SysOrgApplication;

public interface SysOrgApplicationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysOrgApplication record);

    int insertSelective(SysOrgApplication record);

    SysOrgApplication selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysOrgApplication record);

    int updateByPrimaryKey(SysOrgApplication record);

	void deleteByOrgId(String orgId);

	List<SysOrgApplication> selectByOrgId(String orgId);
}