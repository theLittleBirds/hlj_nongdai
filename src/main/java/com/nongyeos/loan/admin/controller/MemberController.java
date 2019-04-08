package com.nongyeos.loan.admin.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusMember;
import com.nongyeos.loan.admin.entity.BusMemberLogin;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.admin.service.IMemberService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.IdCheck;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;

/**
 * 
 * Title:MemberController 
 * Description:  
 * @author lcg
 * @date 2018年4月12日 上午10:01:13
 */
@Controller
@RequestMapping("/member")
public class MemberController {
	
	private static final Logger logger = LogManager.getLogger(MemberController.class);
	
	@Resource
	private IMemberService memberService;
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@RequestMapping("/list")
	public String list(){
		return "member/list";
	}
	
	@RequestMapping("/memberList")
    @ResponseBody
	public PageBeanUtil<BusMember> index(int currentPage,int pageSize,String name,String idCard,String bankPhone) throws Exception{
		BusMember m = new BusMember();
		if(StrUtils.isNotEmpty(name))
			m.setName(name);
		if(StrUtils.isNotEmpty(idCard))
			m.setIdCard(idCard);
		if(StrUtils.isNotEmpty(bankPhone))
			m.setBankPhone(bankPhone);
		return  memberService.selectByPage(currentPage,pageSize,m);
	}
	
	/**
	 * 获取最近的一次进件id
	 * @param id
	 * @return
	 */
	@RequestMapping("/lastintopiece")
    @ResponseBody
	public JSONObject lastIntoPiece(String id){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(id)){
			json.put("code", 400);
			json.put("msg", "用户标识为空");
			return json;
		}
		try {
			List<BusIntoPiece> list = intoPieceService.selectByMemberId(id);
			if(list.size() == 0){
				json.put("code", 400);
				json.put("msg", "无进件");
				return json;
			}else{
				json.put("code", 200);
				json.put("intoPieceId", list.get(0).getId());
				return json;
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
			return json;
		}
	}
	
	
//	@RequestMapping("/save")
//    @ResponseBody
//    @Transactional
//	public Map<String, Object> saveMember(HttpServletResponse response,HttpServletRequest request,BusMember member,BusMemberLogin login) throws Exception{
//		System.err.println(login.getIdCard());
//		System.err.println(member.getIdCard());
//		Map<String, Object> map = new HashMap<String, Object>();
//		if(!IdCheck.isValidatedAllIdcard(member.getIdCard())){
//			map.put("msg", "身份证填写错误，请重新填写！");
//			map.put("errorno", "1000");
//			return map;
//		}
//		if(memberService.existedSameName(member)){
//			map.put("msg", "客户已存在，请重新填写！");
//		}else{
//			member.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
//			member.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
//			member.setUpdOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
//			if(member.getMemberId()==null||member.getMemberId().isEmpty()){
//				member.setCreOperId( request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
//				member.setCreOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
//				member.setCreOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
//				memberService.addMember(member);
//			}else{
//				memberService.updateMember(member);
//			}
//			map.put("msg", "保存成功！");
//		}
//		return map;
//	}
	
	
	@RequestMapping("/delMember")
    @ResponseBody
    @Transactional
	public Map<String, Object> delMember(HttpServletResponse response,HttpServletRequest request,String currIds) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(currIds != null && !currIds.equals("")){
    		String[] memberIds = currIds.split(",");
    		for(String memberId : memberIds){
    			memberService.deleteUser(memberId);
    		}
    		map.put("msg", "删除成功");
    	}else{
    		map.put("msg", "删除失败");
    	}
		return map;
	}
	
	
}
