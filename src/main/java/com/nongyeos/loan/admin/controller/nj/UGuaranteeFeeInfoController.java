package com.nongyeos.loan.admin.controller.nj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.nongyeos.loan.admin.entity.BusGuaranteeFeeInfo;
import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.service.IGuaranteeFeeInfoService;
import com.nongyeos.loan.admin.service.IPersonService;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/nj/guaranteeFeeInfo")
public class UGuaranteeFeeInfoController {
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(UGuaranteeFeeInfoController.class);
	
	@Autowired
	private IGuaranteeFeeInfoService guaranteeFeeInfoService;
	
	@Autowired
	private IPersonService personService;
	
	private static int pageSize = 10;
	
	
	@RequestMapping("/findPaymentList")
	@ResponseBody
	public JSONObject findPaymentList(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		try {
			String userId = (String) request.getAttribute("loginId");
			String memberName = request.getParameter("memberName");
			String page = request.getParameter("page");
			Integer page1=null;
			if(StringUtils.isEmpty(page)){
				page1 =1;
			}else{
				page1= new Integer(page);
			}
			SysPerson person = personService.selectByUserIdAndIsdefault(userId);
			BusGuaranteeFeeInfo feeInfo = new BusGuaranteeFeeInfo();
			feeInfo.setAccountName(StringUtils.isEmpty(memberName)?null:memberName);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("feeInfo", feeInfo);
			PageBeanUtil<BusGuaranteeFeeInfo> pageBean = guaranteeFeeInfoService.selectByPage(person.getPersonId(), map, page1, pageSize);
			List<BusGuaranteeFeeInfo> items = pageBean.getItems();
			int totalPage = pageBean.getTotalPage();
			int totalRow = pageBean.getTotalNum();
			int currentPage = pageBean.getCurrentPage();
			JSONArray arr = new JSONArray();
			if(pageBean.getTotalPage()<page1){
				response.setStatus(400);
				retJson.put("errno", 3011);
				retJson.put("message", "没有更多数据了！");
				return retJson;
			}else{
				if(items!=null&&items.size()>0){
					for (int i = 0; i < items.size(); i++) {
						JSONObject obj = new JSONObject();
						BusGuaranteeFeeInfo info = items.get(i);
						obj.put("memberName", info.getAccountName());
						obj.put("intoPieceId", info.getIntoPieceId());
						obj.put("guaranteeFee", info.getTotalAmount());
						obj.put("status", info.getStatus());
						obj.put("payTime", info.getUpdateDate()==null?"-":DateUtils.format(info.getUpdateDate(), "yyyy-MM-dd"));
						arr.add(obj);
					}
				}
			}
			response.setStatus(200);
			retJson.put("data", arr);
			retJson.put("totalPage", totalPage);
			retJson.put("totalRow", totalRow);
			retJson.put("currentPage", currentPage);
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retJson;
	}
}
