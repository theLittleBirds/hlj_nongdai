package com.nongyeos.loan.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.admin.entity.SysPersonorg;
import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.app.entity.AppAppgroup;
import com.nongyeos.loan.app.entity.AppApplication;
import com.nongyeos.loan.app.entity.BusFins;
import com.nongyeos.loan.app.service.IAppGroupService;
import com.nongyeos.loan.app.service.IApplicationService;
import com.nongyeos.loan.app.service.impl.BusFinsServiceImpl;
import com.nongyeos.loan.base.util.Constants;

@Controller  
@RequestMapping("/group")
public class AppGroupController {
	
	@Resource
	private IAppGroupService groupService;
	
	@Resource
	private IApplicationService appService;
	
	@Resource
	private ISysSenoService sysSenoService;
	
	@RequestMapping(value = "groupTreeStr", method = RequestMethod.POST)
	@ResponseBody
	public String groupTreeStr(HttpSession session){
		List<String> orgIdList = new ArrayList<>();
    	List<SysPersonorg> list = (List<SysPersonorg>) session.getAttribute(Constants.SESSION_ORGLIST);
    	for (SysPersonorg perorg : list) {
			orgIdList.add(perorg.getOrgId());
		}
		return groupService.groupTreeString(orgIdList);
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(AppAppgroup appGroup) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (appGroup != null) {
			if (appGroup.getGroupId().equals("")) {
			    appGroup.setGroupId(sysSenoService.getNextString(
						Constants.TABLE_NAME_APPGROUP, 10, "AG", 1));
			    groupService.addGroup(appGroup);
				map.put("msg", "添加成功");
			} else {
				groupService.updGroup(appGroup);
				map.put("msg", "修改成功");
			}
		} else {
			map.put("msg", "操作失败！");
		}
		return map;
	}
	
	@RequestMapping(value = "getGroupDS", method = RequestMethod.POST)
	@ResponseBody
	public String getGroupDS(String finsId)throws Exception{
		String strJson = "[";
		List<AppAppgroup> list = groupService.getlistByFinsId(finsId);
		if(list != null && list.size() > 0){
			AppAppgroup beanGroup = null;
			for(int i = 0;i < list.size();i++){
				beanGroup = list.get(i);
				if(beanGroup != null){
					if(i>0)
    					strJson = strJson + ", ";
    				
    				strJson = strJson + "{parameterName:'" + beanGroup.getName() + "', parameterValue:'" + beanGroup.getGroupId() + "'}";
				}
			}
		}
		strJson = strJson + "]";
		return strJson;
	}
	
	@RequestMapping(value = "getGroupDS2", method = RequestMethod.POST)
	@ResponseBody
	public String getGroupDS2()throws Exception{
		String strJson = "[";
		List<AppAppgroup> list = groupService.getAll();
		if(list != null && list.size() > 0){
			AppAppgroup beanGroup = null;
			for(int i = 0;i < list.size();i++){
				beanGroup = list.get(i);
				if(beanGroup != null){
					if(i>0)
    					strJson = strJson + ", ";
    				
    				strJson = strJson + "{parameterName:'" + beanGroup.getName() + "', parameterValue:'" + beanGroup.getGroupId() + "'}";
				}
			}
		}
		strJson = strJson + "]";
		return strJson;
	}
	
	@RequestMapping(value = "getGroupById", method = RequestMethod.POST)
	@ResponseBody
	public AppAppgroup getGroupById(String groupId)throws Exception{
		AppAppgroup beanGroup = null;
		if(groupId != null && !groupId.equals("")){
			beanGroup = groupService.getGroupById(groupId);
		}
		return beanGroup;
	}
	
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> delete(String currIds){
		Map<String, Object> map = new HashMap<String, Object>();
		String msg = "";
		try{
			if(currIds != null && !currIds.equals("")){
				String[] groupIds = currIds.split(",");
				for(String groupId : groupIds){
					List<AppApplication> list = appService.selectByGroupId(groupId);
					if(list != null && list.size() > 0){
						msg += groupId+"分类下有应用未删除;";
					}else{
						groupService.delGroup(groupId);
						msg += groupId+"删除成功;";
					}
				}
				map.put("msg", msg);
			}else{
				map.put("msg", "删除失败");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value = "getGroupDSByfinsId", method = RequestMethod.POST)
	@ResponseBody
	public String getGroupDSByfinsId(String finsId)throws Exception{
		String strJson = "[";
		List<AppAppgroup> list = groupService.getlistByFinsId(finsId);
		if(list != null && list.size() > 0){
			AppAppgroup beanGroup = null;
			for(int i = 0;i < list.size();i++){
				beanGroup = list.get(i);
				if(beanGroup != null){
					if(i>0)
    					strJson = strJson + ", ";
    				
    				strJson = strJson + "{parameterName:'" + beanGroup.getName() + "', parameterValue:'" + beanGroup.getGroupId() + "'}";
				}
			}
		}
		strJson = strJson + "]";
		return strJson;
	}
	
	@RequestMapping(value = "getFins", method = RequestMethod.POST)
	@ResponseBody
	public String getFins(HttpSession session)throws Exception{
		List<String> orgIdList = new ArrayList<>();
    	List<SysPersonorg> listSys = (List<SysPersonorg>) session.getAttribute(Constants.SESSION_ORGLIST);
    	for (SysPersonorg perorg : listSys) {
			orgIdList.add(perorg.getOrgId());
		}
    	List<BusFins> list = groupService.getFinsByOrgList(orgIdList);
		String strJson = "[";
		if(list != null && list.size() > 0){
			BusFins busFins = null;
			for(int i = 0;i < list.size();i++){
				busFins = list.get(i);
				if(busFins != null){
					if(i>0)
    					strJson = strJson + ", ";
    				
    				strJson = strJson + "{parameterName:'" + busFins.getCname() + "', parameterValue:'" + busFins.getFinsId() + "'}";
				}
			}
		}
		strJson = strJson + "]";
		return strJson;
	}
}
