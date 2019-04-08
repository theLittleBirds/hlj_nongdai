package com.nongyeos.loan.admin.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusLoan;
import com.nongyeos.loan.admin.entity.BusStationBond;
import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.admin.service.ILoanService;
import com.nongyeos.loan.admin.service.IMediaService;
import com.nongyeos.loan.admin.service.IOrgService;
import com.nongyeos.loan.admin.service.IStationBondService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.QiNiuUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/union")
public class UnionManageController {
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@Autowired
	private IMediaService mediaService;
	
	@Autowired
	private ILoanService loanService;
	
	@Autowired
	private IStationBondService stationBondService;
	
	@Resource
	private IOrgService orgService;
	
	
	@RequestMapping("/list")
	@ResponseBody
	public PageBeanUtil<BusIntoPiece> list(int currentPage, int pageSize,HttpServletRequest request) throws Exception {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String orderStatus = request.getParameter("orderStatus");
			if (StrUtils.isNotEmpty(orderStatus))
				map.put("orderStatus", orderStatus);
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
			return intoPieceService.selectNongZiPage(currentPage, pageSize, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping("/waitpay")
	public String waitPay(){
		return "union/waitpay";
	}
	
	@RequestMapping("/underlinepay")
	public ModelAndView underLinePay(String intoPieceId){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("union/underlinepay");
		mv.addObject("intoPieceId", intoPieceId);
		try {
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
			mv.addObject("money", ip.getRecieveCash());
			mv.addObject("token", QiNiuUtil.upToken());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	@RequestMapping("/underlinepaysave")
	@ResponseBody
	public JSONObject underLinePaySave(HttpServletRequest request,String intoPieceId,String paymentDate){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(intoPieceId)){
			json.put("code", 400);
			json.put("msg", "进件标识为空");	
			return json;
		}
		if(StrUtils.isEmpty(paymentDate)){
			json.put("code", 400);
			json.put("msg", "打款时间为空");	
			return json;
		}
		try {		
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);			
			if(ip == null){
				json.put("code", 400);
				json.put("msg", "查询不到进件信息");	
				return json;
			}
			SysOrg org = orgService.selectByOrgId(ip.getOrgId());
			if(StrUtils.isEmpty(org.getWarrantRate())){
				json.put("code", 400);
				json.put("msg", "请联系管理员设置农资担保费");	
				return json;
			}
			ip.setOrderStatus(2);
			ip.setUpdDate(new Date());
			ip.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
			intoPieceService.updateByPrimaryKey(ip);
			
			BusLoan loan = loanService.selectByIpId(intoPieceId);
			if(loan == null){
				json.put("code", 400);
				json.put("msg", "查询不到进件信息");	
				return json;
			}
			loan.setRemitDate(paymentDate);
			loan.setUpdDate(new Date());
			loan.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
			loanService.updateByPrimaryKey(loan);
			BusStationBond sb = stationBondService.selectByIpId(intoPieceId);
			if(sb == null){
				sb = new BusStationBond();
				sb.setIntoPieceId(intoPieceId);
				sb.setOrgId(ip.getOrgId());
				sb.setMemberName(ip.getMemberName());
				sb.setIdCard(ip.getIdCard());
				sb.setCapital(loan.getCapital());
				sb.setRecieveNongZi(new BigDecimal(loan.getRecieveNongZi()));	
				sb.setBond(new BigDecimal(org.getWarrantRate()).multiply(new BigDecimal("0.01")).multiply(new BigDecimal(loan.getRecieveNongZi())));	
			}
			sb.setUpdDate(new Date());
			sb.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
			if(sb.getId() == null){
				sb.setId(UUIDUtil.getUUID());
				stationBondService.insert(sb);
			}else{
				stationBondService.updateByPrimaryKey(sb);
			}
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
