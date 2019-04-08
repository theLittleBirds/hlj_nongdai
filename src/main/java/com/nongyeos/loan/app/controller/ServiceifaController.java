package com.nongyeos.loan.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.app.entity.IntServiceifa;
import com.nongyeos.loan.app.service.IServiceifaService;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/serviceifa")
public class ServiceifaController {

	@Resource
	private IServiceifaService serviceifaService;

	/**
	 * 通用服务-分页全查
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public PageBeanUtil<IntServiceifa> partnerPage(int limit,int offset) {
		try {
			PageBeanUtil<IntServiceifa> list = serviceifaService.selectAll(limit,offset);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 通用服务-保存
	 * 
	 * @param response
	 * @param request
	 * @param serviceifa
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(IntServiceifa serviceifa) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (serviceifa.getServifaCode() == null|| serviceifa.getServifaCode().equals("")) {
				serviceifa.setServifaCode(UUIDUtil.getUUID());
				serviceifaService.add(serviceifa);
			} else {
				serviceifaService.update(serviceifa);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("msg", "添加成功");
		return map;
	}

	@RequestMapping("/delServiceifa")
	@ResponseBody
	public Map<String, Object> delete(HttpServletResponse response,
			HttpServletRequest request, String currIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (currIds != null && !currIds.equals("")) {
				String[] serviceifaIds = currIds.split(",");
				for (String serviceifaId : serviceifaIds) {
					serviceifaService.deleteServiceifa(serviceifaId);
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
	
	@RequestMapping("/sifaDS")
	@ResponseBody
	public String getProviderDS(){
    	String strJson = "[";
    	try {
			List<IntServiceifa> list = serviceifaService.selectAll();
			for(int i=0;i<list.size();i++)
			{
				IntServiceifa bean = list.get(i);
				if(bean != null)
				{
					if(i>0)
						strJson = strJson + ", ";
					
					strJson = strJson + "{parameterName:'" + bean.getCname() + "', parameterValue:'" + bean.getServifaCode() + "'}";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		strJson = strJson + "]";
		return(strJson);
    }
}
