package com.nongyeos.loan.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.admin.entity.SysPersonorg;
import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.app.entity.AppApplication;
import com.nongyeos.loan.app.entity.AppAppright;
import com.nongyeos.loan.app.entity.AppScore;
import com.nongyeos.loan.app.entity.ScoreScore;
import com.nongyeos.loan.app.service.IAppScoreService;
import com.nongyeos.loan.app.service.IApplicationService;
import com.nongyeos.loan.app.service.IApprightService;
import com.nongyeos.loan.app.service.IScoreService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/application")
public class ApplicationController {

	@Resource
	private IApplicationService applicationService;
	@Resource
	private ISysSenoService sysSenoService;
	@Resource
	private IApprightService apprightService;
	@Resource
	private IAppScoreService appScoreService;
	@Resource
	private IScoreService scoreService;
	
	@RequestMapping(value = "listApplication", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<AppApplication> providerPage(int limit,int offset,String groupId){
		try {
			PageBeanUtil<AppApplication> list = applicationService.selectByPage(limit, offset, groupId);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
	
	@RequestMapping(value = "selectApplication", method = RequestMethod.POST)
	@ResponseBody
	public String applicationList(HttpSession session) throws Exception {
		List<String> orgIdList = new ArrayList<>();
    	List<SysPersonorg> list = (List<SysPersonorg>) session.getAttribute(Constants.SESSION_ORGLIST);
    	for (SysPersonorg perorg : list) {
			orgIdList.add(perorg.getOrgId());
		}
		return applicationService.appTreeString(orgIdList);
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(AppApplication application) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (application != null) {
				if (application.getAppId().equals("")) {
					application.setAppId(sysSenoService.getNextString(
							Constants.TABLE_NAME_APPLICATION, 10, "A", 1));
					applicationService.addApplication(application);
					map.put("msg", "添加成功");
				} else {
					applicationService.updateApplication(application);
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
				String[] appIds = currIds.split(",");
				for (String appId : appIds) {
					applicationService.deleteById(appId);
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
	
	@RequestMapping(value = "getAppById", method = RequestMethod.POST)
	@ResponseBody
	public AppApplication getAppById(String appId){
		try{
			AppApplication application = applicationService.getApplicationById(appId);
		    return application;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "getAppright", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getApprightByEntityId(String entityId)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String objectIdString = "";
		List<AppAppright> list = apprightService.getApprightByEntityId(entityId);
		if(list != null && list.size() > 0){
			AppAppright beanAppAppright = null;
			for(int i = 0;i < list.size();i++){
				beanAppAppright = list.get(i);
				if(objectIdString.equals("")){
					objectIdString = beanAppAppright.getObjectId()+"";
				}else{
					objectIdString += "," + beanAppAppright.getObjectId();
				}
			}
		}
		map.put("objectIds", objectIdString);
    	return map;
	}
	
	@RequestMapping("/appIdDS")
	@ResponseBody
	public String getAppIdDS() throws Exception{
		String strJson = "[";
		List<AppApplication> list = applicationService.selectAllApplications();
		if(list != null && list.size() > 0){
			AppApplication bean = null;
			for(int i = 0;i <list.size();i++){
				bean = list.get(i);
				if(bean != null){
					if(i>0)
    					strJson = strJson + ", ";
    				
    				strJson = strJson + "{parameterName:'" + bean.getCname()+ "', parameterValue:'" + bean.getAppId() + "'}";
				}
			}
		}
		strJson = strJson + "]";
		return strJson;
	}

	@RequestMapping(value = "getApplicationByFins", method = RequestMethod.POST)
	@ResponseBody
	public String getApplicationByFins(String finsId) throws Exception{
		String strJson = "[";
		List<AppApplication> list = applicationService.selectByFinsCode(finsId);
		if(list != null && list.size() > 0){
			AppApplication beanPara = null;
			for(int i = 0;i <list.size();i++){
				beanPara = list.get(i);
				if(beanPara != null){
					if(i>0)
						strJson = strJson + ", ";

					strJson = strJson + "{parameterName:'" + beanPara.getCname()+ "', parameterValue:'" + beanPara.getAppId() + "'}";
				}
			}
		}
		strJson = strJson + "]";
		return strJson;
	}
	
	@RequestMapping("/getScoreDSByAppId")
	@ResponseBody
	public String getScoreDSByAppId(String appId){
		String strJson = "[";
		String scoreId = null;
		try {
			List<AppScore> list = appScoreService.selectByAppId(appId);
			if(list != null && list.size() > 0){
				for(int i = 0;i <list.size();i++){
					scoreId=list.get(i).getScoreId();
					ScoreScore score = scoreService.selectById(scoreId);
					AppScore beanAppScore = list.get(i);
					if(score != null){
						if(i>0)
							strJson = strJson + ", ";
						
						strJson = strJson + "{parameterName:'" + score.getCname()+ "', parameterValue:'" + beanAppScore.getAppscId() + "'}";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		strJson = strJson + "]";
		return strJson;
	}
}
