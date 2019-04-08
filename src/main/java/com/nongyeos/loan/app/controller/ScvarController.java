package com.nongyeos.loan.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.app.entity.ScoreScvar;
import com.nongyeos.loan.app.entity.ScoreVar;
import com.nongyeos.loan.app.service.IScvarCaseService;
import com.nongyeos.loan.app.service.IScvarService;
import com.nongyeos.loan.app.service.IVarService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/scvar")
public class ScvarController {

	@Resource
	private IScvarService scvarService;
	@Resource
	private IScvarCaseService caseService;
	@Resource
	private IVarService varService;
	@Resource
	private ISysSenoService sysSenoService;

	@RequestMapping(value = "listScvar", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<ScoreScvar> scvarPage(int limit,int offset,String scoreid) {
		try {
			PageBeanUtil<ScoreScvar> list = scvarService.selectByPage(limit, offset,scoreid);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "listOtherVar", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<ScoreVar> listOtherVar(int limit, int offset, String scoreid) {
		try {
			PageHelper.startPage(offset, limit);
			List<ScoreVar> list = varService.selectAll();
			int countNums = list.size();
			PageBeanUtil<ScoreVar> pageData = new PageBeanUtil<ScoreVar>(offset, limit, countNums);
			pageData.setItems(list);
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(ScoreScvar scvar) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (scvar != null) {
				if (scvar.getVarType() == 1) {
					scvar.setComponentVarIds("");
				}
				if ("".equals(scvar.getScvarId())) {
						scvar.setScvarId(sysSenoService.getNextString(Constants.TABLE_NAME_SCVAR, 10, "SV", 1));
						scvarService.add(scvar);
						map.put("msg", "添加成功");
				} else {
					scvarService.update(scvar);
					map.put("msg", "修改成功");
				}
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", e);
		}
		return null;
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(String currIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (currIds != null && !currIds.equals("")) {
				String[] scvarIds = currIds.split(",");
				for (String scvarId : scvarIds) {
					caseService.deleteByScvarId(scvarId);
					scvarService.deleteById(scvarId);
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
	
	@RequestMapping(value = "getScvarById", method = RequestMethod.POST)
	@ResponseBody
	public ScoreScvar getScvarById(String scvarId){
		try{
			ScoreScvar scvar = scvarService.getScvarById(scvarId);
		    return scvar;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "scVarIdList", method = RequestMethod.POST)
	@ResponseBody
	public String getscVarIdDS(String scid,String chart) throws Exception{
		String strJson = "[";
		List<ScoreScvar> list = scvarService.selectAll(scid);
		if(list != null && list.size() > 0){
			ScoreScvar bean = null;
			for(int i = 0;i <list.size();i++){
				bean = list.get(i);
				if(bean != null){
					if(i>0){
    					strJson = strJson + ", ";
					}
    				
    				strJson = strJson + "{text:'" + bean.getCname() + "', value:'" + bean.getScvarId() + "'}";
				}
			}
		}
		strJson = strJson + "]";
		return strJson;
	}
	
	@RequestMapping(value = "varToScVar", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAll(String unvarIds, String varIds, String scoreid){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ScoreScvar scvar = new ScoreScvar();
			String[] ids = varIds.split(",");
			for (String id : ids) {
				ScoreVar var = varService.getScvarById(id);
				scvar.setScvarId(sysSenoService.getNextString(Constants.TABLE_NAME_SCVAR, 10, "SV", 1));
				scvar.setScoreId(scoreid);
				scvar.setVarId(id);
				scvar.setCname(var.getCname());
				scvar.setEname(var.getEname());
				scvar.setVarType((short) 1);
				scvar.setDataType(var.getDataType());
				scvar.setParagrpName(var.getParagrpName());
				scvar.setCdesc(var.getCdesc());
				scvar.setEdesc(var.getEdesc());
				scvar.setMemo(var.getMemo());
				scvar.setSeqno(var.getSeqno());
				scvar.setIsDelete(var.getIsDelete());
				scvar.setCreOperCode(var.getCreOperCode());
				scvar.setCreOperName(var.getCreOperName());
				scvar.setCreOrgCode(var.getCreOperCode());
				scvar.setCreDate(var.getCreDate());
				scvar.setUpdOperCode(var.getUpdOperCode());
				scvar.setUpdOperName(var.getUpdOperName());
				scvar.setUpdOrgCode(var.getUpdOrgCode());
				scvar.setUpdDate(var.getUpdDate());
				scvarService.add(scvar);
			}
			map.put("msg", "添加成功");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/getScvarByScoreId")
    @ResponseBody
    public Map<String, Object> getScvarByScoreId(String scoreId) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	String objectIdString = "";
    	List<ScoreScvar> list = scvarService.selectAll(scoreId);
    	if(list != null && list.size()>0){
    		ScoreScvar bean = null;
			for(int i = 0; i < list.size(); i++){
				bean = list.get(i);
				if(objectIdString.equals("")){
					objectIdString = bean.getVarId() + "";
				}else{
					objectIdString += "," + bean.getVarId();
				}
			}
		}
    	map.put("objectIds", objectIdString);
    	return map;
    }
	
	@RequestMapping(value = "getScvarIdDS", method = RequestMethod.POST)
	@ResponseBody
	public String getScvarIdDS(String scid) throws Exception{
		String strJson = "[";
		short type = 1;
		List<ScoreScvar> list = scvarService.selectByPage(scid, type);
		if(list != null && list.size() > 0){
			ScoreScvar bean = null;
			for(int i = 0;i <list.size();i++){
				bean = list.get(i);
				if(bean != null){
					if(i>0){
    					strJson = strJson + ", ";
					}
    				
    				strJson = strJson + "{parameterName:'" + bean.getCname() + "', parameterValue:'" + bean.getScvarId() + "'}";
				}
			}
		}
		strJson = strJson + "]";
		return strJson;
	}
	
	@RequestMapping(value = "getScvarIdDS2", method = RequestMethod.POST)
	@ResponseBody
	public String getScvarIdDS2(String scid) throws Exception{
		String strJson = "[";
		short type = 2;
		List<ScoreScvar> list = scvarService.selectByPage(scid, type);
		if(list != null && list.size() > 0){
			ScoreScvar bean = null;
			for(int i = 0;i <list.size();i++){
				bean = list.get(i);
				if(bean != null){
					if(i>0){
    					strJson = strJson + ", ";
					}
    				
    				strJson = strJson + "{parameterName:'" + bean.getCname() + "', parameterValue:'" + bean.getScvarId() + "'}";
				}
			}
		}
		strJson = strJson + "]";
		return strJson;
	}
	
}
