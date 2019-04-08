package com.nongyeos.loan.admin.controller.nj;

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
import com.nongyeos.loan.base.util.Constants;

@Controller
@RequestMapping("/nj/jpush")
public class JpushController {
	
	@Autowired
	private IMemberLoginService memberLoginService;
	
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(JpushController.class);
	
	@RequestMapping("/saveJpush")
	@ResponseBody
	public JSONObject saveJpush(HttpServletRequest request,HttpServletResponse response,String registrationId){
		JSONObject retJson = new JSONObject();
		String loginId = (String) request.getAttribute("loginId");
		try {
			String platform = request.getHeader("platform");
			int type = Constants.ANDROID;
			if("ios".equals(platform)){
				type=Constants.IOS;
			}
			BusMemberLogin memberLogin = memberLoginService.selectById(loginId);
			if(memberLogin==null){
				//TODO  商户jpush保存
				retJson.put("message", "保存成功！");
				response.setStatus(200);
				return retJson;
			}
			memberLogin.setType((short)type);
			memberLogin.setJpushId(registrationId);
			memberLoginService.updateByPrimaryKey(memberLogin);
			retJson.put("message", "保存成功！");
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "保存失败！");
			response.setStatus(400);
		}
		return retJson;
	}
	
	@RequestMapping("/deleteJpush")
	@ResponseBody
	public JSONObject deleteJpush(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String loginId = (String) request.getAttribute("loginId");
		try {
			BusMemberLogin memberLogin = memberLoginService.selectById(loginId);
			if(memberLogin==null){
				//TODO  商户jpush删除
				retJson.put("message", "删除成功！");
				response.setStatus(200);
				return retJson;
			}
			memberLogin.setType(null);
			memberLogin.setJpushId(null);
			memberLoginService.updateByPrimaryKey(memberLogin);
			retJson.put("message", "删除成功！");
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "删除失败！");
			response.setStatus(400);
		}
		return retJson;
	}
}
