package com.nongyeos.loan.admin.controller.nj;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
import com.google.gson.JsonObject;
import com.nongyeos.loan.admin.entity.BusFamilyCapital;
import com.nongyeos.loan.admin.service.IFamilyCapitalService;
import com.nongyeos.loan.base.util.AppNull;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.NotEmptyUtils;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

/**
 * 
 * Title:CFamilyCapitalController 
 * Description:  
 * @author lcg
 * @date 2018年5月8日 下午3:13:04
 */
@Controller
@RequestMapping("/nj/familyCapital")
public class CFamilyCapitalController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(CFamilyCapitalController.class);
	
	@Autowired
	private IFamilyCapitalService familyCapitalService;
	
	/**
	 * 
	 * @Title: houseSave 
	 * @Description: 房屋资产保存
	 * @param @param request
	 * @param @return     
	 * @return JSONObject     
	 * @throws
	 */
	@RequestMapping("/houseSave")
	@ResponseBody
	public JSONObject houseSave(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String intoPieceId = request.getParameter("intoPieceId");
		if(StringUtils.isEmpty(intoPieceId)){
			retJson.put("message", "家庭财产进件标识为空！");
			retJson.put("errno", 3001);
			response.setStatus(400);
			return retJson;
		}
		String loginId = (String) request.getAttribute("loginId");
		try {
			String rentHouse = request.getParameter("rentHouse");
			String countrysideHouse = request.getParameter("countrysideHouse");
			String smallPropertyHouse = request.getParameter("smallPropertyHouse");
			String smallPropertyHouseArea = request.getParameter("smallPropertyHouseArea");
			if(StringUtils.isNotBlank(smallPropertyHouse)&&smallPropertyHouse.equals("1")){
				Map<String, Object> smallPropertyHouseValuenull = NotEmptyUtils.somthingLose(request, response, "smallPropertyHouseValue", "请填写小产权房/拆迁房预估价值！", retJson);
				if((boolean) smallPropertyHouseValuenull.get("flag"))
					return JSONArray.parseObject(smallPropertyHouseValuenull.get("retJson").toString()) ;
			}
			String smallPropertyHouseValue = request.getParameter("smallPropertyHouseValue");
			String bigPropertyHouse = request.getParameter("bigPropertyHouse");
			String bigPropertyHouseArea = request.getParameter("bigPropertyHouseArea");
			if(StringUtils.isNotBlank(bigPropertyHouse)&&bigPropertyHouse.equals("1")){
				Map<String, Object> bigPropertyHouseValuenull = NotEmptyUtils.somthingLose(request,response,  "bigPropertyHouseValue", "请填写大产权/商品房房预估价值！", retJson);
				if((boolean) bigPropertyHouseValuenull.get("flag"))
					return JSONArray.parseObject(bigPropertyHouseValuenull.get("retJson").toString()) ;
			}
			String bigPropertyHouseValue = request.getParameter("bigPropertyHouseValue");
			BusFamilyCapital familyCapital = new BusFamilyCapital();
			familyCapital.setIntoPieceId(intoPieceId);
			familyCapital.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			BusFamilyCapital fc = familyCapitalService.selectByIntoPieceId(familyCapital);
			if(fc==null){
				familyCapital.setRentHouse(StringUtils.isEmpty(rentHouse)?null:new Integer(rentHouse));
				familyCapital.setCountrysideHouse(StringUtils.isEmpty(countrysideHouse)?null:new Integer(countrysideHouse));
				familyCapital.setSmallPropertyHouse(StringUtils.isEmpty(smallPropertyHouse)?null:new Integer(smallPropertyHouse));
				familyCapital.setSmallPropertyHouseArea(StringUtils.isEmpty(smallPropertyHouseArea)?null:smallPropertyHouseArea);
				familyCapital.setSmallPropertyHouseValue(StringUtils.isEmpty(smallPropertyHouseValue)?null:smallPropertyHouseValue);
				familyCapital.setBigPropertyHouse(StringUtils.isEmpty(bigPropertyHouse)?null:new Integer(bigPropertyHouse));
				familyCapital.setBigPropertyHouseArea(StringUtils.isEmpty(bigPropertyHouseArea)?null:bigPropertyHouseArea);
				familyCapital.setBigPropertyHouseValue(StringUtils.isEmpty(bigPropertyHouseValue)?null:bigPropertyHouseValue);
				familyCapital.setTotalFamilyCapital(caculateSum(familyCapital, 1));
				familyCapital.setTotalFamilyDebtedness(caculateSum(familyCapital, 2));
				familyCapital.setUpdDate(DateUtils.getNowDate());
				familyCapital.setId(UUIDUtil.getUUID());
				familyCapital.setCreOperId(loginId);
				familyCapital.setCreDate(DateUtils.getNowDate());
				familyCapital.setUpdOperId(loginId);
				familyCapital.setUpdDate(DateUtils.getNowDate());
				familyCapitalService.insert(familyCapital);
			}else{
				fc.setRentHouse(StringUtils.isEmpty(rentHouse)?null:new Integer(rentHouse));
				fc.setCountrysideHouse(StringUtils.isEmpty(countrysideHouse)?null:new Integer(countrysideHouse));
				fc.setSmallPropertyHouse(StringUtils.isEmpty(smallPropertyHouse)?null:new Integer(smallPropertyHouse));
				fc.setSmallPropertyHouseArea(StringUtils.isEmpty(smallPropertyHouseArea)?null:smallPropertyHouseArea);
				fc.setSmallPropertyHouseValue(StringUtils.isEmpty(smallPropertyHouseValue)?null:smallPropertyHouseValue);
				fc.setBigPropertyHouse(StringUtils.isEmpty(bigPropertyHouse)?null:new Integer(bigPropertyHouse));
				fc.setBigPropertyHouseArea(StringUtils.isEmpty(bigPropertyHouseArea)?null:bigPropertyHouseArea);
				fc.setBigPropertyHouseValue(StringUtils.isEmpty(bigPropertyHouseValue)?null:bigPropertyHouseValue);
				fc.setTotalFamilyCapital(caculateSum(fc, 1));
				fc.setTotalFamilyDebtedness(caculateSum(fc, 2));
				fc.setUpdOperId(loginId);
				fc.setUpdDate(DateUtils.getNowDate());
				familyCapitalService.updateByPrimaryKey(fc);
			}
			retJson.put("message", "保存成功！");
			retJson.put("errno", 0);
			response.setStatus(200);
			return retJson;
		} catch (Exception e) {
			retJson.put("message", "保存失败！");
			response.setStatus(400);
			e.printStackTrace();
		}
		return retJson;
	}
	
	/**
	 * 
	 * @Title: houseInfo 
	 * @Description: 房屋信息回显
	 * @param @param request
	 * @param @return     
	 * @return JSONObject     
	 * @throws
	 */
	@RequestMapping("/houseInfo")
	@ResponseBody
	public JSONObject houseInfo(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String intoPieceId = request.getParameter("intoPieceId");
		if(StringUtils.isEmpty(intoPieceId)){
			retJson.put("message", "家庭财产进件标识为空！");
			retJson.put("errno", 3001);
			response.setStatus(400);
			return retJson;
		}
		try {
			BusFamilyCapital familyCapital = new BusFamilyCapital();
			familyCapital.setIntoPieceId(intoPieceId);
			familyCapital.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			BusFamilyCapital fc = familyCapitalService.selectByIntoPieceId(familyCapital);
			if(fc==null){
				fc=new BusFamilyCapital();
			}
			JSONObject obj = new JSONObject();
			obj.put("rentHouse", AppNull.o2SNotNull(fc.getRentHouse()));
			obj.put("countrysideHouse", AppNull.o2SNotNull(fc.getCountrysideHouse()));
			obj.put("smallPropertyHouse", AppNull.o2SNotNull(fc.getSmallPropertyHouse()));
			obj.put("smallPropertyHouseArea", AppNull.o2SNotNull(fc.getSmallPropertyHouseArea()));
			obj.put("smallPropertyHouseValue", AppNull.o2SNotNull(fc.getSmallPropertyHouseValue()));
			obj.put("bigPropertyHouse", AppNull.o2SNotNull(fc.getBigPropertyHouse()));
			obj.put("bigPropertyHouseArea", AppNull.o2SNotNull(fc.getBigPropertyHouseArea()));
			obj.put("bigPropertyHouseValue", AppNull.o2SNotNull(fc.getBigPropertyHouseValue()));
			retJson.put("data", obj);
			retJson.put("errno", 0);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retJson;
	}
	
	/**
	 * 
	 * @Title: carSave 
	 * @Description: 车辆信息保存
	 * @param @param request
	 * @param @return     
	 * @return JSONObject     
	 * @throws
	 */
	@RequestMapping("/carSave")
	@ResponseBody
	public JSONObject carSave(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String intoPieceId = request.getParameter("intoPieceId");
		if(StringUtils.isEmpty(intoPieceId)){
			retJson.put("message", "家庭财产进件标识为空！");
			retJson.put("errno", 3001);
			response.setStatus(400);
			return retJson;
		}
		String loginId = (String) request.getAttribute("loginId");
		try {
			String car = request.getParameter("car");
			String carName = request.getParameter("carName");
			String carValue = request.getParameter("carValue");
			String minibus = request.getParameter("minibus");
			String minibusValue = request.getParameter("minibusValue");
			String truck = request.getParameter("truck");
			String truckValue = request.getParameter("truckValue");
			String otherTricycle = request.getParameter("otherTricycle");
			String nongMachine = request.getParameter("nongMachine");
			String nongMachineRemark = request.getParameter("nongMachineRemark");
			String nongMachineEstimatedValue = request.getParameter("nongMachineEstimatedValue");
			BusFamilyCapital familyCapital = new BusFamilyCapital();
			familyCapital.setIntoPieceId(intoPieceId);
			familyCapital.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			BusFamilyCapital fc = familyCapitalService.selectByIntoPieceId(familyCapital);
			if(fc==null){
				familyCapital.setCar(AppNull.s2INull(car));
				familyCapital.setCarName(AppNull.s2SNull(carName));
				familyCapital.setCarValue(AppNull.s2SNull(carValue));
				familyCapital.setMinibus(AppNull.s2INull(minibus));
				familyCapital.setMinibusValue(AppNull.s2SNull(minibusValue));
				familyCapital.setTruck(AppNull.s2INull(truck));
				familyCapital.setTruckValue(AppNull.s2SNull(truckValue));
				familyCapital.setOtherTricycle(AppNull.s2INull(otherTricycle));
				familyCapital.setNongMachine(AppNull.s2INull(nongMachine));
				familyCapital.setNongMachineRemark(AppNull.s2SNull(nongMachineRemark));
				familyCapital.setNongMachineEstimatedValue(AppNull.s2SNull(nongMachineEstimatedValue));
				familyCapital.setTotalFamilyCapital(caculateSum(familyCapital, 1));
				familyCapital.setTotalFamilyDebtedness(caculateSum(familyCapital, 2));
				familyCapital.setUpdDate(DateUtils.getNowDate());
				familyCapital.setId(UUIDUtil.getUUID());
				familyCapital.setCreOperId(loginId);
				familyCapital.setCreDate(DateUtils.getNowDate());
				familyCapital.setUpdOperId(loginId);
				familyCapital.setUpdDate(DateUtils.getNowDate());
				familyCapitalService.insert(familyCapital);
			}else{
				fc.setCar(AppNull.s2INull(car));
				fc.setCarName(AppNull.s2SNull(carName));
				fc.setCarValue(AppNull.s2SNull(carValue));
				fc.setMinibus(AppNull.s2INull(minibus));
				fc.setMinibusValue(AppNull.s2SNull(minibusValue));
				fc.setTruck(AppNull.s2INull(truck));
				fc.setTruckValue(AppNull.s2SNull(truckValue));
				fc.setOtherTricycle(AppNull.s2INull(otherTricycle));
				fc.setNongMachine(AppNull.s2INull(nongMachine));
				fc.setNongMachineRemark(AppNull.s2SNull(nongMachineRemark));
				fc.setNongMachineEstimatedValue(AppNull.s2SNull(nongMachineEstimatedValue));
				fc.setTotalFamilyCapital(caculateSum(fc, 1));
				fc.setTotalFamilyDebtedness(caculateSum(fc, 2));
				fc.setUpdOperId(loginId);
				fc.setUpdDate(DateUtils.getNowDate());
				familyCapitalService.updateByPrimaryKey(fc);
			}
			retJson.put("message", "保存成功！");
			retJson.put("errno", 0);
			response.setStatus(200);
			return retJson;
		} catch (Exception e) {
			retJson.put("message", "保存失败！");
			response.setStatus(400);
			e.printStackTrace();
		}
		return retJson;
	}
	
	@RequestMapping("/carInfo")
	@ResponseBody
	public JSONObject carInfo(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String intoPieceId = request.getParameter("intoPieceId");
		if(StringUtils.isEmpty(intoPieceId)){
			retJson.put("message", "家庭财产进件标识为空！");
			retJson.put("errno", 3001);
			response.setStatus(400);
			return retJson;
		}
		try {
			BusFamilyCapital familyCapital = new BusFamilyCapital();
			familyCapital.setIntoPieceId(intoPieceId);
			familyCapital.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			BusFamilyCapital fc = familyCapitalService.selectByIntoPieceId(familyCapital);
			if(fc==null){
				fc=new BusFamilyCapital();
			}
			JSONObject obj = new JSONObject();
			obj.put("car", AppNull.o2SNotNull(fc.getCar()) );
			obj.put("carName", AppNull.o2SNotNull(fc.getCarName()));
			obj.put("carValue", AppNull.o2SNotNull(fc.getCarValue()));
			obj.put("minibus", AppNull.o2SNotNull(fc.getMinibus()));
			obj.put("minibusValue", AppNull.o2SNotNull(fc.getMinibusValue()));
			obj.put("truck", AppNull.o2SNotNull(fc.getTruck()));
			obj.put("truckValue", AppNull.o2SNotNull(fc.getTruckValue()));
			obj.put("otherTricycle", AppNull.o2SNotNull(fc.getOtherTricycle()));
			obj.put("nongMachine", AppNull.o2SNotNull(fc.getNongMachine()));
			obj.put("nongMachineRemark", AppNull.o2SNotNull(fc.getNongMachineRemark()));
			obj.put("nongMachineEstimatedValue", AppNull.o2SNotNull(fc.getNongMachineEstimatedValue()));
			retJson.put("data", obj);
			retJson.put("errno", 0);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retJson;
	}
	
	/**
	 * 
	 * @Title: landSave 
	 * @Description: 土地保存
	 * @param @param request
	 * @param @return     
	 * @return JSONObject     
	 * @throws
	 */
	@RequestMapping("/landSave")
	@ResponseBody
	public JSONObject landSave(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String intoPieceId = request.getParameter("intoPieceId");
		if(StringUtils.isEmpty(intoPieceId)){
			retJson.put("message", "家庭财产进件标识为空！");
			retJson.put("errno", 3001);
			response.setStatus(400);
			return retJson;
		}
		String loginId = (String) request.getAttribute("loginId");
		try {
//			String selLandType = request.getParameter("selLandType");
//			String selLandNum = request.getParameter("selLandNum");
//			String certLandType = request.getParameter("certLandType");
//			String certLandNum = request.getParameter("certLandNum");
			String landCertId = request.getParameter("landCertId");
			String waterFarmlandRegistered = request.getParameter("waterFarmlandRegistered");
			String dryFarmlandRegistered = request.getParameter("dryFarmlandRegistered");
			String waterFarmlandUnregistered = request.getParameter("waterFarmlandUnregistered");
			String dryFarmlandUnregistered = request.getParameter("dryFarmlandUnregistered");
			String waterFarmlandContract = request.getParameter("waterFarmlandContract");
			String dryFarmlandContract = request.getParameter("dryFarmlandContract");
			BusFamilyCapital familyCapital = new BusFamilyCapital();
			familyCapital.setIntoPieceId(intoPieceId);
			familyCapital.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			BusFamilyCapital fc = familyCapitalService.selectByIntoPieceId(familyCapital);
//			if(StringUtils.isNoneEmpty(selLandNum)){
//				if(!isNumeric(selLandNum)){
//					retJson.put("message", "自有土地请填写数字！");
//					response.setStatus(400);
//					return retJson;
//				}
//			}
//			if(StringUtils.isNoneEmpty(certLandNum)){
//				if(!isNumeric(certLandNum)){
//					retJson.put("message", "承包土地请填写数字！");
//					response.setStatus(400);
//					return retJson;
//				}
//			}
			if(StringUtils.isNoneEmpty(waterFarmlandRegistered)){
				if(!isNumeric(waterFarmlandRegistered)){
					retJson.put("message", "在册水田请填写数字！");
					response.setStatus(400);
					return retJson;
				}
			}
			if(StringUtils.isNoneEmpty(dryFarmlandRegistered)){
				if(!isNumeric(dryFarmlandRegistered)){
					retJson.put("message", "在册旱地请填写数字！");
					response.setStatus(400);
					return retJson;
				}
			}
			if(request.getHeader("channel").equals("SX")){
				if(StringUtils.isEmpty(landCertId)){
					retJson.put("message", "土地经营权证书编号必填！");
					response.setStatus(400);
					return retJson;
				}
				if(StringUtils.isEmpty(dryFarmlandRegistered)){
					retJson.put("message", "在册旱地必填！");
					response.setStatus(400);
					return retJson;
				}
				if(StringUtils.isEmpty(waterFarmlandRegistered)){
					retJson.put("message", "在册水田必填！");
					response.setStatus(400);
					return retJson;
				}
			}
			if(StringUtils.isNoneEmpty(waterFarmlandUnregistered)){
				if(!isNumeric(waterFarmlandUnregistered)){
					retJson.put("message", "册外水田请填写数字！");
					response.setStatus(400);
					return retJson;
				}
			}
			if(StringUtils.isNoneEmpty(dryFarmlandUnregistered)){
				if(!isNumeric(dryFarmlandUnregistered)){
					retJson.put("message", "册外旱地请填写数字！");
					response.setStatus(400);
					return retJson;
				}
			}
			if(StringUtils.isNoneEmpty(waterFarmlandContract)){
				if(!isNumeric(waterFarmlandContract)){
					retJson.put("message", "承包水田请填写数字！");
					response.setStatus(400);
					return retJson;
				}
			}
			if(StringUtils.isNoneEmpty(dryFarmlandContract)){
				if(!isNumeric(dryFarmlandContract)){
					retJson.put("message", "承包旱地请填写数字！");
					response.setStatus(400);
					return retJson;
				}
			}
			if(fc==null){
//				familyCapital.setSelLandType(AppNull.s2INull(selLandType));
//				familyCapital.setSelLandNum(AppNull.s2SNull(selLandNum));
//				familyCapital.setCertLandType(AppNull.s2INull(certLandType));
//				familyCapital.setCertLandNum(AppNull.s2SNull(certLandNum));
				familyCapital.setLandCertId(AppNull.s2SNull(landCertId));
				familyCapital.setWaterFarmlandRegistered(AppNull.s2SNull(waterFarmlandRegistered));
				familyCapital.setDryFarmlandRegistered(AppNull.s2SNull(dryFarmlandRegistered));
				familyCapital.setWaterFarmlandUnregistered(AppNull.s2SNull(waterFarmlandUnregistered));
				familyCapital.setDryFarmlandUnregistered(AppNull.s2SNull(dryFarmlandUnregistered));
				familyCapital.setWaterFarmlandContract(AppNull.s2SNull(waterFarmlandContract));
				familyCapital.setDryFarmlandContract(AppNull.s2SNull(dryFarmlandContract));
				familyCapital.setTotalFamilyCapital(caculateSum(familyCapital, 1));
				familyCapital.setTotalFamilyDebtedness(caculateSum(familyCapital, 2));
				familyCapital.setUpdDate(DateUtils.getNowDate());
				familyCapital.setId(UUIDUtil.getUUID());
				familyCapital.setCreOperId(loginId);
				familyCapital.setCreDate(DateUtils.getNowDate());
				familyCapital.setUpdOperId(loginId);
				familyCapital.setUpdDate(DateUtils.getNowDate());
				familyCapitalService.insert(familyCapital);
			}else{
//				fc.setSelLandType(AppNull.s2INull(selLandType));
//				fc.setSelLandNum(AppNull.s2SNull(selLandNum));
//				fc.setCertLandType(AppNull.s2INull(certLandType));
//				fc.setCertLandNum(AppNull.s2SNull(certLandNum));
				fc.setLandCertId(AppNull.s2SNull(landCertId));
				fc.setWaterFarmlandRegistered(AppNull.s2SNull(waterFarmlandRegistered));
				fc.setDryFarmlandRegistered(AppNull.s2SNull(dryFarmlandRegistered));
				fc.setWaterFarmlandUnregistered(AppNull.s2SNull(waterFarmlandUnregistered));
				fc.setDryFarmlandUnregistered(AppNull.s2SNull(dryFarmlandUnregistered));
				fc.setWaterFarmlandContract(AppNull.s2SNull(waterFarmlandContract));
				fc.setDryFarmlandContract(AppNull.s2SNull(dryFarmlandContract));
				fc.setTotalFamilyCapital(caculateSum(fc, 1));
				fc.setTotalFamilyDebtedness(caculateSum(fc, 2));
				fc.setUpdOperId(loginId);
				fc.setUpdDate(DateUtils.getNowDate());
				familyCapitalService.updateByPrimaryKey(fc);
			}
			retJson.put("message", "保存成功！");
			retJson.put("errno", 0);
			response.setStatus(200);
			return retJson;
		} catch (Exception e) {
			retJson.put("message", "保存失败！");
			response.setStatus(400);
			e.printStackTrace();
		}
		return retJson;
	}
	
	@RequestMapping("/landInfo")
	@ResponseBody
	public JSONObject landInfo(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String intoPieceId = request.getParameter("intoPieceId");
		if(StringUtils.isEmpty(intoPieceId)){
			retJson.put("message", "家庭财产进件标识为空！");
			retJson.put("errno", 3001);
			response.setStatus(400);
			return retJson;
		}
		try {
			BusFamilyCapital familyCapital = new BusFamilyCapital();
			familyCapital.setIntoPieceId(intoPieceId);
			familyCapital.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			BusFamilyCapital fc = familyCapitalService.selectByIntoPieceId(familyCapital);
			if(fc==null){
				fc=new BusFamilyCapital();
			}
			JSONObject obj = new JSONObject();
//			obj.put("selLandType", AppNull.o2SNotNull(fc.getSelLandType()) );
//			obj.put("selLandNum", AppNull.o2SNotNull(fc.getSelLandNum()));
//			obj.put("certLandType", AppNull.o2SNotNull(fc.getCertLandType()));
//			obj.put("certLandNum", AppNull.o2SNotNull(fc.getCertLandNum()));
			obj.put("landCertId", AppNull.o2SNotNull(fc.getLandCertId()));
			obj.put("waterFarmlandRegistered", AppNull.o2SNotNull(fc.getWaterFarmlandRegistered()));
			obj.put("dryFarmlandRegistered", AppNull.o2SNotNull(fc.getDryFarmlandRegistered()));
			obj.put("waterFarmlandUnregistered", AppNull.o2SNotNull(fc.getWaterFarmlandUnregistered()));
			obj.put("dryFarmlandUnregistered", AppNull.o2SNotNull(fc.getDryFarmlandUnregistered()));
			obj.put("waterFarmlandContract", AppNull.o2SNotNull(fc.getWaterFarmlandContract()));
			obj.put("dryFarmlandContract", AppNull.o2SNotNull(fc.getDryFarmlandContract()));
			retJson.put("data", obj);
			retJson.put("errno", 0);
			System.err.println(retJson);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retJson;
	}
	
	/**
	 * 
	 * @Title: saveDebt 
	 * @Description:保存负债情况 
	 * @param @param request
	 * @param @param response
	 * @param @return     
	 * @return JSONObject     
	 * @throws
	 */
	@RequestMapping("/saveDebt")
	@ResponseBody
	public JSONObject saveDebt(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String intoPieceId = request.getParameter("intoPieceId");
		if(StringUtils.isEmpty(intoPieceId)){
			retJson.put("message", "家庭财产进件标识为空！");
			retJson.put("errno", 3001);
			response.setStatus(400);
			return retJson;
		}
		String loginId = (String) request.getAttribute("loginId");
		try {
			String bankLoan = request.getParameter("bankLoan");
			String bankLoanIndebtedness = request.getParameter("bankLoanIndebtedness");
			String p2pPettyLoan = request.getParameter("p2pPettyLoan");
			String p2pPettyLoanIndebtedness = request.getParameter("p2pPettyLoanIndebtedness");
			String friendLoan = request.getParameter("friendLoan");
			String friendLoanIndebtedness = request.getParameter("friendLoanIndebtedness");
			String guaranteeLoan = request.getParameter("guaranteeLoan");
			String guaranteeLoanIndebtedness = request.getParameter("guaranteeLoanIndebtedness");
			String mainInvest = request.getParameter("mainInvest");
			String invisibleDebtedness = request.getParameter("invisibleDebtedness");
			BusFamilyCapital familyCapital = new BusFamilyCapital();
			familyCapital.setIntoPieceId(intoPieceId);
			familyCapital.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			BusFamilyCapital fc = familyCapitalService.selectByIntoPieceId(familyCapital);
			if(fc==null){
				familyCapital.setBankLoan(AppNull.s2INull(bankLoan));
				familyCapital.setBankLoanIndebtedness(AppNull.s2SNull(bankLoanIndebtedness));
				familyCapital.setP2pPettyLoan(AppNull.s2INull(p2pPettyLoan));
				familyCapital.setP2pPettyLoanIndebtedness(AppNull.s2SNull(p2pPettyLoanIndebtedness));
				familyCapital.setFriendLoan(AppNull.s2INull(friendLoan));
				familyCapital.setFriendLoanIndebtedness(AppNull.s2SNull(friendLoanIndebtedness));
				familyCapital.setGuaranteeLoan(AppNull.s2INull(guaranteeLoan));
				familyCapital.setGuaranteeLoanIndebtedness(AppNull.s2SNull(guaranteeLoanIndebtedness));
				familyCapital.setMainInvest(AppNull.s2INull(mainInvest));
				familyCapital.setInvisibleDebtedness(AppNull.s2SNull(invisibleDebtedness));
				familyCapital.setTotalFamilyCapital(caculateSum(familyCapital, 1));
				familyCapital.setTotalFamilyDebtedness(caculateSum(familyCapital, 2));
				familyCapital.setUpdDate(DateUtils.getNowDate());
				familyCapital.setId(UUIDUtil.getUUID());
				familyCapital.setCreOperId(loginId);
				familyCapital.setCreDate(DateUtils.getNowDate());
				familyCapital.setUpdOperId(loginId);
				familyCapital.setUpdDate(DateUtils.getNowDate());
				familyCapitalService.insert(familyCapital);
			}else{
				fc.setBankLoan(AppNull.s2INull(bankLoan));
				fc.setBankLoanIndebtedness(AppNull.s2SNull(bankLoanIndebtedness));
				fc.setP2pPettyLoan(AppNull.s2INull(p2pPettyLoan));
				fc.setP2pPettyLoanIndebtedness(AppNull.s2SNull(p2pPettyLoanIndebtedness));
				fc.setFriendLoan(AppNull.s2INull(friendLoan));
				fc.setFriendLoanIndebtedness(AppNull.s2SNull(friendLoanIndebtedness));
				fc.setGuaranteeLoan(AppNull.s2INull(guaranteeLoan));
				fc.setGuaranteeLoanIndebtedness(AppNull.s2SNull(guaranteeLoanIndebtedness));
				fc.setMainInvest(AppNull.s2INull(mainInvest));
				fc.setInvisibleDebtedness(AppNull.s2SNull(invisibleDebtedness));
				fc.setTotalFamilyCapital(caculateSum(fc, 1));
				fc.setTotalFamilyDebtedness(caculateSum(fc, 2));
				fc.setUpdOperId(loginId);
				fc.setUpdDate(DateUtils.getNowDate());
				familyCapitalService.updateByPrimaryKey(fc);
			}
			retJson.put("message", "保存成功！");
			retJson.put("errno", 0);
			response.setStatus(200);
			return retJson;
		} catch (Exception e) {
			retJson.put("message", "保存失败！");
			response.setStatus(400);
			e.printStackTrace();
		}
		return retJson;
	}
	
	@RequestMapping("/debtInfo")
	@ResponseBody
	public JSONObject debtInfo(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String intoPieceId = request.getParameter("intoPieceId");
		if(StringUtils.isEmpty(intoPieceId)){
			retJson.put("message", "家庭财产进件标识为空！");
			retJson.put("errno", 3001);
			response.setStatus(400);
			return retJson;
		}
		try {
			BusFamilyCapital familyCapital = new BusFamilyCapital();
			familyCapital.setIntoPieceId(intoPieceId);
			familyCapital.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			BusFamilyCapital fc = familyCapitalService.selectByIntoPieceId(familyCapital);
			if(fc==null){
				fc=new BusFamilyCapital();
			}
			JSONObject obj = new JSONObject();
			obj.put("bankLoan", AppNull.o2SNotNull(fc.getBankLoan()) );
			obj.put("bankLoanIndebtedness", AppNull.o2SNotNull(fc.getBankLoanIndebtedness()));
			obj.put("p2pPettyLoan", AppNull.o2SNotNull(fc.getP2pPettyLoan()));
			obj.put("p2pPettyLoanIndebtedness", AppNull.o2SNotNull(fc.getP2pPettyLoanIndebtedness()));
			obj.put("friendLoan", AppNull.o2SNotNull(fc.getFriendLoan()));
			obj.put("friendLoanIndebtedness", AppNull.o2SNotNull(fc.getFriendLoanIndebtedness()));
			obj.put("guaranteeLoan", AppNull.o2SNotNull(fc.getGuaranteeLoan()));
			obj.put("guaranteeLoanIndebtedness", AppNull.o2SNotNull(fc.getGuaranteeLoanIndebtedness()));
			obj.put("invisibleDebtedness", AppNull.o2SNotNull(fc.getInvisibleDebtedness()));
			obj.put("mainInvest", AppNull.o2SNotNull(fc.getMainInvest()));
			retJson.put("data", obj);
			retJson.put("errno", 0);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retJson;
	}
	
	
	/**
	 * 
	 * @Title: caculateSum 
	 * @Description: 根据家庭资产填写的金额计算家庭资产总额或家庭负债总额
	 * @param @param familyCapital 家庭资产及负债对象
	 * @param @param type 类型，计算资产为1，计算负债为2
	 * @param @return     
	 * @return String     
	 * @throws
	 */
	public  String caculateSum(BusFamilyCapital familyCapital, Integer type){
		BigDecimal sum = new BigDecimal("0");
		if(type==1){
			String smallPropertyHouseValue = familyCapital.getSmallPropertyHouseValue();
			String bigPropertyHouseValue = familyCapital.getBigPropertyHouseValue();
			String carValue = familyCapital.getCarValue();
			String minibusValue = familyCapital.getMinibusValue();
			String truckValue = familyCapital.getTruckValue();
			String nongMachineEstimatedValue = familyCapital.getNongMachineEstimatedValue();
			List<String> list = new ArrayList<String>();
			list.add(smallPropertyHouseValue);
			list.add(bigPropertyHouseValue);
			list.add(carValue);
			list.add(minibusValue);
			list.add(truckValue);
			list.add(nongMachineEstimatedValue);
			sum = addValues(sum, list);
		}else{
			String bankLoanIndebtedness = familyCapital.getBankLoanIndebtedness();
			String p2pPettyLoanIndebtedness = familyCapital.getP2pPettyLoanIndebtedness();
			String friendLoanIndebtedness = familyCapital.getFriendLoanIndebtedness();
			String guaranteeLoanIndebtedness = familyCapital.getGuaranteeLoanIndebtedness();
			List<String> list = new ArrayList<String>();
			list.add(bankLoanIndebtedness);
			list.add(p2pPettyLoanIndebtedness);
			list.add(friendLoanIndebtedness);
			list.add(guaranteeLoanIndebtedness);
			sum = addValues(sum, list);
		}
		
		return sum.toString();
	}
	
	public  BigDecimal addValues(BigDecimal sum,List<String> value){
		for (String string : value) {
			if(!StringUtils.isEmpty(string)){
				sum= sum.add(new BigDecimal(string));
			}
		}
		return sum;
	}
	
	public boolean isNumeric(String str){
		// 就是判断是否为整数(正负)
		Pattern pattern = 
			Pattern.compile("^\\d+$|-\\d+$"); 
		//判断是否为小数(正负)
		Pattern pattern2 = 
			Pattern.compile("\\d+\\.\\d+$|-\\d+\\.\\d+$");
		return (pattern.matcher(str).matches() 
			|| pattern2.matcher(str).matches());
	}
	
	@RequestMapping("/saveLand")
	@ResponseBody
	public JSONObject saveLand(HttpServletRequest request,BusFamilyCapital familyCapital,HttpServletResponse response) throws Exception{
		JSONObject json = new JSONObject();
		if(familyCapital==null){
			json.put("message", "家庭财产信息为空");
			response.setStatus(400);
			return json;
		}
		if(StrUtils.isEmpty(familyCapital.getIntoPieceId())){
			json.put("message", "家庭财产进件标识为空");
			response.setStatus(400);
			return json;
		}
		try {
			familyCapital.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			BusFamilyCapital fc = familyCapitalService.selectByIntoPieceId(familyCapital);
			if(fc == null){
				familyCapital.setId(UUIDUtil.getUUID());
				familyCapital.setCreOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
				familyCapital.setCreOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
				familyCapital.setCreOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
				familyCapital.setCreDate(new Date());
				familyCapital.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
				familyCapital.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
				familyCapital.setUpdOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
				familyCapital.setUpdDate(new Date());
				familyCapitalService.insert(familyCapital);
			}else{
				fc.setLandCertId(familyCapital.getLandCertId());
				fc.setWaterFarmlandContract(familyCapital.getWaterFarmlandContract());
				fc.setWaterFarmlandRegistered(familyCapital.getWaterFarmlandRegistered());
				fc.setWaterFarmlandUnregistered(familyCapital.getWaterFarmlandUnregistered());
				fc.setDryFarmlandContract(familyCapital.getDryFarmlandContract());
				fc.setDryFarmlandRegistered(familyCapital.getDryFarmlandRegistered());
				fc.setDryFarmlandUnregistered(familyCapital.getDryFarmlandUnregistered());
				familyCapital.setUpdDate(new Date());
				familyCapitalService.updateByPrimaryKey(fc);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("message", "保存出错");
			response.setStatus(400);
			return json;
		}
		json.put("message", "保存成功");
		response.setStatus(200);
		return json;
	}
	
//	@RequestMapping("/landInfo")
//	@ResponseBody
//	public JSONObject landInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
//		JSONObject json = new JSONObject();
//		try {
//			request.getParameter("");
//		} catch (Exception e) {
//			e.printStackTrace();
//			json.put("message", "保存出错");
//			response.setStatus(400);
//			return json;
//		}
//		json.put("message", "保存成功");
//		response.setStatus(200);
//		return json;
//	}
	
}
