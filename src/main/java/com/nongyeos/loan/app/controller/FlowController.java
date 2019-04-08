package com.nongyeos.loan.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.admin.entity.SysPersonorg;
import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.app.entity.BusFins;
import com.nongyeos.loan.app.entity.FleFlow;
import com.nongyeos.loan.app.entity.FleFlowright;
import com.nongyeos.loan.app.service.IBusFinsService;
import com.nongyeos.loan.app.service.IFleFlowrightService;
import com.nongyeos.loan.app.service.IFlowService;
import com.nongyeos.loan.base.util.Constants;

@Controller
@RequestMapping("/flow")
public class FlowController {

	@Resource
	private IBusFinsService busFinsService;
	@Resource
	private ISysSenoService sysSenoService;
	@Resource
	private IFlowService flowService;
	@Resource
	private IFleFlowrightService flowrightService;

	@RequestMapping(value = "selectFlow", method = RequestMethod.POST)
	@ResponseBody
	public String flowList(HttpSession session) {
		List<String> orgIdList = new ArrayList<>();
    	List<SysPersonorg> perorgList = (List<SysPersonorg>) session.getAttribute(Constants.SESSION_ORGLIST);
    	for (SysPersonorg perorg : perorgList) {
    		orgIdList.add(perorg.getOrgId());
    	}
		String strJson = "[";
		try {
			int num = 1;
			int num2 = 0;
			List<BusFins> list = busFinsService.selectAll(orgIdList);
			BusFins beanBusFins = null;
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					beanBusFins = list.get(i);
					if (i > 0)
						strJson = strJson + ", ";

					strJson = strJson + "{\"id\":" + num + ", \"pid\":0,"
							+ " \"finsId\":\"" + beanBusFins.getFinsId()
							+ "\", \"orgId\":\"" + beanBusFins.getOrgId()
							+ "\", \"name\":\"" + beanBusFins.getCname() 
							+ "\", \"category\":\"" + "" 
							+ "\", \"status\":\"" + "" + "\"}";
					num2 = num;
					num++;
					List<FleFlow> list2 = flowService
							.selectByFinsCode(beanBusFins.getFinsId());
					FleFlow flow = null;
					if (list2 != null && list2.size() > 0) {
						strJson = strJson + ", ";
						for (int j = 0; j < list2.size(); j++) {
							flow = list2.get(j);
							if (flow != null) {
								if (j > 0)
									strJson = strJson + ", ";

								strJson = strJson + "{\"id\":" + num
										+ ", \"pid\":" + num2 
										+ ", \"name\":\"" + flow.getCname() 
										+ "\", \"type\":\"" + flow.getType()
										+ "\", \"flowId\":\"" + flow.getFlowId()
										+ "\", \"category\":\"" + flow.getCategory()
										+ "\", \"finsId\":\"" + flow.getFinsId()
										+ "\", \"cname\":\"" + flow.getCname()
										+ "\", \"status\":\"" + flow.getStatus() 
										+ "\", \"memo\":\"" + flow.getMemo() 
										+ "\", \"seqno\":\"" + flow.getSeqno() + "\"}";
							}
							num++;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		strJson = strJson + "]";
		return (strJson);

	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(FleFlow flow) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (flow != null) {
				if (flow.getFlowId().equals("")) {
					flow.setFlowId(sysSenoService.getNextString(
							Constants.TABLE_NAME_FLOW, 10, "F", 1));
					flowService.add(flow);
					map.put("msg", "添加成功");
				} else {
					flowService.update(flow);
					map.put("msg", "修改成功");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", e);
		}
		return map;
	}

	@RequestMapping(value = "del", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(String currIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (currIds != null && !currIds.equals("")) {
				String[] flowIds = currIds.split(",");
				for (String flowId : flowIds) {
					flowService.deleteById(flowId);
				}
				map.put("msg", "删除成功");
			} else {
				map.put("msg", "删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", e);
		}
		return map;
	}

	@RequestMapping(value = "getFlowById", method = RequestMethod.POST)
	@ResponseBody
	public FleFlow getFlowById(String flowId) {
		try {
			FleFlow flow = flowService.getFlowById(flowId);
			return flow;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "getFlowrightByFlowCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getFlowright(String flowId)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String objectIdString = "";
		List<FleFlowright> list = flowrightService.selectByFlowCode(flowId);
	    if(list != null && list.size() > 0){
	    	FleFlowright bean = null;
	    	for(int i = 0;i < list.size();i++){
	    		bean = list.get(i);
	    		if(objectIdString.equals("")){
					objectIdString = bean.getObjectCode()+"";
				}else{
					objectIdString += "," + bean.getObjectCode();
				}
	    	}
	    }
		map.put("objectIds", objectIdString);
    	return map;
	}
	
	@RequestMapping(value = "saveFlowright", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> saveFlowAndRole(String roleCodes,String flowId)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(flowId != null && !flowId.equals("")){
			flowrightService.delByFlowCode(flowId);
			if(roleCodes != null && !roleCodes.equals("")){
				FleFlowright flowright = new FleFlowright();
				String[] roleIds = roleCodes.split(",");
				for(String roleId : roleIds){
					flowright.setFlowId(flowId);
					flowright.setObjectCode(roleId);
					flowright.setControlType((short)1);
					flowright.setObjectType((short)1);
					flowrightService.insertRole(flowright);
				}
			}
			map.put("msg", "保存成功");
		}else{
			map.put("msg", "保存失败");
		}
		  return map;
	}

}
