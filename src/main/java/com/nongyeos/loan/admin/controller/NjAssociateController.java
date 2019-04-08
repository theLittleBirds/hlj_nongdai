package com.nongyeos.loan.admin.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.NjMerGather;
import com.nongyeos.loan.admin.entity.NjOrderGather;
import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.service.IOrgService;
import com.nongyeos.loan.admin.service.NjMerGatherService;
import com.nongyeos.loan.admin.service.NjOrderGatherService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller  
@RequestMapping("/njAssociate")
public class NjAssociateController {

	@Autowired
	private NjOrderGatherService njOrderGatherService;//联合社订单
	@Autowired
	private NjMerGatherService njMerGatherService;//商户订单
	@Autowired
    private IOrgService orgService;

	/**
	 * 联合社订单列表
	 */
	@RequestMapping("/njAssociateList")
	public String njTableList(){
		return "union/njAssociateList";
	}	
	
	/**
	 * 异步加载商品列表数据，bootstrap的table表格用
	 */
	@RequestMapping("/asyncLoanRiskPostList")
    @ResponseBody
	public PageBeanUtil<NjOrderGather> asyncLoanRiskPostList(int currentPage, int pageSize, 
			String orgName, String memberName, String productName, String status, String merName,
			HttpServletRequest request){
		try {
			NjOrderGather njOrderGather = new NjOrderGather();
			if(StrUtils.isNotEmpty(orgName)){
				njOrderGather.setOrgName(orgName);
			}
			if(StrUtils.isNotEmpty(memberName)){
				njOrderGather.setMemberName(memberName);
			}
			if(StrUtils.isNotEmpty(productName)){
				njOrderGather.setProductName(productName);
			}
			if(StrUtils.isNotEmpty(status)){
				njOrderGather.setStatus(status);
			}
			if(StrUtils.isNotEmpty(merName)){
				njOrderGather.setMerName(merName);
			}
				
			return njOrderGatherService.queryNjProductOrderSelective(currentPage, pageSize, njOrderGather);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	/**
	 * 联合社下单即联合社付定金 ：生成商品订单（线下付定金）
	 */
	@RequestMapping("/njAssociateSave")
	@ResponseBody
	public JSONObject njAssociateSave(NjOrderGather njOrderGather, HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			if(njOrderGather == null || StringUtils.isEmpty(njOrderGather.getId())){
				throw new Exception("联合社订单信息为空");
			}
			
			NjOrderGather orderGather = njOrderGatherService.queryOrderGatherByPk(njOrderGather.getId());
			if(orderGather == null){
				throw new Exception("没有查询到联合社订单信息，不能下单");
			}
			
			/**
			 * 生成商户订单，联合社订单变为失效状态,状态变为“已付定金，商户待接单”
			 */
			//更新联合社订单状态 
			orderGather.setIsDeleted(String.valueOf(Constants.COMMON_IS_DELETE));//失效
			orderGather.setStatus(Constants.ASSOCIATE_WAIT_ORDER);//已付定金，商户待接单
			njOrderGatherService.updateOrderGatherByPKSelective(orderGather);
			
			//查询商户的组织机构
			SysOrg orgPro = orgService.selectByOrgId(orderGather.getMerOrgId());
			//已付定金金额（   担保金比例/商户付款比例（%） ）
			BigDecimal downtotalprice = new BigDecimal(orderGather.getSettleTotalprice()).multiply(new BigDecimal(orgPro.getWarrantRate())).divide(new BigDecimal("100"));
			
			//生成商户订单
			NjMerGather njMerGather = new NjMerGather();
			njMerGather.setId(UUIDUtil.getUUID());
			njMerGather.setOrderId(orderGather.getId());//联合社订单id
			njMerGather.setIntoPieceId(orderGather.getIntoPieceId());//进件id
			njMerGather.setOrgId(orderGather.getOrgId());//进件部门组织机构编码
			njMerGather.setOrgName(orderGather.getOrgName());//进件部门名称（组织机构名称）
			njMerGather.setMemberName(orderGather.getMemberName());//客户姓名
			njMerGather.setMerOrgId(orderGather.getMerOrgId());//商户组织机构编码
			njMerGather.setMerName(orderGather.getMerName());//商家名字
			njMerGather.setProductName(orderGather.getProductName());//商品名称
			njMerGather.setProductBrandName(orderGather.getProductBrandName());//商品品牌名字
			njMerGather.setProductBrand(orderGather.getProductBrand());//商品品牌，  1：沃肯多，2：金正大，3：沃夫特，4：科雨，5：东北吉化，6：万通盛泰，7：施地壮
			njMerGather.setProductCategoryName(orderGather.getProductCategoryName());//商品分类名字
			njMerGather.setProductCategory(orderGather.getProductCategory());//商品分类，  1：化肥，2：种子，3：农药，4：农机，5：其他农用品
			njMerGather.setStatus(Constants.MER_PENDING_ORDER);//订单状态   待接单
			njMerGather.setDownTotalprice(downtotalprice.toString());//已付定金
			njMerGather.setSettleTotalprice(orderGather.getSettleTotalprice());//商家订单结算价格
			njMerGather.setProductNum(orderGather.getProductNum());//商品数量
			njMerGather.setIsDeleted(String.valueOf(Constants.COMMON_ISNOT_DELETE));//有效
			njMerGather.setDownPricetime(DateUtils.formatDateTime(DateUtils.getNowDate()));//定金支付时间
			njMerGather.setCreateDate(DateUtils.getNowDate());//创建时间
			njMerGather.setUpdateDate(DateUtils.getNowDate());//更新时间
			njMerGatherService.addMerGatherSelective(njMerGather);

			json.put("code", 200);
			json.put("msg", "下单成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	/**
	 * 联合社付尾款（线下付尾款）
	 */
	@RequestMapping("/njAssociateFinalPaySave")
	@ResponseBody
	public JSONObject njAssociateFinalPaySave(NjOrderGather njOrderGather, HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			if(njOrderGather == null || StringUtils.isEmpty(njOrderGather.getId())){
				throw new Exception("联合社订单信息为空");
			}
			
			NjOrderGather orderGather = njOrderGatherService.queryOrderGatherByPk(njOrderGather.getId());
			if(orderGather == null){
				throw new Exception("没有查询到联合社订单信息，不能下单");
			}

			/**
			 * 联合社订单变为失效状态
			 */
			orderGather.setIsDeleted(String.valueOf(Constants.COMMON_IS_DELETE));//失效
			njOrderGatherService.updateOrderGatherByPKSelective(orderGather);
			
			//更改商户订单状态
			NjMerGather njMerGather = njMerGatherService.queryMerGatherByOrderId(njOrderGather.getId());
			njMerGather.setStatus(Constants.MER_WAIT_ORDER);//待发货
			njMerGather.setIsDeleted(String.valueOf(Constants.COMMON_ISNOT_DELETE));//有效
			njMerGatherService.updateMerGatherByPKSelective(njMerGather);

			json.put("code", 200);
			json.put("msg", "下单成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}
		return json;
	}	
	
}
