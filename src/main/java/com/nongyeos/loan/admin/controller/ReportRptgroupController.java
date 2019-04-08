package com.nongyeos.loan.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.admin.entity.ReportRptgroup;
import com.nongyeos.loan.admin.service.IReportRptgroupService;
import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.base.util.Constants;

@Controller  
@RequestMapping("/reportGroup")
public class ReportRptgroupController {
	
	@Resource
	private IReportRptgroupService rptgroupService;
	
	@Resource
	private ISysSenoService sysSenoService;
	
	@RequestMapping(value = "groupTreeStr", method = RequestMethod.POST)
	@ResponseBody
	public String groupTreeStr(){
		return rptgroupService.groupTreeString();
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(ReportRptgroup rptgroup) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			String parentGroupId = rptgroup.getParentGroupId();
			String parentGroupIds = "";
			if(parentGroupId != null && !parentGroupId.equals("")){
				ReportRptgroup rptgroup1 = rptgroupService.selectByParentGroupId(parentGroupId);
				if(rptgroup1 != null){
					if(rptgroup1.getParentGroupIds() != null && !rptgroup1.getParentGroupIds().equals("")){
						parentGroupIds = rptgroup1.getParentGroupIds()+","+parentGroupId;
						rptgroup.setParentGroupIds(parentGroupIds);
					}
				}
			}
			if (rptgroup != null) {
				if (rptgroup.getGroupId().equals("")) {
					rptgroup.setGroupId(sysSenoService.getNextString(Constants.TABLE_NAME_RPTGROUP, 10, "RG", 1));
					rptgroupService.add(rptgroup);
					map.put("msg", "添加成功");
				} else {
					rptgroupService.upd(rptgroup);
					map.put("msg", "修改成功");
				}
			} else {
				map.put("msg", "操作失败！");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping(value = "getGroupDS", method = RequestMethod.POST)
	@ResponseBody
	public String getGroupDS(String finsId)throws Exception{
		String strJson = "[";
		List<ReportRptgroup> list = rptgroupService.getlistByFinsId(finsId);
		if(list != null && list.size() > 0){
			ReportRptgroup beanGroup = null;
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
	public ReportRptgroup getGroupById(String groupId)throws Exception{
		ReportRptgroup beanGroup = null;
		if(groupId != null && !groupId.equals("")){
			beanGroup = rptgroupService.getGroupById(groupId);
		}
		return beanGroup;
	}
	
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> delete(String currIds){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(currIds != null && !currIds.equals("")){
				String[] groupIds = currIds.split(",");
				for(String groupId : groupIds){
					rptgroupService.delGroup(groupId);
				}
				map.put("msg", "删除成功");
			}else{
				map.put("msg", "删除失败");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}

}
