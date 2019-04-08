package com.nongyeos.loan.admin.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.IntQimoOutCall;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.service.IQimoOutCallService;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.HttpClientUtil;
import com.nongyeos.loan.base.util.QiNiuUtil;
import com.nongyeos.loan.base.util.StrUtils;

@Controller
@RequestMapping("/qimooutcall")
public class QimoOutCallController {
	
	@Autowired
	private IQimoOutCallService intQimoOutCallService;
	
	@Autowired
	private IWebUserService userService; 
	
	@RequestMapping("/callout")
	@ResponseBody
	public JSONObject callOut(HttpServletRequest request){
		JSONObject json =  new JSONObject();
		try {
			String userId = request.getSession().getAttribute(Constants.SESSION_USERCD).toString();
			SysWebUser user = userService.selectUserById(userId);
			Map<String,String> map = new HashMap<String, String>();
			String signature = user.getUserId() + user.getUsername() + user.getPassword();
			map.put("signature", DigestUtils.md5Hex(signature));
			map.put("userid", userId);
			map.put("intoPieceId", request.getParameter("intoPieceId"));
			map.put("phone", request.getParameter("phone"));
			map.put("ExtenType", request.getParameter("ExtenType"));
			if(StrUtils.isNotEmpty(request.getParameter("from"))){
				map.put(request.getParameter("from"), request.getParameter("from"));
			}
			map.put("member_name", request.getParameter("member_name"));
			map.put("id_card", request.getParameter("id_card"));
			String result = HttpClientUtil.doPost("http://127.0.0.1:8086/apiQimo/ApiBusQimoOutcall", map, "utf-8");
			if(result == null){
				json.put("code", 400);
				json.put("msg", "外呼失败");
				return json;
			}
			JSONObject httpResult = JSONObject.parseObject(result);
			if(Constants.GATE_RESULT_SUCCESS.equals(httpResult.get("isSuccess"))){
				json.put("code", 200);
				json.put("msg", "外呼成功");
				return json;
			}else{
				json.put("code", 400);
				json.put("msg", "外呼失败");
				return json;
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", "外呼失败");
			return json;
		}
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public List<IntQimoOutCall> list(String intoPieceId){
		if(StrUtils.isEmpty(intoPieceId))
			return null;
		try {
			List<IntQimoOutCall> list = intQimoOutCallService.selectByIpId(intoPieceId);
			if(list == null)
				return null;
			List<String> keyList = new LinkedList<String>();
			for (int i = 0; i < list.size(); i++) {
				keyList.add(list.get(i).getVoiceurl());
			}
			keyList = QiNiuUtil.getUrl(keyList);
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setVoiceurl(keyList.get(i));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
