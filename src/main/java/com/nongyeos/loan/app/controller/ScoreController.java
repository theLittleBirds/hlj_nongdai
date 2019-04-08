package com.nongyeos.loan.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.admin.entity.SysPara;
import com.nongyeos.loan.admin.entity.SysParaGroup;
import com.nongyeos.loan.admin.entity.SysPersonRole;
import com.nongyeos.loan.admin.service.IParaGroupService;
import com.nongyeos.loan.admin.service.IParaService;
import com.nongyeos.loan.admin.service.IPersonService;
import com.nongyeos.loan.admin.service.IRoleService;
import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.app.entity.ScoreScore;
import com.nongyeos.loan.app.entity.ScoreScoreright;
import com.nongyeos.loan.app.entity.ScoreScvar;
import com.nongyeos.loan.app.service.IScoreService;
import com.nongyeos.loan.app.service.IScvarService;
import com.nongyeos.loan.base.util.Constants;

@Controller
@RequestMapping("/score")
public class ScoreController {

	@Resource
	private IScoreService scoreService;
	@Resource
	private ISysSenoService sysSenoService;
	@Resource
	private IRoleService roleService;
	@Resource
	private IPersonService personService;
	@Resource  
    private IParaGroupService paraGroupService;
	@Resource  
    private IParaService paraService; 
	@Resource
	private IScvarService scvarService;

	@RequestMapping(value = "selectScore", method = RequestMethod.POST)
	@ResponseBody
	public String scoreList(HttpSession session) {
		String strJson = "[";
		String name = "SCORE_CATEGORY";
		try {
			String personId = (String) session.getAttribute(Constants.SESSION_PERSONCD);
			List<SysPersonRole> proleList = personService.getRoles(personId);
			List<String> roleIdList  = new ArrayList<>();
			HashSet<String> idSet = new HashSet<>();
			for(SysPersonRole prole : proleList){
				roleIdList.add(prole.getRoleId());
			}
			List<ScoreScoreright> scorerightList = scoreService.selectByRoleId(roleIdList);
			for (ScoreScoreright scoreright : scorerightList) {
				idSet.add(scoreright.getscoreId());
			}
			SysParaGroup pgroup = paraGroupService.selectByName(name);
			List<SysPara> list = paraService.getListByPId(pgroup.getPgroupId());
			int num = 1;
			int num2 = 0;
			SysPara bean = null;
			ScoreScore score = null;
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					bean = list.get(i);
					if (i > 0){
						strJson = strJson + ", ";
					}
					strJson = strJson + "{\"id\":" + num + ", \"pid\": 0 " 
							+ ", \"categoryId\":\"" + bean.getValue()
							+ "\", \"name\":\"" + bean.getDescr()
							+ "\"}";
					num2 = num;
					num++;
					List<ScoreScore> list2 = null;
					if (roleIdList.contains("R000000001")) {
						list2 = scoreService.selectByCategory(bean.getValue());
					}else{
						list2 = scoreService.selectByCategoryAndIDList(bean.getValue(), idSet);
					}
					if (list2 != null && list2.size() > 0) {
						strJson = strJson + ", ";
						for (int j = 0; j < list2.size(); j++) {
							score = list2.get(j);
							if (score != null) {
								if (j > 0){
									strJson = strJson + ", ";
								}
								strJson = strJson + "{\"id\":" + num + ", \"pid\":" + num2 
										+ ", \"name\":\"" + score.getCname()
										+ "\", \"scoreId\":\"" + score.getScoreId()
										+ "\", \"finsId\":\"" + score.getFinsId()
										+ "\", \"type\":\"" + score.getType()
										+ "\", \"category\":\"" + score.getCategory()
										+ "\", \"cname\":\"" + score.getCname() 
										+ "\", \"ename\":\"" + score.getEname() 
										+ "\", \"version\":\"" + score.getVersion()
										+ "\", \"status\":\"" + score.getStatus()
										+ "\", \"releaseDate\":\"" + score.getReleaseDate()
										+ "\", \"effectiveDate\":\"" + score.getEffectiveDate()
										+ "\", \"expiredDate\":\"" + score.getExpiredDate()
										+ "\", \"memo\":\"" + score.getMemo()
										+ "\", \"seqno\":\"" + score.getSeqno()
										+ "\", \"isDelete\":\"" + score.getIsDelete()
										+ "\"}";
							}
							num++;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		strJson = strJson + "]";
		return (strJson);

	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(ScoreScore score) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (score != null) {
				if (score.getScoreId().equals("")) {
					score.setScoreId(sysSenoService.getNextString(Constants.TABLE_NAME_SCORE, 10, "SC", 1));
					scoreService.add(score);
					map.put("msg", "添加成功");
				} else {
					scoreService.update(score);
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
				String[] scoreIds = currIds.split(",");
				for (String scoreId : scoreIds) {
					scoreService.deleteById(scoreId);
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
	
	@RequestMapping(value = "getScoreById", method = RequestMethod.POST)
	@ResponseBody
	public ScoreScore getScoreById(String scoreId){
		try{
			ScoreScore score = scoreService.getScoreById(scoreId);
		    return score;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "addRoleAndScore", method = RequestMethod.POST)
    @ResponseBody
	public Map<String,Object> addRoleAndScore(String roleIds,String scoreid) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(scoreid != null && !scoreid.equals("")){
				scoreService.delByScoreId(scoreid);
				if(roleIds != null && !roleIds.equals("")){
					ScoreScoreright scoreRight = new ScoreScoreright();
					String[] roleIds2 = roleIds.split(",");
					for(String roleId : roleIds2){
						scoreRight.setscoreId(scoreid);
						scoreRight.setObjectType((short)1);
						scoreRight.setControlType((short)1);
						scoreRight.setObjectId(roleId);
						roleService.saveByScoreIdAndRoleId(scoreRight);
					}
				}
				map.put("msg", "保存成功");
			}else{
				map.put("msg", "保存失败");
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return map;
		}
	}
	
	@RequestMapping(value = "getScoreRightByScoreId", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getScoreRightByScoreId(String scoreid) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	String objectIdString = "";
    	try {
			List<ScoreScoreright> list = scoreService.getScoreRightByScoreId(scoreid);
			if(list != null && list.size() > 0){
				ScoreScoreright beanScoreRight = null;
				for(int i = 0; i < list.size(); i++){
					beanScoreRight = list.get(i);
					if(objectIdString.equals("")){
						objectIdString = beanScoreRight.getObjectId();
					}else{
						objectIdString += "," + beanScoreRight.getObjectId();
					}
				}
			}
			map.put("objectIds", objectIdString);
			map.put("msg", "成功");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return map;
		}
    }
	
	@RequestMapping(value = "appScoreDS", method = RequestMethod.POST)
	@ResponseBody
	public String appScoreDS()throws Exception{
		String strJson = "[";
		List<ScoreScore> list = scoreService.selectAll();
		if(list != null && list.size() > 0){
			ScoreScore scoreScore = null;
			for(int i = 0;i < list.size();i++){
				scoreScore = list.get(i);
				if(scoreScore != null){
					if(i>0)
    					strJson = strJson + ", ";
    				
    				strJson = strJson + "{parameterName:'" + scoreScore.getCname() + "', parameterValue:'" + scoreScore.getScoreId() + "'}";
				}
			}
		}
		strJson = strJson + "]";
		return strJson;
	}
	
	@RequestMapping(value = "getScvarDS", method = RequestMethod.POST)
	@ResponseBody
	public String getScvarDS(String scoreId)throws Exception{
			String strJson = "[";
			List<ScoreScvar> list=scvarService.selectAll(scoreId);
			ScoreScvar scvar=null;
			if(list!=null && list.size()>0){
				for(int i = 0;i < list.size();i++){
					scvar = list.get(i);
					if(scvar != null){
						if(i>0)
							strJson = strJson + ", ";
						
						strJson = strJson + "{parameterName:'" + scvar.getCname() + "', parameterValue:'" + scvar.getScvarId() + "'}";
					}
				}
			}
			strJson = strJson + "]";
			return strJson;
		}
}
