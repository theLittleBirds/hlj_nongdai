package com.nongyeos.loan.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusGuaranteeReverse;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusLoan;
import com.nongyeos.loan.admin.entity.BusMemberLogin;
import com.nongyeos.loan.admin.entity.BusMessageReminder;
import com.nongyeos.loan.admin.entity.BusSmsTemplate;
import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.model.IntoPieceConfig;
import com.nongyeos.loan.admin.model.RootPointConfig;
import com.nongyeos.loan.admin.service.IGuaranteeReverseService;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.admin.service.IMemberLoginService;
import com.nongyeos.loan.admin.service.IMessageReminderService;
import com.nongyeos.loan.admin.service.IPersonService;
import com.nongyeos.loan.admin.service.ISmsTemplateService;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.HttpClientUtil;

@Controller
@RequestMapping("/guaranteeReverse")
public class GuaranteeReverseController {
	@Autowired
	private IGuaranteeReverseService guaranteeReverseService;
	
	@Autowired
	private IWebUserService userService;

	@Autowired
	private IntoPieceConfig pieceConfig;
	
	@Autowired
	private IMessageReminderService messageReminderService;
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@Autowired
    private RootPointConfig rootPointConfig;
	
	@Autowired
	private ISmsTemplateService smsTemplateService;
	
	@Autowired
	private IPersonService personService;
	@Autowired
	private IMemberLoginService loginService;
	
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager
			.getLogger(GuaranteeReverseController.class);
	
	@RequestMapping("/reverseDetail")
	public ModelAndView reverseDetail(String id){
		ModelAndView mv = new ModelAndView();
		mv.addObject("id", id);
		mv.setViewName("lending/reverseDetail");
		return mv;
	}
	
	@RequestMapping("/reverseDetailList")
	@ResponseBody
	public List<BusGuaranteeReverse> reverseDetailList(String id){
			try {
				return  guaranteeReverseService.selectByIntoPieceId(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}
	
	@RequestMapping("/payWayChange")
	@ResponseBody
	public Map<String, Object> payWayChange(String id,String payWay,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isEmpty(payWay)){
			map.put("code", 400);
			map.put("msg", "请选择支付方式");
			return map;
		}
		try {
			BusGuaranteeReverse reverse = guaranteeReverseService.selectByPrimaryKey(id);
			reverse.setPayWay(payWay);
			if(payWay.equals("1")){
				SysWebUser user =
						 userService.selectUserByUserName(pieceConfig.getUserName());
						 Map<String, String> map1 = new HashMap<String, String>();
						 map1.put("amount", reverse.getTotalAmount().toString());
						 map1.put("certificateNo", reverse.getPayerIdcard());
						 map1.put("accountNo", reverse.getPayerBankcardno());
						 map1.put("accountName", reverse.getPayer());
						 map1.put("mobileNo", reverse.getPayerMobile());
						 map1.put("intoPieceId", id);
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
								 map.put("code", "400");
								 map.put("msg", "代扣服务费失败，请联系管理员！");
								 reverse.setStatus("F");
								 guaranteeReverseService.updateByPrimaryKey(reverse);
								 return map;
							 }
						 }else{
							 map.put("code", "400");
							 map.put("msg", "代扣服务费失败，请联系管理员！");
							 reverse.setStatus("F");
							 guaranteeReverseService.updateByPrimaryKey(reverse);
							 return map;
						 }
						 reverse.setStatus("I");
						 guaranteeReverseService.updateByPrimaryKey(reverse);
			}else if(payWay.equals("3")){
				reverse.setStatus("S");
				reverse.setUpdateDate(DateUtils.getNowDate());
				guaranteeReverseService.updateByPrimaryKey(reverse);
			}else if(payWay.equals("4")){
				reverse.setStatus("S");
				reverse.setUpdateDate(DateUtils.getNowDate());
				guaranteeReverseService.updateByPrimaryKey(reverse);
			}
			map.put("code", "200");
			map.put("msg", "保存成功！");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/repay")
	@ResponseBody
	public Map<String, Object> repay(String id,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BusGuaranteeReverse reverse = guaranteeReverseService.selectByPrimaryKey(id);
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(reverse.getIntoPieceId());
			Date now = DateUtils.getNowDate();
			Map<String, String> value=new HashMap<String, String>();
			value.put("CODE", reverse.getTotalAmount().toString());
			value.put("MEMBER", ip.getMemberName());
			if(reverse.getType().equals(2)){
				SysPerson person = personService.selectByPersonId(ip.getOperUserId());
				BusSmsTemplate smsTemplate = new BusSmsTemplate();
				String mark = rootPointConfig.getMark();
				JSONObject object = JSONObject.parseObject(mark);
				smsTemplate.setBaseOrgId(object.getString(ip.getChannel()));
				smsTemplate.setMsgCode(Constants.U_GREVERSE_REMIND);
				smsTemplate.setIsOpen(1);
				BusSmsTemplate smstemplateuser = smsTemplateService.checkExist(smsTemplate);
				BusMessageReminder reminder = new BusMessageReminder();
				reminder.setIntoPieceId(reverse.getIntoPieceId());
				reminder.setType(Constants.UGUATANTEE_REVERSE);
				reminder.setIsDelete(Constants.COMMON_ISNOT_DELETE);
				BusMessageReminder messageReminder =messageReminderService.queryMRByTypeAndDelete(reminder);
				if(messageReminder==null){
					messageReminderService.insertreminder(smstemplateuser,value,now,ip.getId(),person.getUserId(),Constants.UGUATANTEE_REVERSE);
				}
			}else{
				BusSmsTemplate smsTemplate = new BusSmsTemplate();
				String mark = rootPointConfig.getMark();
				JSONObject object = JSONObject.parseObject(mark);
				smsTemplate.setBaseOrgId(object.getString(ip.getChannel()));
				smsTemplate.setMsgCode(Constants.M_GREVERSE_REMIND);
				smsTemplate.setIsOpen(1);
				BusSmsTemplate smstemplatemember = smsTemplateService.checkExist(smsTemplate);
				BusMessageReminder reminder = new BusMessageReminder();
				reminder.setIntoPieceId(reverse.getIntoPieceId());
				reminder.setType(Constants.MGUATANTEE_REVERSE);
				reminder.setIsDelete(Constants.COMMON_ISNOT_DELETE);
				List<BusMessageReminder> list = messageReminderService.selectByCondition(reminder);
				if(list==null){
					Map<String, String> memberIdChannel = new HashMap<String, String>();
					memberIdChannel.put("memberId", ip.getMemberId());
					memberIdChannel.put("channel", ip.getChannel());
					List<BusMemberLogin> listm = loginService.selectByMemberId(memberIdChannel);
						for (int i = 0; i < listm.size(); i++) {
							messageReminderService.insertreminder(smstemplatemember,value,now,ip.getId(),listm.get(i).getLoginId(),Constants.MGUATANTEE_REVERSE);
						}
				}
			}
			reverse.setStatus(null);
			reverse.setPayWay(null);
			guaranteeReverseService.updateByPrimaryKey(reverse);
			map.put("code", "200");
			map.put("msg", "保存成功！");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", "300");
			map.put("msg", "保存失败！");
			return map;
		}
	}
	
}
