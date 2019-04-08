package com.nongyeos.loan.app.controller;

import java.util.HashMap;
import java.util.List;
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

import com.nongyeos.loan.app.entity.IntProvider;
import com.nongyeos.loan.app.service.IProviderService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("provider")
public class ProviderController {
	
	@Resource
	private IProviderService providerService;
	
	private static final Logger logger = LogManager.getLogger(ProviderController.class);
	
	@RequestMapping(value = "listProvider", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<IntProvider> providerPage(int limit,int offset){
		try {
			PageBeanUtil<IntProvider> list = providerService.selectByPage(limit, offset);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
	
	@RequestMapping(value = "addProvider", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> save(IntProvider pro){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(pro != null){
				if(pro.getProviderCode().equals("")){
					IntProvider provider = providerService.selectByName(pro.getCname());
					if(provider != null){
						map.put("msg", "服务机构名称重复，请重新填写！");
					}else{
						providerService.add(pro);
						map.put("msg", "添加成功");
					}
				}else{
					providerService.update(pro);
					map.put("msg", "修改成功");
				}
			}else{
				map.put("msg", "操作失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping(value = "delProvider", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> delete(HttpServletResponse response,HttpServletRequest request,String currIds){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(currIds != null && !currIds.equals("")){
				String[] pCodes = currIds.split(",");
				for(String pCode : pCodes){
					providerService.deleteByPCode(pCode);
				}
				map.put("msg", "删除成功");
			}else{
				map.put("msg", "删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return map;
	}
	
	@RequestMapping("/providerDS")
	@ResponseBody
	public String getProviderDS(){
    	String strJson = "[";
    	List<IntProvider> list = providerService.selectAll();
    	for(int i=0;i<list.size();i++)
		{
    		IntProvider bean = list.get(i);
			if(bean != null)
			{
				if(i>0)
					strJson = strJson + ", ";
				
				strJson = strJson + "{parameterName:'" + bean.getCname() + "', parameterValue:'" + bean.getProviderCode() + "'}";
			}
		}
		strJson = strJson + "]";
		return(strJson);
    }
	
	@RequestMapping("/providerDS2")
	@ResponseBody
	public String getProviderDS2(){
    	String strJson = "[";
    	List<IntProvider> list = providerService.selectAll();
    	for(int i=0;i<list.size();i++)
		{
    		IntProvider bean = list.get(i);
			if(bean != null)
			{
				if(i>0)
					strJson = strJson + ", ";
				
				strJson = strJson + "{parameterName:'" + bean.getShortCname() + "', parameterValue:'" + bean.getProviderCode() + "'}";
			}
		}
		strJson = strJson + "]";
		return(strJson);
    }
	
}
