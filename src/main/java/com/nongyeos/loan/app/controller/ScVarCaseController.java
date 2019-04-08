package com.nongyeos.loan.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.nongyeos.loan.admin.entity.SysPara;
import com.nongyeos.loan.admin.entity.SysParaGroup;
import com.nongyeos.loan.admin.service.IParaGroupService;
import com.nongyeos.loan.admin.service.IParaService;
import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.app.entity.ScoreScvar;
import com.nongyeos.loan.app.entity.ScoreScvarcase;
import com.nongyeos.loan.app.service.IScvarCaseService;
import com.nongyeos.loan.app.service.IScvarService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/scVarCase")
public class ScVarCaseController {

	@Resource
	private IScvarCaseService caseService;
	@Resource
	private IScvarService scvarService;
	@Resource
	private IParaService paraService;
	@Resource
	private IParaGroupService paraGroupService;
	@Resource
	private ISysSenoService sysSenoService;

	@RequestMapping(value = "listScVarCase", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<ScoreScvarcase> scvarPage(int limit,int offset,String scoreid) {
		try {
			PageBeanUtil<ScoreScvarcase> list = caseService.selectByPage(limit, offset,scoreid);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "getDatatype", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDatatype(String scvarId){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ScoreScvar scvar = scvarService.getScvarById(scvarId);
			short type = scvar.getDataType();
			map.put("type", type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(String json) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ScoreScvarcase varcase = new ScoreScvarcase();
			varcase = JSON.parseObject(json,ScoreScvarcase.class);
			if (varcase != null) {
				if (varcase.getCaseId() == null || "".equals(varcase.getCaseId())) {
					varcase.setCaseId(sysSenoService.getNextString(Constants.TABLE_NAME_SCVARCASE, 10, "SCC", 1));
					caseService.add(varcase);
					map.put("msg", "添加成功");
				} else {
					caseService.update(varcase);
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
				String[] caseIds = currIds.split(",");
				for (String caseId : caseIds) {
					caseService.deleteById(caseId);
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
	
	@RequestMapping(value = "CaseByScVarId", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<ScoreScvarcase> CaseByScVarId(String scvarid,String chart) {
		PageBeanUtil<ScoreScvarcase> pi = new PageBeanUtil<ScoreScvarcase>();
		try {
			List<ScoreScvarcase> list = caseService.selectAll(scvarid);
			pi.setItems(list);
			pi.setTotalNum(list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pi;
	}
	
	@RequestMapping(value = "findParaList", method = RequestMethod.POST)
	@ResponseBody
	public String getParaSEDS(String scvarId) {
		try {
			String strJson = "[";
			ScoreScvar scvar = scvarService.getScvarById(scvarId);
			String name = scvar.getParagrpName();
			SysParaGroup group = paraGroupService.selectByName(name);
			if (group != null) {
				List<SysPara> list = paraService.getListByPId(group.getPgroupId());
				if(list != null && list.size() > 0){
					SysPara bean = null;
					for(int i = 0;i <list.size();i++){
						bean = list.get(i);
						if(bean != null){
							if(i>0){
								strJson = strJson + ", ";
							}
							strJson = strJson + "{text:'" + bean.getDescr() + "', value:'" + bean.getValue() + "'}";
						}
					}
				}
			}else {
				strJson = strJson + "{text:'无对应参数', value:'-1'}";
			}
			strJson = strJson + "]";
			return strJson;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}