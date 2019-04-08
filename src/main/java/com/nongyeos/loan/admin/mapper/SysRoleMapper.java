package com.nongyeos.loan.admin.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.SysRole;

public interface SysRoleMapper {
    int deleteByPrimaryKey(String roleId);

    int insert(SysRole sysRole);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(String roleId);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

	List<SysRole> getRoleListByOrgId(String id);

	SysRole selectByCname(Map<String, Object> map);
	
	SysRole selectByEname(Map<String, Object> map);
	
	List<SysRole> selectAll();
	
	List<SysRole> selectByStatus(Map<String, Object> map);

	int count(String orgId);
	
	List<SysRole> getRolesByPersonOrg(List<String> orgs);
}