package com.nongyeos.loan.admin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.admin.entity.SysMenu;
import com.nongyeos.loan.admin.entity.SysMenuRight;
import com.nongyeos.loan.admin.mapper.SysMenuMapper;
import com.nongyeos.loan.admin.mapper.SysMenuRightMapper;
import com.nongyeos.loan.admin.mapper.SysPersonRoleMapper;
import com.nongyeos.loan.admin.service.IMenuService;
import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.base.util.Constants;

@Service("menuService")
public class MenuServiceImpl implements IMenuService{
	
	@Autowired
	private SysMenuMapper menuMapper;
	@Autowired
	private SysMenuRightMapper menuRightMapper;
	@Autowired
	private SysPersonRoleMapper personRoleMapper;
    @Resource 
    private ISysSenoService sysSenoService;
	
	@Override
	public String selectMenuList(String orgId,List<String> menuIdList,boolean checkbox,String personId) throws Exception {
		
		List<SysMenu> listFirst = null;
		
		List<String> roleList = personRoleMapper.selectRoleByPerson(personId);
		if(roleList != null && roleList.contains(Constants.ROLE_ADMIN)){
			listFirst = getAllFirstList();
		}else{
			if(menuIdList != null && menuIdList.size() > 0){
				listFirst = getFirstLevelList(menuIdList);
			}
		}
		String jsonMenu = "";
		if(listFirst != null)
		{
			for(int i=0;i<listFirst.size();i++)
			{
				SysMenu topMenu = listFirst.get(i);
				if(!jsonMenu.equals(""))
					jsonMenu = jsonMenu + ",";
				
				jsonMenu = jsonMenu + getDescendants(topMenu, menuIdList, checkbox,roleList);
			}
			jsonMenu = "[" + jsonMenu + "]";
		}
		return jsonMenu;
    }
	
	//构造菜单json数据
	private String getDescendants(SysMenu menu, List<String> menuIdList, boolean checkbox, List<String> roleList) throws Exception
	{
		String jsonDescendants = "";
		String jsonSameLevel = "";
		List<SysMenu> childList = null;
		if(menu == null)
			return(jsonDescendants);
		
		jsonDescendants = "{\"text\":\"" + menu.getNameCn() + "\", \"id\":\"" + menu.getMenuId() + "\", \"htmlid\":\"" + menu.getHtmlId() + "\", \"action_js\":\"" + menu.getActionJs() + "\", \"logoUrl\":\"" + menu.getLogoUrl() + "\",\"actionUrl\":"
				+ "\""+menu.getActionUrl()+"\"";
		
		if(roleList != null && roleList.contains(Constants.ROLE_ADMIN)){
			childList = getAllChildList(menu);
		}else{
			if(menuIdList != null && menuIdList.size() > 0){
				childList = getChildList(menu, menuIdList);
			}
		}
		
		if(childList.size() == 0)
			jsonDescendants = jsonDescendants + "}";
		else
		{
			jsonDescendants = jsonDescendants +", \"state\":{\"expanded\":\"true\"}" + " , \"nodes\": [";
			
			for(int i=0;i<childList.size();i++)
			{
				SysMenu childMenu = childList.get(i);
				if(!jsonSameLevel.equals(""))
					jsonSameLevel = jsonSameLevel + ",";
				
				jsonSameLevel = jsonSameLevel + getDescendants(childMenu, menuIdList, checkbox,roleList);
			}
			
			jsonDescendants = jsonDescendants + jsonSameLevel + "]}";
		}
		
		return(jsonDescendants);
	}
	@Override
	public String selectMenuList1(String orgId,List<String> menuIdList,boolean checkbox,String status,String menuIds) throws Exception {  
		
		List<SysMenu> listFirst = null;
		
		if(status.equals("1")){
			if(menuIdList != null && menuIdList.size() > 0){
				listFirst = getFirstLevelList(menuIdList);
			}
		}else{
			listFirst = getFirstLevelList(menuIdList);
		}
		String jsonMenu = "";
		if(listFirst != null)
		{
			for(int i=0;i<listFirst.size();i++)
			{
				SysMenu topMenu = listFirst.get(i);
				if(!jsonMenu.equals(""))
					jsonMenu = jsonMenu + ",";
				
				jsonMenu = jsonMenu + getDescendants1(topMenu, menuIdList, checkbox,menuIds);
			}
			jsonMenu = "[" + jsonMenu + "]";
		}
		return jsonMenu;
    }
	
	//构造菜单json数据
			private String getDescendants1(SysMenu menu, List<String> menuIdList, boolean checkbox,String menuIds) throws Exception
			{
				String jsonDescendants = "";
				String jsonSameLevel = "";
				
				if(menu == null)
					return(jsonDescendants);
				if(menuIds.indexOf(menu.getNameCn()) != -1){
					jsonDescendants = "{\"text\":\"" + menu.getNameCn() + "\", \"state\": {\"checked\":\"true\"}, \"id\":\"" + menu.getMenuId() + "\", \"htmlid\":\"" + menu.getHtmlId() + "\", \"action_js\":\"" + menu.getActionJs() + "\", \"logoUrl\":\"" + menu.getLogoUrl() + "\",\"actionUrl\":"
							+ "\""+menu.getActionUrl()+"\"";
				}else{
					jsonDescendants = "{\"text\":\"" + menu.getNameCn() + "\", \"id\":\"" + menu.getMenuId() + "\", \"htmlid\":\"" + menu.getHtmlId() + "\", \"action_js\":\"" + menu.getActionJs() + "\", \"logoUrl\":\"" + menu.getLogoUrl() + "\",\"actionUrl\":"
							+ "\""+menu.getActionUrl()+"\"";
				}
				
				List<SysMenu> childList = getChildList(menu, menuIdList);
				if(childList.size() == 0)
					jsonDescendants = jsonDescendants + "}";
				else
				{
					jsonDescendants = jsonDescendants + " , \"nodes\": [";
					
					for(int i=0;i<childList.size();i++)
					{
						SysMenu childMenu = childList.get(i);
						if(!jsonSameLevel.equals(""))
							jsonSameLevel = jsonSameLevel + ",";
						
						jsonSameLevel = jsonSameLevel + getDescendants1(childMenu, menuIdList, checkbox,menuIds);
					}
					
					jsonDescendants = jsonDescendants + jsonSameLevel + "]}";
				}
				
				return(jsonDescendants);
			}
	
	//得到父菜单
	@Override
	public List<SysMenu> getFirstLevelList(List<String> menuIdList) throws Exception{
		try {
				List<SysMenu> list = menuMapper.selectFatherMenuList(menuIdList);
				return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	//得到所有父菜单
	@Override
	public List<SysMenu> getAllFirstList() throws Exception{
		try {
				List<SysMenu> list = menuMapper.selectAllFatherMenuList();
				return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	
	//得到子菜单
	public List<SysMenu> getChildList(SysMenu pMenu, List<String> menuIdList) throws Exception{
		try {
			if (pMenu != null) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("parentMenuId", pMenu.getMenuId());
				map.put("menuIdList", menuIdList);
				List<SysMenu> list = menuMapper.selectChildMenuList(map);
				return list;
			}else {
				throw new Exception("pMenu为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	//得到所有子菜单
	@Override
	public List<SysMenu> getAllChildList(SysMenu pMenu) throws Exception{
		try {
			if (pMenu != null) {
				List<SysMenu> list = menuMapper.selectAllChildMenuList(pMenu.getMenuId());
				return list;
			}else {
				throw new Exception("pMenu为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	@Override
	public SysMenu getMenuById(String menuId) throws Exception{
		try{
			if (menuId != null) {
				SysMenu sysMenu = menuMapper.selectByPrimaryKey(menuId);
				return sysMenu;
			}else {
				throw new Exception("menuId为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addMenu(SysMenu sysMenu) throws Exception{
		try{
			if(sysMenu != null){
				sysMenu.setMenuId(sysSenoService.getNextString(Constants.TABLE_NAME_MENU, 10, "M", 1));
				menuMapper.insertSelective(sysMenu);
			}else {
				throw new Exception("sysmenu为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateMenu(SysMenu sysMenu) throws Exception{
		try{
			if(sysMenu != null){
				menuMapper.updateByPrimaryKey(sysMenu);
			}else {
				throw new Exception("sysmenu为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	public SysMenu selMenuByName(String nameCn) throws Exception{
		try{
			if(nameCn != null){
				SysMenu sysMenu = menuMapper.selectMenuByName(nameCn);
				return sysMenu;
			}else {
				throw new Exception("nameCn为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delMenu(String menuId) throws Exception{
		try{
			if(menuId != null){
				menuMapper.deleteByPrimaryKey(menuId);
			}else {
				throw new Exception("menuid为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	public List<SysMenu> getChildMenus(String menuId) throws Exception{
		try {
			if(menuId != null){
				List<SysMenu> list = new ArrayList<SysMenu>();
				SysMenu bean = null;
				bean = menuMapper.selectByPrimaryKey(menuId);
				list = getChildList(bean, null);
				return list;
			}else {
				throw new Exception("menuid为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	public List<SysMenuRight> getMenuRightByMenuId(String menuId) throws Exception{
		try{
			if(menuId != null){
				List<SysMenuRight> list = menuRightMapper.selectByMenuId(menuId);
				return list;
			}else {
				throw new Exception("menuid为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	@Override
	public List<SysMenuRight> getMenusByroleIds(List<String> roleIdList) throws Exception{
		try{
			if(roleIdList != null){
				List<SysMenuRight> list = menuRightMapper.selectMenuByRoleId(roleIdList);
				return list;
			}else {
				throw new Exception("roleIdList为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	@Override
	public List<SysMenuRight> getMenuRightByRoleId(String roleId) throws Exception{
		try{
			if(roleId != null){
				List<SysMenuRight> list = menuRightMapper.selectByRoleId(roleId);
				return list;
			}else {
				throw new Exception("roleId为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
}
