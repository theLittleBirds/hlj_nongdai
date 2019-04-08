package com.nongyeos.loan.app.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.app.service.IDataCopyService;


@Controller  
@RequestMapping("/copy")
public class DataCopyController {
	
	@Resource
	private IDataCopyService copyService;
	
	@RequestMapping(value = "dataCopy", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> dataCopy(String appName, String appId, String finsId, String groupId){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			boolean b = copyService.appCopy(appName, appId, finsId, groupId);
			if(b){
				map.put("msg", "复制成功");
			}else{
				map.put("msg", "复制失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("msg", "复制失败");
		}
		return map;
		
	}

}
