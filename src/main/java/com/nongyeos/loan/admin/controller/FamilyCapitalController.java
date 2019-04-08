package com.nongyeos.loan.admin.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusFamilyCapital;
import com.nongyeos.loan.admin.service.IFamilyCapitalService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/familycapital")
public class FamilyCapitalController {
	
	@Autowired
	private IFamilyCapitalService familyCapitalService;
	
	@RequestMapping("/selectByIntoPieceId")
	@ResponseBody
	public JSONObject selectByIntoPieceId(String intoPieceId) throws Exception{
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(intoPieceId)){
			json.put("msg", "进件标识为空");
			json.put("code", 400);
			return json;
		}
		BusFamilyCapital para = new BusFamilyCapital();
		para.setIntoPieceId(intoPieceId);
		para.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
		BusFamilyCapital fc = familyCapitalService.selectByIntoPieceId(para);
		json.put("familycapital", fc);
		json.put("code", 200);
		return json;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JSONObject save(HttpServletRequest request,BusFamilyCapital familyCapital) throws Exception{
		JSONObject json = new JSONObject();
		if(familyCapital==null){
			json.put("msg", "家庭财产信息为空");
			json.put("code", 400);
			return json;
		}
		if(StrUtils.isEmpty(familyCapital.getIntoPieceId())){
			json.put("msg", "家庭财产进件标识为空");
			json.put("code", 400);
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
				familyCapital.setLandCertId(fc.getLandCertId());
				familyCapital.setWaterFarmlandContract(fc.getWaterFarmlandContract());
				familyCapital.setWaterFarmlandRegistered(fc.getWaterFarmlandRegistered());
				familyCapital.setWaterFarmlandUnregistered(fc.getWaterFarmlandUnregistered());
				familyCapital.setDryFarmlandContract(fc.getDryFarmlandContract());
				familyCapital.setDryFarmlandRegistered(fc.getDryFarmlandRegistered());
				familyCapital.setDryFarmlandUnregistered(fc.getDryFarmlandUnregistered());
				familyCapital.setCreOperId(fc.getCreOperId());
				familyCapital.setCreOperName(fc.getCreOperName());
				familyCapital.setCreOrgId(fc.getCreOrgId());
				familyCapital.setCreDate(new Date());
				familyCapital.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
				familyCapital.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
				familyCapital.setUpdOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
				familyCapital.setUpdDate(new Date());
				familyCapital.setId(fc.getId());
				familyCapitalService.updateByPrimaryKey(familyCapital);
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msg", "保存出错");
			json.put("code", 400);
			return json;
		}
		json.put("msg", "保存成功");
		json.put("code", 200);
		return json;
	}
	@RequestMapping("/saveLand")
	@ResponseBody
	public JSONObject saveLand(HttpServletRequest request,BusFamilyCapital familyCapital) throws Exception{
		JSONObject json = new JSONObject();
		if(familyCapital==null){
			json.put("msg", "家庭财产信息为空");
			json.put("code", 400);
			return json;
		}
		if(StrUtils.isEmpty(familyCapital.getIntoPieceId())){
			json.put("msg", "家庭财产进件标识为空");
			json.put("code", 400);
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
			json.put("msg", "保存出错");
			json.put("code", 400);
			return json;
		}
		json.put("msg", "保存成功");
		json.put("code", 200);
		return json;
	}
}
