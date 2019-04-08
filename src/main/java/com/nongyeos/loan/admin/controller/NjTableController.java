package com.nongyeos.loan.admin.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.NjProduct;
import com.nongyeos.loan.admin.entity.NjProductOrder;
import com.nongyeos.loan.admin.service.NjProductOrderService;
import com.nongyeos.loan.admin.service.NjProductService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.QiNiuUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller  
@RequestMapping("/njTable")
public class NjTableController {

	@Autowired
	private NjProductOrderService njProductOrderService;
	@Autowired
	private NjProductService njProductService;
	
	/**
	 * 套餐列表
	 */
	@RequestMapping("/njTableList")
	public String njTableList(){
		return "productManage/njTableList";
	}
	
	/**
	 * 异步加载商品列表数据，bootstrap的table表格用
	 */
	@RequestMapping("/asyncLoanRiskPostList")
    @ResponseBody
	public PageBeanUtil<NjProductOrder> asyncLoanRiskPostList(int currentPage, int pageSize, 
			String orderName, String status, HttpServletRequest request){
		try {
			NjProductOrder njProductOrder = new NjProductOrder();
			if(StrUtils.isNotEmpty(orderName)){
				njProductOrder.setOrderName(orderName);
			}
			if(StrUtils.isNotEmpty(status)){
				njProductOrder.setStatus(status);
			}
				
			njProductOrder.setPersonId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			return njProductOrderService.queryNjProductOrderSelective(currentPage, pageSize, njProductOrder);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除商品
	 */
	@RequestMapping("/njProductOrderDelete")
    @ResponseBody
	public boolean njProductOrderDelete(String id){
		try {
			if(!StrUtils.isNotEmpty(id)){
				throw new Exception("套餐id为空");
			}
			njProductOrderService.deleteProductOrderByPK(id);
			
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 跳转新增和编辑
	 */
	@RequestMapping("/toNjProductOrderSaveOrEdit")
	public String toNjProductOrderSaveOrEdit(ModelMap model, String id){
		try {
			//获得所有商品
			List<NjProduct> njProductList = njProductService.queryAllNjProduct();
			model.put("njProductList", njProductList);
			model.put("orderCs", new ArrayList<>());
			
			//传入id为编辑
			if(StrUtils.isNotEmpty(id)){
				NjProductOrder njProductOrder = njProductOrderService.queryProductOrderByPK(id);
				if(njProductOrder == null){
					throw new Exception("没有查询到该套餐");
				}
				JSONArray jsonArray = JSONArray.parseArray(njProductOrder.getOrderProductinfo());
				
				//获取封面图片 和 详情图片 具体url
				String coverUrl = QiNiuUtil.getJpgUrl(njProductOrder.getCoverPic());//封面
				String detailUrl = QiNiuUtil.getJpgUrl(njProductOrder.getDetailPic());//详情
				njProductOrder.setCoverPic(coverUrl);
				njProductOrder.setDetailPic(detailUrl);
				
				model.put("cs", njProductOrder);
				model.put("orderCs", jsonArray);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "productManage/njProductOrderSaveOrEdit";
	}
	
	/**
	 * 计算结算价格
	 * @param productArray
	 * @return
	 */
	@RequestMapping("/njOrderCalculate")
	@ResponseBody
	public JSONObject njOrderCalculate(@RequestBody List<NjProduct> productArray){
		JSONObject json = new JSONObject();
		try {
			if(productArray==null || productArray.size()<=0){
				throw new Exception("没有正确选择套餐商品，无法计算结算价格");
			}
			
			BigDecimal settlePrize = BigDecimal.ZERO;
			for (NjProduct njProduct : productArray) {
				NjProduct product = njProductService.queryNjProductByPrimaryKey(njProduct.getProductId());
				//结算价格
				settlePrize = settlePrize.add(new BigDecimal(product.getSettlePrice()).multiply(new BigDecimal(njProduct.getProductNum())));
			}
			
			json.put("code", 200);
			json.put("msg", "结算价格计算成功");
			json.put("calculate", settlePrize);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	/**
	 * 保存套餐
	 */
	@RequestMapping("/njOrderSaveOrEdit")
	@ResponseBody
	public JSONObject njOrderSaveOrEdit(NjProductOrder njProductOrder, 
			MultipartFile coverPicFile, MultipartFile detailPicFile, HttpServletRequest request){
		JSONObject json = new JSONObject();
		try {
			if(coverPicFile != null && !StringUtils.isEmpty(coverPicFile.getOriginalFilename())){
				//封面图片key
				String coverKey = QiNiuUtil.upLoadFile(coverPicFile.getBytes());
				njProductOrder.setCoverPic(coverKey);
			}
			if(detailPicFile != null && !StringUtils.isEmpty(detailPicFile.getOriginalFilename())){
				//详情图片key
				String detailKey = QiNiuUtil.upLoadFile(detailPicFile.getBytes());
				njProductOrder.setDetailPic(detailKey);
			}
			
			String orderProductids = "";//套餐包含商品id
			JSONArray productArray = new JSONArray();
			JSONArray jsonArray = JSONArray.parseArray(njProductOrder.getOrderProductinfo());
			for (Object object : jsonArray) {
				JSONObject jsonObject = JSONObject.parseObject(object.toString());
				String productId = jsonObject.getString("productId");//商品id
				String productNum = jsonObject.getString("productNum");//套餐选择商品数量
				
				//拼接套餐包含商品id
				orderProductids += productId + ",";
				
				//商品
				NjProduct njProduct = njProductService.queryNjProductByPrimaryKey(productId);
				//将商品生产最新的json串
				JSONObject njProductObject = JSONObject.parseObject(JSONObject.toJSONString(njProduct));
				//设置套餐选择商品的数量
				njProductObject.put("productNum", productNum);
				
				//套餐商品明显
				productArray.add(njProductObject);
			}
			//有id为编辑
			if(njProductOrder!=null && !StringUtils.isEmpty(njProductOrder.getId())){
				njProductOrder.setUpdateDate(DateUtils.getNowDate());
				njProductOrder.setOrderProductids(orderProductids);
				njProductOrder.setOrderProductinfo(productArray.toJSONString());//套餐商品明显
				njProductOrderService.updateProductOrderByPKSelective(njProductOrder);
			}else{
				//保存套餐
				njProductOrder.setId(UUIDUtil.getUUID());
				njProductOrder.setPersonId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
				njProductOrder.setOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
				njProductOrder.setCreateDate(DateUtils.getNowDate());
				njProductOrder.setUpdateDate(DateUtils.getNowDate());
				njProductOrder.setStatus(Constants.ORDER_GROUD);
				njProductOrder.setOrderProductids(orderProductids);
				njProductOrder.setOrderProductinfo(productArray.toJSONString());//套餐商品明显
				njProductOrderService.addProductOrderSelective(njProductOrder);
			}
			
			json.put("code", 200);
			json.put("msg", "套餐保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}
		return json;
	}	
	
	/**
	 * 跳转审核
	 */
	@RequestMapping("/toNjProductOrderExamine")
	public String toNjProductOrderExamine(ModelMap model, String id){
		try {
			//获得所有商品
			List<NjProduct> njProductList = njProductService.queryAllNjProduct();
			model.put("njProductList", njProductList);
			model.put("orderCs", new ArrayList<>());
			
			//传入id为编辑
			if(StrUtils.isNotEmpty(id)){
				NjProductOrder njProductOrder = njProductOrderService.queryProductOrderByPK(id);
				if(njProductOrder == null){
					throw new Exception("没有查询到该套餐");
				}
				JSONArray jsonArray = JSONArray.parseArray(njProductOrder.getOrderProductinfo());
				
				//获取封面图片 和 详情图片 具体url
				String coverUrl = QiNiuUtil.getJpgUrl(njProductOrder.getCoverPic());//封面
				String detailUrl = QiNiuUtil.getJpgUrl(njProductOrder.getDetailPic());//详情
				njProductOrder.setCoverPic(coverUrl);
				njProductOrder.setDetailPic(detailUrl);
				
				model.put("cs", njProductOrder);
				model.put("orderCs", jsonArray);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "productManage/njProductOrderExamine";
	}
	
	/**
	 * 上下架
	 */
	@RequestMapping("/njProductOrderTableInfo")
	public String njProductOrderTableInfo(String id, String status){
		try {
			if(StringUtils.isEmpty(id)){
				throw new Exception("商品id为空");
			}
			if(StringUtils.isEmpty(status)){
				throw new Exception("商品状态为空");
			}
			NjProductOrder productOrder = njProductOrderService.queryProductOrderByPK(id);
			productOrder.setStatus(status);
			njProductOrderService.updateProductOrderByPKSelective(productOrder);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return njTableList();
	}	
	
}

