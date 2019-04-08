package com.nongyeos.loan.admin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nongyeos.loan.admin.entity.BusLender;
import com.nongyeos.loan.admin.service.ILenderService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.IdCheck;
import com.nongyeos.loan.base.util.PageBeanUtil;

/**
 * 
 * Title:LenderController 
 * Description: 出借人 
 * @author lcg
 * @date 2018年4月17日 下午5:43:28
 */
@Controller
@RequestMapping("/lender")
public class LenderController {
	
	private static final Logger logger = LogManager.getLogger(LenderController.class);
	
	@Resource
	private ILenderService lenderService;
	
	/**
	 * @throws Exception 
	 * 
	 * @Title: index 
	 * @Description: 出借人列表
	 * @param @param lender
	 * @param @return     
	 * @return PageBeanUtil<BusLender>     
	 * @throws
	 */
	
	@RequestMapping("/lenderList")
    @ResponseBody
	public PageBeanUtil<BusLender> index(int currentPage,int pageSize,BusLender lender) throws Exception{
		if(lender==null)
			lender = new BusLender();
			PageBeanUtil<BusLender> pageBean = lenderService.selectByPage(currentPage,pageSize,lender);
		return pageBean;
	}
	
	/**
	 * @throws Exception 
	 * 
	 * @Title: saveMember 
	 * @Description: 增加或者修改出借人及出借银行
	 * @param @param response
	 * @param @param request
	 * @param @param lender
	 * @param @return     
	 * @return Map<String,Object>     
	 * @throws
	 */
	@RequestMapping("/save")
    @ResponseBody
    @Transactional
    public Map<String, Object> saveLender(HttpServletResponse response,HttpServletRequest request,BusLender lender,String chooseOrgIds) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(lender.getType()==1){
			if(!IdCheck.isValidatedAllIdcard(lender.getIdCard())){
				map.put("msg", "身份证填写错误，请重新填写！");
				map.put("errorno", "1000");
				return map;
			}
		}
		if(lenderService.existedSameName(lender)){
			map.put("msg", "该出借人已存在，请重新填写！");
		}else{
			lender.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
			lender.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
			lender.setUpdOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
			if(StringUtils.isBlank(lender.getLenderId()) ){
				lender.setCreOperId( request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
				lender.setCreOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
				lender.setCreOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
				lenderService.addLender(lender,chooseOrgIds);
			}else{
				lenderService.updateLender(lender,chooseOrgIds);
			}
			map.put("msg", "保存成功！");
		}
		
		return map;
	}
	/**
	 * @throws Exception 
	 * 
	 * @Title: delLender 
	 * @Description: 删除出借人/出借银行
	 * @param @param response
	 * @param @param request
	 * @param @param currIds
	 * @param @return     
	 * @return Map<String,Object>     
	 * @throws
	 */
	@RequestMapping("/delLender")
    @ResponseBody
    @Transactional
	public Map<String, Object> delLender(HttpServletResponse response,HttpServletRequest request,String currIds) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(currIds != null && !currIds.equals("")){
    		String[] lenderIds = currIds.split(",");
    		for(String lenderId : lenderIds){
    			lenderService.delLender(lenderId);
    		}
    		map.put("msg", "删除成功");
    	}else{
    		map.put("msg", "删除失败");
    	}
		return map;
	}
	
	@RequestMapping("/getOrgTree")
	@ResponseBody
	public Map<String, Object> getOrgTree(String lenderId) throws Exception{
		return lenderService.getOrgTree(lenderId);
	}
	
}
