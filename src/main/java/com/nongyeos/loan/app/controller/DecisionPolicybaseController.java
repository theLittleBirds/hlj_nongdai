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
import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.app.entity.DecisionPolicybase;
import com.nongyeos.loan.app.service.IDecisionPolicybaseService;
import com.nongyeos.loan.base.util.Constants;

@Controller
@RequestMapping("/dePoBase")
public class DecisionPolicybaseController {
	
	@Resource
	private IDecisionPolicybaseService dePoliBaseService;
	
	@Resource
	private IParaGroupService paraGroupService;
	
	@Resource
	private IParaService paraService;
	
	@Resource
	private ISysSenoService sysSenoService;
	
	@RequestMapping(value = "deciBase", method = RequestMethod.POST)
	@ResponseBody
	public String applicationList(String appId) {
		String strJson = "[";
		try {
			int num = 1;
			SysParaGroup beanParaGroup= paraGroupService.selectByName("DECISION_CATEGORY");
			List<SysPara> paraList = null;
			if(beanParaGroup != null && !beanParaGroup.getPgroupId().equals("")){
				paraList = paraService.getListByPId(beanParaGroup.getPgroupId());
			}
			SysPara beanPara = null;
			if (paraList != null && paraList.size() > 0) {
				for (int i = 0; i < paraList.size(); i++) {
					beanPara = paraList.get(i);
					if (i > 0)
						strJson = strJson + ", ";

					strJson = strJson + "{\"id\":" + num + ", \"pid\":" + "0"
							+ ", \"name\":\"" + beanPara.getDescr()
							+ "\", \"paraId\":\"" + beanPara.getValue()
//							+ "\", \"name\":\"" + beanBusFins.getCname()
							+ "\"}";
					int num2 = num;
					num++;
					List<DecisionPolicybase> list2 = dePoliBaseService.getlistByAppId(appId, beanPara.getValue());
					if (list2 != null && list2.size() > 0) {
						strJson = strJson + ", ";
						DecisionPolicybase beanBase = null;
						for (int j = 0; j < list2.size(); j++) {
							beanBase = list2.get(j);
							if (beanBase != null) {
								if (j > 0)
									strJson = strJson + ", ";

								strJson = strJson + "{\"id\":" + num
										+ ", \"pid\":" + num2 + ", \"name\":\""
										+ beanBase.getMiaoshu()
										+ "\", \"appId\":\""
										+ beanBase.getAppId()
										+ "\", \"baseId\":\""
										+ beanBase.getBaseId()
										+ "\", \"itemId\":\""
										+ beanBase.getItemId()
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
	public Map<String,Object> save(DecisionPolicybase base){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(base != null){
				if(base.getBaseId().equals("")){
					base.setBaseId(sysSenoService.getNextString(
								Constants.TABLE_NAME_POLICYBASE, 10, "BS", 1));
					dePoliBaseService.addBase(base);
					map.put("msg", "添加成功");
				}else{
					dePoliBaseService.updateBase(base);
					map.put("msg", "修改成功");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("msg", "操作失败！");
		}
		return map;
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String ,Object> delete(String currIds){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (currIds != null && !currIds.equals("")) {
				String[] baseIds = currIds.split(",");
				for (String baseId : baseIds) {
					dePoliBaseService.delBase(baseId);
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
	
	@RequestMapping(value = "getBase", method = RequestMethod.POST)
	@ResponseBody
	public DecisionPolicybase seleteBase(String baseId){
		try{
			DecisionPolicybase beanBase = dePoliBaseService.getBase(baseId);
			return beanBase;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "getBaseDS", method = RequestMethod.POST)
	@ResponseBody
	public String getBaseDS(String appId) throws Exception{
		String strJson = "[";
		List<DecisionPolicybase> list = dePoliBaseService.getAllByAppId(appId);
		if(list != null && list.size() > 0){
			DecisionPolicybase beanBase = null;
			for(int i = 0;i < list.size();i++){
				beanBase = list.get(i);
				if(beanBase != null){
					if(i>0)
    					strJson = strJson + ", ";
    				
    				strJson = strJson + "{parameterName:'" + beanBase.getMiaoshu() + "', parameterValue:'" + beanBase.getBaseId() + "'}";
				}
			}
		}
		strJson = strJson + "]";
		return strJson;
	}

}
