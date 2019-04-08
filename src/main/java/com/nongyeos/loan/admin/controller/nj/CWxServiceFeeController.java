package com.nongyeos.loan.admin.controller.nj;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusGuaranteeFeeInfo;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusMessageReminder;
import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.model.IntoPieceConfig;
import com.nongyeos.loan.admin.service.IGuaranteeFeeInfoService;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.admin.service.IMessageReminderService;
import com.nongyeos.loan.admin.service.IOrgService;
import com.nongyeos.loan.admin.service.IPersonService;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.HttpClientUtil;
import com.nongyeos.loan.base.util.StrUtils;

@Controller
@RequestMapping("/nj/wxServiceFee")
public class CWxServiceFeeController {
	@Autowired
	private IWebUserService userService;
	@Autowired
	private IntoPieceConfig pieceConfig;
	@Autowired
	private IIntoPieceService intoPieceService;
	@Autowired
	private IGuaranteeFeeInfoService guaranteeFeeInfoService;
	@Autowired
	private IPersonService personService;
	
	@Autowired
	private IMessageReminderService messageReminderService;
	
	@RequestMapping("/firstServiceFeePay")
	@ResponseBody
	public JSONObject firstServiceFeePay(HttpServletRequest request ,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		try {
			String intoPieceId = request.getParameter("intoPieceId");
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
			
			String channel = ip.getChannel();
			if(StringUtils.isEmpty(channel)){
				retJson.put("message", "版本错误");
				response.setStatus(400);
				return retJson;
			}
			//微信支付服务费url
			String url = pieceConfig.getWxPayGuaranteeFeeCheck();
			SysWebUser user = userService.selectUserByUserName(pieceConfig.getUserName());
			BusGuaranteeFeeInfo guaranteeFeeInfo = guaranteeFeeInfoService.selectByIntopieceId(intoPieceId);
			SysPerson person = personService.selectByPersonId(ip.getOperUserId());
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", user.getUserId());
			String signature = user.getUserId() + user.getUsername() + user.getPassword();
			map.put("signature", DigestUtils.md5Hex(signature));
			map.put("companyType", channel);
			if(StringUtils.isNoneEmpty(request.getHeader("platform"))&& "wx".equals(request.getHeader("platform")) ){
				map.put("appcode", request.getParameter("appcode"));
			}
			map.put("guaranteeId", guaranteeFeeInfo.getId());
			map.put("amount", guaranteeFeeInfo.getTotalAmount().toString());
//			map.put("certificateNo", person.getCardNo());
			map.put("certificateNo", person.getCardNo());
			map.put("accountName", person.getNameCn());
			map.put("mobileNo", person.getMobile());
			map.put("intoPieceId", intoPieceId);
			System.err.println(map);
			String retMap = HttpClientUtil.doPost(url, map,
					 "utf-8");
			if(retMap!=null){
				 JSONObject retMap1 = JSONObject.parseObject(retMap);
				 if(retMap1==null||StringUtils.isEmpty(retMap1.getString("isSuccess"))||retMap1.getString("isSuccess").equals(Constants.GATE_RESULT_FAIL)){
					 retJson.put("message", "微信预支付订单生成失败");
					 response.setStatus(400);
					 return retJson;
				 }
			 }else{
				 retJson.put("message", "微信预支付订单生成失败");
				 response.setStatus(400);
				 return retJson;
			 }
			
			JSONObject retdata = JSONObject.parseObject(retMap);
			retJson.put("data", retdata.get("data"));
			retJson.put("message", "微信预支付订单生成成功");
			response.setStatus(200);
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "系统出错");
			response.setStatus(400);
			return retJson;
		}
	}
	
	
	@RequestMapping("/payComplete")
	@ResponseBody
	public JSONObject payComplete(HttpServletRequest request,HttpServletResponse response,String intoPieceId){
		JSONObject retJson = new JSONObject();
		try {
			if(StringUtils.isEmpty(intoPieceId)){
				retJson.put("message", "进件标识为空");
				response.setStatus(400);
				return retJson;
			}
			BusMessageReminder reminder = new BusMessageReminder();
			reminder.setIntoPieceId(intoPieceId);
			reminder.setType(Constants.WEI_XIN_SERVICE_PAY);
			reminder.setIsDelete(Constants.COMMON_ISNOT_DELETE);
			BusMessageReminder reminderhis = messageReminderService.queryMRByTypeAndDelete(reminder);
			if(reminderhis!=null){
				reminderhis.setIsDelete(Constants.COMMON_IS_DELETE);
				messageReminderService.updateByPrimaryKey(reminderhis);
			}
			retJson.put("message", "保存成功");
			response.setStatus(200);
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "网络错误");
			response.setStatus(400);
			return retJson;
		}
	}
	
}
