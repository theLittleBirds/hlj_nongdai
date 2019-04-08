package com.nongyeos.loan.admin.controller.nj;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.base.util.QiNiuUtil;

@Controller
@RequestMapping("/nj/qiniuMedia")
public class QiniuMediaController {
	
	@RequestMapping("/getToken")
	@ResponseBody
	public JSONObject getToken(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		try {
			String upToken = QiNiuUtil.upToken();
			retJson.put("upLoadToken", upToken);
			retJson.put("errno", 0);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retJson;
	}
	
}
