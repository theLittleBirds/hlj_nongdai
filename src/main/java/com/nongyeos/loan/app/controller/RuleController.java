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
import com.nongyeos.loan.app.entity.DecisionAction;
import com.nongyeos.loan.app.entity.DecisionRule;
import com.nongyeos.loan.app.service.IApplicationService;
import com.nongyeos.loan.app.service.IBusFinsService;
import com.nongyeos.loan.app.service.IRuleService;
import com.nongyeos.loan.app.service.IRuleactService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/rule")
public class RuleController {

	@Resource
	private IRuleService ruleService;
	@Resource
	private IApplicationService appService;
	@Resource
	private ISysSenoService sysSenoService;
	@Resource  
    private IParaGroupService paraGroupService;
	@Resource  
    private IParaService paraService; 
	@Resource
	private IBusFinsService busFinsService;
	@Resource
	private IRuleactService actService;

	@RequestMapping(value = "selectRule1", method = RequestMethod.POST)
	@ResponseBody
	public String scoreList() {
		String strJson = "[";
		String name = "RULE_CATEGORY";
		try {
			List<AppApplication> applist = appService.selectAllApplications();
			SysParaGroup pgroup = paraGroupService.selectByName(name);
			List<SysPara> paralist = paraService.getListByPId(pgroup.getPgroupId());
			int num = 1;
			int num1 = 0;
			int num2 = 0;
			AppApplication appbean = null;
			SysPara parabean = null;
			if (applist != null && applist.size() > 0) {
				for (int i = 0; i < applist.size(); i++) {
					appbean = applist.get(i);
					if (i > 0){
						strJson = strJson + ", ";
					}
					strJson = strJson + "{\"id\":" + num + ", \"pid\": 0 " 
							+ ", \"name\":\"" + appbean.getCname()
							+ "\", \"appId\":\"" + appbean.getAppId()
							+ "\"}";
					num1 = num;
					num++;
					if (paralist != null && paralist.size() > 0) {
						for (int j = 0; j < paralist.size(); j++) {
							parabean = paralist.get(j);
							if (parabean != null) {
								strJson = strJson + ", ";
								strJson = strJson + "{\"id\":" + num + ", \"pid\":" + num1 
										+ ", \"name\":\"" + parabean.getDescr()
										+ "\", \"value\":\"" + parabean.getValue()
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
	@RequestMapping(value = "selectRule", method = RequestMethod.POST)
	@ResponseBody
	public String selectRule(HttpSession session) {
		List<String> orgIdList = new ArrayList<>();
    	List<SysPersonorg> perorgList = (List<SysPersonorg>) session.getAttribute(Constants.SESSION_ORGLIST);
    	for (SysPersonorg perorg : perorgList) {
    		orgIdList.add(perorg.getOrgId());
    	}
		String strJson = "";
		try {
			int num = 0;
			List<BusFins> list = busFinsService.selectAll(orgIdList);
			BusFins finsBean = null;
			AppApplication appBean = null;
			SysPara paraBean = null;
			DecisionRule ruleBean = null;
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					num ++;
					String jsonFins = "";
					int selfNum1 = num;
					
					finsBean = list.get(i);
					List<AppApplication> list2 = appService.selectByFinsCode(finsBean.getFinsId());
					if (list2 != null && list2.size() > 0) {
						int num2 = selfNum1;
						for (int j = 0; j < list2.size(); j++) {
							num ++;
							String jsonApp = "";
							int selfNum2 = num;
							
							appBean = list2.get(j);
							SysParaGroup pgroup = paraGroupService.selectByName("RULE_CATEGORY");
							List<SysPara> list3 = paraService.getListByPId(pgroup.getPgroupId());
							if (list3 != null && list3.size() > 0) {
								int num3 = num;
								for (int k = 0; k < list3.size(); k++) {
									num ++;
									String jsonPara = "";
									int selfNum3 = num;
									
									paraBean = list3.get(k);
									Short value = (short) Integer.parseInt(paraBean.getValue());
									List<DecisionRule> list4 = ruleService.selectListByAppIdAndPara(appBean.getAppId(),paraBean.getValue());
									if(list4 != null && list4.size() > 0)
									{
										if(!jsonApp.equals(""))
										{
											jsonApp = jsonApp + ", ";
										}
										
										jsonApp = jsonApp + "{\"id\":" + selfNum3
												+ ", \"pid\":" + num3
												+ ", \"name\":\"" + paraBean.getDescr()
												+ "\", \"Id\":\"" + paraBean.getParaId()
												+ "\", \"value\":\"" + paraBean.getValue()
												+ "\"}";
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
										+ ", \"pid\":" + num2 
										+ ", \"name\":\"" + appBean.getCname()
										+ "\", \"appId\":\"" + appBean.getAppId()
										+ "\", \"finsCode\":\"" + appBean.getFinsCode()
										+ "\", \"category\":\"" + appBean.getGroupId()
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
								+ ", \"name\":\"" + finsBean.getCname()
								+ "\", \"finsCode\":\"" + finsBean.getFinsId()
								+ "\", \"orgCode\":\"" + finsBean.getOrgId()
								+ "\"}, " + jsonFins;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		strJson = "[" + strJson +"]";
		System.out.println(strJson);
		return (strJson);
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(DecisionRule rule) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (rule != null) {
				if (rule.getRuleId().equals("")) {
					rule.setRuleId(sysSenoService.getNextString(Constants.TABLE_NAME_RULE, 10, "RL", 1));
					ruleService.add(rule);
					map.put("msg", "添加成功");
				} else {
					ruleService.update(rule);
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
				String[] ruleIds = currIds.split(",");
				for (String ruleId : ruleIds) {
					actService.deleteByRuleId(ruleId, (short)1);
					actService.deleteByRuleId(ruleId, (short)2);
					ruleService.deleteById(ruleId);
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
	
	
	@RequestMapping(value = "getRuleDS", method = RequestMethod.POST)
	@ResponseBody
	public String getRuleDS(String appId)throws Exception{
		String strJson = "[";
		List<DecisionRule> list = ruleService.getRuleByAppId(appId);
		if(list != null && list.size() > 0){
			DecisionRule bean = null;
			for(int i = 0;i < list.size();i++){
				bean = list.get(i);
				if(bean != null){
					if(i>0)
    					strJson = strJson + ", ";
    				
    				strJson = strJson + "{parameterName:'" + bean.getName()+ "', parameterValue:'" + bean.getRuleId() + "'}";
				}
			}
		}
		strJson = strJson + "]";
		return strJson;
	}
	
	@RequestMapping("/ruleList")
    @ResponseBody
	public PageBeanUtil<DecisionRule> rulePage(int currentPage,int pageSize,String value,String appId) throws Exception{
		PageBeanUtil<DecisionRule> list = ruleService.rulePage(currentPage, pageSize, appId, value);
    	if(list != null)
		    return list;
    	else 
    		return null;
	}
	
	
}
