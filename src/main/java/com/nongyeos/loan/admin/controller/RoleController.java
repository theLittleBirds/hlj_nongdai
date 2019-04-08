package com.nongyeos.loan.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.admin.entity.SysMenuRight;
import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.entity.SysPersonRole;
import com.nongyeos.loan.admin.entity.SysPersonorg;
import com.nongyeos.loan.admin.entity.SysRole;
import com.nongyeos.loan.admin.service.IOrgService;
import com.nongyeos.loan.admin.service.IPersonService;
import com.nongyeos.loan.admin.service.IRoleService;
import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller  
@RequestMapping("/role")
public class RoleController {
	
	@Resource
	private IRoleService roleService;
	
	@Resource
	private IOrgService orgService;
	
    @Resource 
    private ISysSenoService sysSenoService;
    
    @Resource 
    private IPersonService personService;

	private static final Logger logger = LogManager.getLogger(RoleController.class);
	
	@RequestMapping(value = "roleList", method = RequestMethod.POST)
    @ResponseBody
    public PageBeanUtil<SysRole> getRoleListByOrgId(int limit,int offset,String orgid) throws Exception{
		PageBeanUtil<SysRole> list = roleService.getRoleListByOrgId(limit,offset,orgid);
    	if(list != null){
		    return list;
    	}else {
    		return null;
    	}
    }
	
	@RequestMapping(value = "roleListAll", method = RequestMethod.POST)
    @ResponseBody
	public PageBeanUtil<SysRole> getRoleListAll() throws Exception{
    	List<SysRole> list = roleService.getRoleAll();
    	PageBeanUtil<SysRole> pageBean = new PageBeanUtil();
    	pageBean.setItems(list);
    	pageBean.setTotalNum(list.size());
    	return pageBean;
    }
	
	@RequestMapping(value = "getRoleDS", method = RequestMethod.POST)
    @ResponseBody
	public String getRoleDS(short status,String orgId) throws Exception{
		String strJson = "[";
		List<SysRole> list = new ArrayList<SysRole>();
		list = roleService.listByStatus(status, orgId);
		if(list != null && list.size() > 0){
			SysRole bean = null;
			for(int i = 0;i < list.size();i++){
				bean = list.get(i);
				if(bean != null){
					if(i>0)
    					strJson = strJson + ", ";
    				
    				strJson = strJson + "{parameterName:'" + bean.getNameCn() + "', parameterValue:'" + bean.getRoleId() + "'}";
				}
			}
		}
		strJson = strJson + "]";
		return strJson;
	}
	
	@RequestMapping(value = "roleSave", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> rolesave(SysRole sysRole) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if (sysRole != null) {
			if (sysRole.getRoleId().equals("")) {
				SysRole sysrole1 = roleService.selectByCname(sysRole.getNameCn(), sysRole.getOrgId());
				if(sysrole1 != null){
					map.put("msg", "角色中文名称重复，请重新填写！");
				}else{
					SysRole sysrole2 = roleService.selectByEname(sysRole.getNameEn(), sysRole.getOrgId());
					if(sysrole2 != null){
						map.put("msg", "角色英文名称重复，请重新填写！");
					}else {
						sysRole.setRoleId(sysSenoService.getNextString(Constants.TABLE_NAME_ROLE, 10, "R", 1));
						roleService.save(sysRole);
						map.put("msg", "添加成功");
					}
				}
			}else{
				roleService.update(sysRole);
				map.put("msg", "修改成功");
			}
		}else{
			map.put("msg", "操作失败");
		}
    	return map;
    }
	
	@RequestMapping(value = "roleDel", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> delete(String currIds) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<SysPersonRole> list = null;
		if(currIds != null && !currIds.equals("")){
			String[] roleIds = currIds.split(",");
			for (String roleId : roleIds) {
				 list = personService.getPersonByRole(roleId);
				 if(list != null && list.size() > 0){
					 map.put("msg", "该角色下有人员绑定，请先解除！");
					 return map;
				 }
		    }
			for (String roleId : roleIds) {
				roleService.delete(roleId);
		    }
			map.put("msg", "删除成功");
		}
		return map;
	}
	
	@RequestMapping(value = "getroleByOrgId", method = RequestMethod.POST)
    @ResponseBody
	public PageBeanUtil<SysRole> getRoleListByFirstOrgId(String orgId) throws Exception{
		List<SysRole> list = roleService.getRoleListByOrgId(orgId);
		PageBeanUtil<SysRole> pageBean = new PageBeanUtil();
    	pageBean.setItems(list);
    	pageBean.setTotalNum(list.size());
    	return pageBean;
	}
	
	@RequestMapping(value = "getroleByPersonOrg", method = RequestMethod.POST)
    @ResponseBody
	public PageBeanUtil<SysRole> getRoleListByFirstOrgId(HttpServletRequest request) throws Exception{
		List<SysPersonorg> orgList = (List<SysPersonorg>) request.getSession().getAttribute(Constants.SESSION_ORGLIST);
		List<String> orgs = new ArrayList<String>();
		if(orgList != null && orgList.size() > 0){
			for(int i = 0; i < orgList.size(); i++){
				orgs.add(orgList.get(i).getOrgId());
			}
		}
		List<SysRole> list = roleService.getRolesByPersonOrg(orgs);
		PageBeanUtil<SysRole> pageBean = new PageBeanUtil();
    	pageBean.setItems(list);
    	pageBean.setTotalNum(list.size());
    	return pageBean;
	} 
	
	@RequestMapping(value = "getroleByOrg", method = RequestMethod.POST)
    @ResponseBody
	public PageBeanUtil<SysRole> getroleByOrg(String orgId) throws Exception{
		List<SysRole> list = roleService.getRoleListByOrgId(orgId);
		PageBeanUtil<SysRole> pageBean = new PageBeanUtil();
    	pageBean.setItems(list);
    	pageBean.setTotalNum(list.size());
    	return pageBean;
	} 
	
	@RequestMapping(value = "addRoleAndPerson", method = RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> addRoleAndPerson(String roleIds,String personId) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(personId != null && !personId.equals("")){
			roleService.delByPersonId(personId);
			if(roleIds != null && !roleIds.equals("")){
				SysPersonRole personRole = new SysPersonRole();
				String[] roleIds2 = roleIds.split(",");
				for(String roleId : roleIds2){
					personRole.setRoleId(roleId);
					personRole.setPersonId(personId);
					roleService.saveByPersonIdAndRoleId(personRole);
				}
			}
			map.put("msg", "保存成功");
		}else{
			map.put("msg", "保存失败");
		}
		return map;
	}
	
	@RequestMapping(value = "addRoleAndMenu", method = RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> addRoleAndMenu(String roleId, String menuIds) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(roleId != null && !roleId.equals("")){
			roleService.delByRoleId(roleId);
			if(menuIds != null && !menuIds.equals("")){
				SysMenuRight menuRight = new SysMenuRight();
				String[] menuIds2 = menuIds.split(",");
				for(String menuId : menuIds2){
					menuRight.setMenuId(menuId);
					menuRight.setObjectType((short)1);
					menuRight.setControlType((short)1);
					menuRight.setObjectId(roleId);
					roleService.saveByMenuIdAndRoleId(menuRight);
				}
			}
			map.put("msg", "保存成功");
		}else{
			map.put("msg", "保存失败");
		}
		return map;
	}

	@RequestMapping(value = "getRoleTree", method = RequestMethod.POST)
    @ResponseBody
	public String roleList()throws Exception{
		String strJson = "[";
		int num = 1;
		List<SysOrg> orgList = orgService.getFirstLevelList(null);
		SysOrg beanOrg = null;
		SysRole beanRole = null;
		if(orgList != null && orgList.size() > 0){
			for(int i = 0;i < orgList.size();i++){
				beanOrg = orgList.get(i);
				if(i > 0){
					strJson = strJson + ", ";
				}
				strJson = strJson + "{\"id\":" + num + ", \"pid\":" + "0"
						+ ", \"name\":\"" + beanOrg.getFullCname()
						+ "\", \"orgId\":\"" + beanOrg.getOrgId()+ "\"}";
				int num2 = num;
				num++;
				List<SysRole> roleList = roleService.getRoleListByOrgId(beanOrg.getOrgId());
				if(roleList != null && roleList.size() > 0){
					strJson = strJson + ", ";
					for(int j = 0;j < roleList.size();j++){
						beanRole = roleList.get(j);
						if(beanRole != null){
							if (j > 0) {
								strJson = strJson + ", ";
							}
							strJson = strJson + "{\"id\":" + num + ", \"pid\":"
									+ num2 + ", \"name\":\"" + beanRole.getNameCn()
									+ "\", \"roleId\":\"" + beanRole.getRoleId() + "\"}";
						}
						num++;
					}
				}
			}
		}
		strJson = strJson + "]";
		return (strJson);
	}
	
	
	
}
