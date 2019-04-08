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
import com.nongyeos.loan.app.service.IAppSvrunitService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/srvunit")
public class AppSrvunitController {

	@Resource
	private IAppSvrunitService svrunitService;
	@Resource
	private ISysSenoService sysSenoService;

	
	@RequestMapping(value = "selectByAppId", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<AppSrvunit> partnerPage(String appId) {
		PageBeanUtil<AppSrvunit> pi = new PageBeanUtil<AppSrvunit>();
		try {
			List<AppSrvunit> list = svrunitService.selectByAppId(appId);
			pi.setItems(list);
			pi.setTotalNum(list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pi;
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(AppSrvunit srvunit) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (srvunit != null) {
				if (srvunit.getSrvunId() == null
						|| srvunit.getSrvunId().equals("")) {
					srvunit.setSrvunId((sysSenoService.getNextString(
							Constants.TABLE_NAME_SRVUNIT, 10, "SU", 1)));
					svrunitService.addSvrunit(srvunit);
					map.put("msg", "添加成功");
				} else {
					svrunitService.updSvrunit(srvunit);
					map.put("msg", "修改成功");
				}
			} else {
				map.put("msg", "操作失败！");
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
				String[] srvunitIds = currIds.split(",");
				for (String srvunitId : srvunitIds) {
					svrunitService.deleteSrvunit(srvunitId);
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
	
	@RequestMapping(value = "getSrvunitByAppId", method = RequestMethod.POST)
	@ResponseBody
	public String getSrvunitDS(String appId){
		String strJson = "[";
		List<AppSrvunit> list;
		try {
			list = svrunitService.selectByAppId(appId);
			if(list != null && list.size() > 0){
				AppSrvunit srvunit = null;
				for(int i = 0;i < list.size();i++){
					srvunit = list.get(i);
					if(srvunit != null){
						if(i>0)
	    					strJson = strJson + ", ";
	    				
	    				strJson = strJson + "{parameterName:'" + srvunit.getCname() + "', parameterValue:'" + srvunit.getSrvunId() + "'}";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		strJson = strJson + "]";
		return strJson;
	}
}
