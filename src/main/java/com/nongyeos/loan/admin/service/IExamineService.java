package com.nongyeos.loan.admin.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusExamine;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.app.entity.AppEntry;

public interface IExamineService {
	
	int insert(BusExamine record) throws Exception;
	
	List<BusExamine> historyByIpId(String intoPieceId) throws Exception;
	
	JSONObject primaryFormSave(BusIntoPiece ip,BusExamine e,String nextNodeId,String pcId,String personId) throws Exception;
	
	JSONObject reviewFormSave(BusIntoPiece ip,BusExamine e,String nextNodeId,String pcId,String personId) throws Exception;
	
	JSONObject guaranteePushSave(BusIntoPiece ip,String nextNodeId,String pcId,String personId) throws Exception;
	
	JSONObject lastFormSave(BusIntoPiece ip,BusExamine e,AppEntry entry,String nextNodeId,String pcId,String personId) throws Exception;
	
	BusExamine selectByIpIdNode(BusExamine record) throws Exception;

	BusExamine selectByIpIdLast(String id)throws Exception;
}
