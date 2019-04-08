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

import com.nongyeos.loan.admin.entity.SysActLog;
import com.nongyeos.loan.admin.entity.SysPara;
import com.nongyeos.loan.admin.entity.SysParaGroup;
import com.nongyeos.loan.admin.entity.SysPersonorg;
import com.nongyeos.loan.admin.service.IParaGroupService;
import com.nongyeos.loan.admin.service.IParaService;
import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.app.entity.AppApplication;
import com.nongyeos.loan.app.entity.BusFins;
import com.nongyeos.loan.app.entity.DecisionAction;
import com.nongyeos.loan.app.service.IApplicationService;
import com.nongyeos.loan.app.service.IBusFinsService;
import com.nongyeos.loan.app.service.IDecisionActionService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/action")
public class DecisonActionController {

	@Resource
	private IDecisionActionService actionService;
	@Resource
	private IBusFinsService busFinsService;
	@Resource
	private IApplicationService applicationService;
	@Resource
	private IParaGroupService paraGroupService;
	@Resource
	private IParaService paraService;
	@Resource
	private ISysSenoService sysSenoService;

	
	@RequestMapping(value="save",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> save(DecisionAction action) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (action != null) {
				if(action.getActionId()==null || action.getActionId().equals("")) {
					action.setActionId((sysSenoService.getNextString(Constants.TABLE_NAME_DECISIONACTION, 12, "ACT", 1)));
					actionService.addAppScore(action);
					map.put("msg", "添加成功");
				} else {
					actionService.updateAppScore(action);
					map.put("msg", "修改成功");
				}
			} else {
				map.put("msg", "操作失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping(value="delAction", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delAction(String currIds) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (currIds != null && !currIds.equals("")) {
                String[] actionIds = currIds.split(",");
                for (String actionId : actionIds) {
                	actionService.delAction(actionId);
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
	
	//四层表格树：全部数据
	@RequestMapping(value = "selectAction", method = RequestMethod.POST)
	@ResponseBody
	public String actionList1(HttpSession session){
		List<String> orgIdList = new ArrayList<>();
    	List<SysPersonorg> perorgList = (List<SysPersonorg>) session.getAttribute(Constants.SESSION_ORGLIST);
    	for (SysPersonorg perorg : perorgList) {
    		orgIdList.add(perorg.getOrgId());
    	}

	    String strJson = "[";
		try{
		int num = 1;
		List<BusFins> list = busFinsService.selectAll(orgIdList);
		BusFins beanBusFins = null;
		AppApplication beanAppApplication = null;
		SysPara beanPara = null;
		DecisionAction beanAction = null;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				beanBusFins = list.get(i);
				List<AppApplication> list2 = applicationService.selectByFinsCode(beanBusFins.getFinsId());
				if (list2 != null && list2.size() > 0) {
					if (i > 0){
						strJson = strJson + ", ";
					}
					strJson = strJson + "{\"id\":" + num + ", \"pid\":" + "0"
							+ ", \"finsCode\":\"" + beanBusFins.getFinsId()
							+ "\", \"orgCode\":\"" + beanBusFins.getOrgId()
							+ "\", \"name\":\"" + beanBusFins.getCname()
							+ "\"}";
					int num2 = num;
					num++;
					strJson = strJson + ", ";
					for (int j = 0; j < list2.size(); j++) {
						beanAppApplication = list2.get(j);
						if (beanAppApplication != null) {
							if (j > 0){
								strJson = strJson + ", ";
							}
							strJson = strJson + "{\"id\":" + num
									+ ", \"pid\":" + num2 + ", \"name\":\""
									+ beanAppApplication.getCname()
									+ "\", \"appId\":\""
									+ beanAppApplication.getAppId()
									+ "\", \"finsCode\":\""
									+ beanAppApplication.getFinsCode()
									+ "\", \"category\":\""
									+ beanAppApplication.getGroupId()
									+ "\"}";
						}
						int num3 = num;
						num++;
						SysParaGroup pGroup = paraGroupService.selectByName("ACTION_CATEGORY");
						List<SysPara> list3 = paraService.selectListByPGroupId(pGroup.getPgroupId());
						if (list3 != null && list3.size() > 0) {
							strJson = strJson + ", ";
							for (int k = 0; k < list3.size(); k++) {
								beanPara = list3.get(k);
								if (beanPara != null) {
									if (k > 0){
										strJson = strJson + ", ";
									}
									strJson = strJson + "{\"id\":" + num
											+ ", \"pid\":" + num3
											+ ", \"name\":\""
											+ beanPara.getDescr()
											+ "\", \"Id\":\""
											+ beanPara.getParaId()
											+ "\", \"value\":\""
											+ beanPara.getValue()
											+ "\"}";
								}
								num++;
							}
						}
					}
				}
			}
		}
	} catch(
	Exception e){
		e.printStackTrace();
	}
	strJson =strJson +"]";
		return(strJson);
  }
	
	/**
	 * 金融机构--》产品--》执行措施类别--》执行措施
	 * 四层表格树：只取存在的数据
	 * @return
	 */
	@RequestMapping(value = "selectAction2", method = RequestMethod.POST)
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
		DecisionAction beanAction = null;
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
						
						SysParaGroup pGroup = paraGroupService.selectByName("ACTION_CATEGORY");
						List<SysPara> list3 = paraService.selectListByPGroupId(pGroup.getPgroupId());
						if (list3 != null && list3.size() > 0) {
							int num3 = num;
							for (int k = 0; k < list3.size(); k++) {
								num ++;
								String jsonPara = "";
								int selfNum3 = num;
								
								beanPara = list3.get(k);

								Short value = (short) Integer.parseInt(beanPara.getValue());
								List<DecisionAction> list4 = actionService.selectByAppIdAndCategory(beanAppApplication.getAppId(), value);
								
								if(list4 != null && list4.size() > 0)
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
	} catch(Exception e){
		e.printStackTrace();
	}
	strJson = "[" + strJson +"]";
	return(strJson);
}
	@RequestMapping("/actionDS")
    @ResponseBody
    public String getActionDS() {
        String strJson = "[";
        List<DecisionAction> list = actionService.selectAll();
        try {
            if (list != null && list.size() > 0) {
            	DecisionAction bean = null;
                for (int i = 0; i < list.size(); i++) {
                	bean = list.get(i);
                    if (bean != null) {
                        if (i > 0)
                            strJson = strJson + ", ";

                        strJson = strJson + "{parameterName:'" + bean.getName() + "', parameterValue:'" + bean.getActionId() + "'}";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        strJson = strJson + "]";
        return (strJson);
    }
	
	
	@RequestMapping("/actionList")
    @ResponseBody
	public PageBeanUtil<DecisionAction> actionPage(int currentPage,int pageSize,String value,String appId) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("value", value);
		map.put("appId", appId);
		PageBeanUtil<DecisionAction> list = actionService.actionPage(currentPage, pageSize, map);
    	if(list != null)
		    return list;
    	else 
    		return null;
	}
	
}
