package com.nongyeos.loan.admin.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusSmsHistory;
import com.nongyeos.loan.admin.service.ISmsHistoryService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;

@Controller
@RequestMapping("/smshistory")
public class SmsHistoryController {
	
	@Autowired
	private ISmsHistoryService smsHistoryService;
	
	private static final Logger logger = LogManager.getLogger(SmsHistoryController.class);
	
	@RequestMapping("/list")
	@ResponseBody
	public PageBeanUtil<BusSmsHistory> list(int currentPage,int pageSize,String phone) throws Exception{
		BusSmsHistory sh = new BusSmsHistory();
		sh.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
		if(StrUtils.isNotEmpty(phone))
			sh.setPhone(phone);
		return smsHistoryService.selectByPage(currentPage, pageSize, sh);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public JSONObject delete(String ids) throws Exception{
		JSONObject json = new JSONObject();
		if(StrUtils.isNotEmpty(ids)){
			String id[] = ids.split(",");
			for (int i = 0; i < id.length; i++) {
				BusSmsHistory sh = new BusSmsHistory();
				sh.setId(id[i]);
				sh.setIsDeleted(Constants.COMMON_IS_DELETE);
				smsHistoryService.updateByPrimaryKeySelective(sh);
			}
			json.put("msg", "删除成功");
			json.put("code", 200);
			return json;
		}
		json.put("msg", "删除失败");
		json.put("code", 400);
		return json;
	}
}
