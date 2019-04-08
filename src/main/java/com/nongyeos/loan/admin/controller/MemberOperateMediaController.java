package com.nongyeos.loan.admin.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusMemberOperateMedia;
import com.nongyeos.loan.admin.service.IMemberOperateMediaService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.QiNiuUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/memberoperatemedia")
public class MemberOperateMediaController {
	
	@Autowired
	private IMemberOperateMediaService memberOperateMediaService;
	
	@RequestMapping("/form")
	@ResponseBody
	public ModelAndView form(String id){
		ModelAndView mv = new ModelAndView();
		mv.addObject("id", id);
		mv.addObject("token", QiNiuUtil.upToken());
		mv.setViewName("memberOperate/image");
		return mv;
	}
	
	/**
	 * 加载图片
	 * @param id
	 * @return
	 */
	@RequestMapping("/loadimage")
	@ResponseBody
	public JSONArray loadImage(String id){
		if(StrUtils.isEmpty(id))
			return null;
		try {
			 List<BusMemberOperateMedia> list = memberOperateMediaService.selectByMOId(id);
			 if(list.size() == 0)
				 return null;
			 return QiNiuUtil.getUrlEndJpg(list);			 
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JSONObject save(HttpServletRequest request,String hashKey,String id){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(id)){
			json.put("code", 400);
			json.put("msg", "客户标识为空");
			return json;
		}
		try {
			BusMemberOperateMedia m = new BusMemberOperateMedia();
			m.setQiniuKey(hashKey);
			m.setMemberOperateId(id);
			m.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			if(memberOperateMediaService.existenceByKey(m) >0){
				json.put("code", 400);
				json.put("msg", "图片已存在");
				return json;
			}
			m.setId(UUIDUtil.getUUID());
			m.setType(Constants.MEDIATYPE_IMAGE);
			m.setCreDate(new Date());
			m.setCreOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			m.setUpdDate(new Date());
			m.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			memberOperateMediaService.insert(m);
			json.put("code", 200);
			json.put("msg", "保存成功");
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
			return json;
		}
	}
}
