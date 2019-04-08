package com.nongyeos.loan.base.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

public class NotEmptyUtils {
	/**
	 * 
	 * @Title: somthingLose 
	 * @Description: 参数未填写，返回错误码3002
	 * @param @param request
	 * @param @param paramName
	 * @param @param message
	 * @param @return     
	 * @return Map<String,Object>     
	 * @throws
	 */
	public static Map<String, Object> somthingLose(HttpServletRequest request,HttpServletResponse response ,String paramName,String message,JSONObject retJson){
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isEmpty(request.getParameter(paramName)) ){
			retJson.put("message", message);
			retJson.put("errno", 3002);
			response.setStatus(400);
			map.put("flag", true);
			map.put("retJson", retJson);
		}else{
			map.put("flag", false);
		}
		return map;
	}
	
	public static Map<String, Object> somethingwrong(String target,HttpServletResponse response ,String regex,String message,JSONObject retJson){
		Map<String, Object> map = new HashMap<String, Object>();
		String replaceAll = target.trim().replaceAll("\\s+","");
		if(!replaceAll.matches(regex)){
			retJson.put("message", message);
			retJson.put("errno", 3003);
			response.setStatus(400);
			map.put("flag", true);
			map.put("retJson", retJson);
		}else{
			map.put("flag", false);
		}
		return map;
	}
	
}
