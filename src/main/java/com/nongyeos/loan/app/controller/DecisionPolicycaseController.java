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
import com.nongyeos.loan.app.entity.DecisionCasebase;
import com.nongyeos.loan.app.entity.DecisionPolicycase;
import com.nongyeos.loan.app.service.IDecisionPolicybaseService;
import com.nongyeos.loan.app.service.IDecisionPolicycaseService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/dePoCase")
public class DecisionPolicycaseController {
	
	@Resource
	private IDecisionPolicycaseService dePoliCaseService;
	@Resource
	private IParaGroupService paraGroupService;
	@Resource
	private IParaService paraService;
	
	@Resource
	private ISysSenoService sysSenoService;
	
	@RequestMapping(value = "deciCase", method = RequestMethod.POST)
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
							+ "\", \"decisionCategoryId\":\"" + beanPara.getParaId()
							+ "\"}";
					int num2 = num;
					num++;
					List<DecisionPolicycase> list2 = dePoliCaseService.getlistByAppId(appId, beanPara.getValue());
					if (list2 != null && list2.size() > 0) {
						strJson = strJson + ", ";
						DecisionPolicycase beanCase = null;
						for (int j = 0; j < list2.size(); j++) {
							beanCase = list2.get(j);
							if (beanCase != null) {
								if (j > 0)
									strJson = strJson + ", ";

								strJson = strJson + "{\"id\":" + num
										+ ", \"pid\":" + num2 + ", \"name\":\""+ beanCase.getMiaoshu()
										+ "\", \"appId\":\""+ beanCase.getAppId()
										+ "\", \"caseId\":\""+ beanCase.getCaseId()
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
	public Map<String,Object> save(DecisionPolicycase Pocase){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if(Pocase != null){
				if(Pocase.getCaseId().equals("")){
					Pocase.setCaseId(sysSenoService.getNextString(
								Constants.TABLE_NAME_POLICYCASE, 10, "CS", 1));
					dePoliCaseService.addCase(Pocase);
					map.put("msg", "添加成功");
				}else{
					dePoliCaseService.updateCase(Pocase);
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
				String[] caseIds = currIds.split(",");
				for (String caseId : caseIds) {
					dePoliCaseService.delCaseAndBase(caseId, (short)1);
					dePoliCaseService.delCaseAndBase(caseId, (short)2);
					dePoliCaseService.delCase(caseId);
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
	
	@RequestMapping(value = "getCase", method = RequestMethod.POST)
	@ResponseBody
	public DecisionPolicycase seleteCase(String caseId){
		try{
			DecisionPolicycase beanCase = dePoliCaseService.getCase(caseId);
			return beanCase;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
    @RequestMapping("/caseList")
    @ResponseBody
    public PageBeanUtil<DecisionCasebase> usersPage(int currentPage,int pageSize,String caseId,int type) throws Exception{
    	
    	PageBeanUtil<DecisionCasebase> list = dePoliCaseService.selectByPage(currentPage, pageSize, caseId, type);
    	if(list != null)
		    return list;
    	else 
    		return null;
    }
    
    @RequestMapping(value="getCabaString", method = RequestMethod.POST)
    @ResponseBody
    public String getCabaString(String caseId,int type) throws Exception{
    	List<DecisionCasebase> list = dePoliCaseService.getCabaByType(caseId, type);
    	String objectString = "";
    	if(list != null && list.size() > 0){
			for(int i = 0;i < list.size();i++){
				objectString += list.get(i).getBaseId() +",";
			}
			if(!objectString.equals(""))
			objectString = objectString.substring(0,objectString.length()-1);
		}
		return objectString;
    }
    
    @RequestMapping(value="getDecisionByGroupNameAndAppId", method = RequestMethod.POST)
    @ResponseBody
    public String getDecisionByGroupNameAndAppId(String appId,String paraGroupName){
		String strJson = "[";
        List<DecisionPolicycase> list = null;
        try {
        	list=dePoliCaseService.getlistByAppId(appId,paraGroupName);
            if (list != null && list.size() > 0) {
                DecisionPolicycase beanDecisionPolicycase = null;
                for (int i = 0; i < list.size(); i++) {
                	beanDecisionPolicycase = list.get(i);
                    if (beanDecisionPolicycase != null) {
                        if (i > 0)
                            strJson = strJson + ", ";
                        	strJson = strJson + "{parameterName:'" + beanDecisionPolicycase.getMiaoshu() + "', parameterValue:'" + beanDecisionPolicycase.getCaseId() + "'}";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        strJson = strJson + "]";
        return (strJson);
	}
    
    @RequestMapping(value="saveCaseBase", method = RequestMethod.POST)
    @ResponseBody
    public Map<String ,Object> saveCase(DecisionCasebase caba){
    	Map<String, Object> map = new HashMap<String, Object>();
    	try{
    		if(caba != null){
    			String caseId = caba.getCaseId();
    			String baseIds = caba.getBaseId();
    			short type = caba.getType();
    			if(caseId != null && !caseId.equals("")){
    				dePoliCaseService.delCaseAndBase(caseId,type);
    				if(baseIds != null && !baseIds.equals("")){
    					DecisionCasebase beanCaba = new DecisionCasebase();
    					String[] baseIds2 = baseIds.split(",");
    					for(String baseId : baseIds2){
    						beanCaba.setCaseId(caseId);
    						beanCaba.setType(caba.getType());
    						beanCaba.setBaseId(baseId);
    						dePoliCaseService.addCaseAndBase(beanCaba);
    					}
    				}
    				map.put("msg", "保存成功");
    			}else{
    				map.put("msg", "保存失败");
    			}
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
		return map;
    }
    
    @RequestMapping(value="delCaba", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> delCaba(String currIds) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
		if(currIds != null && !currIds.equals("")){
			String[] cabaIds = currIds.split(",");
			for(String cabaId : cabaIds){
				int csbaseId = Integer.valueOf(cabaId);
				dePoliCaseService.delCaba(csbaseId);
			}
			map.put("msg", "删除成功");
		}else{
			map.put("msg", "删除失败");
		}
		return map;
    }

    @RequestMapping(value="caseIdDS", method = RequestMethod.POST)
    @ResponseBody
    public String getCaseIdDS(){
		String strJson = "[";
        List<DecisionPolicycase> list = null;
        try {
        	list=dePoliCaseService.getList();
            if (list != null && list.size() > 0) {
                DecisionPolicycase bean = null;
                for (int i = 0; i < list.size(); i++) {
                	bean = list.get(i);
                    if (bean != null) {
                        if (i > 0)
                            strJson = strJson + ", ";
                        	strJson = strJson + "{parameterName:'" + bean.getMiaoshu() + "', parameterValue:'" + bean.getCaseId() + "'}";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        strJson = strJson + "]";
        return (strJson);
	}
    
    @RequestMapping(value="caseIdByAppIdDS", method = RequestMethod.POST)
    @ResponseBody
    public String getCaseIdByAppIdDS(String appId){
		String strJson = "[";
        List<DecisionPolicycase> list = null;
        try {
        	list=dePoliCaseService.getListByAppId2(appId);
            if (list != null && list.size() > 0) {
                DecisionPolicycase bean = null;
                for (int i = 0; i < list.size(); i++) {
                	bean = list.get(i);
                    if (bean != null) {
                        if (i > 0)
                            strJson = strJson + ", ";
                        	strJson = strJson + "{parameterName:'" + bean.getMiaoshu() + "', parameterValue:'" + bean.getCaseId() + "'}";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        strJson = strJson + "]";
        return (strJson);
	}
}
