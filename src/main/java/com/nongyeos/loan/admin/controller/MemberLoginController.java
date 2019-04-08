package com.nongyeos.loan.admin.controller;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusMemberLogin;
import com.nongyeos.loan.admin.service.IMemberLoginService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.IdCheck;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;

@Controller
@RequestMapping("/memberlogin")
public class MemberLoginController {
	
	@Autowired
	private IMemberLoginService memberLoginService;
	
	@Value("${rootpoint.mark}")
	private String appMark;
	
	private static final Logger logger = LogManager.getLogger(MemberLoginController.class);
	
	@RequestMapping("/list")
    @ResponseBody
    public PageBeanUtil<BusMemberLogin> memberLoginList(HttpServletRequest request,
    		int currentPage,int pageSize,String loginName,String name,String idCard) throws Exception{
		String channel = "";
		String baseOrg = request.getSession().getAttribute(Constants.SESSION_ORGCDBASE).toString();
		JSONObject obj = JSONObject.parseObject(appMark);
		Set<String> set = obj.keySet();
		Iterator<String>  it = set.iterator();
		while(it.hasNext()){	
			String next = it.next();
			String orgId = obj.getString(next);
			if(baseOrg.equals(orgId)){
				channel = next;
			}
		}
		BusMemberLogin ml = new BusMemberLogin();
		if(StrUtils.isNotEmpty(loginName))
			ml.setLoginName(loginName);
		if(StrUtils.isNotEmpty(name))
			ml.setName(name);
		if(StrUtils.isNotEmpty(idCard))
			ml.setIdCard(idCard);
		ml.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
		ml.setChannel(channel);
    	return  memberLoginService.selectByPage(currentPage, pageSize,ml);
    }
	
	@RequestMapping("/delete")
    @ResponseBody
	public String delete(String currIds) throws Exception{
		JSONObject json = new JSONObject();
		if(StrUtils.isNotEmpty(currIds)){
			String id[] = currIds.split(",");
			for (int i = 0; i < id.length; i++) {
				BusMemberLogin ml = new BusMemberLogin();
				ml.setLoginId(id[i]);
				ml.setIsDeleted(Constants.COMMON_IS_DELETE);
				memberLoginService.updateByPrimaryKeySelective(ml);
			}
			json.put("msg", "删除成功");
			json.put("code", 200);
		}else{
			json.put("msg", "删除失败");
			json.put("code", 400);
		}
		return json.toString();
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String update(BusMemberLogin ml) throws Exception{
		JSONObject json = new JSONObject();
		if(StrUtils.isNotEmpty(ml.getIdCard())){
			if(!IdCheck.isValidate18Idcard(ml.getIdCard())){
				json.put("msg", "身份证号错误！");
				json.put("code", 400);
				return json.toString();
			}
		}
		if(StrUtils.isNotEmpty(ml.getBankCardNo())){
			if(!ml.getBankCardNo().matches("^[0-9]*$")){
				json.put("msg", "银行卡号错误！");
				json.put("code", 400);
				return json.toString();
			}
		}
		if(StrUtils.isNotEmpty(ml.getBankPhone())){
			if(!ml.getBankPhone().matches("^[0-9]{11}$")){
				json.put("msg", "银行卡预留手机号错误！");
				json.put("code", 400);
				return json.toString();
			}
		}
		memberLoginService.updateByPrimaryKeySelective(ml);
		json.put("msg", "修改成功");
		json.put("code", 200);
		return json.toString();
	}
	
	
}
