package com.nongyeos.loan.core.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.SysPersonRole;
import com.nongyeos.loan.admin.mapper.SysPersonRoleMapper;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.entity.AppItem;
import com.nongyeos.loan.app.entity.AppSrvunit;
import com.nongyeos.loan.app.entity.FlowData;
import com.nongyeos.loan.app.entity.FlowDirection;
import com.nongyeos.loan.app.entity.FlowNode;
import com.nongyeos.loan.app.mapper.AppEntryMapper;
import com.nongyeos.loan.app.mapper.AppItemMapper;
import com.nongyeos.loan.app.mapper.AppSrvunitMapper;
import com.nongyeos.loan.app.mapper.FlowDataMapper;
import com.nongyeos.loan.app.mapper.FlowDirectionMapper;
import com.nongyeos.loan.app.mapper.FlowNodeMapper;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.core.model.BusinessObject;
import com.nongyeos.loan.core.model.MsgQueue;
import com.nongyeos.loan.core.model.ResMsg;
import com.nongyeos.loan.core.service.DecisionStrategyMgr;
import com.nongyeos.loan.core.service.FlowMgr;
import com.nongyeos.loan.core.service.ItemMgr;
import com.nongyeos.loan.core.service.NodeEventMgr;

@Service("flowMgrImpl")
public class FlowMgrImpl implements FlowMgr {
	
	@Autowired
	private FlowDirectionMapper flowDirectionMapper;
	
	@Autowired
	private FlowNodeMapper fleNodeMapper;
	
	@Autowired
	private NodeEventMgr nodeEventMgrImpl;
	
	@Autowired
	private AppEntryMapper appEntryMapper;
	
	@Autowired
	private SysPersonRoleMapper personRoleMapper;
	
	@Autowired
	private DecisionStrategyMgrImpl strategyMgrImpl;
	
	@Autowired
	private AppSrvunitMapper appSrvunitMapper;
	
	@Autowired
	private DecisionStrategyMgr StrategyMgrImpl;
	
	@Autowired
	private FlowDataMapper flowDataMapper;
	
	@Autowired
	private ItemMgr itemMgrService;
	
	@Autowired
	private AppItemMapper itemMapper;
	
	@Autowired
	private DecisionCaseMgrImpl desciCaseMgrImpl;
	/**
	 * 
	 * @param entry
	 * @param type 1:顺序     5:回退    10:驳回       20:事件
	 * @return code==200 返回下一个节点数组
	 */
	private JSONObject getNextNodes(AppEntry entry,Short type){
		JSONObject json = new JSONObject();
		if(entry != null){
			FlowDirection fd = new FlowDirection();
			fd.setNodeId(entry.getNodeId());
			fd.setType(type);
			FlowDirection direction =flowDirectionMapper.selectByNodeIdAndType(fd);
			if(direction != null){
				String[] next = direction.getToNodeIds().split(",");
				JSONArray arr = new JSONArray();
				for (int i = 0; i < next.length; i++) {
					FlowNode fn = fleNodeMapper.selectByNodeId(next[i]);
					if(fn != null){
						JSONObject obj = new JSONObject();
						obj.put("nodeId", fn.getNodeId());
						obj.put("cname", fn.getCname());
						arr.add(obj);						
					}
				}
				json.put("nodes", arr);
				json.put("pcId", direction.getPcId());
			}
			json.put("code", 200);
		}else{
			json.put("code", 400);
		}
		return json;
	}
	/**
	 * code == 200 保存事件执行成功，并且全部通过
	 */
	public JSONObject save(BusinessObject business,String personId) throws Exception{
		JSONObject json = new JSONObject();
		if(business == null ||  personId == null){
			throw new Exception("参数为空");
		}
		try {
			//鉴权
			String curNodeId = business.getEntry().getNodeId();
			FlowNode fn = fleNodeMapper.selectByNodeId(curNodeId);
			String roleCodes = fn.getApproverIds();
			if(roleCodes != null && !"".equals(roleCodes)){
				List<SysPersonRole> list = personRoleMapper.getRoleByPerson(personId);
				boolean flag = false;
				for (int i = 0; i < list.size(); i++) {
					if(roleCodes.indexOf(list.get(i).getRoleId()) != -1){
						flag = true;
						break;
					}
				}
				if(flag == false){
					throw new Exception("无权限");
				}
			}
			
			MsgQueue msgqueue = new MsgQueue();
			business.setMsgQueue(msgqueue);
			
			//流程节点保存事件
			MsgQueue quenue  = nodeEventMgrImpl.save(business);
			ResMsg msg = quenue.poll();
			String errorMsg = "";
			while(msg != null){
				//执行出错
				if(msg.getCode() == 4000 || msg.getCode() == 5000){
					errorMsg = errorMsg + msg.getMsg();
				}
				msg = quenue.poll();
			}
			if(!"".equals(errorMsg)){
				throw new Exception(errorMsg);
			}
			json.put("code", 200);
			json.put("msg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return json;
	}
	/**
	 * code == 200 跳转到下一个节点
	 */
	public JSONObject next(BusinessObject business,String nextNodeId,String pcId,String personId) throws Exception{
		JSONObject json = new JSONObject();
		if(business == null || nextNodeId == null || personId == null){
			throw new Exception("参数为空");
		}
		try {
			//鉴权
			String curNodeId = business.getEntry().getNodeId();
			FlowNode fn = fleNodeMapper.selectByNodeId(curNodeId);
			String roleCodes = fn.getApproverIds();
			if(roleCodes != null && !"".equals(roleCodes)){
				List<SysPersonRole> list = personRoleMapper.getRoleByPerson(personId);
				boolean flag = false;
				for (int i = 0; i < list.size(); i++) {
					if(roleCodes.indexOf(list.get(i).getRoleId()) != -1){
						flag = true;
						break;
					}
				}
				if(flag == false){
					throw new Exception("无权限");
				}
			}
			
			Map<Object, Object> para = new HashMap<Object, Object>();
			para.put("controlType", 4);
			para.put("objectType", 2);
			para.put("nodeId", curNodeId);
			List<FlowData> flowData = flowDataMapper.selectByControlType(para);
			String itemIdMsg = "";
			itemIdMsg+="";
			for (int i = 0; i < flowData.size(); i++) {
				Object obj = itemMgrService.getObjectValue(business, flowData.get(i).getObjectId());
				if(obj == null || "".equals(obj.toString())){
					AppItem item = itemMapper.selectByPrimaryKey(flowData.get(i).getObjectId());
					itemIdMsg = itemIdMsg + item.getCname() + "不能为空；";
				}					
			}
			if(!"".equals(itemIdMsg)){
				throw new Exception(itemIdMsg);
			}
				
			MsgQueue msgqueue = new MsgQueue();
			business.setMsgQueue(msgqueue);
			
			//流程节点结束事件
			MsgQueue quenue  = nodeEventMgrImpl.end(business);
			ResMsg msg = null;
			String errorMsg = "";
			if(quenue != null){
				msg = quenue.poll();
				while(msg != null){
					//执行出错，不跳转
					if(msg.getCode() == 4000 || msg.getCode() == 5000){
						errorMsg = errorMsg + msg.getMsg();
					}
					msg = quenue.poll();
				}
				if(!"".equals(errorMsg)){
					throw new Exception(errorMsg);
				}
			}
						
			//决策
			if(pcId != null && !"".equals(pcId)){
				boolean decisionCase = false;
				try {
					decisionCase = strategyMgrImpl.doStrategy(business,pcId);
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception(e);
				}
				if(decisionCase == false){
					throw new Exception("决策规则配置有错");
				}else{
					quenue = business.getMsgQueue();
					if(quenue != null){
						msg = quenue.poll();
						while(msg != null){
							//流程转向
							if(msg.getCode() == 7000){
								errorMsg =  msg.getMsg();
								nextNodeId = msg.getData().get("nodeId").toString();
							}
							msg = quenue.poll();
						}
					}		
				}
			}
			
			String tmpNextNodeId = nextNodeId;
			
			while(tmpNextNodeId != null)
			{
				AppEntry entry = business.getEntry();
				FlowNode nextNode = fleNodeMapper.selectByNodeId(tmpNextNodeId);
				entry.setRecordId(entry.getNodeId());
				entry.setUpdDate(new Date());
				entry.setUpdOperCode(personId);
				entry.setNodeId(tmpNextNodeId);
				entry.setNodeName(nextNode.getEname());
				appEntryMapper.updateByPrimaryKey(entry);
				
				//流程达到事件
				nodeEventMgrImpl.arrived(business);
				
				tmpNextNodeId = null;
				
				if(nextNode.getType().shortValue() == Constants.NODE_TYPE_AUTO)
				{
					//自动节点
					System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA id="+nextNode.getNodeId()+"    name="+nextNode.getCname());
					List<FlowDirection> listDir = flowDirectionMapper.selectByNodeId(nextNode.getNodeId());
					for(int j=0;j<listDir.size();j++)
					{
						FlowDirection direction = listDir.get(j);
						if(direction != null)
						{
							if(desciCaseMgrImpl.logicExpress(business, direction.getPcId()))
							{
								tmpNextNodeId = takeNextNodeCode(direction.getToNodeIds());
								if(tmpNextNodeId != null)
								{
									nextNodeId = tmpNextNodeId;
									break;
								}
							}
						}
					}
				}
			}			
				
			json.put("code", 200);
			json.put("msg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return json;
	}
	
	private String takeNextNodeCode(String strNextNodeCodes)
	{
		String nextNodeCode = "";
		
		if(strNextNodeCodes != null)
		{
			String[] tmpArr = strNextNodeCodes.split(",");
			if(tmpArr.length > 0)
			{
				nextNodeCode = tmpArr[0].trim();
			}
		}		
		
		return(nextNodeCode);
	}

	@Override
	public JSONObject getNextTask(AppEntry entry) {
		return getNextNodes(entry,Constants.FLOW_ORDER);
	}
	
	@Override
	public JSONObject reject(BusinessObject business,String nextNodeId,String pcId,String personId)  throws Exception{
		return next(business,nextNodeId,pcId,personId);
	}

	@Override
	public JSONObject getRejectTask(AppEntry entry) {
		return getNextNodes(entry,Constants.FLOW_REJECT);
	}

	@Override
	public JSONObject back(BusinessObject business,String nextNodeId,String pcId,String personId)  throws Exception{
		return next(business,nextNodeId,pcId,personId);
	}

	@Override
	public JSONObject getBackTask(AppEntry entry) {
		return getNextNodes(entry,Constants.FLOW_BACK);
	}
	
	@Override
	public JSONObject score(BusinessObject business){
		JSONObject json = new JSONObject();
		if(business == null){
			json.put("code", 400);
			json.put("msg", "流程记录为空");
			return json;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("nodeId", business.getEntry().getNodeId());
		map.put("runtime", 50);
		List<AppSrvunit> list = appSrvunitMapper.selectByNodeTime(map);
		int code = 200;
		for (int i = 0; i < list.size(); i++) {
			try {
				boolean result = StrategyMgrImpl.doStrategy(business, list.get(i).getTardataId());
				if(result == false){
					code = 400;
					json.put("msg", "授信计算规则配置有误");
				}
			} catch (Exception e) {
				e.printStackTrace();
				json.put("msg", e.getMessage());
				code = 400;
			}	
		}
		json.put("code", code);
		return json;
	}
	
	//放弃
		@Override
		public JSONObject abandon(AppEntry entry) {
			return getNextNodes(entry,Constants.FLOW_ABANDON);
		}
		

		/**
		 * 直接跳转到目标节点，不执行策略、事件等
		 */
		@Override
		public JSONObject changeNode(BusinessObject business, String nextNodeId,String personId) throws Exception {
			JSONObject json = new JSONObject();
			if(business == null || nextNodeId == null || personId == null){
				throw new Exception("参数为空");
			}
			try {
				//鉴权
				String curNodeId = business.getEntry().getNodeId();
				FlowNode fn = fleNodeMapper.selectByNodeId(curNodeId);
				String roleCodes = fn.getApproverIds();
				if(roleCodes != null && !"".equals(roleCodes)){
					List<SysPersonRole> list = personRoleMapper.getRoleByPerson(personId);
					boolean flag = false;
					for (int i = 0; i < list.size(); i++) {
						if(roleCodes.indexOf(list.get(i).getRoleId()) != -1){
							flag = true;
							break;
						}
					}
					if(flag == false){
						throw new Exception("无权限");
					}
				}
				AppEntry entry = business.getEntry();
				FlowNode nextNode = fleNodeMapper.selectByNodeId(nextNodeId);
				entry.setRecordId(entry.getNodeId());
				entry.setUpdDate(new Date());
				entry.setUpdOperCode(personId);
				entry.setNodeId(nextNodeId);
				entry.setNodeName(nextNode.getEname());
				appEntryMapper.updateByPrimaryKey(entry);
				json.put("code", 200);
				json.put("msg", "保存成功");
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e);
			}
			return json;
		}
}
