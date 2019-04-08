package com.nongyeos.loan.admin.controller.nj;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.base.util.IdCheck;

@Controller
@RequestMapping("/nj/IdCheck")
public class CIdCheckController {
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(CIdCheckController.class);
	
	@RequestMapping("/check")
	@ResponseBody
	public JSONObject check(String idCard,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		if(StringUtils.isEmpty(idCard)){
			retJson.put("message", "请输入身份证号！");
			retJson.put("errno", 3002);
			response.setStatus(400);
			return retJson;
		}
		if(IdCheck.isValidatedAllIdcard(idCard)){
			response.setStatus(200);
			retJson.put("errno", 0);
		}else{
			retJson.put("message", "身份证号输入有误，请重新输入！");
			retJson.put("errno", 3003);
			response.setStatus(400);
		}
		return retJson;
	}
	
}
