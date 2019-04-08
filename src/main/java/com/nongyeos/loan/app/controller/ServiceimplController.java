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

import com.nongyeos.loan.app.entity.IntServiceimpl;
import com.nongyeos.loan.app.entity.IntServiceresult;
import com.nongyeos.loan.app.service.IServiceimplService;
import com.nongyeos.loan.app.service.IServiceresultService;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("serviceimpl")
public class ServiceimplController {
	
	@Resource
	private IServiceimplService serviceimplService;
	@Resource
	private IServiceresultService serviceresultService;
	
	private static final Logger logger = LogManager.getLogger(ServiceimplController.class);
	
	@RequestMapping(value = "listServiceImpl", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<IntServiceimpl> serimplPage(int limit,int offset){
		try {
			PageBeanUtil<IntServiceimpl> list = serviceimplService.selectByPage(limit, offset);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "addServiceImpl", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> save(IntServiceimpl serimpl){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(serimpl != null){
				if(serimpl.getServimplCode().equals("")){
					IntServiceimpl serviceimpl = serviceimplService.selectByName(serimpl.getCname());
					IntServiceimpl serviceimpl2 = serviceimplService.selectByLocalPCode(serimpl.getLocalPrdcode());
					if(serviceimpl != null){
						map.put("msg", "中文名称重复，请重新填写！");
					}else if (serviceimpl2 != null) {
						map.put("msg", "本地产品编号重复，请重新填写！");
					}else{
						serimpl.setServimplCode(UUIDUtil.getUUID());
						serviceimplService.add(serimpl);
						map.put("msg", "添加成功");
					}
				}else{
					serviceimplService.update(serimpl);
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
	
	@RequestMapping(value = "delServiceImpl", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> delete(HttpServletResponse response,HttpServletRequest request,String currIds){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(currIds != null && !currIds.equals("")){
				String[] servimplCodes = currIds.split(",");
				for(String servimplCode : servimplCodes){
					List<IntServiceresult> list = serviceresultService.getListByCode(servimplCode);
					if (list.size() == 0) {
						serviceimplService.deleteBySerimplCode(servimplCode);
						map.put("msg", "删除成功");
					}else {
						map.put("msg", "请先删除该服务下的服务结果");
					}
				}
			}else{
				map.put("msg", "删除失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return map;
	}
	
	@RequestMapping("/serimplDS")
	@ResponseBody
	public String getSerimplDS(){
    	String strJson = "[";
    	try {
			List<IntServiceimpl> list = serviceimplService.selectAll();
			for(int i=0;i<list.size();i++)
			{
				IntServiceimpl bean = list.get(i);
				if(bean != null)
				{
					if(i>0)
						strJson = strJson + ", ";
					
					strJson = strJson + "{parameterName:'" + bean.getCname() + "', parameterValue:'" + bean.getServimplCode() + "'}";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		strJson = strJson + "]";
		return(strJson);
    }
}
