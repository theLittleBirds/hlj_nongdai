package com.nongyeos.loan.app.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.app.entity.IntServiceresult;
import com.nongyeos.loan.app.service.IServiceresultService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("serviceresult")
public class ServiceresultController {
	
	@Resource
	private IServiceresultService serviceresultService;
	
	private static final Logger logger = LogManager.getLogger(ServiceresultController.class);
	
	@RequestMapping(value = "listServRes", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<IntServiceresult> providerPage(int limit,int offset, String code){
    	try {
			PageBeanUtil<IntServiceresult> list = serviceresultService.selectByPage(limit, offset,code);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
	
	@RequestMapping(value = "addServRes", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> save(IntServiceresult servRes){
		Map<String, Object> map = new HashMap<String, Object>();
		if(servRes != null){
			if (servRes.getServresCode().equals("")) {
				serviceresultService.add(servRes);
				map.put("msg", "添加成功");
			}else{
				serviceresultService.update(servRes);
				map.put("msg", "修改成功");
			}
		}else{
			map.put("msg", "操作失败");
		}
		return map;
	}
	
	@RequestMapping(value = "delServRes", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> delete(HttpServletResponse response,HttpServletRequest request,String currIds){
		Map<String, Object> map = new HashMap<String, Object>();
		if(currIds != null && !currIds.equals("")){
    		String[] pCodes = currIds.split(",");
    		for(String pCode : pCodes){
    			serviceresultService.deleteByPCode(pCode);
    		}
    		map.put("msg", "删除成功");
    	}else{
    		map.put("msg", "删除失败");
    	}
    	return map;
	}
}
