package com.nongyeos.loan.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.app.entity.AppPara;
import com.nongyeos.loan.app.service.IAppParaService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/appPara")
public class AppParaController {

	@Resource
	private IAppParaService appParaService;

	/**
	 * 参数--分页全查
	 * 
	 * @return
	 */
	
	@RequestMapping(value = "select", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<AppPara> selectAll(int limit, int offset, String appId) {
		try {
			PageBeanUtil<AppPara> list = appParaService.selectAll(limit, offset, appId);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 参数--添加和修改
	 * 
	 * @param AppPara
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(AppPara AppPara) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (AppPara.getId() == null || AppPara.getId().equals("")) {
				appParaService.addAppPara(AppPara);
				map.put("msg", "添加成功");
			} else {
				appParaService.updateAppPara(AppPara);
				map.put("msg", "修改成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 参数--单个删除和批量删除
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
				String[] appParaIds = currIds.split(",");
				for (String appParaid : appParaIds) {
					Integer appParaId = Integer.parseInt(appParaid);
					appParaService.deleteAppPara(appParaId);
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
	
	@RequestMapping("/getParaDS")
	@ResponseBody
	public String getParaDS(String appId) throws Exception{
		String strJson = "[";
		List<AppPara> list = appParaService.selectByOne(appId);
		if(list != null && list.size() > 0){
			AppPara beanPara = null;
			for(int i = 0;i <list.size();i++){
				beanPara = list.get(i);
				if(beanPara != null){
					if(i>0)
    					strJson = strJson + ", ";
    				
    				strJson = strJson + "{parameterName:'" + beanPara.getGroupDescr()+ "', parameterValue:'" + beanPara.getGroupName() + "'}";
				}
			}
		}
		strJson = strJson + "]";
		return strJson;
	}
	
	@RequestMapping("/getParaDS2")
	@ResponseBody
	public String getParaDS2() throws Exception{
		String strJson = "[";
		List<AppPara> list = appParaService.selectAllDS();
		if(list != null && list.size() > 0){
			AppPara beanPara = null;
			for(int i = 0;i <list.size();i++){
				beanPara = list.get(i);
				if(beanPara != null){
					if(i>0)
						strJson = strJson + ", ";
					
					strJson = strJson + "{parameterName:'" + beanPara.getGroupDescr()+ "', parameterValue:'" + beanPara.getGroupName() + "'}";
				}
			}
		}
		strJson = strJson + "]";
		return strJson;
	}

	@RequestMapping("/getNodeStatusDS")
	@ResponseBody
	public String getNodeStatusDS(String appParaGroupName,String appId) throws Exception{
		String strJson = "[";
		List<AppPara> list = appParaService.selectByName(appParaGroupName,appId);
		if(list != null && list.size() > 0){
			AppPara beanPara = null;
			for(int i = 0;i <list.size();i++){
				beanPara = list.get(i);
				if(beanPara != null){
					if(i>0)
    					strJson = strJson + ", ";
    				
    				strJson = strJson + "{parameterName:'" + beanPara.getDescr()+ "', parameterValue:'" + beanPara.getValue() + "'}";
				}
			}
		}
		strJson = strJson + "]";
		return strJson;
	}
}
