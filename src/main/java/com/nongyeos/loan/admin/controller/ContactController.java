package com.nongyeos.loan.admin.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.admin.entity.BusContact;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.service.IContactService;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;

@Controller
@RequestMapping("/contact")
public class ContactController {
	
	@Autowired
	private IContactService contactService;
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	private static final Logger logger = LogManager.getLogger(ContactController.class);
	
	@RequestMapping("/list")
	@ResponseBody
	public PageBeanUtil<BusContact> list(int currentPage,int pageSize,String intoPieceId) throws Exception{
		if(StrUtils.isEmpty(intoPieceId))
			return null;
		BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
		if(ip != null && StrUtils.isNotEmpty(ip.getMemberId())){
			BusContact c = new BusContact();
			c.setMemberId(ip.getMemberId());
			c.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			return contactService.selectByPage(currentPage, pageSize, c);
		}
		return null;
	}
}
