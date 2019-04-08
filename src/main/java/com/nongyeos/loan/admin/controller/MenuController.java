package com.nongyeos.loan.admin.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;  
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.admin.entity.SysMenu;
import com.nongyeos.loan.admin.entity.SysMenuRight;
import com.nongyeos.loan.admin.entity.SysPersonRole;
import com.nongyeos.loan.admin.service.IMenuService;
import com.nongyeos.loan.admin.service.IPersonService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.JsonUtil;

  
@Controller
@RequestMapping("/menu")
public class MenuController {  
	
	private static final Logger logger = LogManager.getLogger(MenuController.class);
	
    @Resource  
    private IMenuService menuService;  
    
    @Resource  
    private IPersonService personService;
    
    /*
     * 首页导航菜单展示
     * 添加ResponseBody注解作用返回json格式用于ajax请求
     */
    @RequestMapping(value = "navMenu", method = RequestMethod.POST)
    @ResponseBody
    public String navMenu(String status,String roleId) throws Exception{
    	Session session = SecurityUtils.getSubject().getSession();
    	String personId = session.getAttribute(Constants.SESSION_PERSONCD).toString();
    	List<SysPersonRole> list = personService.getRoles(personId);
    	List<String> roleIdList = new LinkedList<String>();
    	List<String> menuIdList = new LinkedList<String>();
    	if(list != null && list.size() > 0){
    		for(int i = 0;i < list.size(); i++){
    			roleIdList.add(list.get(i).getRoleId());
    		}
    		List<SysMenuRight> list2 = menuService.getMenusByroleIds(roleIdList);
    		if(list2 != null && list2.size() > 0){
    			for(int j = 0;j < list2.size();j++){
    				menuIdList.add(list2.get(j).getMenuId());
    			}
    		}
    	}
    	String menuStr = "";
    	if(!"3".equals(status)){
    		menuStr = menuService.selectMenuList(session.getAttribute(Constants.SESSION_ORGCDBASE).toString(),menuIdList,true,personId);
    	}else{
    		String menuIdString = "";
        	List<SysMenuRight> listMenu = menuService.getMenuRightByRoleId(roleId);
        	if(listMenu != null && listMenu.size() > 0){
        		SysMenuRight beanMenuRight = null;
        		for(int i = 0; i < listMenu.size(); i++){
        			beanMenuRight = listMenu.get(i);
        			SysMenu sysMenu = menuService.getMenuById(beanMenuRight.getMenuId());
        			if(sysMenu != null){
        				if(menuIdString.equals("")){
        					menuIdString = sysMenu.getNameCn();
        				}else{
        					menuIdString += "," + sysMenu.getNameCn();
        				}
        			}
        		}
        	}
    		
    		menuStr = menuService.selectMenuList1(session.getAttribute(Constants.SESSION_ORGCDBASE).toString(),null,true,status,menuIdString);
    	}
		return menuStr;
	}
    
    @RequestMapping(value = "navMenu1", method = RequestMethod.POST)
    @ResponseBody
    public String navMenu1(String status) throws Exception{
    	String jsonMenu = "";
    	Session session = SecurityUtils.getSubject().getSession();
    	String personId = session.getAttribute(Constants.SESSION_PERSONCD).toString();
    	List<SysPersonRole> list = personService.getRoles(personId);
    	List<String> roleIdList = new LinkedList<String>();
    	List<String> menuIdList = new LinkedList<String>();
    	List<SysMenu> list3 = null;
    
        	if(list != null && list.size() > 0){
        		for(int i = 0;i < list.size(); i++){
        			roleIdList.add(list.get(i).getRoleId());
        		}
        		
        		if(roleIdList != null && roleIdList.contains(Constants.ROLE_ADMIN)){
            		list3 = menuService.getAllFirstList();
            	}else{
	        		List<SysMenuRight> list2 = menuService.getMenusByroleIds(roleIdList);
	        		if(list2 != null && list2.size() > 0){
	        			for(int j = 0;j < list2.size();j++){
	        				menuIdList.add(list2.get(j).getMenuId());
	        			}
	        		}
	        		list3 = menuService.getFirstLevelList(menuIdList);
            	}
        	}

    	if(list3 != null && list3.size() > 0){
    		for(SysMenu menu : list3){
    			if(!jsonMenu.equals(""))
					jsonMenu = jsonMenu + ",";
    			
    			jsonMenu += "{\"text\":\"" + menu.getNameCn() + "\", \"id\":\"" + menu.getMenuId() + "\", \"htmlid\":\"" + menu.getHtmlId() + "\", \"action_js\":\"" + menu.getActionJs() + "\", \"logoUrl\":\"" + menu.getLogoUrl() + "\",\"actionUrl\":"
    					+ "\""+menu.getActionUrl()+"\"}";
    		}
    		jsonMenu = "[" + jsonMenu + "]";
    	}
    	return jsonMenu;
    }
    
    @RequestMapping(value = "getChildMenu", method = RequestMethod.POST)
    @ResponseBody
    public String getChildMenu(String menuId) throws Exception{
    	String jsonMenu = "";
    	Session session = SecurityUtils.getSubject().getSession();
    	String personId = session.getAttribute(Constants.SESSION_PERSONCD).toString();
    	List<SysPersonRole> list = personService.getRoles(personId);
    	SysMenu menuBean = menuService.getMenuById(menuId);
    	List<String> roleIdList = new LinkedList<String>();
    	List<String> menuIdList = new LinkedList<String>();
    	List<SysMenu> list3 = null;
    	
        	if(list != null && list.size() > 0){
        		for(int i = 0;i < list.size(); i++){
        			roleIdList.add(list.get(i).getRoleId());
        		}
        		
        		if(roleIdList != null && roleIdList.contains(Constants.ROLE_ADMIN)){
            		list3 = menuService.getAllChildList(menuBean);
            	}else{
	        		List<SysMenuRight> list2 = menuService.getMenusByroleIds(roleIdList);
	        		if(list2 != null && list2.size() > 0){
	        			for(int j = 0;j < list2.size();j++){
	        				menuIdList.add(list2.get(j).getMenuId());
	        			}
	        		}
	        		list3 = menuService.getChildList(menuBean, menuIdList);
            	}
        	}

    	if(list3 != null && list3.size() > 0){
    		for(SysMenu menu : list3){
    			if(!jsonMenu.equals(""))
					jsonMenu = jsonMenu + ",";
    			
    			jsonMenu += "{\"text\":\"" + menu.getNameCn() + "\", \"id\":\"" + menu.getMenuId() + "\", \"htmlid\":\"" + menu.getHtmlId() + "\", \"action_js\":\"" + menu.getActionJs() + "\", \"logoUrl\":\"" + menu.getLogoUrl() + "\",\"actionUrl\":"
    					+ "\""+menu.getActionUrl()+ "\", \"index\":\"" + menu.getSeqno() +"\"}";
    		}
    		jsonMenu = "[" + jsonMenu + "]";
    	}
    	return jsonMenu;
    }
    
    
    
    @RequestMapping(value = "menuInfo", method = RequestMethod.POST)
    @ResponseBody
    public String menuInfo(String menuId) throws Exception{
    	String strJson="";
    	SysMenu sysMenu = menuService.getMenuById(menuId);
    	strJson = JsonUtil.bean2json(sysMenu);
		return strJson;
    }
    
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> save(SysMenu sysMenu) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	if(sysMenu != null){
    		if(sysMenu.getMenuId() == null || sysMenu.getMenuId().equals("")){
    			SysMenu sysMenu2 = menuService.selMenuByName(sysMenu.getNameCn());
    			if(sysMenu2 == null){
					menuService.addMenu(sysMenu);
					map.put("msg","添加成功");
    			}else{
					map.put("msg","名称重复，请重新输入");
				}	
			}else{
				menuService.updateMenu(sysMenu);
				map.put("msg","修改成功");
			}
    	}else{
    		map.put("msg","操作失败");
    	}
    	return map;
    }
    
    @RequestMapping(value = "delMenu", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> delete(String menuId) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	if(menuService.getChildMenus(menuId).size() == 0){
    		menuService.delMenu(menuId);
    		map.put("msg", "删除成功");
    	}else{
    		map.put("msg", "请先删除该菜单下的子菜单");
    	}
    	return map;
    }
    
    @RequestMapping(value = "getMenuRightByMenuId", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getMenuRightByMenuId(String menuId) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	String objectIdString = "";
    	List<SysMenuRight> list = menuService.getMenuRightByMenuId(menuId);
    	if(list != null && list.size() > 0){
    		SysMenuRight beanMenuRight = null;
    		for(int i = 0; i < list.size(); i++){
    			beanMenuRight = list.get(i);
    			if(objectIdString.equals("")){
    				objectIdString = beanMenuRight.getObjectId();
    			}else{
    				objectIdString += "," + beanMenuRight.getObjectId();
    			}
    		}
    	}
    	map.put("objectIds", objectIdString);
    	return map;
    }
    
}  
