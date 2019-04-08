package com.nongyeos.loan.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusStationBond;
import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.admin.service.IPersonService;
import com.nongyeos.loan.admin.service.IStationBondService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.QiNiuUtil;
import com.nongyeos.loan.base.util.StrUtils;

@Controller
@RequestMapping("/stationbond")
public class StationBondController {
	
	@Autowired
	private IStationBondService stationBondService;
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@Autowired
	private IPersonService personService;
	
	@RequestMapping("/list")
	@ResponseBody
	public PageBeanUtil<BusStationBond> list(int currentPage, int pageSize,HttpServletRequest request) throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String orgName = request.getParameter("orgName");
			if (StrUtils.isNotEmpty(orgName))
				map.put("fullCname", orgName);
			String memberName = request.getParameter("memberName");
			if (StrUtils.isNotEmpty(memberName))
				map.put("memberName", memberName);
			String idCard = request.getParameter("idCard");
			if (StrUtils.isNotEmpty(idCard))
				map.put("idCard", idCard);
			map.put("personId", request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			return stationBondService.selectByPage(currentPage, pageSize, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping("/listpage")
	public String waitPay(){
		return "union/stationbond";
	}
	
	@RequestMapping("/underlinepay")
	public ModelAndView underLinePay(String id){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("union/bondunderlinepay");
		try {
			BusStationBond sb = stationBondService.selectByPrimaryKey(id);
			mv.addObject("id", id);
			mv.addObject("intoPieceId", sb.getIntoPieceId());
			mv.addObject("money", sb.getBond());
			mv.addObject("token", QiNiuUtil.upToken());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	@RequestMapping("/underlinepaysave")
	@ResponseBody
	public JSONObject underLinePaySave(HttpServletRequest request,String id,String paymentDate){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(id)){
			json.put("code", 400);
			json.put("msg", "扣款标识为空");	
			return json;
		}
		if(StrUtils.isEmpty(paymentDate)){
			json.put("code", 400);
			json.put("msg", "打款时间为空");	
			return json;
		}
		try {	
			BusStationBond sb = stationBondService.selectByPrimaryKey(id);
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(sb.getIntoPieceId());			
			if(ip == null){
				json.put("code", 400);
				json.put("msg", "查询不到进件信息");	
				return json;
			}
			ip.setOrderStatus(3);
			ip.setUpdDate(new Date());
			ip.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
			intoPieceService.updateByPrimaryKey(ip);
			
			SysPerson person = personService.selectByPersonId(ip.getOperUserId());
			sb.setPayWay("3");	
			sb.setStatus("S");
			sb.setPayDate(paymentDate);
			sb.setUpdDate(new Date());
			sb.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
			if(person!=null)
				sb.setPayer(person.getNameCn());
			stationBondService.updateByPrimaryKey(sb);
			
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
