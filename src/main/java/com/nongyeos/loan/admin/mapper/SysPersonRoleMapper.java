package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.SysPersonRole;

public interface SysPersonRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysPersonRole record);

    int insertSelective(SysPersonRole record);

    SysPersonRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysPersonRole record);

    int updateByPrimaryKey(SysPersonRole record);
    
    List<SysPersonRole> getRoleByPerson(String personId);
    
    void deleteByPersonId(String personId);
    
    List<SysPersonRole> getPersonByRole(String roleId);
    
    List<String> selectRoleByPerson(String personId);
}