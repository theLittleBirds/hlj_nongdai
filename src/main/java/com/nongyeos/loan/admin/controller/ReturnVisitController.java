package com.nongyeos.loan.admin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusReturnVisit;
import com.nongyeos.loan.admin.service.impl.ReturnVisitServiceImpl;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;
@Controller
@RequestMapping("/returnvisit")
public class ReturnVisitController {
	
	@Autowired
	private ReturnVisitServiceImpl returnVisitService;
	
	@RequestMapping("/loanid")
	public ModelAndView page(String id) throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.addObject("id",id);
		mv.setViewName("postLending/returnvisit");
		if(StrUtils.isEmpty(id))
			throw new Exception("借款标识为空");
		try {
			List<BusReturnVisit> list = returnVisitService.selectByLoanId(id);
			mv.addObject("visitList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JSONObject save(HttpServletRequest request,String loanId,String content){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(loanId)){
			json.put("code", 400);
			json.put("msg", "借款标识为空");
			return json;
		}
		if(StrUtils.isEmpty(content)){
			json.put("code", 400);
			json.put("msg", "回访记录为空");
			return json;
		}
		try {
			BusReturnVisit rv = new BusReturnVisit();
			rv.setId(UUIDUtil.getUUID());
			rv.setContent(content);
			rv.setLoanId(loanId);
			rv.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");			
			rv.setCreDate(df.format(new Date()));
			rv.setCreOperId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			rv.setCreOperName(request.getSession().getAttribute(Constants.SESSION_PERSONNM).toString());
			returnVisitService.insert(rv);
			json.put("code", 200);
			json.put("msg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", "保存失败");
		}
		return json;
	}
}
