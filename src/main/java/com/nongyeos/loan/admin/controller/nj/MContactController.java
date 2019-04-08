package com.nongyeos.loan.admin.controller.nj;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusContact;
import com.nongyeos.loan.admin.entity.BusMemberLogin;
import com.nongyeos.loan.admin.service.IContactService;
import com.nongyeos.loan.admin.service.IMemberLoginService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/nj/contact")
public class MContactController {
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(MContactController.class);
	
	@Autowired
	private IMemberLoginService memberLoginService;
	
	@Autowired
	private IContactService contactService;
	
	@RequestMapping("/save")
	@ResponseBody
	public JSONObject saveContact(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String loginId = (String) request.getAttribute("loginId");
		try {
			BusMemberLogin memberLogin = memberLoginService.selectById(loginId);
			int i =0;
			while (true) {
				String name = request.getParameter("contact["+i+"][name]");
				String phone = request.getParameter("contact["+i+"][phone]");
				if(StringUtils.isEmpty(name) && StringUtils.isEmpty(phone)){
					break;
				}
				i++;
				String moblie = phone.replaceAll("\\-", "").replaceAll("\\s", "");
				BusContact contact = new BusContact();
				contact.setMemberId(memberLogin.getMemberId());
				contact.setPhone(moblie);
				contact.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
				BusContact usedContact = contactService.selectByPhoneMember(contact);
				if(usedContact!=null){
					continue;
				}
				contact.setId(UUIDUtil.getUUID());
				contact.setName(name);
				contact.setCreOperId(loginId);
				contact.setCreDate(DateUtils.getNowDate());
				contact.setUpdDate(DateUtils.getNowDate());
				contact.setUpdOperId(loginId);
				contactService.insert(contact);
			}
			retJson.put("message", "保存成功！");
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "保存失败！");
			response.setStatus(400);
		}
		
		return retJson;
	}
}
