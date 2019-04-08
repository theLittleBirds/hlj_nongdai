package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.SysMenu;
import com.nongyeos.loan.admin.entity.SysMenuRight;

public interface IMenuService {

	String selectMenuList(String orgId,List<String> menuIdList,boolean checkbox,String status) throws Exception;

	String selectMenuList1(String orgId,List<String> menuIdList,boolean checkbox,String status,String menuIds) throws Exception;//菜单授权
	
	SysMenu getMenuById(String menuId) throws Exception;

	void addMenu(SysMenu sysMenu) throws Exception;

	void updateMenu(SysMenu sysMenu) throws Exception;

	SysMenu selMenuByName(String nameCn) throws Exception;

	void delMenu(String menuId) throws Exception;

	List<SysMenu> getChildList(SysMenu pMenu, List<String> menuIdList) throws Exception;

	List<SysMenu> getChildMenus(String menuId) throws Exception;

	List<SysMenuRight> getMenuRightByMenuId(String menuId) throws Exception;

	List<SysMenuRight> getMenusByroleIds(List<String> roleIdList) throws Exception;

	List<SysMenu> getFirstLevelList(List<String> menuIdList) throws Exception;

	List<SysMenu> getAllFirstList() throws Exception;

	List<SysMenu> getAllChildList(SysMenu pMenu) throws Exception;
	
	List<SysMenuRight> getMenuRightByRoleId(String roleId) throws Exception;

}
