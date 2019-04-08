package com.nongyeos.loan.admin.controller.nj;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusLoanDetail;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.model.IntoPieceConfig;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.HttpClientUtil;
import com.nongyeos.loan.base.util.StrUtils;

@Controller
@RequestMapping("/nj/wxPayInfo")
public class WxPayInfoController {
	
	@Autowired
	private IWebUserService userService;
	@Autowired
	private IntoPieceConfig pieceConfig;
	
	
	@RequestMapping("/wxPayCheckInfo")
	@ResponseBody
	public JSONObject wxPayCheckInfo(BusLoanDetail loanDetail , HttpServletResponse response, HttpServletRequest request){
		JSONObject retJson = new JSONObject();
		try {
			String channel = request.getHeader("channel");
			if(StrUtils.isEmpty(channel)){
				retJson.put("message", "版本错误");
				response.setStatus(400);
				return retJson;
			}
			
			if(loanDetail== null || StringUtils.isEmpty(loanDetail.getLoanId())){
				throw new Exception("贷款id为空");
			}
			SysWebUser user = userService.selectUserByUserName(pieceConfig.getUserName());
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", user.getUserId());
			String signature = user.getUserId() + user.getUsername() + user.getPassword();
			map.put("signature", DigestUtils.md5Hex(signature));
			map.put("ids", loanDetail.getLoanId());
			map.put("companyType", channel);//公司版本
			//微信支付
			String wxPay = HttpClientUtil.doPost(pieceConfig.getWxPayCheck(), map, "utf-8");
			
			JSONObject httpResult = JSONObject.parseObject(wxPay);
			if(Constants.GATE_RESULT_SUCCESS.equals(httpResult.get("isSuccess"))){
				retJson.put("data", httpResult.get("data"));
				retJson.put("message", httpResult.get("msg"));
				
			}else{
				throw new Exception("微信预支付订单生成失败");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", e);
			response.setStatus(400);
		}
		return retJson;
	}	
}
