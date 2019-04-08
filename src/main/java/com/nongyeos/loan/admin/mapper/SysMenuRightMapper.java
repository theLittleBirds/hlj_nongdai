package com.nongyeos.loan.admin.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.SysMenuRight;

public interface SysMenuRightMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysMenuRight record);

    int insertSelective(SysMenuRight record);

    SysMenuRight selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysMenuRight record);

    int updateByPrimaryKey(SysMenuRight record);
    
    List<SysMenuRight> selectByMenuId(String menuId);
    
    void deleteByMenuId(String menuId);
    
    List<SysMenuRight> selectMenuByRoleId(List<String> roleIdList);
    
    void deleteByRoleId(String roleId);
    
    List<SysMenuRight> selectByRoleId(String roleId);
}