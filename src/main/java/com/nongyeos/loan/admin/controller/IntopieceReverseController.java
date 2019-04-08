package com.nongyeos.loan.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.ApiWXGuaranteeFee;
import com.nongyeos.loan.admin.entity.BusGuaranteeReverse;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusIntopieceReverse;
import com.nongyeos.loan.admin.entity.BusRefund;
import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.model.IntoPieceConfig;
import com.nongyeos.loan.admin.service.ApiWxGuaranteefeeService;
import com.nongyeos.loan.admin.service.IGuaranteeReverseService;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.admin.service.IIntopieceReverseService;
import com.nongyeos.loan.admin.service.IPersonService;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.service.IAppEntryService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.HttpClientUtil;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Controller
@RequestMapping("/intopiecereverse")
public class IntopieceReverseController {
	
	@Autowired
	private IIntopieceReverseService intopieceReverseService;
	
	@Autowired
	private IGuaranteeReverseService guaranteeReverseService;
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@Autowired
	private IAppEntryService entryService;
	
	@Autowired
	private IntoPieceConfig pieceConfig;
	@Autowired
	private IPersonService personService;
	
	@Autowired
	private IWebUserService userService;
	
	@Autowired
	private ApiWxGuaranteefeeService wxGuaranteefeeService;
	
	private static final Logger logger = LogManager
			.getLogger(IntopieceReverseController.class);
	
	@RequestMapping("/intopiecereverselist")
	public String payments(){
		return "lending/intopiecereverselist";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public PageBeanUtil<BusIntopieceReverse> list(int currentPage, int pageSize,
			String orgName, String memberName, String idCard, String phone,
			String status,HttpServletRequest request)throws Exception{
		String personId = (String) request.getSession().getAttribute(Constants.SESSION_PERSONCD);
		try {
			BusIntopieceReverse ipr = new BusIntopieceReverse();
			if(StringUtils.isNotEmpty(orgName))
				ipr.setOrgName(orgName);
			if(StringUtils.isNotEmpty(memberName))
				ipr.setMemberName(memberName);
			if(StringUtils.isNotEmpty(idCard))
				ipr.setIdCard(idCard);
			if(StringUtils.isNotEmpty(phone))
				ipr.setPhone(phone);
			if(StringUtils.isNotEmpty(status))
				ipr.setStatus(status);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ipReverse", ipr);
			return intopieceReverseService.selectByPage(personId,map,currentPage,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return new PageBeanUtil<BusIntopieceReverse>();
		}
	}
	
	@RequestMapping("/refund")
	@ResponseBody
	public JSONObject refund(String id,HttpServletRequest request)throws Exception{
		JSONObject retJson = new JSONObject();
		try {
			BusIntopieceReverse ipr = intopieceReverseService.selectByPrimaryKey(id);
			if(ipr==null){
				retJson.put("code", 300);
				retJson.put("msg", "未找到该订单");
				return retJson;
			}
			AppEntry entry = entryService.queryByAppModeId(ipr.getIntoPieceId());
			if(!entry.getNodeName().equals(Constants.FLOW_REPAY_COMPLETE)){
				retJson.put("code", 300);
				retJson.put("msg", "贷款未完成还款，退款失败");
				return retJson;
			}
			List<BusGuaranteeReverse> listgr = guaranteeReverseService.selectByIntoPieceId(ipr.getIntoPieceId());
			boolean flag = true;
			for (int i = 0; i < listgr.size(); i++) {
				BusGuaranteeReverse reverse = listgr.get(i);
				if(reverse.getStatus()!=null&&reverse.getStatus().equals("RS")){
					continue;
				}
				//银行支付
				if(reverse.getPayWay().equals("1")){
					//TODO 银行支付的支付及退款
					
					//微信支付
				}else if(reverse.getPayWay().equals("2")){
					ApiWXGuaranteeFee wxGuaranteeFee = new ApiWXGuaranteeFee();
					wxGuaranteeFee.setIntoPieceId(ipr.getIntoPieceId());
					wxGuaranteeFee.setStatus("S");
					wxGuaranteeFee= wxGuaranteefeeService.selectByIpIdAndStatus(wxGuaranteeFee);
					String url = pieceConfig.getWxRefundCheck();
					SysWebUser user = userService.selectUserByUserName(pieceConfig.getUserName());
					Map<String, String> map = new HashMap<String, String>();
					map.put("userid", user.getUserId());
					String signature = user.getUserId() + user.getUsername() + user.getPassword();
					map.put("signature", DigestUtils.md5Hex(signature));
					map.put("companyType", "HLJSX");
					map.put("amount", reverse.getTotalAmount().toString());
					map.put("totalAmount",reverse.getTotalAmount().toString() );
//					map.put("certificateNo", person.getCardNo());
					map.put("certificateNo", reverse.getPayerIdcard());
					map.put("accountName", reverse.getPayer());
					map.put("mobileNo", reverse.getPayerMobile());
					map.put("intoPieceId", ipr.getIntoPieceId());
					map.put("refundId", reverse.getId());
					map.put("merchantNo", wxGuaranteeFee.getMerchantNo());
					System.err.println(map);
					String retMap = HttpClientUtil.doPost(url, map,
							 "utf-8");
					System.err.println("退款申请返回数据"+retMap);
					if(retMap!=null){
						JSONObject retMap1 = JSONObject.parseObject(retMap);
						if(retMap1==null||StringUtils.isEmpty(retMap1.getString("isSuccess"))||retMap1.getString("isSuccess").equals(Constants.GATE_RESULT_FAIL)){
							retJson.put("msg", "微信申请退款失败");
							retJson.put("errono", 3000);
							return retJson;
						}
						if(!StringUtils.isEmpty(retMap1.getString("refundId"))&&!StringUtils.isEmpty(retMap1.getString("status"))){
							if(retMap1.getString("refundId").equals(reverse.getId())){
								reverse.setStatus("R"+retMap1.getString("status"));
								guaranteeReverseService.updateByPrimaryKey(reverse);
							}else{
								retJson.put("msg", "微信退款标识错误");
								retJson.put("errono", 3000);
								return retJson;
							}
						}else{
							retJson.put("msg", "微信申请退款失败");
							retJson.put("errono", 3000);
							return retJson;
						}
					}else{
						retJson.put("msg", "未接收到微信回应");
						retJson.put("errono", 3000);
						return retJson;
					}
					
					flag=false;
					//线下支付
				}else if(reverse.getPayWay().equals("3")){
					reverse.setStatus("RS");
					reverse.setUpdateDate(DateUtils.getNowDate());
					guaranteeReverseService.updateByPrimaryKey(reverse);
					//暂不支付
				}else if(reverse.getPayWay().equals("4")){
					reverse.setStatus("RS");
					reverse.setUpdateDate(DateUtils.getNowDate());
					guaranteeReverseService.updateByPrimaryKey(reverse);
				}
			}
			if(flag){
				ipr.setStatus("3");
				intopieceReverseService.updateByPrimaryKey(ipr);
			}else{
				ipr.setStatus("2");
				intopieceReverseService.updateByPrimaryKey(ipr);
			}
			retJson.put("code", 200);
			retJson.put("msg", "发起退款中");
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("code", 300);
			retJson.put("msg", "网络异常");
			return retJson;
		}
	}
	
	
}
