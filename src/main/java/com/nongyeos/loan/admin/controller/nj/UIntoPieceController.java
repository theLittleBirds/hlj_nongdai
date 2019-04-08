package com.nongyeos.loan.admin.controller.nj;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.ApiThirdMsg;
import com.nongyeos.loan.admin.entity.BusExamine;
import com.nongyeos.loan.admin.entity.BusFamilySituation;
import com.nongyeos.loan.admin.entity.BusGuaranteeFee;
import com.nongyeos.loan.admin.entity.BusGuaranteeFeeInfo;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusLoan;
import com.nongyeos.loan.admin.entity.BusProduct;
import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.mapper.ApiThirdMsgMapper;
import com.nongyeos.loan.admin.model.IntoPieceConfig;
import com.nongyeos.loan.admin.resultMap.IntoPieceMap;
import com.nongyeos.loan.admin.service.IApiThirdMsgService;
import com.nongyeos.loan.admin.service.IContactMakeService;
import com.nongyeos.loan.admin.service.IExamineService;
import com.nongyeos.loan.admin.service.IFamilySituationService;
import com.nongyeos.loan.admin.service.IGuaranteeFeeInfoService;
import com.nongyeos.loan.admin.service.IGuaranteeFeeService;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.admin.service.ILoanService;
import com.nongyeos.loan.admin.service.IMessageReminderService;
import com.nongyeos.loan.admin.service.IOrgService;
import com.nongyeos.loan.admin.service.IPersonService;
import com.nongyeos.loan.admin.service.IProductService;
import com.nongyeos.loan.admin.service.ISignatorieService;
import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.entity.FlowNode;
import com.nongyeos.loan.app.service.IAppEntryService;
import com.nongyeos.loan.app.service.IApplicationService;
import com.nongyeos.loan.app.service.IFlowNodeService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.GetRepaymentUtils;
import com.nongyeos.loan.base.util.HttpClientUtil;
import com.nongyeos.loan.base.util.JPushDO;
import com.nongyeos.loan.base.util.JpushUtils;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;
import com.nongyeos.loan.core.model.BusinessObject;
import com.nongyeos.loan.core.service.FlowMgr;

/**
 * 
 * Title:UIntoPieceController 
 * Description:  商户的进件管理
 * @author lcg
 * @date 2018年5月24日 下午3:37:33
 */
@Controller
@RequestMapping("/nj/intoPiece")
public class UIntoPieceController {
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(CApplicationController.class);
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@Autowired
	private IAppEntryService appEntryService;//业务_状态记录
	
	@Autowired
	private FlowMgr flowMgrImpl;
	
	@Autowired
	private IApplicationService applicationService;
	
	@Autowired
	private IPersonService personService;
	
	@Autowired
	private JpushUtils jpushUtils;
	
	@Autowired
	private IWebUserService userService;
	
	@Autowired
	private IApiThirdMsgService thirdMsgService;
	@Autowired
	private IFlowNodeService flowNodeService;
	@Autowired
	private IFlowNodeService fleNodeService;
	
	@Autowired
	private IExamineService examineService;
	
	@Autowired
	private ILoanService loanService;
	
	@Autowired
	private ISysSenoService sysSenoService;
	
	private static int pageSize = 10;
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private IOrgService orgService;
	
	@Autowired
	private IGuaranteeFeeService guaranteeFeeService;
	
	@Autowired
	private IGuaranteeFeeInfoService guaranteeFeeInfoService;
	
	@Autowired
	private IMessageReminderService messageReminderService;
	
	@Autowired
	private IntoPieceConfig pieceConfig;
	
	@Autowired
	private IContactMakeService contactMakeService;
	
	@Autowired
	private ISignatorieService signatorieService;
	
	@Autowired
	private IFamilySituationService familySituationService;
	
	/**
	 * 
	 * @Title: findIntoPiece 
	 * @Description: 进件列表
	 * @param @param request
	 * @param @return     
	 * @return JSONObject     
	 * @throws
	 */
	@RequestMapping("/findIntoPiece")
	@ResponseBody
	public JSONObject findIntoPiece(HttpServletRequest request,HttpServletResponse response,String memberName){
		JSONObject retJson = new JSONObject();
		String userId = (String) request.getAttribute("loginId");
		try {
			String page = request.getParameter("page");
			Integer page1 = null;
			JSONArray arr = new JSONArray();
			if(StringUtils.isEmpty(page)){
				page1 =1;
			}else{
				page1= new Integer(page);
			}
			String status = Constants.FLOW_COMPLETE;
			PageBeanUtil<IntoPieceMap> pageBean = intoPieceService.selectByUserId(page1, pageSize, status, userId, Constants.COMMON_ISNOT_DELETE,memberName);
			if(pageBean!=null){
				List<IntoPieceMap> list = pageBean.getItems();
	 			if(list.size()==0){
	 				response.setStatus(400);
					retJson.put("errno", 3011);
					retJson.put("message", "没有更多数据了！");
					return retJson;
	 			}
				if(pageBean.getTotalPage()<page1){
					response.setStatus(400);
					retJson.put("errno", 3011);
					retJson.put("message", "没有更多数据了！");
					return retJson;
				}else{
					int totalPage = pageBean.getTotalPage();
					int totalRow = pageBean.getTotalNum();
					int currentPage = pageBean.getCurrentPage();
					for (int i = 0; i < pageBean.getItems().size(); i++) {
						IntoPieceMap intoPieceMap = pageBean.getItems().get(i);
						JSONObject obj = new JSONObject();
						obj.put("name", intoPieceMap.getMemberName());
						obj.put("phone", intoPieceMap.getPhone());
						// 状态为1,2,3时，可以补全资料，状态为
						obj.put("intoPieceId", intoPieceMap.getId());
						obj.put("status", intoPieceMap.getStatus());
						arr.add(obj);
					}
					retJson.put("totalPage", totalPage);
					retJson.put("totalRow", totalRow);
					retJson.put("currentPage", currentPage);
					retJson.put("data", arr);
					retJson.put("errno", 0);
				}
			}else{
				response.setStatus(400);
				retJson.put("errno", 3009);
				retJson.put("message", "未找到贷款信息！");
				return retJson;
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(400);
			retJson.put("errno", 3009);
			retJson.put("message", "未找到贷款信息！");
			return retJson;
		}
		return retJson;
	}
	
	/**
	 * 
	 * @Title: submit 
	 * @Description: 提交初审
	 * @param @param request
	 * @param @param intoPieceId
	 * @param @return     
	 * @return JSONObject     
	 * @throws
	 */
	@RequestMapping("/submit")
	@ResponseBody
	public JSONObject submit(HttpServletRequest request,String intoPieceId,HttpServletResponse response){
		String userId = request.getAttribute("loginId").toString();
		JSONObject json = new JSONObject();;
		if(StrUtils.isEmpty(intoPieceId)){
			response.setStatus(400);
			json.put("message", "进件标识为空");
			return json;
		}		
		try {
			AppEntry entry = appEntryService.queryByAppModeId(intoPieceId);
			if(!Constants.FLOW_COMPLETE.equals(entry.getNodeName())){
				response.setStatus(400);
				json.put("message", "状态错误");
				return json;
			}
			SysPerson person = personService.selectByUserIdAndIsdefault(userId);
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
			String type = request.getParameter("type");
			//0提交初审
			if(type.equals("0")){
				String opinion = request.getParameter("opinion");
				if(StringUtils.isEmpty(opinion)){
					response.setStatus(400);
					json.put("message", "请填写意见");
					return json;
				}
				ip.setTrialOpinion(opinion);
				ip.setTrialDate(new Date());
				ip.setTrialBy(userId);
				ip.setTrialName(person.getNameCn());
				intoPieceService.saveIntoPieceAndMember(ip);
				
				/*Map<String, String> signType = new HashMap<String,String>();
				int order = 0;				
				String underline = request.getParameter("underline["+order+"]");
				while (StrUtils.isNotEmpty(underline)) {
					signType.put(underline, "2");
					order++;
					underline = request.getParameter("underline["+order+"]");
				}
				order = 0;
				String online = request.getParameter("online["+order+"]");
				while (StrUtils.isNotEmpty(online)) {
					signType.put(online, "1");
					order++;
					online = request.getParameter("online["+order+"]");
				}
				List<BusFamilySituation> list = familySituationService.thirdpartycredit(intoPieceId);
				for (int i = 0; i < list.size(); i++) {
					//1线上  2线下
					String fstype = signType.get(list.get(i).getId());
					if(StrUtils.isEmpty(fstype)){
						json.put("message", list.get(i).getName()+"授权书签署类型未选择");
						response.setStatus(400);
						return json;
					}
					if(StrUtils.isEmpty(list.get(i).getIdCardP())){
						json.put("code", 400);
						json.put("msg", list.get(i).getName()+"身份证正面照片未上传");
						return json;
					}
					if(StrUtils.isEmpty(list.get(i).getIdCardN())){
						json.put("code", 400);
						json.put("msg", list.get(i).getName()+"身份证反面照片未上传");
						return json;
					}
					list.get(i).setDuty(fstype);
				}*/
				BusLoan loan = loanService.selectByIpId(intoPieceId);
				if(loan == null){
					loan = new BusLoan();
					loan.setId(UUIDUtil.getUUID());
					loan.setIntoPieceId(intoPieceId);
					loan.setContractNo(sysSenoService.getNextString("contract_no", 10, "No", 1));
					loan.setChannel(ip.getChannel());
					loan.setCapital(ip.getCapital());
					loan.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
					loan.setCreOperId( userId);
					loan.setCreOperName(person.getNameCn());
					loan.setCreOrgId(person.getOrgId());
					loan.setCreDate(new Date());
					loan.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
					loan.setUpdOperId(userId);
					loan.setUpdOperName(person.getNameCn());
					loan.setUpdOrgId(person.getOrgId());
					loan.setUpdDate(new Date());
					loanService.insert(loan);
				}	
				JSONObject object = intoPieceService.submitPrimary(ip, loan,null, person.getPersonId());
				request.getSession().setAttribute(Constants.SESSION_PERSONCD, personService.selectByUserIdAndIsdefault(userId).getPersonId());
				JPushDO.Do(intoPieceService, appEntryService, flowNodeService, jpushUtils, applicationService, intoPieceId, object.getString("nextNodeId"), request);
				json.put("message", "保存成功");
			}else{
				FlowNode flowNode = new FlowNode();
				flowNode.setAppId(ip.getModelId());
				flowNode.setEname(Constants.FLOW_REFUSE);
				FlowNode fleNodeId = fleNodeService
						.queryByEnameAndModel(flowNode);
				// 获的相关进件业务数据
				if (entry != null) {
					entry.setNodeId(fleNodeId.getNodeId());// 流程节点id 主键
					entry.setNodeName(fleNodeId.getEname());// 流程节点状态码
					// 更改拒件流程节点
					appEntryService.updateByAppEntrySelective(entry);
				}
				BusExamine examine = new BusExamine();
				examine.setId(UUIDUtil.getUUID());
				examine.setIntoPieceId(intoPieceId);
				examine.setNode(entry.getNodeName());
				examine.setCapital(ip.getCapital());
				examine.setTerm(ip.getTerm());
				examine.setExamineBy(userId);
				examine.setExamineDate(new Date());
				examine.setExamineOpinion("app拒件");
				examineService.insert(examine);
				ip.setUpdOperId(userId);
				ip.setUpdDate(DateUtils.getNowDate());
				intoPieceService.updateByPrimaryKey(ip);
				response.setStatus(200);
				json.put("message", "拒件成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(400);
			json.put("message", e.getMessage());
		}		
		return json;
	}
	
	@RequestMapping("/tongdunBairong")
	public ModelAndView tongdunBairong(HttpServletRequest request,HttpServletResponse response,String intoPieceId) throws Exception{
		ModelAndView mv= new ModelAndView();
		if(StringUtils.isEmpty(intoPieceId)){
			throw new Exception("未传进件标识！");
		}
		String userId = (String) request.getAttribute("loginId");
		
		try {
			BusIntoPiece intoPiece = intoPieceService.selectByPrimaryKey(intoPieceId);
			SysWebUser user = userService.selectUserById(userId);
			Map<String,String> map = new HashMap<String, String>();
			String signature = user.getUserId() + user.getUsername() + user.getPassword();
			map.put("signature", DigestUtils.md5Hex(signature));
			map.put("userid", userId);
			map.put("id", intoPiece.getId());
			map.put("memberName", intoPiece.getMemberName());
			map.put("idCard", intoPiece.getIdCard());
			map.put("bankCardNo", intoPiece.getBankCardNo());
			map.put("phone", intoPiece.getPhone());
			
			String thirdMsgtongdun = HttpClientUtil.doPost(pieceConfig.getTongdunurl(), map, "utf-8");
			String thirdMsgbairong = HttpClientUtil.doPost(pieceConfig.getBairongurl(), map, "utf-8");
			if(thirdMsgtongdun==null){
				throw new Exception("同盾数据查询为空！");
			}
			if(thirdMsgbairong==null){
				throw new Exception("百融数据查询为空！");
			}
			
			JSONObject thirdMsgJsontongdun = JSONObject.parseObject(thirdMsgtongdun);
			if(!thirdMsgJsontongdun.get("isSuccess").equals(Constants.GATE_RESULT_SUCCESS)){
				throw new Exception("同盾数据查询失败！");
			}
			JSONObject thirdMsgJsonbairong = JSONObject.parseObject(thirdMsgbairong);
			if(!thirdMsgJsonbairong.get("isSuccess").equals(Constants.GATE_RESULT_SUCCESS)){
				throw new Exception("百融数据查询失败！");
			}
			
//			ApiThirdMsg selectByPrimaryKey = thirdMsgService.selectByPrimaryKey("37cb21c10f034749964c6314aef1b189");
//			String thirdMsgtongdun = selectByPrimaryKey.getContent();
//			JSONObject thirdMsgJsontongdun = JSONObject.parseObject(thirdMsgtongdun);
//			ApiThirdMsg selectByPrimaryKey1 = thirdMsgService.selectByPrimaryKey("a19b685bb79b481da2825d6e0e3c4c45");
//			String thirdMsgbairong = selectByPrimaryKey1.getContent();
//			JSONObject thirdMsgJsonbairong = JSONObject.parseObject(thirdMsgbairong);
			/**
			 * 同盾，百融合并成大数据报告
			 */
			//同盾
			mv.addObject("report_id", thirdMsgJsontongdun.get("report_id"));//审核报告编号
			mv.addObject("report_time", thirdMsgJsontongdun.get("report_time"));//报告生成时间
			mv.addObject("final_score", thirdMsgJsontongdun.get("final_score"));//报告结论
			mv.addObject("final_decision", thirdMsgJsontongdun.get("final_decision"));//报告评分
	        
			mv.addObject("name", thirdMsgJsontongdun.get("name"));//姓名
			mv.addObject("id_number", thirdMsgJsontongdun.get("id_number"));//身份证
			mv.addObject("mobile", thirdMsgJsontongdun.get("mobile"));//手机号
			mv.addObject("id_card_address", thirdMsgJsontongdun.get("id_card_address"));//身份证所属地
			mv.addObject("mobile_address", thirdMsgJsontongdun.get("mobile_address"));//手机所属地
	        
			mv.addObject("personList", thirdMsgJsontongdun.get("personList"));//个人基本信息核查
			mv.addObject("relationList", thirdMsgJsontongdun.get("relationList"));//关联人信息扫描
			mv.addObject("customList", thirdMsgJsontongdun.get("customList"));//客户行为检测
			mv.addObject("dangerList", thirdMsgJsontongdun.get("dangerList"));//风险信息扫描
			mv.addObject("platformList", thirdMsgJsontongdun.get("platformList"));//多平台借贷申请检测
			
			//百融
			mv.addObject("third_platform", thirdMsgbairong.replace("\"{", "{").replace("}\"", "}").replace("\\\"", "\""));//百融平台数据解析
			mv.addObject("intoPieceId", intoPieceId);
			mv.addObject("token", request.getHeader("token"));
			mv.setViewName("dataRecord/dataRecordAnalysis");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错！");
		}
		
		return mv;
	}
	
	
	@RequestMapping("/getOperName")
	@ResponseBody
	public JSONObject getOperName(HttpServletRequest request,HttpServletResponse response){
		String userId = request.getAttribute("loginId").toString();
		JSONObject json = new JSONObject();;
		try {
			SysPerson person = personService.selectByUserIdAndIsdefault(userId);
			json.put("operName", person.getNameCn());
			json.put("time", DateUtils.format(DateUtils.getNowDate(), "yyyy-MM-dd") );
			response.setStatus(200);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(400);
			json.put("message", "网络异常！");
			return json;
		}
		
	}
	
	/**
	 * TODO 此处有个BUG，金融产品可能有多个
	 */
	@RequestMapping("/getServiceFee")
	@ResponseBody
	public JSONObject getServiceFee(HttpServletRequest request,HttpServletResponse response,String intoPieceId){
		JSONObject retJson = new JSONObject();
		try {
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
			BigDecimal capital = ip.getCapital();
			List<BusProduct> pList = productService.selectListByFinsId(ip.getLenderId());
			//如果计算错误，那么是因为产品多个的原因，需要修改代码
			Map<String, BigDecimal> serviceFee = GetRepaymentUtils.getServiceFee(capital, ip.getTerm(), pList.get(0));
			BigDecimal first = serviceFee.get("first");
			retJson.put("money", first.toString());
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "网络错误！");
			response.setStatus(400);
			return retJson;
		}
		return retJson;
	}
	
	@RequestMapping("/payServiceFee")
	@ResponseBody
	public JSONObject payServiceFee(HttpServletRequest request,HttpServletResponse response,String intoPieceId,String payType){
		JSONObject retJson = new JSONObject();
		String userId = (String)request.getAttribute("loginId");
		try {
			SysPerson person = personService.selectByUserIdAndIsdefault(userId);
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
			BusGuaranteeFeeInfo guaranteeFeeInfo = guaranteeFeeInfoService.selectByIntopieceId(intoPieceId);
			BusGuaranteeFee guaranteeFee = guaranteeFeeService.selectByIntopieceId(intoPieceId);
			String channel = request.getHeader("channel");
			if(payType.equals("1")||payType.equals("2")||payType.equals("3")){
				if(guaranteeFeeInfo!=null){
					 response.setStatus(400);
					 retJson.put("msg", "已经发起过支付，请勿重复发起！");
					 return retJson;
				}
			}
			SysOrg org = orgService.selectByOrgId(ip.getOrgId());
			List<BusProduct> pList = productService.selectListByFinsId(ip.getLenderId());
			//如果计算错误，那么是因为产品多个的原因，需要修改代码
			Map<String, BigDecimal> serviceFee = GetRepaymentUtils.getServiceFee(ip.getCapital(), ip.getTerm(), pList.get(0));
			BigDecimal first = serviceFee.get("first");
			if(StringUtils.isNotEmpty(payType)&&payType.equals("1")){
				if(guaranteeFee==null){
					BusGuaranteeFeeInfo feeInfo = new BusGuaranteeFeeInfo();
					feeInfo.setId(UUIDUtil.getUUID());
					feeInfo.setOrgId(org.getOrgId());
					feeInfo.setOrgName(org.getFullCname());
					feeInfo.setIntoPieceId(ip.getId());
					feeInfo.setUse(ip.getUse());
					feeInfo.setAccountName(ip.getMemberName());
					feeInfo.setCertificateNo(ip.getIdCard());
					//银行卡改成申请金额
					feeInfo.setAccountNo(ip.getCapital().toString());
					feeInfo.setPayerIdcard(org.getIdCard());
					feeInfo.setPayer(org.getLeader());
					feeInfo.setPayWay("1");
					feeInfo.setMobileNo(ip.getPhone());
					feeInfo.setTotalAmount(first);
					feeInfo.setCreateBy(person.getPersonId());
					feeInfo.setCreateDate(DateUtils.getNowDate());
					SysWebUser user =
							 userService.selectUserByUserName(pieceConfig.getUserName());
							 Map<String, String> map1 = new HashMap<String, String>();
							 map1.put("amount", first.toString());
							 map1.put("certificateNo", org.getIdCard());
							 map1.put("accountNo", org.getCardNo());
							 map1.put("accountName", org.getLeader());
							 map1.put("mobileNo", org.getPhone());
							 map1.put("intoPieceId", intoPieceId);
							 map1.put("userid", user.getUserId());
							 map1.put("companyType", channel);
							 String signature = user.getUserId() + user.getUsername() +
							 user.getPassword();
							 map1.put("signature", DigestUtils.md5Hex(signature));
							 String retMap =
							 HttpClientUtil.doPost(pieceConfig.getXfGuaranteeFeeUrl(), map1,
							 "utf-8");
							 System.err.println(retMap);
							 if(retMap!=null){
								 JSONObject retMap1 = JSONObject.parseObject(retMap);
								 if(retMap1==null||StringUtils.isEmpty(retMap1.getString("isSuccess"))||retMap1.getString("isSuccess").equals(Constants.GATE_RESULT_FAIL)){
									 response.setStatus(400);
									 retJson.put("messgae", "代扣服务费失败，请联系管理员！");
									 feeInfo.setStatus("F");
									 guaranteeFeeInfoService.insert(feeInfo);
									 return retJson;
								 }
							 }else{
								 response.setStatus(400);
								 retJson.put("msg", "代扣服务费失败，请联系管理员！");
								 feeInfo.setStatus("F");
								 guaranteeFeeInfoService.insert(feeInfo);
								 return retJson;
							 }
							 guaranteeFeeInfoService.insert(feeInfo);
				}
				//保存服务费扣除方式
				if(StringUtils.isNotEmpty(payType)){
					ip.setServiceFeeWay(new Integer(payType));
					intoPieceService.updateByPrimaryKey(ip);
				}
				retJson.put("payType", "1");
				retJson.put("message", "发起线上扣款成功，请在业务系统查看扣款结果");
				response.setStatus(200);
				return retJson;
				//微信支付
			}else if(StringUtils.isNotEmpty(payType)&&payType.equals("2")&&guaranteeFeeInfo==null){
//				SysPerson person1 = personService.selectByPersonId(ip.getOperUserId());
				if(StringUtils.isNoneEmpty(request.getHeader("platform"))&& "wx".equals(request.getHeader("platform")) ){
					messageReminderService.saveweixinpay(Constants.XIAO_CHENG_XU_SERVICE_PAY,channel,intoPieceId,first.toString());
				}else{
					messageReminderService.saveweixinpay(Constants.WEI_XIN_SERVICE_PAY,channel,intoPieceId,first.toString());
				}
				BusGuaranteeFeeInfo feeInfo = new BusGuaranteeFeeInfo();
				feeInfo.setId(UUIDUtil.getUUID());
				feeInfo.setOrgId(org.getOrgId());
				feeInfo.setOrgName(org.getFullCname());
				feeInfo.setIntoPieceId(ip.getId());
				feeInfo.setUse(ip.getUse());
				feeInfo.setAccountName(ip.getMemberName());
				feeInfo.setCertificateNo(ip.getIdCard());
				
				//银行卡改成申请金额
				feeInfo.setAccountNo(ip.getCapital().toString());
				feeInfo.setMobileNo(ip.getPhone());
				feeInfo.setPayerIdcard(person.getCardNo());
				feeInfo.setPayer(person.getNameCn());
				if(StringUtils.isNoneEmpty(request.getHeader("platform"))&& "wx".equals(request.getHeader("platform")) ){
					feeInfo.setPayWay("5");
				}else{
					feeInfo.setPayWay("2");
				}
				feeInfo.setTotalAmount(first);
				feeInfo.setCreateBy(person.getPersonId());
				feeInfo.setCreateDate(DateUtils.getNowDate());
				feeInfo.setStatus("I");
				guaranteeFeeInfoService.insert(feeInfo);
				String url = pieceConfig.getWxPayGuaranteeFeeCheck();
				SysWebUser user = userService.selectUserByUserName(pieceConfig.getUserName());
				Map<String, String> map = new HashMap<String, String>();
				map.put("userid", user.getUserId());
				String signature = user.getUserId() + user.getUsername() + user.getPassword();
				map.put("signature", DigestUtils.md5Hex(signature));
				map.put("companyType", channel);
				if(StringUtils.isNoneEmpty(request.getHeader("platform"))&& "wx".equals(request.getHeader("platform")) ){
					map.put("appcode", request.getParameter("appcode"));
				}
				map.put("guaranteeId", feeInfo.getId());
				map.put("amount", feeInfo.getTotalAmount().toString());
//				map.put("certificateNo", person.getCardNo());
				map.put("certificateNo", person.getCardNo());
				map.put("accountName", person.getNameCn());
				map.put("mobileNo", person.getMobile());
				map.put("intoPieceId", intoPieceId);
				System.err.println(map);
				String retMap = HttpClientUtil.doPost(url, map,
						 "utf-8");
				if(retMap!=null){
					 JSONObject retMap1 = JSONObject.parseObject(retMap);
					 if(retMap1==null||StringUtils.isEmpty(retMap1.getString("isSuccess"))||retMap1.getString("isSuccess").equals(Constants.GATE_RESULT_FAIL)){
						 retJson.put("message", "微信预支付订单生成失败");
						 response.setStatus(400);
						 return retJson;
					 }
				 }else{
					 retJson.put("message", "微信预支付订单生成失败");
					 response.setStatus(400);
					 return retJson;
				 }
				//保存服务费扣除方式
				if(StringUtils.isNotEmpty(payType)){
					if(StringUtils.isNoneEmpty(request.getHeader("platform"))&& "wx".equals(request.getHeader("platform")) ){
						ip.setServiceFeeWay(new Integer("5"));
					}else{
						ip.setServiceFeeWay(new Integer("2"));
					}
					intoPieceService.updateByPrimaryKey(ip);
				}
				JSONObject retdata = JSONObject.parseObject(retMap);
				retJson.put("payType", "2");
				retJson.put("data", retdata.get("data"));
				retJson.put("message", "微信预支付订单生成成功");
				response.setStatus(200);
				return retJson;
			}else if(StringUtils.isNotEmpty(payType)&&payType.equals("3")&&guaranteeFeeInfo==null){
				BusGuaranteeFeeInfo feeInfo = new BusGuaranteeFeeInfo();
				feeInfo.setId(UUIDUtil.getUUID());
				feeInfo.setOrgId(org.getOrgId());
				feeInfo.setOrgName(org.getFullCname());
				feeInfo.setIntoPieceId(ip.getId());
				feeInfo.setUse(ip.getUse());
				feeInfo.setAccountName(ip.getMemberName());
				feeInfo.setCertificateNo(ip.getIdCard());
				//银行卡改成申请金额
				feeInfo.setAccountNo(ip.getCapital().toString());
				feeInfo.setMobileNo(ip.getPhone());
				feeInfo.setPayer(org.getLeader());
				feeInfo.setPayWay("3");
				feeInfo.setTotalAmount(first);
				feeInfo.setCreateBy(person.getPersonId());
				feeInfo.setCreateDate(DateUtils.getNowDate());
				feeInfo.setStatus("S");
				guaranteeFeeInfoService.insert(feeInfo);
				//保存服务费扣除方式
				if(StringUtils.isNotEmpty(payType)){
					ip.setServiceFeeWay(new Integer(payType));
					intoPieceService.updateByPrimaryKey(ip);
				}
				retJson.put("payType", "3");
				retJson.put("message", "保存成功");
				response.setStatus(200);
				return retJson;
			}
			retJson.put("payType", "4");
			retJson.put("message", "保存成功");
			response.setStatus(200);
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "网络错误！");
			response.setStatus(400);
			return retJson;
		}
	}
}
