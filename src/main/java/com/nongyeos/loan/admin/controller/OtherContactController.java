package com.nongyeos.loan.admin.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusOtherContact;
import com.nongyeos.loan.admin.service.IOtherContactService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/othercontact")
public class OtherContactController {
	
	@Autowired
	private IOtherContactService otherContactService;
	
	@RequestMapping("/list")
	@ResponseBody
	public PageBeanUtil<BusOtherContact> list(int currentPage,int pageSize,String intoPieceId) throws Exception{
		if(StrUtils.isEmpty(intoPieceId))
			return null;
		BusOtherContact oc = new BusOtherContact();
		oc.setIntoPieceId(intoPieceId);
		oc.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
		try {
			return otherContactService.selectByPage(currentPage, pageSize, oc);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错了");
		}
		
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public JSONObject update(HttpServletRequest request,BusOtherContact oc){
		JSONObject json = new JSONObject();
		if(oc == null){
			json.put("msg", "联系人模板为空");
			json.put("code", 400);
			return json;
		}
		if(StrUtils.isEmpty(oc.getId())){
			json.put("msg", "联系人模板id为空");
			json.put("code", 400);
			return json;
		}
		try {
			oc.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
			oc.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
			oc.setUpdOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
			oc.setUpdDate(new Date());
			otherContactService.updateByPrimaryKeySelective(oc);
			json.put("msg", "保存成功");
			json.put("code", 200);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msg", "保存失败");
			json.put("code", 400);
			return json;
		}
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JSONObject save(HttpServletRequest request,BusOtherContact oc){
		JSONObject json = new JSONObject();
		if(oc == null){
			json.put("msg", "联系人模板为空");
			json.put("code", 400);
			return json;
		}
		try {
			if(StrUtils.isEmpty(oc.getId())){
				oc.setId(UUIDUtil.getUUID());
				oc.setCreOperId( request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
				oc.setCreOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
				oc.setCreOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
				oc.setCreDate(new Date());
				oc.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
				oc.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
				oc.setUpdOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
				oc.setUpdDate(new Date());
				oc.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
				otherContactService.insert(oc);
			}else{
				oc.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
				oc.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
				oc.setUpdOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
				oc.setUpdDate(new Date());
				otherContactService.updateByPrimaryKeySelective(oc);
			}
			json.put("msg", "保存成功");
			json.put("code", 200);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msg", "保存失败");
			json.put("code", 400);
			return json;
		}
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public JSONObject delete(HttpServletRequest request,String ids){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(ids)){
			json.put("msg", "联系人模板id为空");
			json.put("code", 400);
			return json;
		}
		try {
			String[] id = ids.split(",");
			for (int i = 0; i < id.length; i++) {
				BusOtherContact oc = new BusOtherContact();
				oc.setId(id[i]);
				oc.setIsDeleted(Constants.COMMON_IS_DELETE);
				otherContactService.updateByPrimaryKeySelective(oc);
			}
			json.put("msg", "删除成功");
			json.put("code", 200);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msg", "删除失败");
			json.put("code", 400);
			return json;
		}
	}
}
