package com.nongyeos.loan.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusFollowData;
import com.nongyeos.loan.admin.service.IFollowDataService;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/followdata")
public class FollowDataController {
	
	@Autowired
	private IFollowDataService followDataService;
	
	@RequestMapping("/save")
	@ResponseBody
	public JSONObject save(String id,String data,String followTypeId,HttpServletRequest request){
		JSONObject json =  new JSONObject();
		if(StrUtils.isEmpty(id) || StrUtils.isEmpty(followTypeId)){
			json.put("msg", "保存失败");
			json.put("code", 400);
			return json;
		}
		try {
			Map<String, String> sqlMap = new HashMap<String, String>();
			sqlMap.put("memberOperateId", id);
			sqlMap.put("type", followTypeId);
			followDataService.deleteBeforeSave(sqlMap);
			if(StrUtils.isNotEmpty(data)){
				Map<String,String> map = new HashMap<String, String>();
				String[] all = data.split(",");
				for (int i = 0; i < all.length; i++) {
					String[] one = all[i].split(":");
					if(one.length != 2){
						continue;
					}
					if(map.get(one[0]) == null){
						map.put(one[0], one[1]);
					}else{
						String value = map.get(one[0]) +"," +one[1];
						map.put(one[0], value);
					}
				}
				for (Map.Entry<String, String> entry : map.entrySet()) {
					BusFollowData fd = new BusFollowData();
					fd.setId(UUIDUtil.getUUID());
					fd.setMemberOperateId(id);
					fd.setFollowItemId(entry.getKey());
					fd.setItemValue(entry.getValue());
					followDataService.insert(fd);
				}
			}
			json.put("msg", "保存成功");
			json.put("code", 200);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msg", e.getMessage());
			json.put("code", 400);
		}
		return json;
	}
}
