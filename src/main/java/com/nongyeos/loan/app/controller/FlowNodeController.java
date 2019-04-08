package com.nongyeos.loan.app.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.app.entity.FlowNode;
import com.nongyeos.loan.app.service.IFlowNodeService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/fleNode")
public class FlowNodeController {

	@Resource
	private IFlowNodeService fleNodeService;
	@Resource
	private ISysSenoService sysSenoService;
	
	
	/**
	 * @param appId
	 * @return
	 */
	@RequestMapping(value = "selectByAppId", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<FlowNode> partnerPage(String appId) {
		PageBeanUtil<FlowNode> pi = new PageBeanUtil<FlowNode>();
		try {
			List<FlowNode> list = fleNodeService.selectByAppId(appId);
			pi.setItems(list);
			pi.setTotalNum(list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pi;
	}
	
	/**
	 * @param nodeId
	 * @return
	 */
	@RequestMapping(value = "selectByNodeId", method = RequestMethod.POST)
	@ResponseBody
	public FlowNode partnerPage1(String nodeId) {
		FlowNode fleNode=null;
		try {
			 fleNode = fleNodeService.selectByNodeId(nodeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fleNode;
	}
	
	/**
	 * 节点-保存
	 * @param fleNode
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(FlowNode fleNode) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (fleNode.getNodeId() == null || fleNode.getNodeId().equals("")) {
				fleNode.setNodeId(sysSenoService.getNextString(Constants.TABLE_NAME_NODE, 10, "N", 1));
				fleNodeService.addFleNode(fleNode);
				map.put("msg", "添加成功");
			} else {
				fleNodeService.updateFleNode(fleNode);
				map.put("msg", "修改成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 节点--单个删除和批量删除
	 * 
	 * @param currIds
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(String currIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (currIds != null && !currIds.equals("")) {
				String[] fleNodeIds = currIds.split(",");
				for (String fleNodeId : fleNodeIds) {
					fleNodeService.deleteFleNode(fleNodeId);
				}
				map.put("msg", "删除成功");
			} else {
				map.put("msg", "删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value = "getApproverIdsByNodeId", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getApproverIdsByNodeId(String nodeId){
		Map<String, Object> map = new HashMap<String, Object>();
		String objectIdString = "";
		FlowNode fleNode=null;
		try {
			fleNode = fleNodeService.getApproverIdsByNodeId(nodeId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(fleNode != null){
			objectIdString = fleNode.getApproverIds();
		}
		map.put("objectIds", objectIdString);
    	return map;
	}
	
	//角色授权
	@RequestMapping(value = "saveRoles", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveRoles(String nodeId,String roleCodes){
		Map<String, Object> map = new HashMap<String, Object>();
		FlowNode node=new FlowNode();
		node.setNodeId(nodeId);
		node.setApproverIds(roleCodes);
		try {
			fleNodeService.updateFleNode(node);
			map.put("msg", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return map;
	}
	
	//审批表单提交
	@RequestMapping(value = "saveNode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveShenPi(FlowNode fleNode,String nodeId){
		Map<String, Object> map = new HashMap<String, Object>();
		fleNode.setNodeId(nodeId);
		try {
			fleNodeService.updateFleNode(fleNode);
			map.put("msg", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return map;
	}
		//状态表单提交
		@RequestMapping(value = "saveStatus", method = RequestMethod.POST)
		@ResponseBody
		public Map<String,Object> saveStatus(String nodeId,Short nodeStatus,Short openStatus,Short initStatus,Short saveStatus){
			FlowNode flowNode=null;
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				flowNode=fleNodeService.selectByNodeId(nodeId);
				flowNode.setNodeStatus(nodeStatus);
				flowNode.setOpenStatus(openStatus);
				flowNode.setInitStatus(initStatus);
				flowNode.setSaveStatus(saveStatus);
				fleNodeService.updateFleNode1(flowNode);
				map.put("msg", "操作成功");
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	return map;
		}
}
