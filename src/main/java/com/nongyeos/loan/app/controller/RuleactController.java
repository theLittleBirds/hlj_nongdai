package com.nongyeos.loan.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.admin.entity.SysPara;
import com.nongyeos.loan.admin.entity.SysParaGroup;
import com.nongyeos.loan.admin.service.IParaGroupService;
import com.nongyeos.loan.admin.service.IParaService;
import com.nongyeos.loan.app.entity.DecisionAction;
import com.nongyeos.loan.app.entity.DecisionRuleact;
import com.nongyeos.loan.app.service.IDecisionActionService;
import com.nongyeos.loan.app.service.IRuleactService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/ruleact")
public class RuleactController {

	@Resource
	private IRuleactService actService;
	@Resource
	private IDecisionActionService actionService;
	@Resource
	private IParaGroupService paraGroupService;
	@Resource
	private IParaService paraService;
	
	@RequestMapping(value = "listLeft", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<DecisionRuleact> listLeft(String code){
		PageBeanUtil<DecisionRuleact> pi = new PageBeanUtil<DecisionRuleact>();
		int type = 1;
		List<DecisionRuleact> list = actService.getList(code,type);
		pi.setItems(list);
		pi.setTotalNum(list.size());
		return pi;
	}

	@RequestMapping(value = "listRight", method = RequestMethod.POST)
	@ResponseBody
	public PageBeanUtil<DecisionRuleact> listRight(String code){
		PageBeanUtil<DecisionRuleact> pi = new PageBeanUtil<DecisionRuleact>();
		int type = 0;
		List<DecisionRuleact> list = actService.getList(code,type);
		pi.setItems(list);
		pi.setTotalNum(list.size());
		return pi;
	}
	
	@RequestMapping(value = "showLeft", method = RequestMethod.POST)
	@ResponseBody
	public String showLeft(String appId){
		String strJson = "[";
		int num = 1;
		int num2 = 0;
		try {
			SysParaGroup pGroup = paraGroupService.selectByName("ACTION_CATEGORY");
			List<SysPara> list1 = paraService.selectListByPGroupId(pGroup.getPgroupId());
			SysPara paraBean = null;
			if (list1 != null && list1.size() > 0) {
				for (int i = 0; i < list1.size(); i++) {
					paraBean = list1.get(i);
					if (paraBean != null) {
						if (i > 0)
							strJson = strJson + ", ";
						
						strJson = strJson + "{\"id\":" + num + ", \"pid\":" + 0
								+ ", \"name\":\"" + paraBean.getDescr()
								+ "\"}";
					}
					num2 = num;
					num++;
					Short value = (short) Integer.parseInt(paraBean.getValue());
					List<DecisionAction> list2 = actionService.selectByAppIdAndCategory(appId, value);
					DecisionAction beanAction = null;
					if (list2 != null && list2.size() > 0) {
						strJson = strJson + ", ";
						for (int j = 0; j < list2.size(); j++) {
							beanAction = list2.get(j);
							if (beanAction != null) {
								if (j > 0)
									strJson = strJson + ", ";

								strJson = strJson + "{\"id\":" + num
										+ ", \"pid\":" + num2
										+ ", \"name\":\"" + beanAction.getName()
										+ "\", \"actionId\":\"" + beanAction.getActionId()
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
		strJson =strJson +"]";
		return(strJson);
	}
	
	@RequestMapping(value = "showRight", method = RequestMethod.POST)
	@ResponseBody
	public String showRight(String appId){
		String strJson = "[";
		int num = 1;
		int num2 = 0;
		try {
			SysParaGroup pGroup = paraGroupService.selectByName("ACTION_CATEGORY");
			List<SysPara> list1 = paraService.selectListByPGroupId(pGroup.getPgroupId());
			SysPara paraBean = null;
			if (list1 != null && list1.size() > 0) {
				for (int i = 0; i < list1.size(); i++) {
					paraBean = list1.get(i);
					if (paraBean != null) {
						if (i > 0)
							strJson = strJson + ", ";
						
						strJson = strJson + "{\"id\":" + num + ", \"pid\":" + 0
								+ ", \"name\":\"" + paraBean.getDescr()
								+ "\"}";
					}
					num2 = num;
					num++;
					Short value = (short) Integer.parseInt(paraBean.getValue());
					List<DecisionAction> list2 = actionService.selectByAppIdAndCategory(appId, value);
					DecisionAction beanAction = null;
					if (list2 != null && list2.size() > 0) {
						strJson = strJson + ", ";
						for (int j = 0; j < list2.size(); j++) {
							beanAction = list2.get(j);
							if (beanAction != null) {
								if (j > 0)
									strJson = strJson + ", ";
								
								strJson = strJson + "{\"id\":" + num
										+ ", \"pid\":" + num2
										+ ", \"name\":\"" + beanAction.getName()
										+ "\", \"actionId\":\"" + beanAction.getActionId()
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
		strJson =strJson +"]";
		return(strJson);
	}
	
	@RequestMapping(value = "actionToLeft", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> actionToLeft(String actionIds,String ruleId){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String[] ids = actionIds.split(",");
			DecisionRuleact act = new DecisionRuleact();
			Short type = 1;
			actService.deleteByRuleId(ruleId,type);
			for (int i = 0; i < ids.length; i++) {
				act.setActionId(ids[i]);
				act.setRuleId(ruleId);
				act.setType(type);
				act.setSeqno(i + 1);
				actService.add(act);
			}
			map.put("msg", "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping(value = "actionToRight", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> actionToRight(String unactionIds, String actionIds,String ruleId){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String[] ids = actionIds.split(",");
			DecisionRuleact act = new DecisionRuleact();
			Short type = 0;
			actService.deleteByRuleId(ruleId,type);
			for (int i = 0; i < ids.length; i++) {
				act.setActionId(ids[i]);
				act.setRuleId(ruleId);
				act.setType(type);
				act.setSeqno(i + 1);
				actService.add(act);
			}
			map.put("msg", "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/getLeftList")
    @ResponseBody
    public Map<String, Object> getLeftList(String ruleId) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	String objectIdString = "";
    	Short type = 1;
    	List<DecisionRuleact> list = actService.getLeftListByRuleId(ruleId,type);
    	if(list != null && list.size()>0){
    		DecisionRuleact bean = null;
			for(int i = 0; i < list.size(); i++){
				bean = list.get(i);
				if(objectIdString.equals("")){
					objectIdString = bean.getActionId() + "";
				}else{
					objectIdString += "," + bean.getActionId();
				}
			}
		}
    	map.put("objectIds", objectIdString);
    	return map;
    }
	
	@RequestMapping("/getRightList")
	@ResponseBody
	public Map<String, Object> getRightList(String ruleId) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String objectIdString = "";
		Short type = 0;
		List<DecisionRuleact> list = actService.getRightListByRuleId(ruleId,type);
		if(list != null && list.size()>0){
			DecisionRuleact bean = null;
			for(int i = 0; i < list.size(); i++){
				bean = list.get(i);
				if(objectIdString.equals("")){
					objectIdString = bean.getActionId() + "";
				}else{
					objectIdString += "," + bean.getActionId();
				}
			}
		}
		map.put("objectIds", objectIdString);
		return map;
	}
	
	@RequestMapping(value="deleteLeft", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteLeft(String currIds) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (currIds != null && !currIds.equals("")) {
                String[] ids = currIds.split(",");
                for (String id : ids) {
                	int Id = Integer.parseInt(id);
                	actService.deleteByPrimaryKey(Id);
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
	
	@RequestMapping(value="deleteRight", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteRight(String currIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (currIds != null && !currIds.equals("")) {
				String[] ids = currIds.split(",");
				for (String id : ids) {
					int Id = Integer.parseInt(id);
					actService.deleteByPrimaryKey(Id);
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
	
}
