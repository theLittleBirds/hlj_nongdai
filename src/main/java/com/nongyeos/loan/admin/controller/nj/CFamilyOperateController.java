package com.nongyeos.loan.admin.controller.nj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.nongyeos.loan.admin.entity.BusFamilyOperate;
import com.nongyeos.loan.admin.service.IFamilyOperateService;
import com.nongyeos.loan.base.util.AppNull;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/nj/familyOperate")
public class CFamilyOperateController {
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(CApplicationController.class);
	
	@Autowired
	private IFamilyOperateService operateService;
	
	@RequestMapping("/chooseType")
	@ResponseBody
	public JSONObject chooseType(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String loginId = (String) request.getAttribute("loginId");
		try {
			String intoPieceId = request.getParameter("intoPieceId");
			BusFamilyOperate familyOperate = operateService.selectByIpId(intoPieceId);
			String blc = request.getParameter("blc");
			String livestock = request.getParameter("livestock");
			String nongSell = request.getParameter("nongSell");
			String nonenong = request.getParameter("nonenong");
			
			if(StringUtils.isEmpty(blc)&&StringUtils.isEmpty(livestock)&&StringUtils.isEmpty(nongSell)&&StringUtils.isEmpty(nonenong)){
				retJson.put("message", "请选择经营种类！");
				response.setStatus(400);
				return retJson;
			}
			List<String> asList = new ArrayList<String>();
			if(!StringUtils.isEmpty(blc)&&blc.equals("1"))
				asList.add("1");
			if(!StringUtils.isEmpty(livestock)&&livestock.equals("1"))
				asList.add("2");
			if(!StringUtils.isEmpty(nongSell)&&nongSell.equals("1"))
				asList.add("3");
			if(!StringUtils.isEmpty(nonenong)&&nonenong.equals("1"))
				asList.add("4");
			Collections.sort(asList);
			String operateType = StringUtils.join(asList, ",");
			if(familyOperate==null){
				familyOperate=new BusFamilyOperate();
				familyOperate.setIntoPieceId(intoPieceId);
				familyOperate.setId(UUIDUtil.getUUID());
				familyOperate.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
				familyOperate.setCreDate(DateUtils.getNowDate());
				familyOperate.setCreOperId(loginId);
				familyOperate.setUpdOperId(loginId);
				familyOperate.setUpdDate(DateUtils.getNowDate());
				familyOperate.setOperateType(operateType);
				operateService.insert(familyOperate);
			}else{
				familyOperate.setOperateType(operateType);
				familyOperate.setUpdOperId(loginId);
				familyOperate.setUpdDate(DateUtils.getNowDate());
				operateService.updateByPrimaryKey(familyOperate);
			}
			retJson.put("message", "保存成功！");
			setOperateType(retJson, familyOperate);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "保存失败！");
			response.setStatus(400);
		}
		
		return retJson;
	}
	
	
	@RequestMapping("/operateTypeInfo")
	@ResponseBody
	public JSONObject operateTypeInfo(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		try {
			String intoPieceId = request.getParameter("intoPieceId");
			BusFamilyOperate familyOperate = operateService.selectByIpId(intoPieceId);
			setOperateType(retJson, familyOperate);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retJson;
	}
	@RequestMapping("/blcTypeSave")
	@ResponseBody
	public JSONObject blcTypeSave(HttpServletRequest request,HttpServletResponse response,String wheat,String corn,String rice,String vegetables
			,String other,String moneyCrops){
		JSONObject retJson = new JSONObject();
		String loginId = (String) request.getAttribute("loginId");
		try {
			String intoPieceId = request.getParameter("intoPieceId");
			BusFamilyOperate familyOperate = operateService.selectByIpId(intoPieceId);
			if(familyOperate==null){
				retJson.put("message", "请先选择经营种类！");
				response.setStatus(400);
				return retJson;
			}else{
				if(StringUtils.isEmpty(wheat)&&StringUtils.isEmpty(corn)&&StringUtils.isEmpty(rice)&&StringUtils.isEmpty(vegetables)&&StringUtils.isEmpty(moneyCrops)
						&&StringUtils.isEmpty(other)){
					retJson.put("message", "请选择种植种类！");
					response.setStatus(400);
					return retJson;
				}
				List<String> asList = new ArrayList<String>();
				if(!StringUtils.isEmpty(wheat)&&wheat.equals("1"))
					asList.add("1");
				if(!StringUtils.isEmpty(corn)&&corn.equals("1"))
					asList.add("2");
				if(!StringUtils.isEmpty(rice)&&rice.equals("1"))
					asList.add("3");
				if(!StringUtils.isEmpty(other)&&other.equals("1"))
					asList.add("4");
				if(!StringUtils.isEmpty(moneyCrops)&&moneyCrops.equals("1"))
					asList.add("5");
				Collections.sort(asList);
				String bigLandCropType = StringUtils.join(asList, ",");
				familyOperate.setBigLandCropType(bigLandCropType);
				familyOperate.setUpdDate(DateUtils.getNowDate());
				familyOperate.setUpdOperId(loginId);
				operateService.updateByPrimaryKey(familyOperate);
				retJson.put("message", "保存成功！");
				setOperateType(retJson, familyOperate);
				response.setStatus(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "保存失败！");
			response.setStatus(400);
		}
		return retJson;
	}
	
	@RequestMapping("/blcTypeInfo")
	@ResponseBody
	public JSONObject blcTypeInfo(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		try {
			String intoPieceId = request.getParameter("intoPieceId");
			BusFamilyOperate familyOperate = operateService.selectByIpId(intoPieceId);
			if(familyOperate==null){
				retJson.put("message", "请先选择经营种类！");
				response.setStatus(400);
				return retJson;
			}
			JSONObject obj = new JSONObject();
			obj.put("wheat", "2");
			obj.put("corn", "2");
			obj.put("rice", "2");
			obj.put("moneyCrops", "2");
			obj.put("other", "2");
			if(StringUtils.isNotEmpty(familyOperate.getBigLandCropType())){
				String[] split = familyOperate.getBigLandCropType().split(",");
				for (int i = 0; i < split.length; i++) {
					if(split[i].equals("1")){
						obj.put("wheat", "1");
					}else if(split[i].equals("2")){
						obj.put("corn", "1");
					}else if(split[i].equals("3")){
						obj.put("rice", "1");
					}else if(split[i].equals("4")){
						obj.put("other", "1");
					}else if(split[i].equals("5")){
						obj.put("moneyCrops", "1");
					}
				}
				
			}
			retJson.put("data", obj);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retJson;
	}
	@RequestMapping("/blcSave")
	@ResponseBody
	public JSONObject blcSave(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String loginId = (String) request.getAttribute("loginId");
		try {
			String intoPieceId = request.getParameter("intoPieceId");
			BusFamilyOperate familyOperate = operateService.selectByIpId(intoPieceId);
			if(familyOperate==null){
				retJson.put("message", "请先选择经营种类！");
				response.setStatus(400);
				return retJson;
			}else{
				familyOperate.setBlcScale(request.getParameter("blcScale"));
				familyOperate.setBlcYears(request.getParameter("blcYears"));
				familyOperate.setBlcExpectedValue(request.getParameter("blcExpectedValue"));
				familyOperate.setBlcHarvestTime(request.getParameter("blcHarvestTime"));
				familyOperate.setBlcInvestment(request.getParameter("blcInvestment"));
				familyOperate.setBlcHistoryOperate(AppNull.s2INull(request.getParameter("blcHistoryOperate")) );
				familyOperate.setUpdDate(DateUtils.getNowDate());
				familyOperate.setUpdOperId(loginId);
				operateService.updateByPrimaryKey(familyOperate);
				retJson.put("message", "保存成功！");
				setOperateType(retJson, familyOperate);
				response.setStatus(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "保存失败！");
			response.setStatus(400);
		}
		return retJson;
	}
	
	@RequestMapping("/blcInfo")
	@ResponseBody
	public JSONObject blcInfo(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		try {
			String intoPieceId = request.getParameter("intoPieceId");
			BusFamilyOperate familyOperate = operateService.selectByIpId(intoPieceId);
			if(familyOperate==null){
				retJson.put("message", "请先选择经营种类！");
				response.setStatus(400);
				return retJson;
			}
			JSONObject obj = new JSONObject();
			obj.put("blcScale", AppNull.o2SNotNull(familyOperate.getBlcScale()));
			obj.put("blcYears", AppNull.o2SNotNull(familyOperate.getBlcYears()));
			obj.put("blcExpectedValue", AppNull.o2SNotNull(familyOperate.getBlcExpectedValue()));
			obj.put("blcHarvestTime", AppNull.o2SNotNull(familyOperate.getBlcHarvestTime()));
			obj.put("blcInvestment", AppNull.o2SNotNull(familyOperate.getBlcInvestment()));
			obj.put("blcHistoryOperate", AppNull.o2SNotNull(familyOperate.getBlcHistoryOperate()));
			retJson.put("data", obj);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retJson;
	}
//	livestockType
	@RequestMapping("/livestockTypeSave")
	@ResponseBody
	public JSONObject livestockTypeSave(HttpServletRequest request,HttpServletResponse response,String cow ,String pig,String sheep,String chicken
			,String fish,String other){
		JSONObject retJson = new JSONObject();
		String loginId = (String) request.getAttribute("loginId");
		try {
			String intoPieceId = request.getParameter("intoPieceId");
			BusFamilyOperate familyOperate = operateService.selectByIpId(intoPieceId);
			if(familyOperate==null){
				retJson.put("message", "请先选择经营种类！");
				response.setStatus(400);
				return retJson;
			}else{
				if(StringUtils.isEmpty(cow)&&StringUtils.isEmpty(pig)&&StringUtils.isEmpty(sheep)&&StringUtils.isEmpty(chicken)
						&&StringUtils.isEmpty(fish)&&StringUtils.isEmpty(other)){
					retJson.put("message", "请选择养殖种类！");
					response.setStatus(400);
					return retJson;
				}
				List<String> asList = new ArrayList<String>();
				if(!StringUtils.isEmpty(cow)&&cow.equals("1"))
					asList.add("1");
				if(!StringUtils.isEmpty(pig)&&pig.equals("1"))
					asList.add("2");
				if(!StringUtils.isEmpty(sheep)&&sheep.equals("1"))
					asList.add("3");
				if(!StringUtils.isEmpty(chicken)&&chicken.equals("1"))
					asList.add("4");
				if(!StringUtils.isEmpty(fish)&&fish.equals("1"))
					asList.add("5");
				if(!StringUtils.isEmpty(other)&&other.equals("1"))
					asList.add("6");
				Collections.sort(asList);
				String livestockType = StringUtils.join(asList, ",");
				familyOperate.setLivestockType(livestockType);
				familyOperate.setUpdDate(DateUtils.getNowDate());
				familyOperate.setUpdOperId(loginId);
				operateService.updateByPrimaryKey(familyOperate);
				retJson.put("message", "保存成功！");
				setOperateType(retJson, familyOperate);
				response.setStatus(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "保存失败！");
			response.setStatus(400);
		}
		return retJson;
	}
	
	@RequestMapping("/livestockTypeInfo")
	@ResponseBody
	public JSONObject livestockTypeInfo(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		try {
			String intoPieceId = request.getParameter("intoPieceId");
			BusFamilyOperate familyOperate = operateService.selectByIpId(intoPieceId);
			if(familyOperate==null){
				retJson.put("message", "请先选择经营种类！");
				response.setStatus(400);
				return retJson;
			}
			JSONObject obj = new JSONObject();
			obj.put("cow", "2");
			obj.put("pig", "2");
			obj.put("sheep", "2");
			obj.put("chicken", "2");
			obj.put("fish", "2");
			obj.put("other", "2");
			if(StringUtils.isNotEmpty(familyOperate.getLivestockType())){
				String[] split = familyOperate.getLivestockType().split(",");
				for (int i = 0; i < split.length; i++) {
					if(split[i].equals("1")){
						obj.put("cow", "1");
					}else if(split[i].equals("2")){
						obj.put("pig", "1");
					}else if(split[i].equals("3")){
						obj.put("sheep", "1");
					}else if(split[i].equals("4")){
						obj.put("chicken", "1");
					}else if(split[i].equals("5")){
						obj.put("fish", "1");
					}else if(split[i].equals("6")){
						obj.put("other", "1");
					}
				}
				
			}
			retJson.put("data", obj);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retJson;
	}
	
	@RequestMapping("/livestockSave")
	@ResponseBody
	public JSONObject livestockSave(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String loginId = (String) request.getAttribute("loginId");
		try {
			String intoPieceId = request.getParameter("intoPieceId");
			BusFamilyOperate familyOperate = operateService.selectByIpId(intoPieceId);
			if(familyOperate==null){
				retJson.put("message", "请先选择经营种类！");
				response.setStatus(400);
				return retJson;
			}else{
				familyOperate.setLivestockScale(request.getParameter("livestockScale"));
				familyOperate.setLivestockYears(request.getParameter("livestockYears"));
				familyOperate.setLivestockExpectedValue(request.getParameter("livestockExpectedValue"));
				familyOperate.setLivestockSiteType(AppNull.s2INull(request.getParameter("livestockSiteType")));
				familyOperate.setEiaCertificate(request.getParameter("eiaCertificate"));
				familyOperate.setLivestockHistoryOperate(AppNull.s2INull(request.getParameter("livestockHistoryOperate")));
				familyOperate.setUpdDate(DateUtils.getNowDate());
				familyOperate.setUpdOperId(loginId);
				operateService.updateByPrimaryKey(familyOperate);
				retJson.put("message", "保存成功！");
				setOperateType(retJson, familyOperate);
				response.setStatus(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "保存失败！");
			response.setStatus(400);
		}
		return retJson;
	}
	
	@RequestMapping("/livestockInfo")
	@ResponseBody
	public JSONObject livestockInfo(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		
		try {
			String intoPieceId = request.getParameter("intoPieceId");
			BusFamilyOperate familyOperate = operateService.selectByIpId(intoPieceId);
			if(familyOperate==null){
				retJson.put("message", "请先选择经营种类！");
				response.setStatus(400);
				return retJson;
			}
			JSONObject obj = new JSONObject();
			obj.put("livestockScale", AppNull.o2SNotNull(familyOperate.getLivestockScale()));
			obj.put("livestockYears", AppNull.o2SNotNull(familyOperate.getLivestockYears()));
			obj.put("livestockExpectedValue", AppNull.o2SNotNull(familyOperate.getLivestockExpectedValue()));
			obj.put("livestockSiteType", AppNull.o2SNotNull(familyOperate.getLivestockSiteType()));
			obj.put("eiaCertificate", AppNull.o2SNotNull(familyOperate.getEiaCertificate()));
			obj.put("livestockHistoryOperate", AppNull.o2SNotNull(familyOperate.getLivestockHistoryOperate()));
			retJson.put("data", obj);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retJson;
	}
	
	@RequestMapping("/nongSellTypeSave")
	@ResponseBody
	public JSONObject nongSellTypeSave(HttpServletRequest request,HttpServletResponse response,String huafei,String seed,String feed,String farmProduct,String other){
		JSONObject retJson = new JSONObject();
		String loginId = (String) request.getAttribute("loginId");
		try {
			String intoPieceId = request.getParameter("intoPieceId");
			BusFamilyOperate familyOperate = operateService.selectByIpId(intoPieceId);
			if(familyOperate==null){
				retJson.put("message", "请先选择经营种类！");
				response.setStatus(400);
				return retJson;
			}else{
				if(StringUtils.isEmpty(huafei)&&StringUtils.isEmpty(seed)&&StringUtils.isEmpty(feed)&&StringUtils.isEmpty(farmProduct)&&StringUtils.isEmpty(other)){
					retJson.put("message", "请选择经销种类！");
					response.setStatus(400);
					return retJson;
				}
				List<String> asList = new ArrayList<String>();
				if(!StringUtils.isEmpty(huafei)&&huafei.equals("1"))
					asList.add("1");
				if(!StringUtils.isEmpty(seed)&&seed.equals("1"))
					asList.add("2");
				if(!StringUtils.isEmpty(feed)&&feed.equals("1"))
					asList.add("3");
				if(!StringUtils.isEmpty(farmProduct)&&farmProduct.equals("1"))
					asList.add("4");
				if(!StringUtils.isEmpty(other)&&other.equals("1"))
					asList.add("5");
				Collections.sort(asList);
				String nongSellType = StringUtils.join(asList, ",");
				familyOperate.setNongSellType(nongSellType);
				familyOperate.setUpdDate(DateUtils.getNowDate());
				familyOperate.setUpdOperId(loginId);
				operateService.updateByPrimaryKey(familyOperate);
				retJson.put("message", "保存成功！");
				setOperateType(retJson, familyOperate);
				response.setStatus(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "保存失败！");
			response.setStatus(400);
		}
		return retJson;
	}
	
	@RequestMapping("/nongSellTypeInfo")
	@ResponseBody
	public JSONObject nongSellTypeInfo(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		try {
			String intoPieceId = request.getParameter("intoPieceId");
			BusFamilyOperate familyOperate = operateService.selectByIpId(intoPieceId);
			if(familyOperate==null){
				retJson.put("message", "请先选择经营种类！");
				response.setStatus(400);
				return retJson;
			}
			JSONObject obj = new JSONObject();
			obj.put("huafei", "2");
			obj.put("seed", "2");
			obj.put("feed", "2");
			obj.put("farmProduct", "2");
			obj.put("other", "2");
			if(StringUtils.isNotEmpty(familyOperate.getNongSellType())){
				String[] split = familyOperate.getNongSellType().split(",");
				for (int i = 0; i < split.length; i++) {
					if(split[i].equals("1")){
						obj.put("huafei", "1");
					}else if(split[i].equals("2")){
						obj.put("seed", "1");
					}else if(split[i].equals("3")){
						obj.put("feed", "1");
					}else if(split[i].equals("4")){
						obj.put("farmProduct", "1");
					}else if(split[i].equals("5")){
						obj.put("other", "1");
					}
				}
				
			}
			retJson.put("data", obj);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retJson;
	}
	
	@RequestMapping("/nongSellSave")
	@ResponseBody
	public JSONObject nongSellSave(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String loginId = (String) request.getAttribute("loginId");
		try {
			String intoPieceId = request.getParameter("intoPieceId");
			BusFamilyOperate familyOperate = operateService.selectByIpId(intoPieceId);
			if(familyOperate==null){
				retJson.put("message", "请先选择经营种类！");
				response.setStatus(400);
				return retJson;
			}
			familyOperate.setNsScale(request.getParameter("nsScale"));
			familyOperate.setNsYears(request.getParameter("nsYears"));
			familyOperate.setNsSiteType(AppNull.s2INull(request.getParameter("nsSiteType")));
			familyOperate.setNsHistoryOperate(AppNull.s2INull(request.getParameter("nsHistoryOperate")));
			familyOperate.setUpdDate(DateUtils.getNowDate());
			familyOperate.setUpdOperId(loginId);
			operateService.updateByPrimaryKey(familyOperate);
			retJson.put("message", "保存成功！");
			setOperateType(retJson, familyOperate);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "保存失败！");
			response.setStatus(400);
		}
		return retJson;
	}
	
	@RequestMapping("/nongSellInfo")
	@ResponseBody
	public JSONObject nongSellInfo(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		try {
			String intoPieceId = request.getParameter("intoPieceId");
			BusFamilyOperate familyOperate = operateService.selectByIpId(intoPieceId);
			if(familyOperate==null){
				retJson.put("message", "请先选择经营种类！");
				response.setStatus(400);
				return retJson;
			}
			JSONObject obj = new JSONObject();
			obj.put("nsScale", AppNull.o2SNotNull(familyOperate.getNsScale()));
			obj.put("nsYears", AppNull.o2SNotNull(familyOperate.getNsYears()));
			obj.put("nsSiteType", AppNull.o2SNotNull(familyOperate.getNsSiteType()));
			obj.put("nsHistoryOperate", AppNull.o2SNotNull(familyOperate.getNsHistoryOperate()));
			retJson.put("data", obj);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retJson;
	}
	
	@RequestMapping("/nonenongSave")
	@ResponseBody
	public JSONObject nonenongSave(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String loginId = (String) request.getAttribute("loginId");
		try {
			String intoPieceId = request.getParameter("intoPieceId");
			BusFamilyOperate familyOperate = operateService.selectByIpId(intoPieceId);
			if(familyOperate==null){
				retJson.put("message", "请先选择经营种类！");
				response.setStatus(400);
				return retJson;
			}
			familyOperate.setNonenongType(request.getParameter("nonenongType"));
			familyOperate.setNonenongIncome(request.getParameter("nonenongIncome"));
			familyOperate.setNonenongYears(request.getParameter("nonenongYears"));
			familyOperate.setSocialSecurity(AppNull.s2INull(request.getParameter("socialSecurity")));
			familyOperate.setSocialSecurityMoney(request.getParameter("socialSecurityMoney"));
			familyOperate.setAccumulationFund(AppNull.s2INull(request.getParameter("accumulationFund")));
			familyOperate.setAccumulationFundMoney(request.getParameter("accumulationFundMoney"));
			familyOperate.setUpdDate(DateUtils.getNowDate());
			familyOperate.setUpdOperId(loginId);
			operateService.updateByPrimaryKey(familyOperate);
			retJson.put("message", "保存成功！");
			setOperateType(retJson, familyOperate);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "保存失败！");
			response.setStatus(400);
		}
		return retJson;
	}
	
	@RequestMapping("/nonenongInfo")
	@ResponseBody
	public JSONObject nonenongInfo(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		try {
			String intoPieceId = request.getParameter("intoPieceId");
			BusFamilyOperate familyOperate = operateService.selectByIpId(intoPieceId);
			if(familyOperate==null){
				retJson.put("message", "请先选择经营种类！");
				response.setStatus(400);
				return retJson;
			}
			JSONObject obj = new JSONObject();
			obj.put("nonenongType", AppNull.o2SNotNull(familyOperate.getNonenongType()));
			obj.put("nonenongIncome", AppNull.o2SNotNull(familyOperate.getNonenongIncome()));
			obj.put("nonenongYears", AppNull.o2SNotNull(familyOperate.getNonenongYears()));
			obj.put("socialSecurity", AppNull.o2SNotNull(familyOperate.getSocialSecurity()));
			obj.put("socialSecurityMoney", AppNull.o2SNotNull(familyOperate.getSocialSecurityMoney()));
			obj.put("accumulationFund", AppNull.o2SNotNull(familyOperate.getAccumulationFund()));
			obj.put("accumulationFundMoney", AppNull.o2SNotNull(familyOperate.getAccumulationFundMoney()));
			retJson.put("data", obj);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retJson;
	}
	
	
	public void setOperateType(JSONObject retJson,BusFamilyOperate familyOperate){
		if(familyOperate==null){
			familyOperate = new BusFamilyOperate();
		}
		JSONObject obj = new JSONObject();
		obj.put("blc", "2");
		obj.put("livestock", "2");
		obj.put("nongSell", "2");
		obj.put("nonenong", "2");
		if(StringUtils.isNotEmpty(familyOperate.getOperateType())){
			String[] split = familyOperate.getOperateType().split(",");
			for (int i = 0; i < split.length; i++) {
				if(split[i].equals("1")){
					obj.put("blc", "1");
				}else if(split[i].equals("2")){
					obj.put("livestock", "1");
				}else if(split[i].equals("3")){
					obj.put("nongSell", "1");
				}else if(split[i].equals("4")){
					obj.put("nonenong", "1");
				}
			}
		}
		retJson.put("data", obj);
		retJson.put("errno", 0);
	}
	
}
