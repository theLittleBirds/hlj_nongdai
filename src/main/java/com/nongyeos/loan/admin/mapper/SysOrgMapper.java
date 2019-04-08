package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.SysOrg;

public interface SysOrgMapper {
    int deleteByPrimaryKey(String orgId);

    int insert(SysOrg record);

    int insertSelective(SysOrg record);

    SysOrg selectByPrimaryKey(String orgId);
    
    SysOrg selectByName(String name);

    int updateByPrimaryKeySelective(SysOrg record);

    int updateByPrimaryKey(SysOrg record);
    
    List<SysOrg> selectPrList();
    
    List<SysOrg> selectPList(String Id);

	List<SysOrg> selectByNameSearch(SysOrg org);

	List<String> selectByIdOrParent(String orgId);

	List<SysOrg> selectOrgsByPerson(String personId);

	List<SysOrg> selectAll();
	
	List<SysOrg> selectBaseOrg();

	List<SysOrg> selectipOrgs(String personId);
}