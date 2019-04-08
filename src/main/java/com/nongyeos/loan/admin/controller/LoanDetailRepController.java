package com.nongyeos.loan.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.resultMap.LoandetailRepaymentMap;
import com.nongyeos.loan.admin.service.ILoanDetailService;
import com.nongyeos.loan.admin.service.ILoandetailRepaymentService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;


@Controller
@RequestMapping("/loanDetailRep")
public class LoanDetailRepController{

	@Autowired
	private ILoandetailRepaymentService loandetailRepaymentService;
	@Autowired
	private ILoanDetailService loanDetailService;
	
	/**
	 * 还款复核列表
	 * @return
	 */
	@RequestMapping("/waitVerify")
	public String waitVerify(){
		return "waitVerify/waitVerifyInfoList";
	}
	
	/**
	 * 异步获取还款复核列表，bootstrap的table表格用
	 * @return
	 */
	@RequestMapping("/asyncWaitVerifyInfoList")
    @ResponseBody
	public PageBeanUtil<LoandetailRepaymentMap> asyncWaitVerifyInfoList(int currentPage, int pageSize, String memberName, 
			String idCard, HttpServletRequest request){
		try {
			LoandetailRepaymentMap LoandetailRepayment = new LoandetailRepaymentMap();
			if(StrUtils.isNotEmpty(memberName)){
				LoandetailRepayment.setMember_name(memberName);
			}
				
			if(StrUtils.isNotEmpty(idCard)){
				LoandetailRepayment.setId_card(idCard);
			}
			
			LoandetailRepayment.setPersonId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			LoandetailRepayment.setStatus(Constants.LOAN_DETIAL_REPAYMENT_VERIFYING);//复审中
			LoandetailRepayment.setDelFlag(Integer.valueOf(String.valueOf(Constants.COMMON_ISNOT_DELETE)));
			return loandetailRepaymentService.queryWaitVerify(currentPage, pageSize, LoandetailRepayment);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 还款复核
	 * @param model
	 * @param request
	 * @param ldrId是
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/waitVerifyCheck")
	public boolean waitVerifyCheck(ModelMap model, HttpServletRequest request, String ldrId){
		try {
			if(StringUtils.isEmpty(ldrId)){
				throw new Exception("贷款明细记录id为空");
			}
			
			JSONObject object = loanDetailService.onLineRepay(ldrId, request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			if(object!=null && object.getIntValue("code") == 200){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	} 
	
}
