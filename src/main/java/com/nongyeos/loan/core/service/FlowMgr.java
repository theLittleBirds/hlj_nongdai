package com.nongyeos.loan.core.service;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.core.model.BusinessObject;

public interface FlowMgr {
	
	public JSONObject save(BusinessObject business,String personId) throws Exception;
	
	public JSONObject next(BusinessObject business,String nextNodeId,String pcId,String personId) throws Exception;
	
	public JSONObject getNextTask(AppEntry entry);
	
	public JSONObject reject(BusinessObject business,String nextNodeId,String pcId,String personId) throws Exception;
	
	public JSONObject getRejectTask(AppEntry entry);
	
	public JSONObject back(BusinessObject business,String nextNodeId,String pcId,String personId) throws Exception;
	
	public JSONObject getBackTask(AppEntry entry);
	
	public JSONObject score(BusinessObject business);
	
	public JSONObject abandon(AppEntry entry);
	
	public JSONObject changeNode(BusinessObject business,String nextNodeId,String personId) throws Exception;

}
