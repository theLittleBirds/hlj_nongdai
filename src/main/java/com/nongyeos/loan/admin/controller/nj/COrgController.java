package com.nongyeos.loan.admin.controller.nj;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.model.RootPointConfig;
import com.nongyeos.loan.admin.service.IOrgService;
import com.nongyeos.loan.admin.service.IPersonService;

@Controller
@RequestMapping("/nj/org")
public class COrgController {
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(COrgController.class);
	
	@Autowired
	private IOrgService orgService;
	
	@Autowired
    private IPersonService personService;
	
	@Autowired
	private RootPointConfig rootPointConfig;
	
	@RequestMapping("/findByName")
	@ResponseBody
	public JSONObject findByName(HttpServletRequest request,String orgName) throws Exception{
		JSONObject retJson = new JSONObject();
		List<SysOrg> list =null;
		String channel = request.getHeader("channel");
		JSONObject channelJson = JSONObject.parseObject(rootPointConfig.getMark());
		String rootOrgId = channelJson.getString(channel);
		
		try {
			JSONArray arr = new JSONArray();
			if(StringUtils.isNotEmpty(orgName)){
				list = orgService.selectByNameSearch(orgName,rootOrgId);	
				if(list!=null&&list.size()>0){
					for(int i=0;i<list.size();i++){
						JSONObject j = new JSONObject();
						j.put("orgId", list.get(i).getOrgId());
						j.put("orgName", list.get(i).getFullCname());
						arr.add(j);
					}
				}
			}
			retJson.put("data", arr);
			retJson.put("errno", 0);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return retJson;
	}
	
	@RequestMapping("/userOrg")
	@ResponseBody
	public JSONObject userOrg(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject retJson = new JSONObject();
		try {
			String userId =(String) request.getAttribute("loginId");
	        SysPerson sysPerson = personService.selectByUserIdAndIsdefault(userId);
			if(sysPerson != null)
			{
				
				List<SysOrg> orgs = orgService.selectOrgsByPerson(sysPerson.getPersonId());
				JSONArray arr = new JSONArray();
				if(orgs != null)
				{
					for (int i = 0; i < orgs.size(); i++) {
						JSONObject obj = new JSONObject();
						SysOrg org = orgs.get(i);
						obj.put("orgId", org.getOrgId());
						obj.put("orgName", org.getFullCname());
						arr.add(obj);
					}
					retJson.put("data", arr);
					retJson.put("errno", 0);
					response.setStatus(200);
				}else{
					retJson.put("message", "您没有关联的商家！");
					retJson.put("errno", 3033);
					response.setStatus(400);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		
		return retJson;
	}
	
}
