package com.nongyeos.loan.admin.controller.nj;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusGuaranteeFeeInfo;
import com.nongyeos.loan.admin.entity.BusGuaranteeReverse;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusMemberLogin;
import com.nongyeos.loan.admin.entity.BusMessageReminder;
import com.nongyeos.loan.admin.entity.BusProduct;
import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.model.IntoPieceConfig;
import com.nongyeos.loan.admin.service.IGuaranteeReverseService;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.admin.service.IMemberLoginService;
import com.nongyeos.loan.admin.service.IMessageReminderService;
import com.nongyeos.loan.admin.service.IPersonService;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.GetRepaymentUtils;
import com.nongyeos.loan.base.util.HttpClientUtil;

@Controller
@RequestMapping("/nj/guaranteeReverse")
public class CGuaranteeReverseController {
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(CGuaranteeReverseController.class);
	
	@Autowired
	private IGuaranteeReverseService guaranteeReverseService;
	
	@Autowired
	private IMemberLoginService memberLoginService;
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@Autowired
	private IWebUserService userService;

	@Autowired
	private IntoPieceConfig pieceConfig;
	
	@Autowired
	private IPersonService personService;
	
	@Autowired
	private IMessageReminderService messageReminderService;
	
	@RequestMapping("/payGuaranteeReverse")
	@ResponseBody
	public JSONObject payGuaranteeReverse(HttpServletRequest request,HttpServletResponse response,String payType,String intoPieceId){
		JSONObject retJson = new JSONObject();
		String loginId = (String) request.getAttribute("loginId");
		try {
			if(StringUtils.isEmpty(payType)){
				retJson.put("message", "请选择支付方式");
				response.setStatus(400);
				return retJson;
			}
			BusMemberLogin ml = null;
			ml = memberLoginService.selectById(loginId);
			String userId = null;
			if(ml==null){
				userId=loginId;
			}
			BusGuaranteeReverse guaranteeReverse = null;
			BusMessageReminder messageReminder = new BusMessageReminder();
			messageReminder.setIntoPieceId(intoPieceId);
			messageReminder.setIsDelete(Constants.COMMON_ISNOT_DELETE);
			BusMessageReminder reminder =null;
			if(userId==null){
				BusGuaranteeReverse record = new BusGuaranteeReverse();
				record.setIntoPieceId(intoPieceId);
				record.setType(1);
				guaranteeReverse=guaranteeReverseService.selectByTypeAndIpId(record);
				if(guaranteeReverse==null ){
					retJson.put("message", "未查询到反担保金订单");
					response.setStatus(400);
					return retJson;
				}
				if("S".equals(guaranteeReverse.getStatus())){
					retJson.put("message", "已支付过，请勿重复支付");
					response.setStatus(400);
					return retJson;
				}
				messageReminder.setType(Constants.MGUATANTEE_REVERSE);
				try {
					reminder = messageReminderService.queryMRByTypeAndDelete(messageReminder);
				} catch (Exception e) {
					List<BusMessageReminder> list = messageReminderService.selectByCondition(messageReminder);
					for (int i = 0; i < list.size(); i++) {
						if(list.get(i).getMemberLoginId().equals(loginId)){
							reminder=list.get(i);
						}else{
							BusMessageReminder busMessageReminder = list.get(i);
							busMessageReminder.setIsDelete(Constants.COMMON_IS_DELETE);
							messageReminderService.updateByPrimaryKey(busMessageReminder);
						}
					}
				}
				if(reminder==null){
					retJson.put("message", "网络异常");
					response.setStatus(400);
					return retJson;
				}
			}else{
				BusGuaranteeReverse record = new BusGuaranteeReverse();
				record.setIntoPieceId(intoPieceId);
				record.setType(2);
				guaranteeReverse=guaranteeReverseService.selectByTypeAndIpId(record);
				if(guaranteeReverse==null ){
					retJson.put("message", "未查询到反担保金订单");
					response.setStatus(400);
					return retJson;
				}
				if("S".equals(guaranteeReverse.getStatus())){
					retJson.put("message", "已支付过，请勿重复支付");
					response.setStatus(400);
					return retJson;
				}
				messageReminder.setType(Constants.UGUATANTEE_REVERSE);
				reminder = messageReminderService.queryMRByTypeAndDelete(messageReminder);
				if(reminder==null){
					retJson.put("message", "网络异常");
					response.setStatus(400);
					return retJson;
				}
			}
			SysWebUser user =
					 userService.selectUserByUserName(pieceConfig.getUserName());
			String succeededMSG="支付成功";
			switch (payType) {
				//先锋支付
			case "1":
				 Map<String, String> map1 = new HashMap<String, String>();
				 map1.put("amount", guaranteeReverse.getTotalAmount().toString());
				 map1.put("certificateNo", guaranteeReverse.getPayerIdcard());
				 map1.put("accountNo", guaranteeReverse.getPayerBankcardno());
				 map1.put("accountName", guaranteeReverse.getPayer());
				 map1.put("mobileNo", guaranteeReverse.getPayerMobile());
				 map1.put("intoPieceId", intoPieceId);
				 map1.put("userid", user.getUserId());
				 map1.put("companyType", "HLJSX");
				 String signature = user.getUserId() + user.getUsername() +
				 user.getPassword();
				 map1.put("signature", DigestUtils.md5Hex(signature));
				 String retMap =
				 HttpClientUtil.doPost(pieceConfig.getXfGuaranteeFeeUrl(), map1,
				 "utf-8");
				 System.err.println(retMap);
				 if(retMap!=null){
					 JSONObject retMap1 = JSONObject.parseObject(retMap);
					 if(retMap1==null||StringUtils.isEmpty(retMap1.getString("isSuccess"))||retMap1.getString("isSuccess").equals(Constants.GATE_RESULT_FAIL)){
						 response.setStatus(400);
						 retJson.put("message", "代扣服务费失败，请联系管理员！");
						 guaranteeReverse.setStatus("F");
						 guaranteeReverseService.updateByPrimaryKey(guaranteeReverse);
						 return retJson;
					 }
				 }else{
					 response.setStatus(400);
					 retJson.put("message", "代扣服务费失败，请联系管理员！");
					 guaranteeReverse.setStatus("F");
					 guaranteeReverseService.updateByPrimaryKey(guaranteeReverse);
					 return retJson;
				 }
				 guaranteeReverse.setPayWay(payType);
				 guaranteeReverse.setStatus("I");
				 guaranteeReverseService.updateByPrimaryKey(guaranteeReverse);
				 succeededMSG="成功发起支付，请等待结果";
				 reminder.setIsDelete(Constants.COMMON_IS_DELETE);
				 messageReminderService.updateByPrimaryKey(reminder);
				break;
				//微信支付
			case "2":
				//微信支付服务费url
				String url = pieceConfig.getWxPayGuaranteeFeeCheck();
				Map<String, String> map = new HashMap<String, String>();
				map.put("userid", user.getUserId());
				String signature1 = user.getUserId() + user.getUsername() + user.getPassword();
				map.put("signature", DigestUtils.md5Hex(signature1));
				map.put("companyType", "HLJSX");
				if(StringUtils.isNoneEmpty(request.getHeader("platform"))&& "wx".equals(request.getHeader("platform")) ){
					map.put("appcode", request.getParameter("appcode"));
				}
				map.put("guaranteeId", guaranteeReverse.getId());
				map.put("amount", guaranteeReverse.getTotalAmount().toString());
//				map.put("certificateNo", person.getCardNo());
				map.put("certificateNo", guaranteeReverse.getPayerIdcard());
				map.put("accountName", guaranteeReverse.getPayer());
				map.put("mobileNo", guaranteeReverse.getPayerMobile());
				map.put("intoPieceId", intoPieceId);
				System.err.println(map);
				String retMap1 = HttpClientUtil.doPost(url, map,
						 "utf-8");
				if(retMap1!=null){
					 JSONObject retMap2 = JSONObject.parseObject(retMap1);
					 if(retMap2==null||StringUtils.isEmpty(retMap2.getString("isSuccess"))||retMap2.getString("isSuccess").equals(Constants.GATE_RESULT_FAIL)){
						 retJson.put("message", "微信预支付订单生成失败");
						 response.setStatus(400);
						 return retJson;
					 }
				 }else{
					 retJson.put("message", "微信预支付订单生成失败");
					 response.setStatus(400);
					 return retJson;
				 }
				
				JSONObject retdata = JSONObject.parseObject(retMap1);
				retJson.put("data", retdata.get("data"));
				succeededMSG="微信预支付订单生成成功";
				guaranteeReverse.setStatus("I");
				guaranteeReverseService.updateByPrimaryKey(guaranteeReverse);
				break;
				//线下支付
			case "3":
				guaranteeReverse.setPayWay(payType);
				guaranteeReverse.setStatus("S");
				guaranteeReverse.setUpdateDate(DateUtils.getNowDate());
				guaranteeReverseService.updateByPrimaryKey(guaranteeReverse);
				reminder.setIsDelete(Constants.COMMON_IS_DELETE);
				 messageReminderService.updateByPrimaryKey(reminder);
				break;
				//暂不支付
			case "4":
				guaranteeReverse.setPayWay(payType);
				guaranteeReverse.setStatus("S");
				guaranteeReverse.setUpdateDate(DateUtils.getNowDate());
				guaranteeReverseService.updateByPrimaryKey(guaranteeReverse);
				reminder.setIsDelete(Constants.COMMON_IS_DELETE);
				messageReminderService.updateByPrimaryKey(reminder);
				break;
			}
			response.setStatus(200);
			retJson.put("message", succeededMSG);
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(400);
			retJson.put("message", "网络异常");
			return retJson;
		}
	}
	
	@RequestMapping("/getReverseFee")
	@ResponseBody
	public JSONObject getReverseFee(HttpServletRequest request,HttpServletResponse response,String intoPieceId){
		JSONObject retJson = new JSONObject();
		try {
			String loginId = (String) request.getAttribute("loginId");
			BusMemberLogin ml = null;
			ml = memberLoginService.selectById(loginId);
			String userId = null;
			if(ml==null){
				userId=loginId;
			}
			BusGuaranteeReverse guaranteeReverse = null;
			if(userId==null){
				BusGuaranteeReverse record = new BusGuaranteeReverse();
				record.setIntoPieceId(intoPieceId);
				record.setType(1);
				guaranteeReverse=guaranteeReverseService.selectByTypeAndIpId(record);
				if(guaranteeReverse==null ){
					retJson.put("message", "未查询到反担保金订单");
					response.setStatus(400);
					return retJson;
				}
				if("S".equals(guaranteeReverse.getStatus())){
					retJson.put("message", "已支付过，请勿重复支付");
					response.setStatus(400);
					return retJson;
				}
			}else{
				BusGuaranteeReverse record = new BusGuaranteeReverse();
				record.setIntoPieceId(intoPieceId);
				record.setType(2);
				guaranteeReverse=guaranteeReverseService.selectByTypeAndIpId(record);
				if(guaranteeReverse==null ){
					retJson.put("message", "未查询到反担保金订单");
					response.setStatus(400);
					return retJson;
				}
				if("S".equals(guaranteeReverse.getStatus())){
					retJson.put("message", "已支付过，请勿重复支付");
					response.setStatus(400);
					return retJson;
				}
			}
			retJson.put("money", guaranteeReverse.getTotalAmount().toString());
			response.setStatus(200);
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "网络错误！");
			response.setStatus(400);
			return retJson;
		}
	}
}
