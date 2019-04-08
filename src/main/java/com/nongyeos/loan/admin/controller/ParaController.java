package com.nongyeos.loan.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.SysPara;
import com.nongyeos.loan.admin.entity.SysParaGroup;
import com.nongyeos.loan.admin.service.IParaGroupService;
import com.nongyeos.loan.admin.service.IParaService;
import com.nongyeos.loan.base.util.PageBeanUtil;
  
@Controller  
@RequestMapping("/para")
public class ParaController {  
    @Resource  
    private IParaService paraService; 
    @Resource  
    private IParaGroupService paraGroupService;
    
    private static final Logger logger = LogManager.getLogger(ParaController.class);
    
    /*
     * 添加ResponseBody注解作用返回json格式用于ajax请求
     */
    @RequestMapping(value = "paralist", method = RequestMethod.POST)
    @ResponseBody
    public String getListByPGCode(String paraGroupName) throws Exception{
    	String paraStr = "";
    	SysParaGroup pGroup = paraGroupService.selectByName(paraGroupName);
		if(pGroup == null)
		{
			return paraStr;
		}
		else{
			paraStr = paraService.selectByPGroupId(pGroup.getPgroupId());		
			return paraStr;
		}
    }
    
    @RequestMapping(value = "paralist2", method = RequestMethod.POST)
    @ResponseBody
    public String getListByPGCode2(String paraGroupName) throws Exception{
    	String paraStr = "";
    	SysParaGroup pGroup = paraGroupService.selectByName(paraGroupName);
		if(pGroup == null)
		{
			return paraStr;
		}
		else{
			paraStr = paraService.selectByPGroupId2(pGroup.getPgroupId());		
			return paraStr;
		}
    }
    
    @RequestMapping(value = "pgrouplist", method = RequestMethod.POST)
    @ResponseBody
    public PageBeanUtil<SysParaGroup> pgrouplist(int offset,int limit) throws Exception{
    	PageBeanUtil<SysParaGroup> list = paraGroupService.selectByPage(offset, limit);
    	if(list != null)
		    return list;
    	else 
    		return null;
    }
    
    @RequestMapping(value = "sysparalist", method = RequestMethod.POST)
    @ResponseBody
    public PageBeanUtil<SysPara> sysparalist(int offset,int limit,String curParaGroupCode) throws Exception{
    	PageBeanUtil<SysPara> list = paraService.getList(offset,limit,Integer.parseInt(curParaGroupCode));
    	if(list != null){
		    return list;
    	}else {
    		return null;
    	}
    }
    
    
    @RequestMapping(value = "pgroupsave", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> pgroupsave(SysParaGroup paraGroupBean,String anyname) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	if(paraGroupService.existedSameName(paraGroupBean)){
    		map.put("msg", "名称重复，请重新填写！");
    	}else if(paraGroupService.existedSameDesc(paraGroupBean)){
    		map.put("msg", "描述重复，请重新填写！");
    	}else{
    		if(paraGroupBean.getPgroupId() == null){
        		paraGroupService.save(paraGroupBean);
        	}
        	else{
        		paraGroupService.update(paraGroupBean);
        	}
    		map.put("msg", "保存成功");
    	}
    	return map;
    }
    
    @RequestMapping(value = "parasave", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> parasave(SysPara para) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	if(paraService.existedSameName(para)){
    		map.put("msg", "名称重复，请重新填写！");
    	}else if(paraService.existedSameDesc(para)){
    		map.put("msg", "描述重复，请重新填写！");
    	}else{
    		if(para.getParaId() == null){
        		paraService.save(para);
        	}
        	else{
        		paraService.update(para);
        	}
    		map.put("msg", "保存成功");
    	}
    	return map;
    }
    
    @RequestMapping(value = "paradelete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> paradelete(String selCodes) throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
    	if(selCodes != null && !selCodes.equals("")){
    		paraService.delete(selCodes);
			map.put("msg", "删除成功");
		}else{
			map.put("msg", "删除失败");
		}
    	return map;
    }
    
    @RequestMapping(value = "paragroupdelete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> paragroupdelete(String selCodes) throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
    	if(selCodes != null && !selCodes.equals("")){
    		paraGroupService.delete(selCodes);
    		map.put("msg", "删除成功");
		}else{
			map.put("msg", "删除失败");
		}
    	return map;
    }
    
    /*
     * 根据名称获取字典，字符串可以传多条名称，逗号分隔
     */
    @RequestMapping(value = "someparalist", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getSomeListByPGCode(String someParaGroupName) throws Exception{
        JSONObject json = new JSONObject();
    	if(someParaGroupName == null || "".equals(someParaGroupName))
    		return null;
    	String[] paraName = someParaGroupName.split(",");
    	for (int i = 0; i < paraName.length; i++) {
    		SysParaGroup pGroup = paraGroupService.selectByName(paraName[i]);
    		if(pGroup != null){
    			json.put(paraName[i], paraService.selectByPGroupId(pGroup.getPgroupId()));
    		}
		}
    	return json;
    }
    
    @RequestMapping(value = "pGrpList", method = RequestMethod.POST)
	@ResponseBody
	public String getPGrpParaDS() throws Exception{
		String strJson = "[";
		List<SysParaGroup> list = paraGroupService.getList();
		if(list != null && list.size() > 0){
			SysParaGroup bean = null;
			for(int i = 0;i <list.size();i++){
				bean = list.get(i);
				if(bean != null){
					if(i>0){
    					strJson = strJson + ", ";
					}
    				
    				strJson = strJson + "{parameterName:'" + bean.getDescr() + "', parameterValue:'" + bean.getName() + "'}";
				}
			}
		}
		strJson = strJson + "]";
		return strJson;
	}
    
}  
