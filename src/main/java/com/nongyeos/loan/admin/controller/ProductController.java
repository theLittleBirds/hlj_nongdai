package com.nongyeos.loan.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.admin.entity.BusLender;
import com.nongyeos.loan.admin.entity.BusProduct;
import com.nongyeos.loan.admin.entity.BusProductOrg;
import com.nongyeos.loan.admin.entity.SysPara;
import com.nongyeos.loan.admin.entity.SysParaGroup;
import com.nongyeos.loan.admin.service.IParaGroupService;
import com.nongyeos.loan.admin.service.IParaService;
import com.nongyeos.loan.admin.service.IProductService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/product")
public class ProductController {
	@Resource
	private IProductService productService;
	
	@Autowired
	private IParaService paraService;
	
	@Autowired  
    private IParaGroupService paraGroupService;
	
	private static final Logger logger = LogManager.getLogger(ProductController.class);
	/**
	 * @throws Exception 
	 * 
	 * @Title: productList 
	 * @Description:产品列表查询展示 
	 * @param @param currentPage
	 * @param @param pageSize
	 * @param @param product
	 * @param @return     
	 * @return PageBeanUtil<BusProduct>     
	 * @throws
	 */
	@RequestMapping("/productList")
	@ResponseBody
	public PageBeanUtil<BusProduct> productList(HttpServletRequest request,int currentPage,int pageSize,BusProduct product) throws Exception{
		String personId = (String) request.getSession().getAttribute(Constants.SESSION_PERSONCD);
		if(product==null)
			product = new BusProduct();
		PageBeanUtil<BusProduct>  pageBean=productService.selectByPage(personId,currentPage,pageSize,product);
		return pageBean;
	}
	
	/**
	 * @throws Exception 
	 * 
	 * @Title: saveProduct 
	 * @Description:保存更新产品 
	 * @param @param response
	 * @param @param request
	 * @param @param product
	 * @param @return     
	 * @return Map<String,Object>     
	 * @throws
	 */
	@RequestMapping("/save")
    @ResponseBody
    @Transactional
	public Map<String, Object> saveProduct(HttpServletResponse response,HttpServletRequest request,BusProduct product,String chooseOrgIds) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(productService.existedSameName(product)){
			map.put("msg", "该产品已存在，请重新填写！");
		}else{
			product.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
			product.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
			product.setUpdOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
			if(StringUtils.isBlank(product.getProductId())){
				product.setCreOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
				product.setCreOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
				product.setCreOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
				productService.addProduct(product,chooseOrgIds);
			}else{
				productService.updateProduct(product,chooseOrgIds);
			}
		}
		map.put("msg", "保存成功！");
		return map;
	}
	/**
	 * @throws Exception 
	 * 
	 * @Title: delLender 
	 * @Description: 删除产品及产品部门关联关系
	 * @param @param response
	 * @param @param request
	 * @param @param currIds
	 * @param @return     
	 * @return Map<String,Object>     
	 * @throws
	 */
	@RequestMapping("/delProduct")
    @ResponseBody
    @Transactional
	public Map<String, Object> delLender(HttpServletResponse response,HttpServletRequest request,String currIds) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(currIds != null && !currIds.equals("")){
    		String[] productIds = currIds.split(",");
    		for(String productId : productIds){
    			productService.delProduct(productId);
    		}
    		map.put("msg", "删除成功");
    	}else{
    		map.put("msg", "删除失败");
    	}
		return map;
	}
	
	/**
	 * @throws Exception 
	 * 
	 * @Title: delLender 
	 * @Description: 查询产品与部门关联
	 * @param @param response
	 * @param @param request
	 * @param @param currIds
	 * @param @return     
	 * @return Map<String,Object>     
	 * @throws
	 */
	@RequestMapping("/getOrgByProduct")
	@ResponseBody
	public Map<String, Object> getOrgByProduct(String productId) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<BusProductOrg> productOrgs = productService.selectOrgByProductId(productId);
		String objectIds = "";
		if(productOrgs!=null&&productOrgs.size()>0){
			for(int i = 0;i<productOrgs.size();i++){
				if(objectIds.equals(""))
					objectIds+=productOrgs.get(i).getOrgId();
				else
					objectIds+=","+productOrgs.get(i).getOrgId();
			}
		}
		map.put("objectIds", objectIds);
		return map;
	}
	/**
	 * @throws Exception 
	 * 
	 * @Title: getOrgTree 
	 * @Description: 展示树状部门
	 * @param @return     
	 * @return String     
	 * @throws
	 */
	@RequestMapping("/findFins")
	@ResponseBody
	public Map<String, Object> findFins(String productId,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		try {
			String personId = (String) request.getSession().getAttribute(Constants.SESSION_PERSONCD);
			return productService.findFins(productId,personId);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", "<option value=''>--请选择--</option>");
			return map;
		}
	}
	
	
	
	/**
	 * 获取金融产品详情
	 * @return
	 */
	@RequestMapping("/productdetail")
	@ResponseBody
	public String productDetail(String productId){
		try {
			BusProduct busProduct = productService.selectByProductId(productId);
			SysParaGroup pGroup = paraGroupService.selectByName("REPAYMENT_WAY");
			if(pGroup != null){
				List<SysPara> list = paraService.getListByPId(pGroup.getPgroupId());
				String mothRateType="月";
				if(busProduct.getMonthRateType()!=null&&busProduct.getMonthRateType()==2){
					mothRateType="年";
				}
				String serviceRateType="月";
				if(busProduct.getServiceRateType()!=null&&busProduct.getServiceRateType()==2){
					serviceRateType="年";
				}
				for (int i = 0; i < list.size(); i++) {
					if((""+busProduct.getRepaymentWay()).equals(list.get(i).getValue())){
						return  list.get(i).getDescr()+","+mothRateType+"利率"+busProduct.getMonthRate()+"%,"+serviceRateType+"服务费率"+busProduct.getServiceRate()+"%";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
