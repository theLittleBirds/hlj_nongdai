package com.nongyeos.loan.admin.controller.nj;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusMemberLogin;
import com.nongyeos.loan.admin.service.IMemberLoginService;
import com.nongyeos.loan.admin.service.IPersonService;

@Controller
@RequestMapping("/nj/account")
public class CAccountController {
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(CAccountController.class);
	
	@Autowired
	private IMemberLoginService memberLoginService;
	
	@Autowired  
	private IPersonService personService;
	
	@RequestMapping("/accountInfo")
	@ResponseBody
	public JSONObject accountInfo(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String loginId = (String) request.getAttribute("loginId");
		try {
			BusMemberLogin ml = memberLoginService.selectById(loginId);
			String userId=null;
			if(ml==null){
				userId=loginId;
			}
			JSONObject obj = new JSONObject();
			if(ml!=null){
				obj.put("name", ml.getName()==null?"无":ml.getName());
				obj.put("phone", ml.getLoginName()==null?"":ml.getLoginName());
			}else{
				Map<String, Object> map = personService.selectNameAndOrgByUserId(userId);
				obj.put("name", map.get("nameCn")==null?"无":map.get("nameCn").toString());
				obj.put("orgName", map.get("orgCname")==null?"":map.get("orgCname").toString());
			}
			retJson.put("data", obj);
			retJson.put("errno", 0);
			response.setStatus(200);
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("errno", 3041);
			retJson.put("message", "查询失败！");
			response.setStatus(400);
		}
		return retJson;
	}
}
