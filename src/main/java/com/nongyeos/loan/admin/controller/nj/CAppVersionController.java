package com.nongyeos.loan.admin.controller.nj;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/nj/appversion")
public class CAppVersionController {
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(CAppVersionController.class);
	
	@Resource
	private RedisTemplate<Object, Object> redisTemplate;
	
	@RequestMapping("/index")
	@ResponseBody
	public JSONObject index(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject json = new JSONObject();
		String platform = request.getHeader("platform");
		String channel = request.getHeader("channel");
		if("android".equals(platform)){
			HashOperations<Object, String, Object> opsForHash = redisTemplate.opsForHash();
			Map<String,Object> app = opsForHash.entries(channel+"APP");
			if(app != null){
				Object versionNumber = app.get("version");
				Object url = app.get("url");
				json.put("url", url.toString());
				json.put("version", versionNumber.toString());
				return json;
			}
		}
		return json;
	}
}
