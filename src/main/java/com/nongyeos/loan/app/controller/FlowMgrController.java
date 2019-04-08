package com.nongyeos.loan.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.service.IAppEntryService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.core.service.FlowMgr;
@Controller
@RequestMapping("/flowmgr")
public class FlowMgrController {
	
	@Autowired
	private IAppEntryService appEntryService;
	
	@Autowired
	private FlowMgr flowMgrImpl;
	
	@RequestMapping("/getnodes")
	@ResponseBody
	public JSONObject getNodes(String intoPieceId,Short type){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(intoPieceId) || type == null){
			json.put("code", 400);
			json.put("msg","参数为空");
			return json;
		}
		try {
			AppEntry entry = appEntryService.queryByAppModeId(intoPieceId);
			if(type == Constants.FLOW_ORDER){
				return flowMgrImpl.getNextTask(entry);	
			}else if(type == Constants.FLOW_BACK){
				return flowMgrImpl.getBackTask(entry);
			}else if(type == Constants.FLOW_REJECT){
				return flowMgrImpl.getRejectTask(entry);
			}else{
				json.put("code", 400);
				json.put("msg","类型错误");
				return json;
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
			return json;
		}
	}
	
	@RequestMapping("/getnextnodes")
	@ResponseBody
	public JSONObject getNextNodes(String intoPieceId){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(intoPieceId)){
			json.put("code", 400);
			json.put("msg","参数为空");
			return json;
		}
		try {
			AppEntry entry = appEntryService.queryByAppModeId(intoPieceId);
			return flowMgrImpl.getNextTask(entry);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
			return json;
		}
		
	}
}
