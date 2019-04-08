package com.nongyeos.loan.admin.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.base.util.IdCheck;

@Controller
@RequestMapping("/idMetch")
public class IdMetchController {

	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(IdMetchController.class);
	
	@RequestMapping("/index")
	@ResponseBody
	public JSONObject index(HttpServletRequest request,String idCard){
		JSONObject retJson = new JSONObject();
		String real = idCard.trim().replaceAll("\\s+","");
		if(real!=null&&IdCheck.isValidatedAllIdcard(real)){
			if(real.length()==15){
				real = IdCheck.convertIdcarBy15bit(real);
			}
			int age = IdCheck.getAgeByIdCard(real);
			String sex = IdCheck.getGenderByIdCard(real);
			retJson.put("sex", sex);
			retJson.put("age", age);
			retJson.put("code", 200);
		}else{
			retJson.put("code", 1000);
			retJson.put("msg","非法身份证号");
		}
		return retJson;
	}
	
}
