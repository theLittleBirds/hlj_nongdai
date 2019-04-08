package com.nongyeos.loan.admin.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusFamilyOperate;
import com.nongyeos.loan.admin.service.IFamilyOperateService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;
@Controller
@RequestMapping("/familyoperate")
public class FamilyOperateController {
	
	@Autowired
	private IFamilyOperateService familyOperateService;
	
	
	@RequestMapping("/getbyipid")
	@ResponseBody
	public JSONObject selectByIpId(String id){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(id)){
			json.put("code", 400);
			json.put("msg", "进件标识为空");
			return json;
		}
		try {
			json.put("obj", familyOperateService.selectByIpId(id));
			json.put("code", 200);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JSONObject save(HttpServletRequest request,BusFamilyOperate operate){
		JSONObject json = new JSONObject();
		if(operate == null){
			json.put("code", 400);
			json.put("msg", "经营情况模板为空");
			return json;
		}
		if(operate.getIntoPieceId() == null){
			json.put("code", 400);
			json.put("msg", "进件标识为空");
			return json;
		}
		try {
			operate.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			operate.setUpdDate(new Date());
			operate.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			operate.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
			operate.setUpdOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
			BusFamilyOperate check = familyOperateService.selectByIpId(operate.getIntoPieceId());
			if(check == null){
				operate.setId(UUIDUtil.getUUID());
				operate.setCreDate(new Date());
				operate.setCreOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
				operate.setCreOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
				operate.setCreOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
				familyOperateService.insert(operate);
			}else{
				operate.setId(check.getId());
				familyOperateService.updateByPrimaryKey(operate);
			}
			json.put("code", 200);
			json.put("msg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}
		return json;
	}
}
