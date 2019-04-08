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

import com.nongyeos.loan.admin.entity.SysPara;
import com.nongyeos.loan.admin.entity.SysParaGroup;
import com.nongyeos.loan.admin.entity.SysPersonorg;
import com.nongyeos.loan.admin.service.IParaGroupService;
import com.nongyeos.loan.admin.service.IParaService;
import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.app.entity.AppApplication;
import com.nongyeos.loan.app.entity.BusFins;
import com.nongyeos.loan.app.entity.DecisionStrategy;
import com.nongyeos.loan.app.entity.DecisionStrule;
import com.nongyeos.loan.app.service.IApplicationService;
import com.nongyeos.loan.app.service.IBusFinsService;
import com.nongyeos.loan.app.service.IDecisionStrategyService;
import com.nongyeos.loan.app.service.IRuleService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/strategy")
public class DecisionStrategyController {
	
	@Resource
	private IBusFinsService busFinsService;
	@Resource
	private IApplicationService applicationService;
	@Resource
	private IParaGroupService paraGroupService;
	@Resource
	private IParaService paraService;
	@Resource
	private IDecisionStrategyService strategyService;
	@Resource
	private ISysSenoService sysSenoService;
	@Resource
	private IDecisionStrategyService decisionStrategyService;
	@Resource
	private IRuleService ruleService;

	
	
	@RequestMapping(value = "getTree", method = RequestMethod.POST)
	@ResponseBody
	public String actionList2(HttpSession session){
		List<String> orgIdList = new ArrayList<>();
    	List<SysPersonorg> perorgList = (List<SysPersonorg>) session.getAttribute(Constants.SESSION_ORGLIST);
    	for (SysPersonorg perorg : perorgList) {
    		orgIdList.add(perorg.getOrgId());
    	}
		
			String strJson = "";
				try{
				int num = 0;
				List<BusFins> list = busFinsService.selectAll(orgIdList);
				BusFins beanBusFins = null;
				AppApplication beanAppApplication = null;
				SysPara beanPara = null;
				DecisionStrategy beanAction = null;
				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						num ++;
						String jsonFins = "";
						int selfNum1 = num;
						
						beanBusFins = list.get(i);
						List<AppApplication> list2 = applicationService.selectByFinsCode(beanBusFins.getFinsId());
						if (list2 != null && list2.size() > 0) {
							int num2 = selfNum1;
							for (int j = 0; j < list2.size(); j++) {
								num ++;
								String jsonApp = "";
								int selfNum2 = num;
								
								beanAppApplication = list2.get(j);
								
								SysParaGroup pGroup = paraGroupService.selectByName("STRATEGY_CATEGORY");
								List<SysPara> list3 = paraService.selectListByPGroupId(pGroup.getPgroupId());
								if (list3 != null && list3.size() > 0) {
									int num3 = num;
									for (int k = 0; k < list3.size(); k++) {
										num ++;
										String jsonPara = "";
										int selfNum3 = num;
										
										beanPara = list3.get(k);
		
										Short value = (short) Integer.parseInt(beanPara.getValue());
										List<DecisionStrategy> list4 = strategyService.selectByAppIdAndCategory(beanAppApplication.getAppId(), value);
										if (list4 != null && list4.size() > 0) {
											int num4 = num;
											
											for (int m = 0; m < list4.size(); m++) {
												num ++;
												beanAction = list4.get(m);
												if (beanAction != null) {
													if(!jsonPara.equals(""))
													{
														jsonPara = jsonPara + ", ";
													}
													
													jsonPara = jsonPara + "{\"id\":" + num
															+ ", \"pid\":" + num4
															+ ", \"name\":\""
															+ beanAction.getName()
															+ "\", \"type\":\""
															+ beanAction.getType()
															+ "\", \"category\":\""
															+ beanAction.getCategory()
															+ "\", \"strategyId\":\""
															+ beanAction.getStrategyId()
															+ "\", \"appId\":\""
															+ beanAction.getAppId()
															+ "\", \"seqno\":\""
															+ beanAction.getSeqno()
															+ "\"}";
												}
											}
										}
										
										if(!jsonPara.equals(""))
										{
											if(!jsonApp.equals(""))
											{
												jsonApp = jsonApp + ", ";
											}
											
											jsonApp = jsonApp + "{\"id\":" + selfNum3
													+ ", \"pid\":" + num3
													+ ", \"name\":\""
													+ beanPara.getDescr()
													+ "\", \"Id\":\""
													+ beanPara.getParaId()
													+ "\", \"value\":\""
													+ beanPara.getValue()
													+ "\"}, " + jsonPara;
										}
									}
								}
								
								if(!jsonApp.equals(""))
								{
									if(!jsonFins.equals(""))
									{
										jsonFins = jsonFins + ", ";
									}
									
									jsonFins = jsonFins + "{\"id\":" + selfNum2
											+ ", \"pid\":" + num2 + ", \"name\":\""
											+ beanAppApplication.getCname()
											+ "\", \"appId\":\""
											+ beanAppApplication.getAppId()
											+ "\", \"finsCode\":\""
											+ beanAppApplication.getFinsCode()
											+ "\", \"category\":\""
											+ beanAppApplication.getGroupId()
											+ "\", \"cname\":\""
											+ beanAppApplication.getCname()
											+ "\"}, " + jsonApp;
								}
							}
						}
						
						if(!jsonFins.equals(""))
						{
							if(!strJson.equals(""))
							{
								strJson = strJson + ", ";
							}
							
							strJson = strJson + "{\"id\":" + selfNum1 + ", \"pid\":" + "0"
									+ ", \"finsCode\":\"" + beanBusFins.getFinsId()
									+ "\", \"orgCode\":\"" + beanBusFins.getOrgId()
									+ "\", \"name\":\"" + beanBusFins.getCname()
									+ "\", \"cname\":\"" + beanBusFins.getCname()
									+ "\", \"ename\":\"" + beanBusFins.getEname()
									+ "\"}, " + jsonFins;
						}
					}
				}
			} catch(
			Exception e){
				e.printStackTrace();
			}
			strJson = "[" + strJson +"]";
				return(strJson);
		}
	
	
		@RequestMapping(value = "save", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> save(DecisionStrategy strategy) {
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				if (strategy != null) {
					if (strategy.getStrategyId().equals("")) {
						strategy.setStrategyId(sysSenoService.getNextString(
								Constants.TABLE_NAME_STRATEGY, 10, "DS", 1));
						strategyService.addStrategy(strategy);
						map.put("msg", "添加成功");
					} else {
						strategyService.updateStrategy(strategy);
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
					String[] strategyIds = currIds.split(",");
					for (String strategyId : strategyIds) {
						strategyService.delStrategy(strategyId);
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
		
		@RequestMapping(value = "getStrategy", method = RequestMethod.POST)
		@ResponseBody
		public DecisionStrategy getStrategy(String strategyId){
			try{
				DecisionStrategy bean = strategyService.getStrategy(strategyId);
			    return bean;
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}
		
	    @RequestMapping(value = "strulelist", method = RequestMethod.POST)
	    @ResponseBody
	    public PageBeanUtil<DecisionStrule> strulelist(int offset,int limit,String strategyId) throws Exception{
	    	PageBeanUtil<DecisionStrule> list = strategyService.getStruleByStrategyId(offset, limit, strategyId);
	    	if(list != null)
			    return list;
	    	else 
	    		return null;
	    }
	    
	    @RequestMapping(value = "getRuleIds", method = RequestMethod.POST)
	    @ResponseBody
	    public String getRuleIds(String strategyId){
	    	String ruleIds = "";
	    	try{
	    		if(strategyId != null && !strategyId.equals("")){
	    			List<DecisionStrule> list = strategyService.getStrulelist(strategyId);
	    			if(list != null && list.size() > 0){
	    				for(int i = 0;i < list.size();i++){
	    					if(i == 0){
	    					  ruleIds += list.get(i).getRuleId();
	    					}else{
	    						ruleIds += "," + list.get(i).getRuleId();
	    					}
	    				}
	    			}
	    		}
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	return ruleIds;
	    }
	    
	    @RequestMapping(value = "saveStrule", method = RequestMethod.POST)
	    @ResponseBody
	    public Map<String ,Object> saveStrule(DecisionStrule strule){
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	try{
	    		if(strule != null){
	    			if(strule.getId() == null){
	    				strategyService.addStrule(strule);
		    			map.put("msg", "添加成功");
	    			}else{
	    				strategyService.updateStrule(strule);
	    				map.put("msg", "修改成功");
	    			}
	    		}
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	return map;
	    }
	    
	    @RequestMapping(value="delStrule", method = RequestMethod.POST)
	    @ResponseBody
	    public Map<String,Object> delStrule(String currIds) throws Exception{
	    	Map<String, Object> map = new HashMap<String, Object>();
			if(currIds != null && !currIds.equals("")){
				String[] struleIds = currIds.split(",");
				for(String struleId : struleIds){
					int id = Integer.valueOf(struleId);
					strategyService.delStruleById(id);
				}
				map.put("msg", "删除成功");
			}else{
				map.put("msg", "删除失败");
			}
			return map;
	    }

	    @RequestMapping(value = "getStrategyByAppId", method = RequestMethod.POST)
		@ResponseBody
		public String getStrategyByAppId(String appId){
				String strJson = "[";
				List<DecisionStrategy> startegyList = null;
				List<DecisionStrule> struleList = null;
				SysParaGroup pGroup = null;
				List<SysPara> sysParaList = null;
				try {
					pGroup = paraGroupService.selectByName("STRATEGY_CATEGORY");
					sysParaList = paraService.selectListByPGroupId(pGroup.getPgroupId());
					SysPara sysPara = null;
					int num=0;
					if(sysParaList != null && sysParaList.size() > 0){
						for(int i=0;i<sysParaList.size();i++){
							sysPara = sysParaList.get(i);
							if(num>0){
								strJson = strJson + ", ";
							}
							strJson = strJson + "{parameterName:'" + sysPara.getDescr()+ "', parameterValue:'" + sysPara.getValue() + "'}";
							Short value = (short) Integer.parseInt(sysPara.getValue());
							startegyList = decisionStrategyService.selectByAppIdAndCategory(appId, value);
							if(startegyList != null && startegyList.size() > 0){
								for (int j = 0; j < startegyList.size(); j++) {
									DecisionStrategy beanDecisionStrategy = startegyList.get(j);
									strJson = strJson + ", ";
									strJson = strJson + "{parameterName:'" + "　　"+beanDecisionStrategy.getName() + "', parameterValue:'" + beanDecisionStrategy.getStrategyId() + "'}";
								}
							}
							num++;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				strJson = strJson + "]";
				return strJson;
			}
}
