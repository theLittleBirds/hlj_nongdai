package com.nongyeos.loan.admin.controller.nj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusMemberOperate;
import com.nongyeos.loan.admin.entity.SysMenuRight;
import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.entity.SysPersonRole;
import com.nongyeos.loan.admin.service.IMemberOperateService;
import com.nongyeos.loan.admin.service.IMenuService;
import com.nongyeos.loan.admin.service.IOrgService;
import com.nongyeos.loan.admin.service.IPersonService;
import com.nongyeos.loan.base.util.AppNull;
import com.nongyeos.loan.base.util.CompletingDegree;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/nj/memberOperate")
public class UMemberOperateController {
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(CApplicationController.class);
	
	
	@Autowired
	private IMemberOperateService memberOperateService;
	
	@Autowired
	private IPersonService personService;
	
	@Autowired
	private IOrgService orgService;
	
	@Resource  
    private IMenuService menuService;  
	
	@RequestMapping("/checkMORight")
	@ResponseBody
	public JSONObject checkMORight(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String userId = (String) request.getAttribute("loginId");
		try {
			SysPerson person = personService.selectByUserIdAndIsdefault(userId);
			List<SysPersonRole> list = personService.getRoles(person.getPersonId());
			List<String> roleIdList = new LinkedList<String>();
			List<String> menuIdList = new LinkedList<String>();
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", e.getMessage());
			response.setStatus(400);
		}
    	
    	return retJson;
	}
	
	
	@RequestMapping("/basicSave")
	@ResponseBody
	public JSONObject basicSave(HttpServletRequest request,HttpServletResponse response) {
		JSONObject retJson = new JSONObject();
		String userId = (String) request.getAttribute("loginId");
		try {
			SysPerson person = personService.selectByUserIdAndIsdefault(userId);
			BusMemberOperate operate = new BusMemberOperate();
			String id = request.getParameter("id");
			if(StringUtils.isNotEmpty(id)){
				operate = memberOperateService.selectByPrimaryKey(id);
			}
			String name = request.getParameter("name");
			if(StringUtils.isEmpty(name)){
				retJson.put("message", "请填写姓名！");
				retJson.put("errno", 3002);
				response.setStatus(400);
				return retJson;
			}
			String age = request.getParameter("age");
			String phone = request.getParameter("phone");
			if(StringUtils.isNoneEmpty(phone)){
				String regex ="^1\\d{10}$";
				phone = phone.trim().replaceAll("\\s+","");
				if(!phone.matches(regex)){
					retJson.put("message", "请填写正确的手机号！");
					retJson.put("errno", 3003);
					response.setStatus(400);
					return retJson;
				}
			}
			String address = request.getParameter("address");
			String operateAddress = request.getParameter("operateAddress");
			String livestock = request.getParameter("livestock");
			String bigLandCrop = request.getParameter("bigLandCrop");
			String cashCrop = request.getParameter("cashCrop");
			String nongSell = request.getParameter("nongSell");
			String other = request.getParameter("other");
			List<String> asList = new ArrayList<String>();
			if(!StringUtils.isEmpty(bigLandCrop)&&bigLandCrop.equals("1"))
				asList.add("1");
			if(!StringUtils.isEmpty(cashCrop)&&cashCrop.equals("1"))
				asList.add("2");
			if(!StringUtils.isEmpty(livestock)&&livestock.equals("1"))
				asList.add("3");
			if(!StringUtils.isEmpty(nongSell)&&nongSell.equals("1"))
				asList.add("4");
			if(!StringUtils.isEmpty(other)&&other.equals("1"))
				asList.add("5");
			Collections.sort(asList);
			String operateType = StringUtils.join(asList, ",");
			
			operate.setName(AppNull.s2SNull(name));
			operate.setAge(AppNull.s2INull(age));
			operate.setPhone(AppNull.s2SNull(phone));
			operate.setAddress(AppNull.s2SNull(address));
			operate.setOperateAddress(AppNull.s2SNull(operateAddress));
			operate.setOperateType(AppNull.s2SNull(operateType));
			operate.setUpdDate(DateUtils.getNowDate());
			operate.setUpdOperId(userId);
			operate.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			operate = CompletingDegree.caculate(operate);
			if(StringUtils.isEmpty(id)){
				operate.setOperUserId(person.getPersonId());
				SysOrg org = orgService.selectByOrgId(person.getOrgId());
				operate.setOrgId(org.getOrgId());
				BusMemberOperate operate1= memberOperateService.selectHistory(operate);
				if(operate1!=null){
					retJson.put("message", "已存在的客户档案，请不要重复提交");
					retJson.put("errno", 3027);
					response.setStatus(400);
					return retJson;
				}
				operate.setId(UUIDUtil.getUUID());
				operate.setCreDate(DateUtils.getNowDate());
				operate.setCreOperId(userId);
				memberOperateService.insert(operate);
				retJson.put("id", operate.getId());
			}else{
				operate.setId(id);
				memberOperateService.updateByPrimaryKey(operate);
			}
			retJson.put("message", "保存成功");
			retJson.put("errno", 0);
			response.setStatus(200);
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", e.getMessage());
			retJson.put("errno", 3027);
			response.setStatus(400);
		}
		return retJson;
	}
	
	
	@RequestMapping("/basicInfo")
	@ResponseBody
	public JSONObject basicInfo(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		try {
			JSONObject obj = new JSONObject();
			String id = request.getParameter("id");
			BusMemberOperate memberOperate = null;
			if(!StringUtils.isEmpty(id)){
				memberOperate =memberOperateService.selectByPrimaryKey(id);
			}
			if(memberOperate==null){
				memberOperate=new BusMemberOperate();
			}
			obj.put("livestock", "2");
			obj.put("bigLandCrop", "2");
			obj.put("cashCrop", "2");
			obj.put("nongSell", "2");
			obj.put("other", "2");
			if(StringUtils.isNotEmpty(memberOperate.getOperateType())){
				String[] split = memberOperate.getOperateType().split(",");
				for (int i = 0; i < split.length; i++) {
					if(split[i].equals("1")){
						obj.put("livestock", "1");
					}else if(split[i].equals("2")){
						obj.put("bigLandCrop", "1");
					}else if(split[i].equals("3")){
						obj.put("cashCrop", "1");
					}else if(split[i].equals("4")){
						obj.put("nongSell", "1");
					}else if(split[i].equals("5")){
						obj.put("other", "1");
					}
				}
				
			}
			obj.put("id", AppNull.o2SNotNull(memberOperate.getId()));
			obj.put("name", AppNull.o2SNotNull(memberOperate.getName()));
			obj.put("age", AppNull.o2SNotNull(memberOperate.getAge()));
			obj.put("phone", AppNull.o2SNotNull(memberOperate.getPhone()));
			obj.put("address", AppNull.o2SNotNull(memberOperate.getAddress()));
			obj.put("operateAddress", AppNull.o2SNotNull(memberOperate.getOperateAddress()));
			retJson.put("data", obj);
			retJson.put("errno", 0);
			response.setStatus(200);
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", e.getMessage());
			retJson.put("errno", 3029);
			response.setStatus(400);
		}
		return retJson;
	}
	
	@RequestMapping("/cropSave")
	@ResponseBody
	public JSONObject cropSave(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String userId = (String) request.getAttribute("loginId");
		try {
			SysPerson person = personService.selectByUserIdAndIsdefault(userId);
			BusMemberOperate operate = new BusMemberOperate();
			String id = request.getParameter("id");
			if(StringUtils.isNotEmpty(id)){
				operate = memberOperateService.selectByPrimaryKey(id);
			}
			String cropType = request.getParameter("cropType");
			String cropYears = request.getParameter("cropYears");
			String cropScale = request.getParameter("cropScale");
			String cropExpectedValue = request.getParameter("cropExpectedValue");
			String cropPeriod = request.getParameter("cropPeriod");
			String cropInvestment = request.getParameter("cropInvestment");
			String cropMainNeeds = request.getParameter("cropMainNeeds");
			operate.setCropType(AppNull.s2SNull(cropType));
			operate.setCropYears(AppNull.s2SNull(cropYears));
			operate.setCropScale(AppNull.s2SNull(cropScale));
			operate.setCropExpectedValue(AppNull.s2SNull(cropExpectedValue));
			operate.setCropPeriod(AppNull.s2SNull(cropPeriod));
			operate.setCropInvestment(AppNull.s2SNull(cropInvestment));
			operate.setCropMainNeeds(AppNull.s2SNull(cropMainNeeds));
			operate.setUpdDate(DateUtils.getNowDate());
			operate.setUpdOperId(userId);
			operate.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			operate = CompletingDegree.caculate(operate);
			if(StringUtils.isEmpty(id)){
				operate.setOperUserId(person.getPersonId());
				SysOrg org = orgService.selectByOrgId(person.getOrgId());
				operate.setOrgId(org.getOrgId());
				operate.setId(UUIDUtil.getUUID());
				operate.setCreDate(DateUtils.getNowDate());
				operate.setCreOperId(userId);
				memberOperateService.insert(operate);
				retJson.put("id", operate.getId());
			}else{
				operate.setId(id);
				memberOperateService.updateByPrimaryKey(operate);
			}
			retJson.put("message", "保存成功");
			retJson.put("errno", 0);
			response.setStatus(200);
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", e.getMessage());
			retJson.put("errno", 3027);
			response.setStatus(400);
		}
		return retJson;
	}
	
	@RequestMapping("/cropInfo")
	@ResponseBody
	public JSONObject cropInfo(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		try {
			JSONObject obj = new JSONObject();
			String id = request.getParameter("id");
			BusMemberOperate memberOperate = null;
			if(!StringUtils.isEmpty(id)){
				memberOperate =memberOperateService.selectByPrimaryKey(id);
			}
			if(memberOperate==null){
				memberOperate=new BusMemberOperate();
			}
			obj.put("cropType", AppNull.o2SNotNull(memberOperate.getCropType()));
			obj.put("id", AppNull.o2SNotNull(memberOperate.getId()));
			obj.put("cropYears", AppNull.o2SNotNull(memberOperate.getCropYears()));
			obj.put("cropScale", AppNull.o2SNotNull(memberOperate.getCropScale()));
			obj.put("cropExpectedValue", AppNull.o2SNotNull(memberOperate.getCropExpectedValue()));
			obj.put("cropPeriod", AppNull.o2SNotNull(memberOperate.getCropPeriod()));
			obj.put("cropInvestment", AppNull.o2SNotNull(memberOperate.getCropInvestment()));
			obj.put("cropMainNeeds", AppNull.o2SNotNull(memberOperate.getCropMainNeeds()));
			retJson.put("data", obj);
			retJson.put("errno", 0);
			response.setStatus(200);
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", e.getMessage());
			retJson.put("errno", 3029);
			response.setStatus(400);
		}
		return retJson;
	}
	
	
	@RequestMapping("/livestockSave")
	@ResponseBody
	public JSONObject livestockSave(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String userId = (String) request.getAttribute("loginId");
		try {
			SysPerson person = personService.selectByUserIdAndIsdefault(userId);
			BusMemberOperate operate = new BusMemberOperate();
			String id = request.getParameter("id");
			if(StringUtils.isNotEmpty(id)){
				operate = memberOperateService.selectByPrimaryKey(id);
			}
			String livestockType = request.getParameter("livestockType");
			String livestockScale = request.getParameter("livestockScale");
			String livestockYears = request.getParameter("livestockYears");
			String livestockExpectedValue = request.getParameter("livestockExpectedValue");
			String livestockSiteType = request.getParameter("livestockSiteType");
			String livestockPeriod = request.getParameter("livestockPeriod");
			String livestockMainNeeds = request.getParameter("livestockMainNeeds");
			operate.setLivestockType(AppNull.s2SNull(livestockType));
			operate.setLivestockScale(AppNull.s2SNull(livestockScale));
			operate.setLivestockYears(AppNull.s2SNull(livestockYears));
			operate.setLivestockExpectedValue(AppNull.s2SNull(livestockExpectedValue));
			operate.setLivestockSiteType(AppNull.s2INull(livestockSiteType));
			operate.setLivestockPeriod(AppNull.s2SNull(livestockPeriod));
			operate.setLivestockMainNeeds(AppNull.s2SNull(livestockMainNeeds));
			operate.setUpdDate(DateUtils.getNowDate());
			operate.setUpdOperId(userId);
			operate.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			operate = CompletingDegree.caculate(operate);
			if(StringUtils.isEmpty(id)){
				operate.setOperUserId(person.getPersonId());
				SysOrg org = orgService.selectByOrgId(person.getOrgId());
				operate.setOrgId(org.getOrgId());
				operate.setId(UUIDUtil.getUUID());
				operate.setCreDate(DateUtils.getNowDate());
				operate.setCreOperId(userId);
				memberOperateService.insert(operate);
				retJson.put("id", operate.getId());
			}else{
				operate.setId(id);
				memberOperateService.updateByPrimaryKey(operate);
			}
			retJson.put("message", "保存成功");
			retJson.put("errno", 0);
			response.setStatus(200);
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", e.getMessage());
			retJson.put("errno", 3027);
			response.setStatus(400);
		}
		return retJson;
	}
	
	@RequestMapping("/livestockInfo")
	@ResponseBody
	public JSONObject livestockInfo(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		try {
			JSONObject obj = new JSONObject();
			String id = request.getParameter("id");
			BusMemberOperate memberOperate = null;
			if(!StringUtils.isEmpty(id)){
				memberOperate =memberOperateService.selectByPrimaryKey(id);
			}
			if(memberOperate==null){
				memberOperate=new BusMemberOperate();
			}
			obj.put("livestockType", AppNull.o2SNotNull(memberOperate.getLivestockType()));
			obj.put("id", AppNull.o2SNotNull(memberOperate.getId()));
			obj.put("livestockScale", AppNull.o2SNotNull(memberOperate.getLivestockScale()));
			obj.put("livestockYears", AppNull.o2SNotNull(memberOperate.getLivestockYears()));
			obj.put("livestockExpectedValue", AppNull.o2SNotNull(memberOperate.getLivestockExpectedValue()));
			obj.put("livestockSiteType", AppNull.o2SNotNull(memberOperate.getLivestockSiteType()));
			obj.put("livestockPeriod", AppNull.o2SNotNull(memberOperate.getLivestockPeriod()));
			obj.put("livestockMainNeeds", AppNull.o2SNotNull(memberOperate.getLivestockMainNeeds()));
			retJson.put("data", obj);
			retJson.put("errno", 0);
			response.setStatus(200);
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", e.getMessage());
			retJson.put("errno", 3029);
			response.setStatus(400);
		}
		return retJson;
	}
	
	@RequestMapping("/nongSellSave")
	@ResponseBody
	public JSONObject nongSellSave(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String userId = (String) request.getAttribute("loginId");
		try {
			SysPerson person = personService.selectByUserIdAndIsdefault(userId);
			BusMemberOperate operate = new BusMemberOperate();
			String id = request.getParameter("id");
			if(StringUtils.isNotEmpty(id)){
				operate = memberOperateService.selectByPrimaryKey(id);
			}
			String nongsellOtherType = request.getParameter("nongsellOtherType");
			String workNoRemark = request.getParameter("workNoRemark");
			String otherNoRemark = request.getParameter("otherNoRemark");
			String noYears = request.getParameter("noYears");
			String noIncome = request.getParameter("noIncome");
			String noSite = request.getParameter("noSite");
			String noMainNeeds = request.getParameter("noMainNeeds");
			operate.setNongsellOtherType(AppNull.s2SNull(nongsellOtherType));
			operate.setWorkNoRemark(AppNull.s2SNull(workNoRemark));
			operate.setOtherNoRemark(AppNull.s2SNull(otherNoRemark));
			operate.setNoYears(AppNull.s2SNull(noYears));
			operate.setNoIncome(AppNull.s2SNull(noIncome));
			operate.setNoSite(AppNull.s2INull(noSite));
			operate.setNoMainNeeds(AppNull.s2SNull(noMainNeeds));
			operate.setUpdDate(DateUtils.getNowDate());
			operate.setUpdOperId(userId);
			operate.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			operate = CompletingDegree.caculate(operate);
			if(StringUtils.isEmpty(id)){
				operate.setOperUserId(person.getPersonId());
				SysOrg org = orgService.selectByOrgId(person.getOrgId());
				operate.setOrgId(org.getOrgId());
				operate.setId(UUIDUtil.getUUID());
				operate.setCreDate(DateUtils.getNowDate());
				operate.setCreOperId(userId);
				memberOperateService.insert(operate);
				retJson.put("id", operate.getId());
			}else{
				operate.setId(id);
				memberOperateService.updateByPrimaryKey(operate);
			}
			retJson.put("message", "保存成功");
			retJson.put("errno", 0);
			response.setStatus(200);
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", e.getMessage());
			retJson.put("errno", 3027);
			response.setStatus(400);
		}
		return retJson;
	}
	
	@RequestMapping("/nongSellInfo")
	@ResponseBody
	public JSONObject nongSellInfo(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		try {
			JSONObject obj = new JSONObject();
			String id = request.getParameter("id");
			BusMemberOperate memberOperate = null;
			if(!StringUtils.isEmpty(id)){
				memberOperate =memberOperateService.selectByPrimaryKey(id);
			}
			if(memberOperate==null){
				memberOperate=new BusMemberOperate();
			}
			obj.put("nongsellOtherType", AppNull.o2SNotNull(memberOperate.getNongsellOtherType()));
			obj.put("id", AppNull.o2SNotNull(memberOperate.getId()));
			obj.put("workNoRemark", AppNull.o2SNotNull(memberOperate.getWorkNoRemark()));
			obj.put("otherNoRemark", AppNull.o2SNotNull(memberOperate.getOtherNoRemark()));
			obj.put("noYears", AppNull.o2SNotNull(memberOperate.getNoYears()));
			obj.put("noIncome", AppNull.o2SNotNull(memberOperate.getNoIncome()));
			obj.put("noSite", AppNull.o2SNotNull(memberOperate.getNoSite()));
			obj.put("noMainNeeds", AppNull.o2SNotNull(memberOperate.getNoMainNeeds()));
			retJson.put("data", obj);
			retJson.put("errno", 0);
			response.setStatus(200);
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", e.getMessage());
			retJson.put("errno", 3029);
			response.setStatus(400);
		}
		return retJson;
	}
	
	@RequestMapping("/memberOperateList")
	@ResponseBody
	public JSONObject memberOperateList(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String userId = (String) request.getAttribute("loginId");
		try {
			JSONArray arr = new JSONArray();
			List<BusMemberOperate> list= memberOperateService.selectByUser(userId);
			if(list!=null&&list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					JSONObject obj = new JSONObject();
					obj.put("id", list.get(i).getId());
					obj.put("name", AppNull.o2SNotNull(list.get(i).getName()));
					obj.put("phone", AppNull.o2SNotNull(list.get(i).getPhone()));
					obj.put("completingDegree", AppNull.o2SNotNull(list.get(i).getCompletingDegree()) );
					arr.add(obj);
				}
			}else{
				retJson.put("message", "没有更多数据了");
				retJson.put("errno", 3011);
				response.setStatus(400);
				return retJson;
			}
			retJson.put("data", arr);
			retJson.put("errno", 0);
			response.setStatus(200);
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", e.getMessage());
			retJson.put("errno", 3027);
			response.setStatus(400);
		}
		return retJson;
	}
	@RequestMapping("/listByName")
	@ResponseBody
	public JSONObject memberOperateListByName(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String userId = (String) request.getAttribute("loginId");
		try {
			String name = request.getParameter("name");
			if(StringUtils.isEmpty(name)){
				name=null;
			}
			BusMemberOperate mo = new BusMemberOperate();
			mo.setName(name);
			mo.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			JSONArray arr = new JSONArray();
			List<BusMemberOperate> list= memberOperateService.selectByUserAndCondition(userId,mo);
			if(list!=null&&list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					JSONObject obj = new JSONObject();
					obj.put("id", list.get(i).getId());
					obj.put("name", AppNull.o2SNotNull(list.get(i).getName()));
					obj.put("phone", AppNull.o2SNotNull(list.get(i).getPhone()));
					obj.put("completingDegree", AppNull.o2SNotNull(list.get(i).getCompletingDegree()) );
					arr.add(obj);
				}
			}else{
				retJson.put("message", "没有更多数据了");
				retJson.put("errno", 3011);
				response.setStatus(400);
				return retJson;
			}
			retJson.put("data", arr);
			retJson.put("errno", 0);
			response.setStatus(200);
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", e.getMessage());
			retJson.put("errno", 3027);
			response.setStatus(400);
		}
		return retJson;
	}
	
}
