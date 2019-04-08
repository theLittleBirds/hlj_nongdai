package com.nongyeos.loan.core.event;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.core.model.BusinessObject;

public class CheckEvent {
	
	/**
	 * 
	 * @param entry
	 * @return
	 */
	public JSONObject checkPhone(BusinessObject business,SqlSession session){
		JSONObject json = new JSONObject();
		if(business == null){
			json.put("msg", "手机号验证失败");
			json.put("code",  400);
		}
		try {
			String phone = ((BusIntoPiece)business.getBean()).getPhone();
			if(phone != null){
				if(phone.matches("1[0-9]{10}")){
					json.put("msg", "手机号验证通过");
					json.put("code", 200);
					json.put("result", 1);
					return json;
				}
			}
			json.put("msg", "手机号验证不通过");
			json.put("code", 200);
			json.put("result", 0);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msg", "手机号证验证失败");
			json.put("code", 400);
			return json;
		}
	}
}
