package com.nongyeos.loan.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.NjProduct;
import com.nongyeos.loan.admin.entity.NjProductOrder;
import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.entity.SysPara;
import com.nongyeos.loan.admin.entity.SysParaGroup;
import com.nongyeos.loan.admin.service.IOrgService;
import com.nongyeos.loan.admin.service.IParaGroupService;
import com.nongyeos.loan.admin.service.IParaService;
import com.nongyeos.loan.admin.service.NjProductOrderService;
import com.nongyeos.loan.admin.service.NjProductService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.QiNiuUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller  
@RequestMapping("/njProduct")
public class NjProductController {

	@Autowired
	private NjProductService njProductService;
	@Autowired
	private IParaGroupService iParaGroupService;
	@Autowired
	private IParaService iParaService;
	@Autowired
	private NjProductOrderService njProductOrderService;
	@Autowired  
    private IOrgService orgService;
	
	/**
	 * 商品列表列表
	 */
	@RequestMapping("/njProductList")
	public String njProductList(ModelMap model, HttpServletRequest request){
		try {
			String ordId = request.getSession().getAttribute(Constants.SESSION_ORGCD).toString();
			if(!StringUtils.isEmpty(ordId)){
				//首先判断是机构还是商户
				SysOrg org = orgService.selectByOrgId(ordId);
				if(org != null){
					//商户
					if(Constants.SELLER.equals(org.getOrgType())){
						//商品品牌
						SysParaGroup brand = iParaGroupService.selectByName(Constants.PRODUCT_BRAND);
						if(brand == null){
							throw new Exception("商品品牌为空");
						}
						
						//商品品牌参数
						List<SysPara> brandList = new ArrayList<SysPara>();
						if(!StringUtils.isEmpty(org.getProductBrand())){
							String[] pBrand = org.getProductBrand().split(",");
							for (String productBrand : pBrand) {
								SysPara sysPara = iParaService.queryParaByGroupIdAndVal(String.valueOf(brand.getPgroupId()), productBrand);
								brandList.add(sysPara);
							}
						}
						model.put("brandList", brandList);
						
					}else{
						/**
						 * 组织机构查看全部品牌
						 */
						//商品品牌
						SysParaGroup brand = iParaGroupService.selectByName(Constants.PRODUCT_BRAND);
						if(brand == null){
							throw new Exception("商品品牌为空");
						}
						//商品品牌参数
						List<SysPara> brandList = iParaService.getListByPId(brand.getPgroupId());
						model.put("brandList", brandList);
					}
				}else{
					throw new Exception("没有查询到组织机构");
				}
			}else{
				throw new Exception("登录失效请重新登录");
			}
			//商品分类
			SysParaGroup category = iParaGroupService.selectByName(Constants.PRODUCT_CATEGORY);
			if(category == null){
				throw new Exception("商品分类为空");
			}
			
			//商品分类参数
			List<SysPara> categoryList = iParaService.getListByPId(category.getPgroupId());
			model.put("categoryList", categoryList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "productManage/njProductList";
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
	 * 删除商品
	 */
	@RequestMapping("/njProductDelete")
    @ResponseBody
	public boolean njProductDelete(String id){
		try {
			if(!StrUtils.isNotEmpty(id)){
				throw new Exception("商品id为空");
			}
			njProductService.deleteNjProductByPK(id);
			
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 跳转新增和编辑
	 */
	@RequestMapping("/toNjProductSaveOrEdit")
	public String toNjProductSaveOrEdit(ModelMap model, String id, HttpServletRequest request){
		try {
			String ordId = request.getSession().getAttribute(Constants.SESSION_ORGCD).toString();
			if(!StringUtils.isEmpty(ordId)){
				//首先判断是机构还是商户
				SysOrg org = orgService.selectByOrgId(ordId);
				if(org != null){
					//商户
					if(Constants.SELLER.equals(org.getOrgType())){
						//商品品牌
						SysParaGroup brand = iParaGroupService.selectByName(Constants.PRODUCT_BRAND);
						if(brand == null){
							throw new Exception("商品品牌为空");
						}
						
						//商品品牌参数
						List<SysPara> brandList = new ArrayList<SysPara>();
						if(!StringUtils.isEmpty(org.getProductBrand())){
							String[] pBrand = org.getProductBrand().split(",");
							for (String productBrand : pBrand) {
								SysPara sysPara = iParaService.queryParaByGroupIdAndVal(String.valueOf(brand.getPgroupId()), productBrand);
								brandList.add(sysPara);
							}
						}
						model.put("brandList", brandList);
						
					}else{
						/**
						 * 组织机构查看全部品牌
						 */
						//商品品牌
						SysParaGroup brand = iParaGroupService.selectByName(Constants.PRODUCT_BRAND);
						if(brand == null){
							throw new Exception("商品品牌为空");
						}
						//商品品牌参数
						List<SysPara> brandList = iParaService.getListByPId(brand.getPgroupId());
						model.put("brandList", brandList);
					}
				}else{
					throw new Exception("没有查询到组织机构");
				}
			}else{
				throw new Exception("登录失效请重新登录");
			}
			//商品分类
			SysParaGroup category = iParaGroupService.selectByName(Constants.PRODUCT_CATEGORY);
			if(category == null){
				throw new Exception("商品分类为空");
			}
			
			//商品分类参数
			List<SysPara> categoryList = iParaService.getListByPId(category.getPgroupId());
			model.put("categoryList", categoryList);		
			
			//传入id为编辑
			if(StrUtils.isNotEmpty(id)){
				NjProduct njProduct = njProductService.queryNjProductByPrimaryKey(id);
				//获取封面图片 和 详情图片 具体url
				String coverUrl = QiNiuUtil.getJpgUrl(njProduct.getCoverPic());//封面
				String detailUrl = QiNiuUtil.getJpgUrl(njProduct.getDetailPic());//详情
				njProduct.setCoverPic(coverUrl);
				njProduct.setDetailPic(detailUrl);
				
				model.put("cs", njProduct);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "productManage/njProductSaveOrEdit";
	}
	
	/**
	 * 保存和编辑商品
	 */
	@RequestMapping("/njSaveOrEdit")
	@ResponseBody
	public JSONObject njSaveOrEdit(NjProduct cs, MultipartFile coverPicFile, MultipartFile detailPicFile, HttpServletRequest request){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(cs.getProductBrand())){
			json.put("code", 400);
			json.put("msg", "请选择商品品牌");
			return json;
		}
		if(StrUtils.isEmpty(cs.getProductCategory())){
			json.put("code", 400);
			json.put("msg", "请选择商品分类");
			return json;
		}
		if(StrUtils.isEmpty(cs.getProductName())){
			json.put("code", 400);
			json.put("msg", "商品名称不能为空");
			return json;
		}
		if(StrUtils.isEmpty(cs.getStandard())){
			json.put("code", 400);
			json.put("msg", "商品规格不能为空");
			return json;
		}
		if(StrUtils.isEmpty(cs.getPrice())){
			json.put("code", 400);
			json.put("msg", "显示价格不能为空");
			return json;
		}
		if(StrUtils.isEmpty(cs.getSettlePrice())){
			json.put("code", 400);
			json.put("msg", "结算价格不能为空");
			return json;
		}
		if(StrUtils.isEmpty(cs.getProductDesc())){
			json.put("code", 400);
			json.put("msg", "商品描述不能为空");
			return json;
		}
		try {
			if(coverPicFile != null && !StringUtils.isEmpty(coverPicFile.getOriginalFilename())){
				//封面图片key
				String coverKey = QiNiuUtil.upLoadFile(coverPicFile.getBytes());
				cs.setCoverPic(coverKey);
			}
			if(detailPicFile != null && !StringUtils.isEmpty(detailPicFile.getOriginalFilename())){
				//详情图片key
				String detailKey = QiNiuUtil.upLoadFile(detailPicFile.getBytes());
				cs.setDetailPic(detailKey);
			}
			
			//商品分类
			SysParaGroup category = iParaGroupService.selectByName(Constants.PRODUCT_CATEGORY);
			//商品品牌
			SysParaGroup brand = iParaGroupService.selectByName(Constants.PRODUCT_BRAND);
			
			//商品分类名称
			SysPara sysProductCategory = iParaService.queryParaByGroupIdAndVal(String.valueOf(category.getPgroupId()), cs.getProductCategory());
			//商品品牌名称
			SysPara sysProductBrand = iParaService.queryParaByGroupIdAndVal(String.valueOf(brand.getPgroupId()), cs.getProductBrand());
			
			if(cs!=null && StringUtils.isEmpty(cs.getId())){
				/**
				 * 设置商品剩下参数
				 */
				cs.setProductCategoryName(sysProductCategory.getDescr());
				cs.setProductBrandName(sysProductBrand.getDescr());
				cs.setStatus(Constants.PRODUCT_SHELF);
				cs.setId(UUIDUtil.getUUID());
				cs.setPersonId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
				cs.setOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
				cs.setCreateDate(DateUtils.getNowDate());
				cs.setUpdateDate(DateUtils.getNowDate());
				njProductService.addNjProductSelective(cs);
			}else{
				NjProduct csProduct = njProductService.queryNjProductByPrimaryKey(cs.getId());
				cs.setPersonId(csProduct.getPersonId());
				cs.setOrgId(csProduct.getOrgId());
				cs.setProductCategoryName(sysProductCategory.getDescr());
				cs.setProductBrandName(sysProductBrand.getDescr());
				cs.setStatus(Constants.PRODUCT_SHELF);
				cs.setCreateDate(csProduct.getCreateDate());
				cs.setUpdateDate(DateUtils.getNowDate());
				njProductService.updateNjProductPKSelective(cs);
				
				//修改商品 把相关套餐也更改为下架
				List<NjProductOrder> orderList = njProductOrderService.queryNjProductOrderProductids(csProduct.getId());
				for (NjProductOrder njProductOrder : orderList) {
					njProductOrder.setStatus(Constants.ORDER_REJECT);//下架
					njProductOrderService.updateProductOrderByPKSelective(njProductOrder);
				}
			}
			
			json.put("code", 200);
			json.put("msg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
}
