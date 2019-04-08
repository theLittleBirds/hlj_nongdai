package com.nongyeos.loan.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.nongyeos.loan.admin.entity.SysMenu;

public interface SysMenuMapper {
    int deleteByPrimaryKey(String menuId);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(String menuId);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);
    
    List<SysMenu> selectAllMenuList();
    
    List<SysMenu> selectFatherMenuList(List<String> menuIdList);
    
    List<SysMenu> selectAllFatherMenuList();
    
    List<SysMenu> selectChildMenuList(Map<String,Object> map);
    
    List<SysMenu> selectAllChildMenuList(String parentMenuId);
    
    SysMenu selectMenuByName(String nameCn);
}