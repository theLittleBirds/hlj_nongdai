package com.nongyeos.loan.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.NjMerGather;
import com.nongyeos.loan.admin.entity.NjOrderGather;
import com.nongyeos.loan.admin.service.NjMerGatherService;
import com.nongyeos.loan.admin.service.NjOrderGatherService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;

@Controller  
@RequestMapping("/njMerchant")
public class NjMerchantController {

	@Autowired
	private NjOrderGatherService njOrderGatherService;//联合社订单
	@Autowired
	private NjMerGatherService njMerGatherService;//商户订单

	/**
	 * 商户订单列表
	 */
	@RequestMapping("/njMerchantList")
	public String njMerchantList(){
		return "union/njMerchantList";
	}
	
	/**
	 * 异步加载商品列表数据，bootstrap的table表格用
	 */
	@RequestMapping("/asyncLoanRiskPostList")
    @ResponseBody
	public PageBeanUtil<NjMerGather> asyncLoanRiskPostList(int currentPage, int pageSize, 
			String orgName, String memberName, String productName, String status, String merName,
			HttpServletRequest request){
		try {
			NjMerGather njMerGather = new NjMerGather();
			if(StrUtils.isNotEmpty(orgName)){
				njMerGather.setOrgName(orgName);
			}
			if(StrUtils.isNotEmpty(memberName)){
				njMerGather.setMemberName(memberName);
			}
			if(StrUtils.isNotEmpty(productName)){
				njMerGather.setProductName(productName);
			}
			if(StrUtils.isNotEmpty(status)){
				njMerGather.setStatus(status);
			}
			
			njMerGather.setPersonId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			return njMerGatherService.queryNjMerGatherSelective(currentPage, pageSize, njMerGather);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 接单
	 */
	@RequestMapping("/njMerchantApproveOrder")
	@ResponseBody
	public JSONObject njMerchantApproveOrder(NjMerGather njMerGather){
		JSONObject json = new JSONObject();
		try {
			if(njMerGather == null || StringUtils.isEmpty(njMerGather.getId())){
				throw new Exception("商户订单信息为空");
			}
			
			NjMerGather merGather = njMerGatherService.queryMerGatherByPK(njMerGather.getId());
			if(merGather == null){
				throw new Exception("没有查询到商户订单信息，不能下单");
			}
			
			/**
			 * 商户订单变为失效状态
			 */
			merGather.setIsDeleted(String.valueOf(Constants.COMMON_IS_DELETE));//失效
			njMerGatherService.updateMerGatherByPKSelective(merGather);
			
			/**
			 * 联合社订单更新状态
			 */
			NjOrderGather njOrderGather = njOrderGatherService.queryOrderGatherByPk(merGather.getOrderId());
			njOrderGather.setStatus(Constants.ASSOCIATE_MER_ORDER);//商户已接单，待付尾款
			njOrderGather.setIsDeleted(String.valueOf(Constants.COMMON_ISNOT_DELETE));//有效
			njOrderGatherService.updateOrderGatherByPKSelective(njOrderGather);
			
			json.put("code", 200);
			json.put("msg", "接单成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}
		return json;
	}	
	
	/**
	 * 发货
	 */
	@RequestMapping("/njAssociateProductOut")
	@ResponseBody
	public JSONObject njAssociateProductOut(NjMerGather njMerGather){
		JSONObject json = new JSONObject();
		try {
			if(njMerGather == null || StringUtils.isEmpty(njMerGather.getId())){
				throw new Exception("商户订单信息为空");
			}
			
			NjMerGather merGather = njMerGatherService.queryMerGatherByPK(njMerGather.getId());
			if(merGather == null){
				throw new Exception("没有查询到商户订单信息，不能下单");
			}

			/**
			 * 商户订单变为失效状态
			 */
			merGather.setIsDeleted(String.valueOf(Constants.COMMON_IS_DELETE));//失效
			merGather.setStatus(Constants.MER_DELIVER_ORDER);//已发货
			njMerGatherService.updateMerGatherByPKSelective(merGather);
			
			/**
			 * 联合社订单更新状态
			 */
			NjOrderGather njOrderGather = njOrderGatherService.queryOrderGatherByPk(merGather.getOrderId());
			njOrderGather.setStatus(Constants.ASSOCIATE_DELIVER_ORDER);//商户已发货
			njOrderGather.setIsDeleted(String.valueOf(Constants.COMMON_ISNOT_DELETE));//有效
			njOrderGatherService.updateOrderGatherByPKSelective(njOrderGather);			
			
			json.put("code", 200);
			json.put("msg", "发货成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
}
