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
import com.nongyeos.loan.app.entity.ScoreVar;
import com.nongyeos.loan.app.service.IVarService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/var")
public class VarController {
	
	@Resource
	private IVarService varService;
	@Resource
	private ISysSenoService sysSenoService;

	@RequestMapping(value = "listVar", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<ScoreVar> scvarPage(int limit,int offset){
		try {
			PageBeanUtil<ScoreVar> list = varService.selectByPage(limit, offset);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(ScoreVar var) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (var != null) {
				if (var.getVarId().equals("")) {
					var.setVarId(sysSenoService.getNextString(Constants.TABLE_NAME_VAR, 10, "VAR", 1));
					varService.add(var);
					map.put("msg", "添加成功");
				} else {
					varService.update(var);
					map.put("msg", "修改成功");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", e);
		}
		return map;
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(String currIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (currIds != null && !currIds.equals("")) {
				String[] varIds = currIds.split(",");
				for (String varId : varIds) {
					varService.deleteById(varId);
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
	
	@RequestMapping(value = "getVarIdDS", method = RequestMethod.POST)
	@ResponseBody
	public String getVarIdDS(){
		String strJson = "[";
		List<ScoreVar> list = varService.selectAll();
		if(list != null && list.size() > 0){
			ScoreVar bean = null;
			for(int i = 0;i <list.size();i++){
				bean = list.get(i);
				if(bean != null){
					if(i>0){
    					strJson = strJson + ", ";
					}
    				
    				strJson = strJson + "{parameterName:'" + bean.getCname() + "', parameterValue:'" + bean.getVarId() + "'}";
				}
			}
		}
		strJson = strJson + "]";
		return strJson;
	}
	
}
