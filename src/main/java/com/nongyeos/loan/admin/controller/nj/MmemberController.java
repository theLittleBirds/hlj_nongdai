package com.nongyeos.loan.admin.controller.nj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusMember;
import com.nongyeos.loan.admin.entity.BusMemberLogin;
import com.nongyeos.loan.admin.model.FileConfig;
import com.nongyeos.loan.admin.service.IMemberLoginService;
import com.nongyeos.loan.admin.service.IMemberService;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.IdCheck;
import com.nongyeos.loan.base.util.NotEmptyUtils;
import com.nongyeos.loan.base.util.UUIDUtil;
/**
 * 
 * Title:MmemberController 
 * Description:  个人用户补全个人资料
 * @author lcg
 * @date 2018年5月3日 上午10:05:26
 */
@Controller
@RequestMapping("/nj/member")
public class MmemberController {
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(MmemberController.class);
	
	@Autowired
	private FileConfig fileConfig;
	
	@Autowired
	private IMemberLoginService memberLoginService;
	
	@Autowired
    private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private IMemberService memberService;
	
	/**
	 * @throws Exception 
	 * 
	 * @Title: memberInfo 
	 * @Description: 进件时回显填过的个人资料
	 * @param @param request
	 * @param @return     
	 * @return JSONObject     
	 * @throws
	 */
	@RequestMapping("/memberinfo")
    @ResponseBody
	public JSONObject memberInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject retJson = new JSONObject();
		try {
			String loginId = (String) request.getAttribute("loginId");
			BusMemberLogin memberLogin = memberLoginService.selectById(loginId);
				if(StringUtils.isEmpty(memberLogin.getIdCard())){
					retJson.put("message", "请补充个人资料！");
					retJson.put("errno", 3019);
					response.setStatus(403);
				}else{
					retJson.put("name", memberLogin.getName());
//					retJson.put("bankPhone", memberLogin.getBankPhone());
					retJson.put("idCard", memberLogin.getIdCard());
//					retJson.put("bankCardNo", memberLogin.getBankCardNo());
//					retJson.put("bank", memberLogin.getBank());
					retJson.put("errno", 0);
					response.setStatus(200);
				}
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "系统错误！");
			response.setStatus(400);
		}
		return retJson;
	}
	
	/**
	 * @throws Exception 
	 * 
	 * @Title: memberInfo 
	 * @Description: 
	 * @param @param request
	 * @param @return     
	 * @return JSONObject     
	 * @throws
	 */
	@RequestMapping("/save")
    @ResponseBody
    @Transactional
	public JSONObject memberSave(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		JSONObject retJson = new JSONObject();
		try {
			String loginId = (String) request.getAttribute("loginId");
			BusMemberLogin memberLogin2 = memberLoginService.selectById(loginId);
			String userId= null;
			if(memberLogin2==null){
				userId=loginId;
			}
			if(memberLogin2!=null&&StringUtils.isNotEmpty(memberLogin2.getMemberId())){
				BusMember member = memberService.selectByPrimaryKey(memberLogin2.getMemberId());
				if(member!=null&&StringUtils.isNotEmpty(member.getIdCard()) ){
					retJson.put("memberId", member.getMemberId());
			    	response.setStatus(200);
			    	return retJson;
				}
			}
			Map<String, Object> namenull = NotEmptyUtils.somthingLose(request, response, "name", "请输入姓名！", retJson);
			if((boolean) namenull.get("flag")){
				return  JSONArray.parseObject(namenull.get("retJson").toString()) ;
			}
				
			//身份证号非空校验
			String idCard="";
			Map<String, Object> idnull = NotEmptyUtils.somthingLose(request,response,  "idCard", "请输入身份证号！", retJson);
			if((boolean) idnull.get("flag"))
				return JSONArray.parseObject(idnull.get("retJson").toString());
			idCard= request.getParameter("idCard").trim().replaceAll("\\s+","");
//			short age = new Integer(IdCheck.getAgeByIdCard(idCard)).shortValue();
			/*if(age>60){
				retJson.put("message", "年龄超过60，无法进件！");
    			retJson.put("errno", 3003);
    			response.setStatus(400);
        		return retJson;
			}*/
    		if(!IdCheck.isValidatedAllIdcard(idCard)){
    			retJson.put("message", "身份证号输入有误，请重新输入！");
    			retJson.put("errno", 3003);
    			response.setStatus(400);
        		return retJson;
    		}
//    		if(!request.getHeader("channel").equals("SX")){
//    			//银行卡非空校验
//        		Map<String, Object> bankCardnull = NotEmptyUtils.somthingLose(request,response,  "bankCardNo", "请输入银行卡号！", retJson);
//    			if((boolean) bankCardnull.get("flag"))
//    				return JSONArray.parseObject(bankCardnull.get("retJson").toString());
//    			//开户银行
//    			Map<String, Object> banknull = NotEmptyUtils.somthingLose(request,response,  "bank",  "请输入银行卡号！", retJson);
//    			if((boolean) banknull.get("flag"))
//    				return JSONArray.parseObject(banknull.get("retJson").toString());
//    		}
			//银行预留手机非空校验
			Map<String, Object> bankPhonenull = NotEmptyUtils.somthingLose(request,response,  "bankPhone", "请输入手机号！", retJson);
			if((boolean) bankPhonenull.get("flag"))
				return JSONArray.parseObject(bankPhonenull.get("retJson").toString());
			//银行预留手机
			Map<String, Object> bankPhonewrong = NotEmptyUtils.somethingwrong(request.getParameter("bankPhone"),response,  "^1\\d{10}$", "预留手机号输入有误，请重新输入！", retJson);
			if((boolean) bankPhonewrong.get("flag"))
				return JSONArray.parseObject(bankPhonewrong.get("retJson").toString());
			//家庭住址
			Map<String, Object> addressnull = NotEmptyUtils.somthingLose(request, response, "address",  "请输入家庭住址！", retJson);
			if((boolean) addressnull.get("flag"))
				return JSONArray.parseObject(addressnull.get("retJson").toString());
			//文化程度
			Map<String, Object> educationLevelnull = NotEmptyUtils.somthingLose(request,response,  "educationLevel", "请选择文化程度！", retJson);
			if((boolean) educationLevelnull.get("flag"))
				return JSONArray.parseObject(educationLevelnull.get("retJson").toString());
			//婚姻状况
			Map<String, Object> maritalStatusnull = NotEmptyUtils.somthingLose(request, response, "maritalStatus", "请选择婚姻状况！", retJson);
			if((boolean) maritalStatusnull.get("flag"))
				return JSONArray.parseObject(maritalStatusnull.get("retJson").toString());
			BusMemberLogin memberLogin = new BusMemberLogin();
			if(StringUtils.isEmpty(userId)){
				memberLogin.setName(request.getParameter("name"));
				memberLogin.setBankPhone(request.getParameter("bankPhone"));
				memberLogin.setBankCardNo(request.getParameter("bankCardNo"));
				memberLogin.setBank(request.getParameter("bank"));
				memberLogin.setIdCard(idCard);
			}
			//商户和个人都需要保存的
			BusMember member = memberService.selectByIdCard(idCard);
			if(member==null){
				member = new BusMember();
			}
			member.setAddress(request.getParameter("address"));
			member.setName(request.getParameter("name"));
			member.setAge(new Integer(IdCheck.getAgeByIdCard(idCard)).shortValue());
			member.setSex(new Integer(IdCheck.getGenderByIdCard(idCard)).shortValue());
			member.setIdCard(idCard);
			member.setBank(request.getParameter("bank"));
			member.setBankCardNo(request.getParameter("bankCardNo"));
			member.setBankPhone(request.getParameter("bankPhone"));
			member.setEducationLevel(new Short(request.getParameter("educationLevel")));
			member.setMaritalStatus(new Short(request.getParameter("maritalStatus")));
			member.setUpdOperId(StringUtils.isEmpty(userId)?loginId:userId);
			if(StringUtils.isEmpty(member.getMemberId())){
				member.setCreOperId(StringUtils.isEmpty(userId)?loginId:userId);
				memberService.addMember(member,request.getHeader("channel"));
			}else{
				memberService.updateMember(member,request.getHeader("channel"));
			}
			BusMember selectByIdCard = memberService.selectByIdCard(idCard);
			if(StringUtils.isEmpty(userId)){
				memberLogin.setMemberId(selectByIdCard.getMemberId());
				memberLogin.setLoginId(memberLogin2.getLoginId());
				memberLogin.setUpdDate(DateUtils.getNowDate());
				memberLoginService.updateByPrimaryKeySelective(memberLogin);
			}
			String memberOperateId = request.getParameter("memberOperateId");
			if(!StringUtils.isEmpty(memberOperateId)){
				retJson.put("memberOperateId", memberOperateId);
			}
			retJson.put("memberId", selectByIdCard.getMemberId());
			retJson.put("message", "保存成功！");
	    	response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", e.getMessage());
			retJson.put("errno", 3034);
			response.setStatus(400);
		}
		return retJson;
	}
	
	@RequestMapping("/userSave")
	@ResponseBody
	public JSONObject userSave(HttpServletRequest request,BusMember member){
		JSONObject retJson = new JSONObject();
		
		return retJson;
	}
	
	
	/**
	 * @throws Exception 
	 * 
	 * @Title: checkMember 
	 * @Description: 检查是否已补全完资料
	 * @param @return     
	 * @return JSONObject     
	 * @throws
	 */
	@RequestMapping("/checkMember")
    @ResponseBody
    @Transactional
	public JSONObject checkMember(HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject retJson = new JSONObject();
		JSONObject data = new JSONObject();
		try {
			String loginId = (String)request.getAttribute("loginId");
			BusMemberLogin memberLogin = memberLoginService.selectById(loginId);
			if(StringUtils.isEmpty(memberLogin.getName())||StringUtils.isEmpty(memberLogin.getBankCardNo()) ||StringUtils.isEmpty(memberLogin.getBankPhone()) ||StringUtils.isEmpty(memberLogin.getIdCard()) ){
				retJson.put("message", "请补充个人资料！");
				retJson.put("errno", 3019);
				data.put("result", false);
				retJson.put("data", data);
				response.setStatus(200);
				return retJson;
			}
			BusMember member = memberService.selectByIdCard(memberLogin.getIdCard());
			retJson.put("memberId", member.getMemberId());
			retJson.put("message", "已补充个人资料！");
			retJson.put("errno", 0);
			data.put("result", true);
			retJson.put("data", data);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retJson;
	}
	
	
}
