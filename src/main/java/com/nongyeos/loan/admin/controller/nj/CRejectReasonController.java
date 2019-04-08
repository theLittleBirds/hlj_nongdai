package com.nongyeos.loan.admin.controller.nj;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusRejectReason;
import com.nongyeos.loan.admin.service.IRejectReasonService;
import com.nongyeos.loan.app.service.IApplicationService;

@Controller
@RequestMapping("/nj/rejectReason")
public class CRejectReasonController {
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(CRejectReasonController.class);
	
	@Autowired
	private IRejectReasonService rejectReasonService;
	
	@RequestMapping("/findReason")
	@ResponseBody
	public JSONObject findReason(HttpServletResponse response,String intoPieceId){
		JSONObject retJson = new JSONObject();
		JSONArray arr = new JSONArray();
		if(StringUtils.isEmpty(intoPieceId)){
			retJson.put("message", "进件标识为空！");
			retJson.put("errno", 3001);
			response.setStatus(400);
			return retJson;
		
		}
		try {
			List<BusRejectReason> list =  rejectReasonService.selectByIpId(intoPieceId);
			if(list!=null&&list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					String rejectReason = list.get(i).getRejectReason();
					arr.add(rejectReason);
				}
				retJson.put("data", arr);
				retJson.put("errno", 0);
				response.setStatus(200);
			}else{
				retJson.put("message", "无法查看，请联系系统管理员");
				retJson.put("errno", 3033);
				response.setStatus(400);
			}
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "系统出错了！");
			response.setStatus(400);
		}
		return retJson;
	}
	
}
