package com.nongyeos.loan.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.nongyeos.loan.admin.entity.NjProduct;
import com.nongyeos.loan.admin.entity.SysPara;
import com.nongyeos.loan.admin.entity.SysParaGroup;
import com.nongyeos.loan.admin.service.IParaGroupService;
import com.nongyeos.loan.admin.service.IParaService;
import com.nongyeos.loan.admin.service.NjProductService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.QiNiuUtil;
import com.nongyeos.loan.base.util.StrUtils;

@Controller  
@RequestMapping("/njReview")
public class NjReviewController {

	@Autowired
	private NjProductService njProductService;
	@Autowired
	private IParaGroupService iParaGroupService;
	@Autowired
	private IParaService iParaService;	
	
	/**
	 * 上架审核
	 */
	@RequestMapping("/njReviewList")
	public String njReviewList(ModelMap model){
		try {
			//商品分类
			SysParaGroup category = iParaGroupService.selectByName(Constants.PRODUCT_CATEGORY);
			//商品品牌
			SysParaGroup brand = iParaGroupService.selectByName(Constants.PRODUCT_BRAND);
			
			if(category == null){
				throw new Exception("商品分类为空");
			}
			if(brand == null){
				throw new Exception("商品品牌为空");
			}
			//商品分类参数
			List<SysPara> categoryList = iParaService.getListByPId(category.getPgroupId());
			//商品品牌参数
			List<SysPara> brandList = iParaService.getListByPId(brand.getPgroupId());
			
			model.put("categoryList", categoryList);
			model.put("brandList", brandList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "productManage/njReviewList";
	}
	
	/**
	 * 异步加载商品列表数据，bootstrap的table表格用
	 */
	@RequestMapping("/asyncLoanRiskPostList")
    @ResponseBody
	public PageBeanUtil<NjProduct> asyncLoanRiskPostList(int currentPage, int pageSize, String productBrand, 
			String productName, String productCategory, String status, HttpServletRequest request){
		try {
			NjProduct njProduct = new NjProduct();
			if(StrUtils.isNotEmpty(productBrand)){
				njProduct.setProductBrand(productBrand);
			}
				
			if(StrUtils.isNotEmpty(productName)){
				njProduct.setProductName(productName);
			}
				
			if(StrUtils.isNotEmpty(productCategory)){
				njProduct.setProductCategory(productCategory);
			}
			
			if(StrUtils.isNotEmpty(status)){
				njProduct.setStatus(status);
			}
			
			njProduct.setPersonId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			return njProductService.queryNjProductSelective(currentPage, pageSize, njProduct);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	/**
	 * 跳转审核
	 */
	@RequestMapping("/toNjProductSaveOrEdit")
	public String toNjProductSaveOrEdit(ModelMap model, String id){
		try {
			//传入id
			if(StringUtils.isEmpty(id)){
				throw new Exception("商品id为空");
			}
			//商品分类
			SysParaGroup category = iParaGroupService.selectByName(Constants.PRODUCT_CATEGORY);
			//商品品牌
			SysParaGroup brand = iParaGroupService.selectByName(Constants.PRODUCT_BRAND);
			
			if(category == null){
				throw new Exception("商品分类为空");
			}
			if(brand == null){
				throw new Exception("商品品牌为空");
			}
			//商品分类参数
			List<SysPara> categoryList = iParaService.getListByPId(category.getPgroupId());
			//商品品牌参数
			List<SysPara> brandList = iParaService.getListByPId(brand.getPgroupId());
			
			model.put("categoryList", categoryList);
			model.put("brandList", brandList);			
			
			NjProduct njProduct = njProductService.queryNjProductByPrimaryKey(id);
			//获取封面图片 和 详情图片 具体url
			String coverUrl = QiNiuUtil.getJpgUrl(njProduct.getCoverPic());//封面
			String detailUrl = QiNiuUtil.getJpgUrl(njProduct.getDetailPic());//详情
			njProduct.setCoverPic(coverUrl);
			njProduct.setDetailPic(detailUrl);
			
			model.put("cs", njProduct);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "productManage/njReviewInfo";
	}	
	
	/**
	 * 审核，驳回
	 */
	@RequestMapping("/njProductReviewInfo")
	public String toNjProductSaveOrEdit(String id, String status, ModelMap model){
		try {
			if(StringUtils.isEmpty(id)){
				throw new Exception("商品id为空");
			}
			if(StringUtils.isEmpty(status)){
				throw new Exception("商品状态为空");
			}
			NjProduct njProduct = njProductService.queryNjProductByPrimaryKey(id);
			njProduct.setStatus(status);
			njProductService.updateNjProductPKSelective(njProduct);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return njReviewList(model);
	}
}
