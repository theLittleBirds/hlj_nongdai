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
import com.nongyeos.loan.app.entity.AppSrvunit;
import com.nongyeos.loan.app.entity.FlowNevent;
import com.nongyeos.loan.app.entity.IntServiceresult;
import com.nongyeos.loan.app.service.IAppSvrunitService;
import com.nongyeos.loan.app.service.IFlowNeventService;
import com.nongyeos.loan.app.service.IServiceresultService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("flowNevent")
public class FlowNeventController {

	@Resource
	private IFlowNeventService neventService;
	@Resource
	private ISysSenoService sysSenoService;
	@Resource
	private IServiceresultService serviceresultService;
	@Resource
	private IAppSvrunitService srvunitService;
	
	@RequestMapping(value = "selectByNodeId", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<FlowNevent> partnerPage(String nodeId) {
		PageBeanUtil<FlowNevent> pi = new PageBeanUtil<FlowNevent>();
		try {
			List<FlowNevent> list = neventService.selectByNodeId(nodeId);
			pi.setItems(list);
			pi.setTotalNum(list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pi;
	}
	
	@RequestMapping(value = "getNeventByNodeId", method = RequestMethod.POST)
	@ResponseBody
	public String getNeventDS(String nodeId) throws Exception{
		String strJson = "[";
		List<FlowNevent> list = neventService.selectByNodeId(nodeId);
		if(list != null && list.size() > 0){
			FlowNevent beanNevent = null;
			for(int i = 0;i <list.size();i++){
				beanNevent = list.get(i);
				AppSrvunit srvunit = srvunitService.selectBySrvunitId(beanNevent.getSrvunId());
				if(beanNevent != null){
					if(i>0)
    					strJson = strJson + ", ";
    				strJson = strJson + "{parameterName:'" + srvunit.getCname()+ "', parameterValue:'" + beanNevent.getNeventId() + "'}";
				}
			}
		}
		strJson = strJson + "]";
		return strJson;
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(FlowNevent flowNevent) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (flowNevent.getNeventId() == null || flowNevent.getNeventId().equals("")) {
				flowNevent.setNeventId(sysSenoService.getNextString(Constants.TABLE_NAME_NODENEVENT, 12, "NE", 1));
				neventService.addNevent(flowNevent);
				map.put("msg", "添加成功");
			} else {
				neventService.updateNevent(flowNevent);
				map.put("msg", "修改成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(String currIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (currIds != null && !currIds.equals("")) {
				String[] neventIds = currIds.split(",");
				for (String neventId : neventIds) {
					neventService.deleteNevent(neventId);
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
	
//	//根据事件id获取服务结果id，根据服务结果id获取服务结果
	@RequestMapping(value = "getServiceResultByNeventId", method = RequestMethod.POST)
	@ResponseBody
	public String getServiceResultByNeventId(String neventId) throws Exception{
		String strJson = "[";
		FlowNevent flowNevent = neventService.selectByNeventId(neventId);
		if(flowNevent!=null){
			String srvunitId=flowNevent.getSrvunId();
			AppSrvunit srvunit=srvunitService.selectBySrvunitId(srvunitId);
			String servimplId="";
			if(srvunit != null){
				servimplId = srvunit.getServimplId();
			}
			List<IntServiceresult> list=serviceresultService.selectServiceResultByServimplId(servimplId);
			if(list != null && list.size() > 0){
			IntServiceresult serviceResult=null;
			for(int i = 0;i <list.size();i++){
				serviceResult=list.get(i);
				if(serviceResult!=null){
					if(i>0){
						strJson = strJson + ", ";
					}
					strJson = strJson + "{parameterName:'" + serviceResult.getResultText()+ "', parameterValue:'" + serviceResult.getServimplCode() + "'}";
				}
			}
		}
	}
		strJson = strJson + "]";
		return strJson;
}
	//根据服务结果id找到服务结果text
	@RequestMapping(value = "getResult", method = RequestMethod.POST)
	@ResponseBody
	public String getResult() throws Exception{
		String strJson = "[";
		List<IntServiceresult> serviceResult = serviceresultService.selectAll();
		if(serviceResult !=null && serviceResult.size()>0){
			IntServiceresult beanResult=null;
			for(int i=0;i<serviceResult.size();i++){
				beanResult=serviceResult.get(i);
				if(serviceResult!=null){
					if(i>0){
						strJson=strJson+", ";
					}
					strJson = strJson + "{parameterName:'" + beanResult.getResultText()+ "', parameterValue:'" + beanResult.getServimplCode() + "'}";
				}
			}
		}
		
		strJson = strJson + "]";
		return strJson;
}
}