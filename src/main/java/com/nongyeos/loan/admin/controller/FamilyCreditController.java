package com.nongyeos.loan.admin.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusFamilyCredit;
import com.nongyeos.loan.admin.entity.BusFamilySituation;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.service.IFamilyCreditService;
import com.nongyeos.loan.admin.service.IFamilySituationService;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/familycredit")
public class FamilyCreditController {
	
	@Autowired
	private IFamilyCreditService familyCreditService;
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@Autowired
	private IFamilySituationService familySituationService;
	
	@RequestMapping("/form")
	@ResponseBody
	public BusFamilyCredit form(String intoPieceId) throws Exception{
		if(StrUtils.isEmpty(intoPieceId)){
			return null;
		}
		try {
			BusFamilyCredit fc = new BusFamilyCredit();
			fc.setIntoPieceId(intoPieceId);
			fc.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			return familyCreditService.selectByIpId(fc);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JSONObject save(HttpServletRequest request,BusFamilyCredit familyCredit){
		JSONObject json = new JSONObject();
		if(familyCredit == null){
			json.put("msg", "家庭财产模板为空");
			json.put("code", 400);
			return json;
		}
		if(StrUtils.isEmpty(familyCredit.getIntoPieceId())){
			json.put("msg", "家庭财产进件标识为空");
			json.put("code", 400);
			return json;
		}
		try {
			familyCredit.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			BusFamilyCredit fc = familyCreditService.selectByIpId(familyCredit);
			//新增
			if(fc == null){
				familyCredit.setId(UUIDUtil.getUUID());
				familyCredit.setCreOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
				familyCredit.setCreOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
				familyCredit.setCreOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
				familyCredit.setCreDate(new Date());
				familyCredit.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
				familyCredit.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
				familyCredit.setUpdOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
				familyCredit.setUpdDate(new Date());
				familyCreditService.insert(familyCredit);
			}else{
				fc.setIsWhite(familyCredit.getIsWhite());
				fc.setHasOverdueCurrent(familyCredit.getHasOverdueCurrent());
				fc.setHasOverdueOutNinetyDay(familyCredit.getHasOverdueOutNinetyDay());
				fc.setLoanTimesWithFiveYear(familyCredit.getLoanTimesWithFiveYear());
				fc.setNinetyDayOverdueMoney(familyCredit.getNinetyDayOverdueMoney());
				fc.setOrgSearchTimesWithCredit(familyCredit.getOrgSearchTimesWithCredit());
				fc.setOverdueTimes(familyCredit.getOverdueTimes());
				fc.setSelSearchTimesWithCredit(familyCredit.getSelSearchTimesWithCredit());
				fc.setNegativeInformation(familyCredit.getNegativeInformation());
				fc.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
				fc.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
				fc.setUpdOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
				fc.setUpdDate(new Date());
				familyCreditService.updateByPrimaryKey(fc);
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
	
	@RequestMapping("/thirdpartycredit")
	@ResponseBody
	public JSONArray thirdPartyCredit(String intoPieceId){
		if(StrUtils.isEmpty(intoPieceId)){
			return null;
		}
		try {
			JSONArray arr = new JSONArray();
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
			if(ip != null){
				JSONObject json = new JSONObject();
				json.put("name", ip.getMemberName());
				json.put("idCard", ip.getIdCard());
				json.put("phone", ip.getPhone());
				json.put("bankNo", ip.getBankCardNo());
				arr.add(json);
			}
			List<BusFamilySituation> list = familySituationService.thirdpartycredit(intoPieceId);
			for (int i = 0; i < list.size(); i++) {
				JSONObject json = new JSONObject();
				json.put("name", list.get(i).getName());
				json.put("idCard", list.get(i).getIdCard());
				json.put("phone", list.get(i).getPhone());
				json.put("bankNo", "");
				arr.add(json);
			}
			return arr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
