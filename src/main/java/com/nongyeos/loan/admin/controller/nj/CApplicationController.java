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
import com.nongyeos.loan.app.entity.AppApplication;
import com.nongyeos.loan.app.service.IApplicationService;

@Controller
@RequestMapping("/nj/application")
public class CApplicationController {
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(CApplicationController.class);
	
	@Autowired
	private IApplicationService applicationService;
	
	@RequestMapping("/findAll")
	@ResponseBody
	public JSONObject findAllApplication(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		try {
			JSONArray data = new JSONArray();
			String orgId = request.getParameter("orgId");
			if(StringUtils.isEmpty(orgId)){
				retJson.put("message", "请选择部门！");
				retJson.put("errno", 3002);
				response.setStatus(400);
				return retJson;
			}
			List<AppApplication> list =applicationService.selectByOrgId(orgId);
			if(list!=null&&list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					JSONObject obj = new JSONObject();
					obj.put("appId", list.get(i).getAppId());
					obj.put("appName", list.get(i).getCname());
					data.add(obj);
				}
			}
			retJson.put("data", data);
			retJson.put("errno", 0);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", e.getMessage());
			response.setStatus(400);
		}
		return retJson;
	}
}
