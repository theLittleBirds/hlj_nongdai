package com.nongyeos.loan.admin.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.StringUtil;
import com.nongyeos.loan.admin.entity.*;
import com.nongyeos.loan.admin.service.*;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.QiNiuUtil;
import com.nongyeos.loan.base.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/njBuying")
public class NjBuyingController {

	@Autowired
	private IOrgService orgService;
	@Autowired
	private IParaGroupService iParaGroupService;
	@Autowired
	private IParaService iParaService;
	@Autowired
	private NjProductOrderService njProductOrderService;
	@Autowired
	private NjProductService njProductService;

	/**
	 * 商品列表
	 * @return
	 */
	@RequestMapping("/njProductList")
	public String njBuyingList(ModelMap model, HttpServletRequest request){
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

		}catch (Exception e){
			e.printStackTrace();
		}
		return "shoppingCenter/njProList";
	}

	/**
	 * 异步加载商品列表数据，bootstrap的table表格用
	 */
	@RequestMapping("/njProsList")
	@ResponseBody
	public PageBeanUtil<NjProduct> asyncLoanRiskPostList(int currentPage, int pageSize,String productBrand, String productName, String productCategory){
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
			njProduct.setStatus("1");
			PageBeanUtil<NjProduct> productList = njProductService.queryNjProductSelective(currentPage, pageSize, njProduct);
			return productList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 套餐列表
	 */
	@RequestMapping("/njTaocanList")
	public String njTableList(){
		return "shoppingCenter/njTaocanList";
	}

	/**
	 * 异步加载商品套餐列表数据，bootstrap的table表格用
	 */
	@RequestMapping("/asyncLoanRiskPostList")
	@ResponseBody
	public PageBeanUtil<NjProductOrder> asyncLoanRiskPostList(int currentPage, int pageSize,String orderName){
		try {
			NjProductOrder njProductOrder = new NjProductOrder();
			if(StrUtils.isNotEmpty(orderName)){
				njProductOrder.setOrderName(orderName);
			}
			njProductOrder.setStatus("1");
			return njProductOrderService.queryNjProductOrderSelective(currentPage, pageSize, njProductOrder);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 商品详情
	 */

	@RequestMapping("/njProductInfo")
	public String productInfo(ModelMap model,String type,String productId, HttpServletRequest request){
		try {
			if(StringUtil.isNotEmpty(type) && StringUtil.isNotEmpty(productId)){
				//type:1单品 2套餐
				if(type.equals("1")){
					// 组织机构查看全部品牌
					SysParaGroup brand = iParaGroupService.selectByName(Constants.PRODUCT_BRAND);
					if(brand == null){
						throw new Exception("商品品牌为空");
					}
					//商品品牌参数
					List<SysPara> brandList = iParaService.getListByPId(brand.getPgroupId());
					model.put("brandList", brandList);

					//商品分类
					SysParaGroup category = iParaGroupService.selectByName(Constants.PRODUCT_CATEGORY);
					if(category == null){
						throw new Exception("商品分类为空");
					}
					//商品分类参数
					List<SysPara> categoryList = iParaService.getListByPId(category.getPgroupId());
					model.put("categoryList", categoryList);

					//根据id获得具体的商品
					NjProduct njProduct =  njProductService.queryNjProductByPrimaryKey(productId);
					if(njProduct != null){
						//获取封面图片 和 详情图片 具体url
						String coverUrl = QiNiuUtil.getJpgUrl(njProduct.getCoverPic());//封面
						String detailUrl = QiNiuUtil.getJpgUrl(njProduct.getDetailPic());//详情
						njProduct.setCoverPic(coverUrl);
						njProduct.setDetailPic(detailUrl);
						model.put("njProduct",njProduct);
						model.put("type","1");
					}else{
						throw new Exception("商品为空");
					}
				}else if(type.equals("2")){
					//获得所有商品
					List<NjProduct> njProductList = njProductService.queryAllNjProduct();
					model.put("njProductList", njProductList);
					model.put("orderCs", new ArrayList<>());

					NjProductOrder njProductOrder = njProductOrderService.queryProductOrderByPK(productId);
					JSONArray jsonArray = JSONArray.parseArray(njProductOrder.getOrderProductinfo());
					if(njProductOrder != null){
						//获取封面图片 和 详情图片 具体url
						String coverUrl = QiNiuUtil.getJpgUrl(njProductOrder.getCoverPic());//封面
						String detailUrl = QiNiuUtil.getJpgUrl(njProductOrder.getDetailPic());//详情
						njProductOrder.setCoverPic(coverUrl);
						njProductOrder.setDetailPic(detailUrl);
						model.put("cs",njProductOrder);
						model.put("orderCs", jsonArray);
						model.put("type",2);
					}else{
						throw new Exception("套餐为空");
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	return "shoppingCenter/njProsInfo";
	}

}
