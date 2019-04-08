package com.nongyeos.loan.admin.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.ApiWXGuaranteeFee;
import com.nongyeos.loan.admin.entity.BusApplyPieceExt;
import com.nongyeos.loan.admin.entity.BusExamine;
import com.nongyeos.loan.admin.entity.BusFamilyCapital;
import com.nongyeos.loan.admin.entity.BusFamilyCredit;
import com.nongyeos.loan.admin.entity.BusFamilyOperate;
import com.nongyeos.loan.admin.entity.BusFamilySituation;
import com.nongyeos.loan.admin.entity.BusGuaranteeFee;
import com.nongyeos.loan.admin.entity.BusGuaranteeFeeInfo;
import com.nongyeos.loan.admin.entity.BusGuaranteeReverse;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusLoan;
import com.nongyeos.loan.admin.entity.BusMedia;
import com.nongyeos.loan.admin.entity.BusMemberOperate;
import com.nongyeos.loan.admin.entity.BusMemberOperateMedia;
import com.nongyeos.loan.admin.entity.BusMessageReminder;
import com.nongyeos.loan.admin.entity.BusOtherContact;
import com.nongyeos.loan.admin.entity.BusProduct;
import com.nongyeos.loan.admin.entity.BusRefund;
import com.nongyeos.loan.admin.entity.BusRejectReason;
import com.nongyeos.loan.admin.entity.BusTransferLand;
import com.nongyeos.loan.admin.entity.NjProduct;
import com.nongyeos.loan.admin.entity.NjProductOrder;
import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.entity.SysPara;
import com.nongyeos.loan.admin.entity.SysParaGroup;
import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.model.FileConfig;
import com.nongyeos.loan.admin.model.IntoPieceConfig;
import com.nongyeos.loan.admin.model.RootPointConfig;
import com.nongyeos.loan.admin.model.TwoElementsConfig;
import com.nongyeos.loan.admin.resultMap.DynamicDataMap;
import com.nongyeos.loan.admin.resultMap.IntoPieceMap;
import com.nongyeos.loan.admin.resultMap.LoanMap;
import com.nongyeos.loan.admin.service.ApiWxGuaranteefeeService;
import com.nongyeos.loan.admin.service.IApplyPieceExtService;
import com.nongyeos.loan.admin.service.IBusIntopieceScoreService;
import com.nongyeos.loan.admin.service.IContactMakeService;
import com.nongyeos.loan.admin.service.IExamineService;
import com.nongyeos.loan.admin.service.IFamilyCapitalService;
import com.nongyeos.loan.admin.service.IFamilyCreditService;
import com.nongyeos.loan.admin.service.IFamilyOperateService;
import com.nongyeos.loan.admin.service.IFamilySituationService;
import com.nongyeos.loan.admin.service.IGuaranteeFeeInfoService;
import com.nongyeos.loan.admin.service.IGuaranteeFeeService;
import com.nongyeos.loan.admin.service.IGuaranteeReverseService;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.admin.service.ILoanService;
import com.nongyeos.loan.admin.service.IMediaService;
import com.nongyeos.loan.admin.service.IMemberOperateMediaService;
import com.nongyeos.loan.admin.service.IMemberOperateService;
import com.nongyeos.loan.admin.service.IMemberService;
import com.nongyeos.loan.admin.service.IMessageReminderService;
import com.nongyeos.loan.admin.service.IOrgService;
import com.nongyeos.loan.admin.service.IOtherContactService;
import com.nongyeos.loan.admin.service.IParaGroupService;
import com.nongyeos.loan.admin.service.IParaService;
import com.nongyeos.loan.admin.service.IPersonService;
import com.nongyeos.loan.admin.service.IProductService;
import com.nongyeos.loan.admin.service.IRefundService;
import com.nongyeos.loan.admin.service.IRejectReasonService;
import com.nongyeos.loan.admin.service.ISignatorieService;
import com.nongyeos.loan.admin.service.ISysSenoService;
import com.nongyeos.loan.admin.service.ITransferLandService;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.admin.service.NjProductOrderService;
import com.nongyeos.loan.admin.service.NjProductService;
import com.nongyeos.loan.app.entity.AppApplication;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.entity.AppPara;
import com.nongyeos.loan.app.entity.AppSection;
import com.nongyeos.loan.app.entity.BusFins;
import com.nongyeos.loan.app.entity.FlowEntrance;
import com.nongyeos.loan.app.entity.FlowNode;
import com.nongyeos.loan.app.service.IAppEntryService;
import com.nongyeos.loan.app.service.IAppItemService;
import com.nongyeos.loan.app.service.IAppParaService;
import com.nongyeos.loan.app.service.IApplicationService;
import com.nongyeos.loan.app.service.IBusFinsService;
import com.nongyeos.loan.app.service.IDecisionPolicybaseService;
import com.nongyeos.loan.app.service.IDecisionPolicycaseService;
import com.nongyeos.loan.app.service.IFlowDirectionService;
import com.nongyeos.loan.app.service.IFlowEntranceService;
import com.nongyeos.loan.app.service.IFlowNodeService;
import com.nongyeos.loan.app.service.ISectionService;
import com.nongyeos.loan.base.util.AppNull;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.FileUtils;
import com.nongyeos.loan.base.util.GetRepaymentUtils;
import com.nongyeos.loan.base.util.HttpClientUtil;
import com.nongyeos.loan.base.util.HttpUtils;
import com.nongyeos.loan.base.util.JPushDO;
import com.nongyeos.loan.base.util.JpushUtils;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;
import com.nongyeos.loan.core.model.BusinessObject;
import com.nongyeos.loan.core.service.FlowMgr;

@Controller
@RequestMapping("/intopiece")
public class IntoPieceController {

	@Autowired
	private IIntoPieceService intoPieceService;
	@Autowired
	private IMemberService memberService;

	@Autowired
	private IFlowEntranceService flowEntranceService;
	@Autowired
	private IFlowNodeService fleNodeService;
	@Autowired
	private IAppEntryService appEntryService;// 业务_状态记录
	@Autowired
	private IFlowDirectionService flowDirectionService;

	@Autowired
	private IFamilyCreditService familyCreditService;// 家庭征信
	@Autowired
	private IFamilyCapitalService familyCapitalService;// 家庭资产
	@Autowired
	private IFamilySituationService familySituationService;// 家庭联系人
	@Autowired
	private IDecisionPolicycaseService decisionPolicycaseService;// 决策引擎_决策条件
	@Autowired
	private IDecisionPolicybaseService decisionPolicybaseService;// 决策引擎_基本条件
	@Autowired
	private IAppItemService appItemService;// 应用_数据项
	@Autowired
	private IApplyPieceExtService applyPieceExtService;// 业务_扩展数据
	@Autowired
	private IPersonService personService;
	@Autowired
	private IBusIntopieceScoreService intopieceScoreService;

	@Autowired
	private FlowMgr flowMgrImpl;

	@Autowired
	private IApplicationService applicationService;

	@Autowired
	private IFlowNodeService flowNodeService;

	@Autowired
	private JpushUtils jpushUtils;

	@Autowired
	private IExamineService examineService;

	@Autowired
	private IRejectReasonService reasonService;

	@Autowired
	private IFamilyOperateService familyOperateService;

	@Autowired
	private IMemberOperateService memberOperateService;

	@Autowired
	private IMemberOperateMediaService memberOperateMediaService;

	@Autowired
	private IMediaService mediaService;

	@Autowired
	private IWebUserService userService;

	@Autowired
	private ITransferLandService transferLandService;

	@Autowired
	private IntoPieceConfig pieceConfig;

	@Autowired
	private IOtherContactService otherContactService;

	@Autowired
	private RootPointConfig rootPointConfig;

	@Autowired
	private FileConfig fileConfig;

	@Autowired
	private IOrgService orgService;

	@Autowired
	private IAppParaService appParaService;

	@Autowired
	private ISectionService sectionService;

	@Autowired
	private ILoanService loanService;

	@Autowired
	private ISysSenoService sysSenoService;

	@Autowired
	private IBusFinsService finsService;

	@Autowired
	private IContactMakeService contactMakeService;

	@Autowired
	private IGuaranteeFeeInfoService guaranteeFeeInfoService;

	@Autowired
	private ISignatorieService signatorieService;

	@Autowired
	private IGuaranteeFeeService guaranteeFeeService;

	@Autowired
	private IProductService productService;

	@Autowired
	private IMessageReminderService messageReminderService;

	@Autowired
	private NjProductService njproductService;

	@Autowired
	private NjProductOrderService njProductOrderService;

	@Autowired
	private IParaService paraService;

	@Autowired
	private IParaGroupService paraGroupService;

	@Autowired
	private ApiWxGuaranteefeeService wxGuaranteefeeService;


	@Autowired
	private IRefundService refundService;

	@Autowired
	private TwoElementsConfig twoElementsConfig;

	@Autowired
	private IGuaranteeReverseService guaranteeReverseService;

	private static final Logger logger = LogManager
			.getLogger(IntoPieceController.class);

	/**
	 * 进件信息列表 初审、复审、保函出具、终审列表
	 *
	 * @param currentPage
	 * @param pageSize
	 * @param orgName
	 * @param memberName
	 * @param idCard
	 * @param phone
	 * @param status
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@ResponseBody
	public PageBeanUtil<IntoPieceMap> list(int currentPage, int pageSize,
										   String orgName, String memberName, String idCard, String phone,
										   String status, String use,String capital,String appName,
										   String startDate,String endDate,HttpServletRequest request) throws Exception {
		try {
			IntoPieceMap ip = new IntoPieceMap();
			if (StrUtils.isNotEmpty(orgName))
				ip.setFullCname(orgName);
			if (StrUtils.isNotEmpty(memberName))
				ip.setMemberName(memberName);
			if (StrUtils.isNotEmpty(idCard))
				ip.setIdCard(idCard);
			if (StrUtils.isNotEmpty(phone))
				ip.setPhone(phone);
			if (StrUtils.isNotEmpty(status))
				ip.setStatus(status);
			if(StrUtils.isNotEmpty(use))
				ip.setUse(Integer.parseInt(use));
			if(StrUtils.isNotEmpty(capital))
				ip.setCapital(new BigDecimal(capital));
			if(StrUtils.isNotEmpty(appName))
				ip.setAppName(appName);
			if (StrUtils.isNotEmpty(startDate))
				ip.setStartDate(startDate);
			if (StrUtils.isNotEmpty(endDate))
				ip.setEndDate(endDate + " 23:59:59");
			ip.setPersonId(request.getSession()
					.getAttribute(Constants.SESSION_PERSONCD).toString());
			ip.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			return intoPieceService.selectByPage(currentPage, pageSize, ip);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String ids) throws Exception {
		JSONObject json = new JSONObject();
		if (StrUtils.isNotEmpty(ids)) {
			String id[] = ids.split(",");
			for (int i = 0; i < id.length; i++) {
				BusIntoPiece ip = new BusIntoPiece();
				ip.setId(id[i]);
				ip.setIsDeleted(Constants.COMMON_IS_DELETE);
				intoPieceService.updateByPrimaryKeySelective(ip);
			}
			json.put("msg", "删除成功");
			json.put("code", 200);
		} else {
			json.put("msg", "删除失败");
			json.put("code", 400);
		}
		return json.toString();
	}

	/**
	 *
	 * @Title: saveIntoPiece
	 * @Description: 进件
	 * @param @param response
	 * @param @param request
	 * @param @param intoPiece
	 * @param @return
	 * @param @throws Exception
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> saveIntoPiece(HttpServletResponse response,
											 HttpServletRequest request, BusIntoPiece intoPiece,
											 String memberOperateId,String continuedLoanId) throws Exception {
		BusIntoPiece selectByPrimaryKey = null;
		Map<String, Object> resmap = new HashMap<String, Object>();
		try {
			if (!StringUtils.isEmpty(intoPiece.getId())) {
				selectByPrimaryKey = intoPieceService
						.selectByPrimaryKey(intoPiece.getId());
			}
			SysOrg org = orgService.selectByOrgId(intoPiece.getOrgId());
			String mark = rootPointConfig.getMark();
			JSONObject markJson = JSONObject.parseObject(mark);
			Set<String> keySet = markJson.keySet();
			Iterator<String> iterator = keySet.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				String rootOrgId = markJson.getString(key);
				if (org.getParentOrgIds().contains(rootOrgId)) {
					intoPiece.setChannel(key);
					break;
				} else if (rootOrgId.equals(org.getOrgId())) {
					intoPiece.setChannel(key);
					break;
				}
			}
			AppApplication app = applicationService.selectByModelId(intoPiece
					.getModelId());
			if(StringUtils.isNoneEmpty(app.getEname()) ){
				if(app.getEname().equals(Constants.LOAN_LAND_PRODUCT)||app.getEname().equals(Constants.LOAN_LAND_PRODUCT_GRAIN)){
					//待打款
					intoPiece.setOrderStatus(2);
				}
			}
			intoPiece.setLenderId(app.getFinsCode());
			intoPiece.setType(StringUtils.isEmpty(app.getEname())?new Integer(Constants.LOAN_LAND):new Integer(app.getEname()));
			SysPerson person = personService.selectByUserIdAndIsdefault(request
					.getSession().getAttribute(Constants.SESSION_USERCD)
					.toString());
			if (selectByPrimaryKey == null) {
				intoPiece.setCreOperId(request.getSession()
						.getAttribute(Constants.SESSION_USERCD).toString());
				intoPiece.setCreOperName(request.getSession()
						.getAttribute(Constants.SESSION_USERNM).toString());
				intoPiece.setCreOrgId(request.getSession()
						.getAttribute(Constants.SESSION_ORGCD).toString());
			} else {
				intoPiece.setId(selectByPrimaryKey.getId());
			}
			intoPiece.setUpdOperId(request.getSession()
					.getAttribute(Constants.SESSION_USERCD).toString());
			intoPiece.setUpdOperName(request.getSession()
					.getAttribute(Constants.SESSION_USERNM).toString());
			intoPiece.setUpdOrgId(request.getSession()
					.getAttribute(Constants.SESSION_ORGCD).toString());
			intoPiece.setOperUserId(person.getPersonId());
			if(!intoPiece.getMemberName().contains("测试")&&!intoPiece.getMemberName().contains("TEST")){
				 //二要素验证
			 	 String host=twoElementsConfig.getHOST();
			 	 String path = twoElementsConfig.getPATH();
			 	 String appCode = twoElementsConfig.getAPPCODE();
				 Map<String, String> headers = new HashMap<String, String>();
				 headers.put("Authorization", "APPCODE " + appCode);
				 headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				 Map<String, String> querys = new HashMap<String, String>();
			     Map<String, String> bodys = new HashMap<String, String>();
			     bodys.put("idNo", intoPiece.getIdCard());
			     bodys.put("name", intoPiece.getMemberName());
			     HttpResponse response2 = HttpUtils.doPost(host, path, "POST", headers, querys, bodys);
			     HttpEntity entity = response2.getEntity();
			     JSONObject json = null;
			     try {
			    	 json = JSONObject.parseObject(EntityUtils.toString(entity));
				 } catch (Exception e) {
					 e.printStackTrace();
					 resmap.put("msg", "系统错误！");
					 resmap.put("errono", 1000);
					 return resmap;
				 }
			     if(json==null){
			    	 resmap.put("msg", "身份证信息查询失败！");
					 resmap.put("errono", 1000);
					 return resmap;
			     }
			     if(!"0000".equals(json.getString("respCode"))){
			    	 resmap.put("msg", json.getString("respMessage"));
					 resmap.put("errono", 1000);
					 return resmap;
			     }
			 }

			BusIntoPiece busIntoPiece = intoPieceService
					.saveIntoPieceAndMember(intoPiece);
			if (busIntoPiece == null) {
				resmap.put("msg", "该客户有未完成的贷款，无法再次申请！");
				resmap.put("errono", "1001");
				return resmap;
			}
			AppApplication application = applicationService
					.selectByModelId(busIntoPiece.getModelId());
			if (busIntoPiece.getReject() == null) {
				// 记录当前流程节点
				// 应用_产品应用
				// 进件表的modelId是业务_状态记录的id又是T_FLOW_ENTRANCE表的appid
				List<FlowEntrance> flowEntranceInit = flowEntranceService
						.selectByAppId(intoPiece.getModelId());
				if (flowEntranceInit != null && flowEntranceInit.size() > 0) {
					// 现在目前用一个流程入口，手机端和pc端的流程是一样的（在选择入口时候目前不加入决策）
					FlowEntrance flowEntrance = flowEntranceInit.get(0);
					if (flowEntrance != null) {
						// 查询流程节点
						FlowNode fleNodeId = fleNodeService
								.selectByNodeId(flowEntrance.getStartNodeId());

						// 保存业务_状态记录
						AppEntry appEntry = new AppEntry();
						appEntry.setEntryId(UUIDUtil.getUUID());// 记录表主键
						appEntry.setChannelId(Constants.WEB_USER);// 进件渠道
						appEntry.setModeId(busIntoPiece.getId());// 业务表主键
						appEntry.setFromId(Constants.T_BUS_INTOPIECE);// 进件表名字
						appEntry.setOrgId(request.getSession()
								.getAttribute(Constants.SESSION_ORGCD)
								.toString());// 当前用户的orgid
						appEntry.setFinsId(application.getFinsCode());//金融机构
						appEntry.setAppId(intoPiece.getModelId());// 产品应用的appid
						// 主键
						appEntry.setNodeId(fleNodeId.getNodeId());// 流程节点id 主键
						appEntry.setNodeName(fleNodeId.getEname());// 流程节点状态码
						appEntry.setAppName(application.getCname());
						appEntry.setCreDate(DateUtils.getNowDate());// 创建时间
						appEntry.setUpdDate(DateUtils.getNowDate());// 更新时间
						appEntryService.addAppEntrySelective(appEntry);
						if(StringUtils.isNoneEmpty(continuedLoanId)){
							BusIntoPiece ip = intoPieceService.selectByPrimaryKey(continuedLoanId);
							BusFamilySituation fs = new BusFamilySituation();
							fs.setIntoPieceId(ip.getId());
							fs.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
							List<BusFamilySituation> familyListco = familySituationService.queryByIntopId(fs);
							BusFamilyOperate familyOperateco = familyOperateService.selectByIpId(ip.getId());
							BusFamilyCapital familyCapital = new BusFamilyCapital();
							familyCapital.setIntoPieceId(ip.getId());
							familyCapital.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
							BusFamilyCapital familyCapitalco = familyCapitalService.selectByIntoPieceId(familyCapital);
							BusFamilyCredit familyCredit = new BusFamilyCredit();
							familyCredit.setIntoPieceId(ip.getId());
							familyCredit.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
							BusFamilyCredit familyCreditco = familyCreditService.selectByIpId(familyCredit);
							List<BusTransferLand> landsco = transferLandService.selectByIpId(ip.getId());
							BusMedia m = new BusMedia();
							m.setIntoPieceId(ip.getId());
							m.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
							List<BusMedia> mediaco = mediaService.selectByIpId(m);
							//动态数据
							List<BusApplyPieceExt> listco = applyPieceExtService.selectByIpId(ip.getId());
							if(familyListco!=null&&familyListco.size()>0){
								for (int i = 0; i < familyListco.size(); i++) {
									BusFamilySituation busFamilySituation = familyListco.get(i);
									busFamilySituation.setIntoPieceId(busIntoPiece.getId());
									busFamilySituation.setId(UUIDUtil.getUUID());
									familySituationService.insert(busFamilySituation);
								}
							}
							if(familyOperateco!=null){
								familyOperateco.setId(UUIDUtil.getUUID());
								familyOperateco.setIntoPieceId(busIntoPiece.getId());
								familyOperateService.insert(familyOperateco);
							}
							if(familyCapitalco!=null){
								familyCapitalco.setId(UUIDUtil.getUUID());
								familyCapitalco.setIntoPieceId(busIntoPiece.getId());
								familyCapitalService.insert(familyCapitalco);
							}
							if(familyCreditco!=null){
								familyCreditco.setId(UUIDUtil.getUUID());
								familyCreditco.setIntoPieceId(busIntoPiece.getId());
								familyCreditService.insert(familyCreditco);
							}
							if(landsco!=null&&landsco.size()>0){
								for (int i = 0; i < landsco.size(); i++) {
									BusTransferLand busTransferLand = landsco.get(i);
									busTransferLand.setId(UUIDUtil.getUUID());
									busTransferLand.setIntoPieceId(busIntoPiece.getId());
									transferLandService.insert(busTransferLand);
								}
							}
							if(mediaco!=null&&mediaco.size()>0){
								for (int i = 0; i < mediaco.size(); i++) {
									BusMedia busMedia = mediaco.get(i);
									if(StringUtils.isNoneEmpty(busMedia.getType())&&Constants.MEDIATYPE_FILE.equals(busMedia.getType())){
										continue;
									}
									busMedia.setId(UUIDUtil.getUUID());
									busMedia.setIntoPieceId(busIntoPiece.getId());
									mediaService.insert(busMedia);
								}
							}
							if(listco!=null&&listco.size()>0){
								for (int i = 0; i < listco.size(); i++) {
									BusApplyPieceExt busApplyPieceExt = listco.get(i);
									busApplyPieceExt.setExtId(UUIDUtil.getUUID());
									busApplyPieceExt.setIntoPieceId(busIntoPiece.getId());
									applyPieceExtService.insert(busApplyPieceExt);
								}
							}
						}
					} else {
						throw new Exception("流程入口内流向为空");
					}
				} else {
					throw new Exception("没有查询到符合条件的流程入口");
				}
			} else {
				// 自动拒件 自动拒件让流程节点直接到已拒件
				BusIntoPiece primaryKey = intoPieceService
						.selectByPrimaryKey(intoPiece.getId());
				if (primaryKey == null) {
					throw new Exception("没有找到相应进件");
				}
				// 查询流程节点
				FlowNode flowNode = new FlowNode();
				flowNode.setAppId(primaryKey.getModelId());
				flowNode.setEname(Constants.FLOW_REFUSE);
				FlowNode fleNodeId = fleNodeService
						.queryByEnameAndModel(flowNode);
				// 获的相关进件业务数据
				AppEntry appEntry = appEntryService.queryByAppModeId(primaryKey
						.getId());
				if (appEntry == null) {
					appEntry = new AppEntry();
					appEntry.setEntryId(UUIDUtil.getUUID());// 记录表主键
					appEntry.setChannelId(Constants.WEB_USER);// 进件渠道
					appEntry.setModeId(busIntoPiece.getId());// 业务表主键
					appEntry.setFromId(Constants.T_BUS_INTOPIECE);// 进件表名字
					appEntry.setOrgId(request.getSession()
							.getAttribute(Constants.SESSION_ORGCD).toString());// 当前用户的orgid
					appEntry.setFinsId(application.getFinsCode());//金融机构
					appEntry.setAppId(intoPiece.getModelId());// 产品应用的appid 主键
					appEntry.setNodeId(fleNodeId.getNodeId());// 流程节点id 主键
					appEntry.setNodeName(fleNodeId.getEname());// 流程节点状态码
					appEntry.setAppName(application.getCname());
					appEntry.setCreDate(DateUtils.getNowDate());// 创建时间
					appEntry.setUpdDate(DateUtils.getNowDate());// 更新时间
					appEntryService.addAppEntrySelective(appEntry);
				} else {
					appEntry.setNodeId(fleNodeId.getNodeId());// 流程节点id 主键
					appEntry.setNodeName(fleNodeId.getEname());// 流程节点状态码
					// 更改拒件流程节点
					appEntryService.updateByAppEntrySelective(appEntry);
				}
				resmap.put("msg", "该客户不符合进件申请条件，请在列表查看并核实原因，30天后重新发起申请！");
				resmap.put("errono", 1001);
				return resmap;
			}
			try {
				if (!StringUtils.isEmpty(memberOperateId)) {
					BusMemberOperate MO = memberOperateService
							.selectByPrimaryKey(memberOperateId);
					if (MO != null) {
						BusFamilyOperate familyOperate = new BusFamilyOperate();
						familyOperate.setId(UUIDUtil.getUUID());
						familyOperate.setIntoPieceId(busIntoPiece.getId());
						familyOperate.setCreOperId(request.getSession()
								.getAttribute(Constants.SESSION_USERCD)
								.toString());
						familyOperate.setUpdOperId(request.getSession()
								.getAttribute(Constants.SESSION_USERCD)
								.toString());
						familyOperate.setCreDate(DateUtils.getNowDate());
						familyOperate.setUpdDate(DateUtils.getNowDate());
						familyOperate
								.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
						StringBuilder sb = new StringBuilder();
						String cropType = MO.getCropType();
						String cropYears = MO.getCropYears();
						String cropScale = MO.getCropScale();
						String cropExpectedValue = MO.getCropExpectedValue();
						String cropInvestment = MO.getCropInvestment();
						if (!StringUtils.isEmpty(cropType)
								|| !StringUtils.isEmpty(cropYears)
								|| !StringUtils.isEmpty(cropScale)
								|| !StringUtils.isEmpty(cropExpectedValue)
								|| !StringUtils.isEmpty(cropInvestment)) {
							sb.append("1");
							familyOperate.setBigLandCropType(cropType);
							familyOperate.setBlcYears(cropYears);
							familyOperate.setBlcScale(cropScale);
							familyOperate
									.setBlcExpectedValue(cropExpectedValue);
							familyOperate.setBlcInvestment(cropInvestment);
						}
						String livestockType = MO.getLivestockType();
						String livestockScale = MO.getLivestockScale();
						String livestockYears = MO.getLivestockYears();
						String livestockExpectedValue = MO
								.getLivestockExpectedValue();
						Integer livestockSiteType = MO.getLivestockSiteType();
						if (!StringUtils.isEmpty(livestockType)
								|| !StringUtils.isEmpty(livestockScale)
								|| !StringUtils.isEmpty(livestockYears)
								|| !StringUtils.isEmpty(livestockExpectedValue)
								|| livestockSiteType != null) {
							String sb1 = sb.toString();
							if (StringUtils.isEmpty(sb1)) {
								sb.append("3");
							} else {
								sb.append(",3");
							}
							familyOperate.setLivestockType(livestockType);
							familyOperate.setLivestockScale(livestockScale);
							familyOperate.setLivestockYears(livestockYears);
							familyOperate
									.setLivestockExpectedValue(livestockExpectedValue);
							familyOperate
									.setLivestockSiteType(livestockSiteType);
						}
						familyOperate.setOperateType(AppNull.s2SNull(sb
								.toString()));
						familyOperateService.insert(familyOperate);
						// memberOperateMediaService
						List<BusMemberOperateMedia> list = memberOperateMediaService
								.selectByMOId(memberOperateId);
						if (list != null && list.size() > 0) {
							for (int i = 0; i < list.size(); i++) {
								BusMedia media = new BusMedia();
								BusMemberOperateMedia busMemberOperateMedia = list
										.get(i);
								media.setIntoPieceId(busIntoPiece.getId());
								media.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
								media.setQiniuKey(busMemberOperateMedia
										.getQiniuKey());
								media.setId(UUIDUtil.getUUID());
								media.setFileType(Constants.OTHER);
								media.setExtName("jpg");
								media.setType(Constants.MEDIATYPE_IMAGE);
								media.setCreDate(DateUtils.getNowDate());
								media.setCreOperId(request.getSession()
										.getAttribute(Constants.SESSION_USERCD)
										.toString());
								media.setUpdDate(DateUtils.getNowDate());
								media.setUpdOperId(request.getSession()
										.getAttribute(Constants.SESSION_USERCD)
										.toString());
								mediaService.insert(media);
							}
						}
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			resmap.put("msg", "保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			resmap.put("msg", e.getMessage());
		}

		return resmap;
	}

	@RequestMapping("/update")
	@ResponseBody
	public JSONObject update(HttpServletRequest request, BusIntoPiece intoPiece)
			throws Exception {
		JSONObject json = new JSONObject();
		if (intoPiece == null) {
			json.put("code", 400);
			json.put("msg", "保存失败");
			return json;
		}
		if (StrUtils.isEmpty(intoPiece.getId())) {
			json.put("code", 400);
			json.put("msg", "保存失败");
			return json;
		}
		try {
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPiece
					.getId());
			ip.setAddress(intoPiece.getAddress());
			ip.setAge(intoPiece.getAge());
			ip.setBank(intoPiece.getBank());
			ip.setBankCardNo(intoPiece.getBankCardNo());
//			ip.setCapital(intoPiece.getCapital());
//			ip.setDuty(intoPiece.getDuty());
			ip.setEducationLevel(intoPiece.getEducationLevel());
			ip.setIdCard(intoPiece.getIdCard());
			ip.setMaritalStatus(intoPiece.getMaritalStatus());
//			ip.setMemberName(intoPiece.getMemberName());
			ip.setName(intoPiece.getName());
//			ip.setNonfarmComAddress(intoPiece.getNonfarmComAddress());
//			ip.setNonfarmComName(intoPiece.getNonfarmComName());
//			ip.setNonfarmComPhone(intoPiece.getNonfarmComPhone());
			ip.setPhone(intoPiece.getPhone());
//			ip.setProduct(intoPiece.getProduct());
			ip.setSex(intoPiece.getSex());
			ip.setTerm(intoPiece.getTerm());
			ip.setUse(intoPiece.getUse());
			ip.setUseRemark(intoPiece.getUseRemark());
			ip.setUpdDate(new Date());
			ip.setUpdOperId(request.getSession()
					.getAttribute(Constants.SESSION_USERCD).toString());
			ip.setUpdOperName(request.getSession()
					.getAttribute(Constants.SESSION_USERNM).toString());
			ip.setUpdOrgId(request.getSession()
					.getAttribute(Constants.SESSION_ORGCD).toString());
			intoPieceService.updateByPrimaryKey(ip);
			json.put("code", 200);
			json.put("msg", "保存成功");
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", "保存失败");
			return json;
		}
	}

	/**
	 * @throws Exception
	 *
	 * @Title: selectModelList
	 * @Description: 查询模型列表
	 * @param @param intoPieceId
	 * @param @return
	 * @return Map<String,Object>
	 * @throws
	 */
	@RequestMapping("/modelList")
	@ResponseBody
	public String selectModelList(String intoPieceId, String orgId)
			throws Exception {
		return intoPieceService.selectModelByIntoPID(intoPieceId, orgId);
	}

	@RequestMapping("/getOrgTree")
	@ResponseBody
	public Map<String, Object> getOrgTree(String intopId) throws Exception {
		return intoPieceService.getOrgTree(intopId);
	}

	@RequestMapping("/getOrgs")
	@ResponseBody
	public Map<String, Object> getOrgs(HttpServletRequest request,
									   HttpServletResponse response) throws Exception {
		String intoPieceId = request.getParameter("intoPieceId");
		return intoPieceService.getOrgs(
				request.getSession().getAttribute(Constants.SESSION_PERSONCD)
						.toString(), intoPieceId);
	}

	@RequestMapping("/getById")
	@ResponseBody
	public JSONObject getById(String intoPieceId) throws Exception {
		JSONObject json = new JSONObject();
		AppEntry entry = appEntryService.queryByAppModeId(intoPieceId);
		// 进件是否可编辑 0 不可编辑 1可以编辑
		int edit = 0;
		if (entry != null) {
			if (Constants.FLOW_COMPLETE.equals(entry.getNodeName())
					|| Constants.FLOW_FIRST_TRIAL.equals(entry.getNodeName())
					|| Constants.FLOW_SECOND_TRIAL.equals(entry.getNodeName())
					|| Constants.FLOW_THIRD_TRIAL.equals(entry.getNodeName())
					|| Constants.FLOW_FORCH_TRIAL.equals(entry.getNodeName())) {
				edit = 1;
			}
		}
		json.put("model", intoPieceService.selectByPrimaryKey(intoPieceId));
		json.put("edit", edit);
		return json;
	}

	@RequestMapping("/submitprimary")
	@ResponseBody
	public JSONObject submitPrimary(HttpServletRequest request,String intoPieceId,String trail_opinion) {
		JSONObject json = new JSONObject();
		if (StrUtils.isEmpty(intoPieceId)) {
			json.put("code", 400);
			json.put("msg", "进件标识为空");
			return json;
		}
		if (StrUtils.isEmpty(trail_opinion)) {
			json.put("code", 400);
			json.put("msg", "请填写意见");
			return json;
		}
		try {
//			BusGuaranteeFeeInfo guaranteeFeeInfo = guaranteeFeeInfoService.selectByIntopieceId(intoPieceId);
//			BusGuaranteeFee guaranteeFee = guaranteeFeeService.selectByIntopieceId(intoPieceId);
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
			/*List<BusFamilySituation> list = familySituationService.thirdpartycredit(intoPieceId);
			for (int i = 0; i < list.size(); i++) {
				//1线上  2线下
				String type = request.getParameter(list.get(i).getId());
				if(StrUtils.isEmpty(type)){
					json.put("code", 400);
					json.put("msg", list.get(i).getName()+"授权书签署类型未选择");
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
				list.get(i).setDuty(type);
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
				loan.setCreOperId( request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
				loan.setCreOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
				loan.setCreOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
				loan.setCreDate(new Date());
				loan.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
				loan.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
				loan.setUpdOperName(request.getSession().getAttribute(Constants.SESSION_USERNM).toString());
				loan.setUpdOrgId(request.getSession().getAttribute(Constants.SESSION_ORGCD).toString());
				loan.setUpdDate(new Date());
				loanService.insert(loan);
			}
			ip.setTrialOpinion(trail_opinion);
			ip.setUpdDate(DateUtils.getNowDate());
			intoPieceService.updateByPrimaryKey(ip);
			json = intoPieceService.submitPrimary(ip,loan, null,request
					.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			if (json.getIntValue("code") == 200) {
				JPushDO.Do(intoPieceService, appEntryService, flowNodeService,
						jpushUtils, applicationService, intoPieceId,
						json.getString("nextNodeId"), request);
			}
//			String channel = ip.getChannel();
//			if(serviceFeeWay.equals("1")||serviceFeeWay.equals("2")||serviceFeeWay.equals("4")){
//				if(guaranteeFeeInfo!=null){
//					return json;
//				}
//			}
//			SysOrg org = orgService.selectByOrgId(ip.getOrgId());
//			List<BusProduct> pList = productService.selectListByFinsId(ip.getLenderId());
//			Map<String, BigDecimal> serviceFee = GetRepaymentUtils.getServiceFee(ip.getCapital(), ip.getTerm(), pList.get(0));
//			BigDecimal first = serviceFee.get("first");
//			if(StringUtils.isNotEmpty(serviceFeeWay)&&serviceFeeWay.equals("1")){
//				if(guaranteeFee==null){
//					BusGuaranteeFeeInfo feeInfo = new BusGuaranteeFeeInfo();
//					feeInfo.setId(UUIDUtil.getUUID());
//					feeInfo.setOrgId(org.getOrgId());
//					feeInfo.setOrgName(org.getFullCname());
//					feeInfo.setIntoPieceId(ip.getId());
//					feeInfo.setUse(ip.getUse());
//					feeInfo.setAccountName(ip.getMemberName());
//					feeInfo.setCertificateNo(ip.getIdCard());
//					//银行卡改成申请金额
//					feeInfo.setAccountNo(ip.getCapital().toString());
//					feeInfo.setPayer(org.getLeader());
//					feeInfo.setPayerIdcard(org.getIdCard());
//					feeInfo.setPayWay("1");
//					feeInfo.setMobileNo(ip.getPhone());
//					feeInfo.setTotalAmount(first);
//					feeInfo.setCreateBy((String)request.getSession().getAttribute(Constants.SESSION_PERSONCD));
//					feeInfo.setCreateDate(DateUtils.getNowDate());
//					SysWebUser user =
//							 userService.selectUserByUserName(pieceConfig.getUserName());
//							 Map<String, String> map1 = new HashMap<String, String>();
//							 map1.put("amount", first.toString());
//							 map1.put("certificateNo", org.getIdCard());
//							 map1.put("accountNo", org.getCardNo());
//							 map1.put("accountName", org.getLeader());
//							 map1.put("mobileNo", org.getPhone());
//							 map1.put("intoPieceId", intoPieceId);
//							 map1.put("userid", user.getUserId());
//							 map1.put("companyType", channel);
//							 String signature = user.getUserId() + user.getUsername() +
//							 user.getPassword();
//							 map1.put("signature", DigestUtils.md5Hex(signature));
//							 String retMap =
//							 HttpClientUtil.doPost(pieceConfig.getXfGuaranteeFeeUrl(), map1,
//							 "utf-8");
//							 System.err.println(retMap);
//							 if(retMap!=null){
//								 JSONObject retMap1 = JSONObject.parseObject(retMap);
//								 if(retMap1==null||StringUtils.isEmpty(retMap1.getString("isSuccess"))||retMap1.getString("isSuccess").equals(Constants.GATE_RESULT_FAIL)){
//									 json.put("code", 400);
//									 json.put("messgae", "代扣服务费失败，请联系管理员！");
//									 feeInfo.setStatus("F");
//									 guaranteeFeeInfoService.insert(feeInfo);
//									 return json;
//								 }
//							 }else{
//								 json.put("code", 400);
//								 json.put("msg", "代扣服务费失败，请联系管理员！");
//								 feeInfo.setStatus("F");
//								 guaranteeFeeInfoService.insert(feeInfo);
//								 return json;
//							 }
//							 guaranteeFeeInfoService.insert(feeInfo);
//				}
//				//保存服务费扣除方式
//				if(StringUtils.isNotEmpty(serviceFeeWay)){
//					ip.setServiceFeeWay(new Integer(serviceFeeWay));
//					intoPieceService.updateByPrimaryKey(ip);
//				}
//				//微信支付
//			}else if(StringUtils.isNotEmpty(serviceFeeWay)&&serviceFeeWay.equals("2")&&guaranteeFeeInfo==null){
//				SysPerson person = personService.selectByPersonId(ip.getOperUserId());
//				messageReminderService.saveweixinpay(channel,intoPieceId,first.toString());
//				BusGuaranteeFeeInfo feeInfo = new BusGuaranteeFeeInfo();
//				feeInfo.setId(UUIDUtil.getUUID());
//				feeInfo.setOrgId(org.getOrgId());
//				feeInfo.setOrgName(org.getFullCname());
//				feeInfo.setIntoPieceId(ip.getId());
//				feeInfo.setUse(ip.getUse());
//				feeInfo.setAccountName(ip.getMemberName());
//				feeInfo.setCertificateNo(ip.getIdCard());
//				//银行卡改成申请金额
//				feeInfo.setAccountNo(ip.getCapital().toString());
//				feeInfo.setMobileNo(ip.getPhone());
//				feeInfo.setPayerIdcard(person.getCardNo());
//				feeInfo.setPayer(person.getNameCn());
//				feeInfo.setPayWay("2");
//				feeInfo.setTotalAmount(first);
//				feeInfo.setCreateBy((String)request.getSession().getAttribute(Constants.SESSION_PERSONCD));
//				feeInfo.setCreateDate(DateUtils.getNowDate());
//				feeInfo.setStatus("I");
//				guaranteeFeeInfoService.insert(feeInfo);
//			}
//			if(StringUtils.isNotEmpty(serviceFeeWay)){
//				ip.setServiceFeeWay(new Integer(serviceFeeWay));
//				intoPieceService.updateByPrimaryKey(ip);
//			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put(
					"msg",
					"无法提交，原因："
							+ e.getMessage().substring(
							e.getMessage().indexOf(":") + 1,
							e.getMessage().length()));
		}
		return json;
	}

	@RequestMapping("/rejectReason")
	@ResponseBody
	public JSONObject rejectReason(HttpServletRequest request, String id) {
		JSONObject retJson = new JSONObject();
		try {
			BusExamine record = new BusExamine();
			record.setIntoPieceId(id);
			BusExamine examine = examineService.selectByIpIdLast(id);
			StringBuilder sb = new StringBuilder(
					"<div style=\"width: 400px; height: 380px; top: 50.5px; left: 633.5px;\"><table class=\"table table-striped table-condensed table-hover table-bordered\"><tbody>");
			List<BusRejectReason> selectByIpId = reasonService.selectByIpId(id);
			if (selectByIpId != null && selectByIpId.size() > 0) {
				for (int i = 0; i < selectByIpId.size(); i++) {
					int j = i + 1;
					sb.append("<tr><td>自动拒件第" + j + "条</td><td>"
							+ selectByIpId.get(i).getRejectReason()
							+ "</td></tr>");
				}
			} else {
				if (examine == null) {
					retJson.put("result", "未查询到原因！");
					retJson.put("errno", 3000);
					return retJson;

				} else {
					sb.append("<tr><td>手动拒件</td><td>"
							+ examine.getExamineOpinion() + "</td></tr>");
				}
			}

			sb.append("</tbody></table></div>");
			retJson.put("result", sb.toString());
			retJson.put("errno", 2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retJson;
	}

	@RequestMapping("/reject")
	@ResponseBody
	public JSONObject reject(HttpServletRequest request, String id,String rjReason)
			throws Exception {
		JSONObject retJson = new JSONObject();
		try {
			BusIntoPiece primaryKey = intoPieceService.selectByPrimaryKey(id);
			AppEntry entry = appEntryService.queryByAppModeId(id);
			FlowNode flowNode = new FlowNode();
			flowNode.setAppId(primaryKey.getModelId());
			flowNode.setEname(Constants.FLOW_REFUSE);
			FlowNode fleNodeId = fleNodeService
					.queryByEnameAndModel(flowNode);
			// 获的相关进件业务数据
			if (entry != null) {
				entry.setNodeId(fleNodeId.getNodeId());// 流程节点id 主键
				entry.setNodeName(fleNodeId.getEname());// 流程节点状态码
				// 更改拒件流程节点
				appEntryService.updateByAppEntrySelective(entry);
			}else{
				retJson.put("msg", "未找到该进件！");
				retJson.put("errno", 3000);
				return retJson;
			}
			BusExamine examine = new BusExamine();
			examine.setId(UUIDUtil.getUUID());
			examine.setIntoPieceId(id);
			examine.setNode(entry.getNodeName());
			examine.setCapital(primaryKey.getCapital());
			examine.setTerm(primaryKey.getTerm());
			examine.setExamineBy(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
			examine.setExamineDate(new Date());
			examine.setExamineOpinion(rjReason);
			examine.setExamineName(request.getSession().getAttribute("personName").toString());
			primaryKey.setUpdOperId(request.getSession().getAttribute(Constants.SESSION_USERCD).toString());
			primaryKey.setUpdDate(DateUtils.getNowDate());
			examineService.insert(examine);
			retJson.put("msg", "拒件成功");
			retJson.put("errno", 2000);
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("msg", "系统异常");
			retJson.put("errno", 3000);
		}
		return retJson;
	}

	@RequestMapping("/operChange")
	@ResponseBody
	public JSONObject operChange(HttpServletRequest request, String intoPieceId)
			throws Exception {
		JSONObject retJson = new JSONObject();
		try {
			BusIntoPiece intoPiece = intoPieceService
					.selectByPrimaryKey(intoPieceId);
			List<SysPerson> list = personService.selectByPage(intoPiece
					.getOrgId());
			StringBuilder selectsb = new StringBuilder();
			selectsb.append("<option value=\"\" >--请选择--</option>");
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i)
							.getPersonId()
							.equals(intoPiece.getOperUserId() == null ? ""
									: intoPiece.getOperUserId())) {
						selectsb.append("<option value=\""
								+ list.get(i).getPersonId()
								+ "\" selected=true>" + list.get(i).getNameCn()
								+ "</option>");
					} else {
						selectsb.append("<option value=\""
								+ list.get(i).getPersonId() + "\" >"
								+ list.get(i).getNameCn() + "</option>");
					}
				}

			}
			retJson.put("result", selectsb);
			retJson.put("errno", 2000);
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("msg", "系统异常");
			retJson.put("errno", 3000);
		}
		return retJson;
	}

	@RequestMapping("/operUserIdChange")
	@ResponseBody
	public JSONObject operUserIdChange(HttpServletRequest request,
									   BusIntoPiece intoPiece) {
		JSONObject retJson = new JSONObject();
		try {
			BusIntoPiece intoPiece2 = intoPieceService
					.selectByPrimaryKey(intoPiece.getId());
			if (StringUtils.isNotEmpty(intoPiece.getOperUserId())
					&& !intoPiece.getOperUserId().equals(
					intoPiece2.getOperUserId())) {
				intoPiece2.setOperUserId(intoPiece.getOperUserId());
				intoPieceService.updateByPrimaryKey(intoPiece2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("msg", "修改失败！");
			retJson.put("errno", 3000);
			return retJson;
		}
		retJson.put("msg", "修改成功！");
		retJson.put("errno", 2000);
		return retJson;
	}

	@RequestMapping("/applyInfo")
	@ResponseBody
	public ResponseEntity<FileSystemResource> applyInfo(
			HttpServletRequest request, HttpServletResponse response,
			String intopieceId) throws Exception {
		try {
			BusIntoPiece intoPiece = intoPieceService
					.selectByPrimaryKey(intopieceId);
			List<BusTransferLand> transferLands = transferLandService
					.selectByIpId(intopieceId);
			File templetFile = new File(fileConfig.getBaseDir()
					+ "Export//landinfo.xls");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", intoPiece.getMemberName());
			map.put("tel", intoPiece.getPhone());
			map.put("address", intoPiece.getAddress());
			map.put("id_card", intoPiece.getIdCard());
			BigDecimal total = new BigDecimal("0");
			if (transferLands != null && transferLands.size() > 0) {
				for (int i = 1; i < transferLands.size() + 1; i++) {
					BusTransferLand land = transferLands.get(i - 1);
					map.put("land_name" + i, land.getLandName());
					map.put("land_level" + i, land.getLandLevel());
					map.put("land_area" + i, land.getLandArea());
					total = total.add(new BigDecimal(StringUtils.isEmpty(land
							.getLandArea()) ? "0" : land.getLandArea())
							.setScale(2, RoundingMode.HALF_UP));
				}
			}
			String totalarea= total.compareTo(new BigDecimal(0))==0?"":total.toString();
			BusFamilyCapital record = new BusFamilyCapital();
			record.setIntoPieceId(intopieceId);
			record.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			BusFamilyCapital familyCapital = familyCapitalService
					.selectByIntoPieceId(record);
			if (familyCapital != null) {
				String dc = familyCapital.getDryFarmlandRegistered();
				if (StringUtils.isEmpty(dc)) {
					dc = "0";
				}
				String wc = familyCapital.getWaterFarmlandRegistered();
				if (StringUtils.isEmpty(wc)) {
					wc = "0";
				}
				map.put("land_cert_num",
						familyCapital.getLandCertId() == null ? ""
								: familyCapital.getLandCertId());
				map.put("contract_area",
						(StringUtils.isEmpty(dc) && StringUtils.isEmpty(wc)) ? ""
								: new BigDecimal(wc).add(new BigDecimal(dc)));
			} else {
				map.put("land_cert_num", "");
				map.put("contract_area", "");
			}

			map.put("total", totalarea);
			map.put("capital", intoPiece.getCapital());
			map.put("term", intoPiece.getTerm());
			map.put("use", intoPiece.getUseRemark());
			InputStream is = new BufferedInputStream(new FileInputStream(
					templetFile));
			XLSTransformer transformer = new XLSTransformer();
			Workbook workBook = transformer.transformXLS(is, map);
			File file = new File(fileConfig.getBaseDir() + "ExportTemp//"
					+ intoPiece.getMemberName() + "申请表" + ".xls");
			workBook.write(new FileOutputStream(file));
			// Workbook exportExcel = ExcelExportUtil.exportExcel(params, map);
			// exportExcel.write(new FileOutputStream(file));
			HttpHeaders headers = new HttpHeaders();
			String parseGBK = FileUtils.parseGBK(intoPiece.getMemberName()
					+ "申请表.xls");
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Content-Disposition", "attachment; filename="
					+ parseGBK);
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
			headers.add("Last-Modified", new Date().toString());
			headers.add("ETag", String.valueOf(System.currentTimeMillis()));
			return ResponseEntity
					.ok()
					.headers(headers)
					.contentLength(file.length())
					.contentType(
							MediaType
									.parseMediaType("application/octet-stream"))
					.body(new FileSystemResource(file));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("导出错误");
		}
	}

	@RequestMapping("/transferland")
	@ResponseBody
	public ResponseEntity<FileSystemResource> transferland(
			HttpServletRequest request, HttpServletResponse response,
			String intopieceId) throws Exception {
		try {
			BusIntoPiece intoPiece = intoPieceService
					.selectByPrimaryKey(intopieceId);
			List<BusTransferLand> transferLands = transferLandService
					.selectByIpId(intopieceId);
			File templetFile = new File(fileConfig.getBaseDir()
					+ "Export//landtransfer.xls");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", intoPiece.getMemberName());
			map.put("address", intoPiece.getAddress());
			BigDecimal total = new BigDecimal("0");
			if (transferLands != null && transferLands.size() > 0) {
				for (int i = 1; i < transferLands.size() + 1; i++) {
					BusTransferLand land = transferLands.get(i - 1);
					map.put("land_name" + i, land.getLandName());
					map.put("land_level" + i, land.getLandLevel());
					map.put("land_area" + i, land.getLandArea());
					total = total.add(new BigDecimal(StringUtils.isEmpty(land
							.getLandArea()) ? "0" : land.getLandArea())
							.setScale(2, RoundingMode.HALF_UP));
				}
			}
			String totalarea= total.compareTo(new BigDecimal(0))==0?"":total.toString();
			BusFamilyCapital record = new BusFamilyCapital();
			record.setIntoPieceId(intopieceId);
			record.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			BusFamilyCapital familyCapital = familyCapitalService
					.selectByIntoPieceId(record);
			if (familyCapital != null) {
				String dc = familyCapital.getDryFarmlandRegistered();
				if (StringUtils.isEmpty(dc)) {
					dc = "0";
				}
				String wc = familyCapital.getWaterFarmlandRegistered();
				if (StringUtils.isEmpty(wc)) {
					wc = "0";
				}
				map.put("land_cert_num",
						familyCapital.getLandCertId() == null ? ""
								: familyCapital.getLandCertId());
				map.put("contract_area",
						(StringUtils.isEmpty(dc) && StringUtils.isEmpty(wc)) ? ""
								: new BigDecimal(wc).add(new BigDecimal(dc)));
			} else {
				map.put("land_cert_num", "");
				map.put("contract_area", "");
			}

			map.put("total", totalarea);
			InputStream is = new BufferedInputStream(new FileInputStream(
					templetFile));
			XLSTransformer transformer = new XLSTransformer();
			Workbook workBook = transformer.transformXLS(is, map);
			File file = new File(fileConfig.getBaseDir() + "ExportTemp//"
					+ intoPiece.getMemberName() + "土地备案回执" + ".xls");
			workBook.write(new FileOutputStream(file));
			// Workbook exportExcel = ExcelExportUtil.exportExcel(params, map);
			// exportExcel.write(new FileOutputStream(file));
			HttpHeaders headers = new HttpHeaders();
			String parseGBK = FileUtils.parseGBK(intoPiece.getMemberName()
					+ "土地备案回执.xls");
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Content-Disposition", "attachment; filename="
					+ parseGBK);
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
			headers.add("Last-Modified", new Date().toString());
			headers.add("ETag", String.valueOf(System.currentTimeMillis()));
			return ResponseEntity
					.ok()
					.headers(headers)
					.contentLength(file.length())
					.contentType(
							MediaType
									.parseMediaType("application/octet-stream"))
					.body(new FileSystemResource(file));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("导出错误");
		}
	}

	@RequestMapping("/dynamicDateExport")
	@ResponseBody
	public ResponseEntity<FileSystemResource> dynamicDateExport(
			HttpServletRequest request, HttpServletResponse response,
			String intopieceId) throws Exception {
		try {
			BusIntoPiece intoPiece = intoPieceService
					.selectByPrimaryKey(intopieceId);
			List<AppSection> selectAll = sectionService.selectAll(intoPiece
					.getModelId());
			List<ExcelExportEntity> colList = new ArrayList<ExcelExportEntity>();
			ExcelExportEntity colEntity = new ExcelExportEntity("参数名",
					"paraName");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(60);
			colList.add(colEntity);

			colEntity = new ExcelExportEntity("参数值", "paraValue");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(50);
			colList.add(colEntity);
			List<Map<String, Object>> listexp = new ArrayList<Map<String, Object>>();
			if (selectAll != null && selectAll.size() > 0) {
				for (int i = 0; i < selectAll.size(); i++) {
					DynamicDataMap dd = new DynamicDataMap();
					dd.setSectionId(selectAll.get(i).getSectionId());
					dd.setIntoPieceId(intopieceId);
					List<DynamicDataMap> list = applyPieceExtService
							.selectBySectionId(dd);
					for (int j = 0; j < list.size(); j++) {
						Map<String, Object> valMap = new HashMap<String, Object>();
						valMap.put("paraValue", "");
						String optionsGroup = list.get(j).getOptionsGroup();
						if (StrUtils.isNotEmpty(optionsGroup)) {
							if (list.get(j).getInputType() == 1) {
								valMap.put("paraName", list.get(j).getCname());
								valMap.put("paraValue",
										StringUtils.isEmpty(list.get(j)
												.getItemValue()) ? "" : list
												.get(j).getItemValue()
												+ list.get(j).getUnit());
							} else if (list.get(j).getInputType() == 2) {
								AppPara app = new AppPara();
								app.setAppId(intoPiece.getModelId());
								app.setGroupName(optionsGroup);
								List<AppPara> appList = appParaService
										.selectByGroupName(app);
								valMap.put("paraName", list.get(j).getCname());
								for (int k = 0; k < appList.size(); k++) {
									if (appList.get(k).getValue()
											.equals(list.get(j).getItemValue())) {
										valMap.put("paraValue", appList.get(k)
												.getDescr());
									}
								}
							} else if (list.get(j).getInputType() == 3) {
								AppPara app = new AppPara();
								app.setAppId(intoPiece.getModelId());
								app.setGroupName(optionsGroup);
								List<AppPara> appList = appParaService
										.selectByGroupName(app);
								valMap.put("paraName", list.get(j).getCname());
								String dscrib = "";
								String itemValue = list.get(j).getItemValue();
								if (StringUtils.isNotEmpty(itemValue)) {
									for (int k = 0; k < appList.size(); k++) {
										if (list.get(j)
												.getItemValue()
												.contains(
														appList.get(k)
																.getValue())) {
											dscrib += appList.get(k).getDescr()
													+ "/";
										}
									}
								}

								if (StringUtils.isNotEmpty(dscrib)) {
									dscrib = dscrib.substring(0,
											dscrib.length() - 1);
								}
								valMap.put("paraValue", dscrib);
							}
						} else {
							valMap.put("paraName", list.get(j).getCname());
							valMap.put("paraValue", list.get(j).getItemValue()
									+ list.get(j).getUnit());
						}
						listexp.add(valMap);
					}
				}
			}
			Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(
							intoPiece.getMemberName() + "的审核记录", "审核记录"), colList,
					listexp);
			String baseurl = fileConfig.getBaseDir();
			baseurl = baseurl + "xlsFile\\download\\";
			FileUtils.createDirectory(baseurl);
			File file = new File(baseurl, intoPiece.getMemberName()
					+ "的审核记录.xls");
			workbook.write(new FileOutputStream(file));
			HttpHeaders headers = new HttpHeaders();
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			String parseGBK = FileUtils.parseGBK(intoPiece.getMemberName()
					+ "的审核记录.xls");
			headers.add("Content-Disposition", "attachment; filename="
					+ parseGBK);
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
			headers.add("Last-Modified", new Date().toString());
			headers.add("ETag", String.valueOf(System.currentTimeMillis()));
			return ResponseEntity
					.ok()
					.headers(headers)
					.contentLength(file.length())
					.contentType(
							MediaType
									.parseMediaType("application/octet-stream"))
					.body(new FileSystemResource(file));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@RequestMapping("/memberInfo")
	@ResponseBody
	public ResponseEntity<FileSystemResource> memberInfo(
			HttpServletRequest request, HttpServletResponse response,
			String intopieceId) throws Exception {
		try {
			File templetFile = new File(fileConfig.getBaseDir()
					+ "Export//memberinfo.xls");
			Map<String, Object> exportMap = intoPieceService
					.selectExportMap(intopieceId);
			if (exportMap == null)
				exportMap = new HashMap<String, Object>();
			BusFamilySituation familySituations = new BusFamilySituation();
			familySituations.setIntoPieceId(intopieceId);
			List<BusFamilySituation> list = familySituationService
					.queryByIntopId(familySituations);
			BusFamilyCredit familyCredit = new BusFamilyCredit();
			familyCredit.setIntoPieceId(intopieceId);
			familyCredit.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			BusFamilyCapital busFamilyCapital = new BusFamilyCapital();
			busFamilyCapital.setIntoPieceId(intopieceId);
			busFamilyCapital.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			BusFamilyCapital familyCapital = familyCapitalService
					.selectByIntoPieceId(busFamilyCapital);
			BusFamilyCredit credit = familyCreditService
					.selectByIpId(familyCredit);
			BusFamilyOperate familyOperate = familyOperateService
					.selectByIpId(intopieceId);
			BusOtherContact busOtherContact = new BusOtherContact();
			busOtherContact.setIntoPieceId(intopieceId);
			busOtherContact.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			List<BusOtherContact> ocs = otherContactService
					.selectByIntpId(busOtherContact);
			List<Map<String, Object>> expspingfen = intopieceScoreService
					.selectExpByIntopieceId(intopieceId);
			BusExamine record = new BusExamine();
			record.setIntoPieceId(intopieceId);
			record.setNode(Constants.FLOW_FORCH_TRIAL);
			BusExamine examine = examineService.selectByIpIdNode(record);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("member_name", "");
			map.put("sex", "");
			map.put("age", "");
			map.put("id_card", "");
			map.put("phone", "");
			map.put("bank", "");
			map.put("bank_card_no", "");
			map.put("address", "");
			map.put("education_level", "");
			map.put("marital_status", "");
			map.put("capital", "");
			map.put("term", "");
			map.put("repayment_type", "");
			map.put("use", "");
			map.put("sxmoney", "");
			if (exportMap != null) {
				map.put("member_name", exportMap.get("memberName"));
				map.put("sex", "1".equals(exportMap.get("sex")) ? "男" : "女");
				map.put("age", exportMap.get("age"));
				map.put("id_card", exportMap.get("idCard"));
				map.put("phone", exportMap.get("phone"));
				map.put("bank", exportMap.get("bank"));
				map.put("bank_card_no", exportMap.get("bankCardNo"));
				map.put("address", exportMap.get("address"));
				if (exportMap.get("educationLevel") != null) {
					String edulevel = "";
					if (exportMap.get("educationLevel").toString().equals("1")) {
						edulevel = "初中以下";
					} else if (exportMap.get("educationLevel").toString()
							.equals("2")) {
						edulevel = "中专/高中";
					} else if (exportMap.get("educationLevel").toString()
							.equals("3")) {
						edulevel = "大专";
					} else if (exportMap.get("educationLevel").toString()
							.equals("4")) {
						edulevel = "本科及以上";
					}
					map.put("education_level", edulevel);

				}
				if (exportMap.get("maritalStatus") != null) {
					String marlevel = "";
					if (exportMap.get("maritalStatus").toString().equals("1")) {
						marlevel = "已婚";
					} else if (exportMap.get("maritalStatus").toString()
							.equals("2")) {
						marlevel = "未婚";
					} else if (exportMap.get("maritalStatus").toString()
							.equals("3")) {
						marlevel = "离异";
					} else if (exportMap.get("maritalStatus").toString()
							.equals("4")) {
						marlevel = "丧偶";
					} else if (exportMap.get("maritalStatus").toString()
							.equals("5")) {
						marlevel = "二婚";
					}
					map.put("marital_status", marlevel);
				}
				map.put("capital", exportMap.get("capital"));
				map.put("term", exportMap.get("term"));
				if (exportMap.get("repaymentType") != null) {
					String repaymentType = "";
					if (exportMap.get("repaymentType").toString().equals("1")) {
						repaymentType = "等额本息";
					} else if (exportMap.get("repaymentType").toString()
							.equals("2")) {
						repaymentType = "先息后本";
					} else if (exportMap.get("repaymentType").toString()
							.equals("3")) {
						repaymentType = "组合贷款";
					} else if (exportMap.get("repaymentType").toString()
							.equals("4")) {
						repaymentType = "惠农通";
					} else if (exportMap.get("repaymentType").toString()
							.equals("5")) {
						repaymentType = "按季度还息、到期还本";
					} else if (exportMap.get("repaymentType").toString()
							.equals("5")) {
						repaymentType = "一次还清本息";
					}
					map.put("repayment_type", repaymentType);
				}
				map.put("use", exportMap.get("useRemark"));
				map.put("sxmoney", exportMap.get("creditCapital") == null ? ""
						: exportMap.get("creditCapital"));
			}
			map.put("fname", "");
			map.put("fage", "");
			map.put("fid", "");
			map.put("flive", "");
			map.put("fworscho", "");
			map.put("mname", "");
			map.put("mage", "");
			map.put("mid", "");
			map.put("mlive", "");
			map.put("mworscho", "");
			map.put("pname", "");
			map.put("page", "");
			map.put("pid", "");
			map.put("plive", "");
			map.put("pworscho", "");
			map.put("fcname", "");
			map.put("fcage", "");
			map.put("fcid", "");
			map.put("fclive", "");
			map.put("fcworscho", "");
			map.put("scname", "");
			map.put("scage", "");
			map.put("scid", "");
			map.put("sclive", "");
			map.put("scworscho", "");
			map.put("oname", "");
			map.put("oage", "");
			map.put("oid", "");
			map.put("olive", "");
			map.put("oworscho", "");
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					BusFamilySituation familySituation = list.get(i);
					switch (list.get(i).getType()) {
						case 1:
							map.put("fname", familySituation.getName());
							map.put("fage", familySituation.getAge() == null ? ""
									: familySituation.getAge().toString());
							map.put("fid", familySituation.getIdCard());
							Integer flives = familySituation.getStatus();
							if (flives != null) {
								switch (flives) {
									case 1:
										map.put("flive", "同住");
										break;
									case 2:
										map.put("flive", "不同住");
										break;
									default:
										map.put("flive", "去世");
										break;
								}
							}
							map.put("fworscho",
									familySituation.getNonfarmComAddress());
							break;
						case 2:
							map.put("mname", familySituation.getName());
							map.put("mage", familySituation.getAge() == null ? ""
									: familySituation.getAge().toString());
							map.put("mid", familySituation.getIdCard());
							Integer mlives = familySituation.getStatus();
							if (mlives != null) {
								switch (mlives) {
									case 1:
										map.put("mlive", "同住");
										break;
									case 2:
										map.put("mlive", "不同住");
										break;
									default:
										map.put("mlive", "去世");
										break;
								}
							}
							map.put("mworscho",
									familySituation.getNonfarmComAddress());
							break;
						case 3:
							map.put("pname", familySituation.getName());
							map.put("page", familySituation.getAge() == null ? ""
									: familySituation.getAge().toString());
							map.put("pid", familySituation.getIdCard());
							Integer plives = familySituation.getStatus();
							if (plives != null) {
								switch (plives) {
									case 1:
										map.put("plive", "同住");
										break;
									case 2:
										map.put("plive", "不同住");
										break;
								}
							}
							map.put("pworscho",
									familySituation.getNonfarmComAddress());
							break;
						case 4:
							map.put("fcname", familySituation.getName());
							map.put("fcage", familySituation.getAge() == null ? ""
									: familySituation.getAge().toString());
							map.put("fcid", familySituation.getIdCard());
							Integer fclives = familySituation.getStatus();
							if (fclives != null) {
								switch (fclives) {
									case 1:
										map.put("fclive", "同住");
										break;
									case 2:
										map.put("fclive", "不同住");
										break;
								}
							}
							map.put("fcworscho",
									familySituation.getNonfarmComAddress());
							break;
						case 5:
							map.put("scname", familySituation.getName());
							map.put("scage", familySituation.getAge() == null ? ""
									: familySituation.getAge().toString());
							map.put("scid", familySituation.getIdCard());
							Integer sclives = familySituation.getStatus();
							if (sclives != null) {
								switch (sclives) {
									case 1:
										map.put("sclive", "同住");
										break;
									case 2:
										map.put("sclive", "不同住");
										break;
								}
							}
							map.put("scworscho",
									familySituation.getNonfarmComAddress());
							break;
						default:
							map.put("oname", familySituation.getName());
							map.put("oage", familySituation.getAge() == null ? ""
									: familySituation.getAge().toString());
							map.put("oid", familySituation.getIdCard());
							Integer olives = familySituation.getStatus();
							if (olives != null) {
								switch (olives) {
									case 1:
										map.put("olive", "同住");
										break;
									case 2:
										map.put("olive", "不同住");
										break;
								}
							}
							map.put("oworscho",
									familySituation.getNonfarmComAddress());
							break;
					}
				}
			}
			map.put("farmland_registered", "");
			map.put("farmland_registered_type", "");
			map.put("farmland_unregistered", "");
			map.put("farmland_unregistered_type", "");
			map.put("farmland_contract", "");
			map.put("farmland_contract_type", "");
			map.put("house_type", "");
			map.put("house_area", "");
			map.put("house_value", "");
			map.put("livestock_type", "");
			map.put("livestock_number", "");
			map.put("livestock_value", "");
			map.put("farm_machinery_type", "");
			map.put("farm_machinery_number", "");
			map.put("farm_machinery_value", "");
			map.put("other_assets_type", "");
			map.put("other_assets_number", "");
			map.put("other_assets_value", "");
			map.put("now_crops_value", "");
			map.put("now_crops_type", "");
			map.put("invisible_liabilities", "");
			if (familyCapital != null) {
				String farmland_registered_type = "";
				String rd = familyCapital.getDryFarmlandRegistered();
				String rw = familyCapital.getWaterFarmlandRegistered();
				if (rd != null)
					farmland_registered_type += "旱/";
				if (rw != null)
					farmland_registered_type += "水/";
				String farmland_unregistered_type = "";
				String urd = familyCapital.getDryFarmlandUnregistered();
				String urw = familyCapital.getWaterFarmlandUnregistered();
				if (urd != null)
					farmland_unregistered_type += "旱/";
				if (urw != null)
					farmland_unregistered_type += "水/";
				String farmland_contract_type = "";
				String cd = familyCapital.getDryFarmlandContract();
				String cw = familyCapital.getWaterFarmlandContract();
				if (cd != null)
					farmland_contract_type += "旱/";
				if (cw != null)
					farmland_contract_type += "水/";

				BigDecimal registered = new BigDecimal(StringUtils.isEmpty(rd) ? "0" : rd).add(new BigDecimal(StringUtils.isEmpty(rw) ? "0": rw));
				map.put("farmland_registered",
						registered.compareTo(new BigDecimal(0))==0?"":
								registered.setScale(2, RoundingMode.HALF_UP));
				map.put("farmland_registered_type",
						StringUtils.isEmpty(farmland_registered_type) ? ""
								: "（"+farmland_registered_type.subSequence(0,
								farmland_registered_type.length() - 1)+"）");
				BigDecimal unregistered = new BigDecimal(StringUtils.isEmpty(urd) ? "0" : urd).add(new BigDecimal(StringUtils.isEmpty(urw) ? "0" : urw));
				map.put("farmland_unregistered",
						unregistered.compareTo(new BigDecimal(0))==0?"":
								unregistered.setScale(2, RoundingMode.HALF_UP));
				map.put("farmland_unregistered_type",
						StringUtils.isEmpty(farmland_unregistered_type) ? ""
								: "（"+farmland_unregistered_type.subSequence(0,
								farmland_unregistered_type.length() - 1)+"）");
				BigDecimal contract = new BigDecimal(StringUtils.isEmpty(cd) ? "0" : cd).add(
						new BigDecimal(StringUtils.isEmpty(cw) ? "0"
								: cw));
				map.put("farmland_contract",
						contract.compareTo(new BigDecimal(0))==0?"":contract
								.setScale(2, RoundingMode.HALF_UP));
				map.put("farmland_contract_type",
						StringUtils.isEmpty(farmland_contract_type) ? ""
								: "（"+farmland_contract_type.subSequence(0,
								farmland_contract_type.length() - 1)+"）");
				map.put("sel_land_num", StringUtils.isEmpty(familyCapital
						.getSelLandNum()) ? "" : familyCapital.getSelLandNum());
				map.put("cert_land_num",
						StringUtils.isEmpty(familyCapital.getCertLandNum()) ? ""
								: familyCapital.getCertLandNum());
				map.put("land_cert_id", StringUtils.isEmpty(familyCapital
						.getLandCertId()) ? "" : familyCapital.getLandCertId());
				map.put("land_water_registered", StringUtils.isEmpty(rw) ? ""
						: rw);
				map.put("land_dry_registered", StringUtils.isEmpty(rd) ? ""
						: rd);
				map.put("land_water_unregistered",
						StringUtils.isEmpty(urw) ? "" : urw);
				map.put("land_dry_unregistered", StringUtils.isEmpty(urd) ? ""
						: urd);
				map.put("land_water_contract", StringUtils.isEmpty(cw) ? ""
						: cw);
				map.put("land_dry_contract", StringUtils.isEmpty(cd) ? "" : cd);
				map.put("total_assets",
						StringUtils.isEmpty(familyCapital
								.getTotalFamilyCapital()) ? "" : familyCapital
								.getTotalFamilyCapital());
				map.put("total_family_debtedness", StringUtils
						.isEmpty(familyCapital.getTotalFamilyDebtedness()) ? ""
						: familyCapital.getTotalFamilyDebtedness());
				map.put("bank_loan_indebtedness", StringUtils
						.isEmpty(familyCapital.getBankLoanIndebtedness()) ? ""
						: familyCapital.getBankLoanIndebtedness());
				map.put("p2p_petty_loan_indebtedness",
						StringUtils.isEmpty(familyCapital
								.getP2pPettyLoanIndebtedness()) ? ""
								: familyCapital.getP2pPettyLoanIndebtedness());
				map.put("friend_loan_indebtedness",
						StringUtils.isEmpty(familyCapital
								.getFriendLoanIndebtedness()) ? ""
								: familyCapital.getFriendLoanIndebtedness());
				map.put("guarantee_loan_indebtedness",
						StringUtils.isEmpty(familyCapital
								.getGuaranteeLoanIndebtedness()) ? ""
								: familyCapital.getGuaranteeLoanIndebtedness());
				if (familyCapital.getMainInvest() != null) {
					if (familyCapital.getMainInvest() == 1) {
						map.put("main_invest", "有");
					} else {
						map.put("main_invest", "无");
					}
				}
				String house_type = "";
				BigDecimal house_area = new BigDecimal("0");
				BigDecimal house_value = new BigDecimal("0");
				if (familyCapital.getSmallPropertyHouse() != null
						&& familyCapital.getSmallPropertyHouse() == 1) {
					house_type += "自购小产权房、安置房/";
					map.put("small_area",
							StringUtils.isEmpty(familyCapital
									.getSmallPropertyHouseArea()) ? ""
									: familyCapital.getSmallPropertyHouseArea());
					map.put("small_value", StringUtils.isEmpty(familyCapital
							.getSmallPropertyHouseValue()) ? ""
							: familyCapital.getSmallPropertyHouseValue());
					house_area
							.add(new BigDecimal(StringUtils
									.isEmpty(familyCapital
											.getSmallPropertyHouseArea()) ? "0"
									: familyCapital.getSmallPropertyHouseArea()));
					house_value.add(new BigDecimal(
							StringUtils.isEmpty(familyCapital
									.getSmallPropertyHouseValue()) ? "0"
									: familyCapital
									.getSmallPropertyHouseValue()));
				}
				if (familyCapital.getBigPropertyHouse() != null
						&& familyCapital.getBigPropertyHouse() == 1) {
					house_type += "自购大产权房、商品房/";
					map.put("big_area",
							StringUtils.isEmpty(familyCapital
									.getBigPropertyHouseArea()) ? ""
									: familyCapital.getBigPropertyHouseArea());
					map.put("big_value",
							StringUtils.isEmpty(familyCapital
									.getBigPropertyHouseValue()) ? ""
									: familyCapital.getBigPropertyHouseValue());
					house_area.add(new BigDecimal(
							StringUtils.isEmpty(familyCapital
									.getBigPropertyHouseArea()) ? ""
									: familyCapital.getBigPropertyHouseArea()));
					house_value
							.add(new BigDecimal(StringUtils
									.isEmpty(familyCapital
											.getBigPropertyHouseValue()) ? ""
									: familyCapital.getBigPropertyHouseValue()));
				}
				if (familyCapital.getRentHouse() != null
						&& familyCapital.getRentHouse() == 1) {
					house_type += "在外租房/";
				}
				if (familyCapital.getCountrysideHouse() != null
						&& familyCapital.getCountrysideHouse() == 1) {
					house_type += "农村房屋/";
				}
				// String livestock_type="";
				// if(familyCapital.getSmallPropertyHouse()==1){
				// livestock_type+="自购小产权房、安置房/";
				// }
				// if(familyCapital.getSmallPropertyHouse()==1){
				// livestock_type+="自购大产权房、商品房/";
				// }
				String farm_machinery_type = "";
				if (StringUtils
						.isNotEmpty(familyCapital.getNongMachineRemark())) {
					farm_machinery_type = familyCapital.getNongMachineRemark();
				}
				String other_assets_type = "";
				BigDecimal other_assets_value = new BigDecimal("0");
				if (familyCapital.getCar() != null
						&& familyCapital.getCar() == 1) {
					other_assets_type += "轿车/";
					other_assets_value.add(new BigDecimal(StringUtils
							.isEmpty(familyCapital.getCarValue()) ? "0"
							: familyCapital.getCarValue()));
				}
				if (familyCapital.getMinibus() != null
						&& familyCapital.getMinibus() == 1) {
					other_assets_type += "面包车/";
					other_assets_value.add(new BigDecimal(StringUtils
							.isEmpty(familyCapital.getMinibusValue()) ? "0"
							: familyCapital.getMinibusValue()));
				}
				if (familyCapital.getTruck() != null
						&& familyCapital.getTruck() == 1) {
					other_assets_type += "货车/";
					other_assets_value.add(new BigDecimal(StringUtils
							.isEmpty(familyCapital.getTruckValue()) ? "0"
							: familyCapital.getTruckValue()));
				}
				if (familyCapital.getOtherTricycle() != null
						&& familyCapital.getOtherTricycle() == 1) {
					other_assets_type += "三轮车、其它/";
				}
				map.put("house_type", StringUtils.isEmpty(house_type) ? ""
						: house_type.subSequence(0, house_type.length() - 1));
				map.put("house_area",
						house_area.setScale(0, RoundingMode.HALF_UP));
				map.put("house_value",
						house_value.setScale(0, RoundingMode.HALF_UP));
				map.put("farm_machinery_type", StringUtils
						.isEmpty(farm_machinery_type) ? ""
						: farm_machinery_type);
				map.put("car_name", StringUtils.isEmpty(familyCapital
						.getCarName()) ? "" : familyCapital.getCarName());
				map.put("car_value", StringUtils.isEmpty(familyCapital
						.getCarValue()) ? "" : familyCapital.getCarValue());
				map.put("minibus_value",
						StringUtils.isEmpty(familyCapital.getMinibusValue()) ? ""
								: familyCapital.getMinibusValue());
				map.put("truck_value", StringUtils.isEmpty(familyCapital
						.getTruckValue()) ? "" : familyCapital.getTruckValue());

				// map.put("farm_machinery_number", "");
				map.put("farm_machinery_value",
						StringUtils.isEmpty(familyCapital
								.getNongMachineEstimatedValue()) ? ""
								: familyCapital.getNongMachineEstimatedValue());
				map.put("other_assets_type",
						StringUtils.isEmpty(other_assets_type) ? ""
								: other_assets_type.subSequence(0,
								other_assets_type.length() - 1));
				// map.put("other_assets_number", "");
				map.put("other_assets_value",
						other_assets_value.setScale(0, RoundingMode.HALF_UP));
				map.put("invisible_liabilities", StringUtils
						.isEmpty(familyCapital.getInvisibleDebtedness()) ? ""
						: familyCapital.getInvisibleDebtedness());
			}
			map.put("business_type", "");
			map.put("plant_breed_type", "");
			map.put("harvest_time", "");
			map.put("agricultural_sales", "");
			map.put("chemical_fertilizer", "");
			map.put("sales_years", "");
			map.put("sales_revenue", "");
			if (familyOperate != null) {
				String livestock_type = "";
				if (StringUtils.isNotEmpty(familyOperate.getLivestockType())) {
					String livestockType = familyOperate.getLivestockType();
					String[] split = livestockType.split(",");
					for (int i = 0; i < split.length; i++) {
						if (split[i].equals("1")) {
							livestock_type += "牛/";
						} else if (split[i].equals("2")) {
							livestock_type += "猪/";
						} else if (split[i].equals("3")) {
							livestock_type += "羊/";
						} else if (split[i].equals("4")) {
							livestock_type += "鸡/";
						} else if (split[i].equals("5")) {
							livestock_type += "鱼/";
						} else if (split[i].equals("6")) {
							livestock_type += "其它/";
						}
					}
				}
				map.put("livestock_scale",
						StringUtils.isEmpty(familyOperate.getLivestockScale()) ? ""
								: familyOperate.getLivestockScale());
				map.put("livestock_years",
						StringUtils.isEmpty(familyOperate.getLivestockYears()) ? ""
								: familyOperate.getLivestockYears());
				if (familyOperate.getLivestockSiteType() != null) {
					if (familyOperate.getLivestockSiteType() == 1) {
						map.put("livestock_site_type", "租用");
						map.put("livestock_site_fee",
								StringUtils.isEmpty(familyOperate
										.getLivestockSiteRent()) ? ""
										: familyOperate.getLivestockSiteRent());
					} else {
						map.put("livestock_site_type", "自建");
						map.put("livestock_site_fee", StringUtils
								.isEmpty(familyOperate
										.getLivestockSiteInvestment()) ? ""
								: familyOperate.getLivestockSiteInvestment());
					}
				}
				if (StringUtils.isNotEmpty(familyOperate.getEiaCertificate())) {
					if (familyOperate.getEiaCertificate().equals("1")) {
						map.put("livestock_eia_certificate", "有");
					} else {
						map.put("livestock_eia_certificate", "无");
					}
				}
				Integer lhist = familyOperate.getLivestockHistoryOperate();
				if (lhist != null) {
					if (lhist == 1) {
						map.put("livestock_history_operate", "盈亏平衡");
					} else if (lhist == 2) {
						map.put("livestock_history_operate", "略有盈利");
					} else if (lhist == 3) {
						map.put("livestock_history_operate", "较大盈利");
					} else if (lhist == 4) {
						map.put("livestock_history_operate", "亏损");
					}
				}
				map.put("livestock_type",
						StringUtils.isEmpty(livestock_type) ? ""
								: livestock_type.subSequence(0,
								livestock_type.length() - 1));
				map.put("livestock_number",
						StringUtils.isEmpty(familyOperate.getLivestockScale()) ? ""
								: familyOperate.getLivestockScale());
				map.put("livestock_value",
						StringUtils.isEmpty(familyOperate
								.getLivestockExpectedValue()) ? ""
								: familyOperate.getLivestockExpectedValue());
				String now_crops_type = "";
				if (StringUtils.isNotEmpty(familyOperate.getBigLandCropType())) {
					String bigLandCropType = familyOperate.getBigLandCropType();
					String[] split = bigLandCropType.split(",");
					for (int i = 0; i < split.length; i++) {
						if (split[i].equals("1")) {
							now_crops_type += "水稻/";
						} else if (split[i].equals("2")) {
							now_crops_type += "玉米/";
						} else if (split[i].equals("3")) {
							now_crops_type += "小麦/";
						} else if (split[i].equals("4")) {
							now_crops_type += "其他/";
						}
					}
				}
				Integer blchist = familyOperate.getBlcHistoryOperate();
				if (blchist != null) {
					if (blchist == 1) {
						map.put("blc_history_operate", "盈亏平衡");
					} else if (blchist == 2) {
						map.put("blc_history_operate", "略有盈利");
					} else if (blchist == 3) {
						map.put("blc_history_operate", "较大盈利");
					} else if (blchist == 4) {
						map.put("blc_history_operate", "亏损");
					}
				}
				map.put("now_crops_investment", StringUtils
						.isEmpty(familyOperate.getBlcInvestment()) ? ""
						: familyOperate.getBlcInvestment());
				map.put("now_crops_years", StringUtils.isEmpty(familyOperate
						.getBlcYears()) ? "0" : familyOperate.getBlcYears());
				map.put("now_crops_scale", StringUtils.isEmpty(familyOperate
						.getBlcScale()) ? "0" : familyOperate.getBlcScale());
				map.put("now_crops_value",
						StringUtils.isEmpty(familyOperate.getBlcExpectedValue()) ? ""
								: familyOperate.getBlcExpectedValue());
				map.put("now_crops_type",
						StringUtils.isEmpty(now_crops_type) ? ""
								: now_crops_type.subSequence(0,
								now_crops_type.length() - 1));
				map.put("harvest_time",
						StringUtils.isEmpty(familyOperate.getBlcHarvestTime()) ? ""
								: familyOperate.getBlcHarvestTime());
				String nong_sell_type = "";
				if (StringUtils.isNotEmpty(familyOperate.getNongSellType())) {
					String nongSellType = familyOperate.getNongSellType();
					String[] split = nongSellType.split(",");
					for (int i = 0; i < split.length; i++) {
						if (split[i].equals("1")) {
							nong_sell_type += "化肥/";
						} else if (split[i].equals("2")) {
							nong_sell_type += "种子/";
						} else if (split[i].equals("3")) {
							nong_sell_type += "饲料/";
						} else if (split[i].equals("4")) {
							nong_sell_type += "农产品/";
						} else if (split[i].equals("5")) {
							nong_sell_type += "其它/";
						}
					}
				}
				Integer nshist = familyOperate.getNsHistoryOperate();
				if (nshist != null) {
					if (nshist == 1) {
						map.put("ns_history_operate", "盈亏平衡");
					} else if (nshist == 2) {
						map.put("ns_history_operate", "略有盈利");
					} else if (nshist == 3) {
						map.put("ns_history_operate", "较大盈利");
					} else if (nshist == 4) {
						map.put("ns_history_operate", "亏损");
					}
				}
				map.put("nong_sell_type",
						StringUtils.isEmpty(nong_sell_type) ? ""
								: nong_sell_type.subSequence(0,
								nong_sell_type.length() - 1));
				map.put("none_nong_type",
						StringUtils.isEmpty(familyOperate.getNonenongType()) ? ""
								: familyOperate.getNonenongType());
				map.put("none_nong_income",
						StringUtils.isEmpty(familyOperate.getNonenongIncome()) ? ""
								: familyOperate.getNonenongIncome());
				map.put("none_nong_years",
						StringUtils.isEmpty(familyOperate.getNonenongYears()) ? ""
								: familyOperate.getNonenongYears());
				if (familyOperate.getSocialSecurity() != null) {
					if (familyOperate.getSocialSecurity() == 1) {
						map.put("social_security", "有");
						map.put("social_security_money",
								StringUtils.isEmpty(familyOperate
										.getSocialSecurityMoney()) ? ""
										: familyOperate
										.getSocialSecurityMoney());
					} else {
						map.put("social_security", "无");
						map.put("social_security_money",
								StringUtils.isEmpty(familyOperate
										.getSocialSecurityMoney()) ? ""
										: familyOperate
										.getSocialSecurityMoney());
					}
				}
				if (familyOperate.getAccumulationFund() != null) {
					if (familyOperate.getAccumulationFund() == 1) {
						map.put("accumulation", "有");
						map.put("accumulation_fund", StringUtils
								.isEmpty(familyOperate
										.getAccumulationFundMoney()) ? ""
								: familyOperate.getAccumulationFundMoney());
					} else {
						map.put("accumulation", "无");
						map.put("accumulation_fund", StringUtils
								.isEmpty(familyOperate
										.getAccumulationFundMoney()) ? ""
								: familyOperate.getAccumulationFundMoney());
					}
				}
				String business_type = "";
				if (StringUtils.isNotEmpty(familyOperate.getOperateType())) {
					String operateType = familyOperate.getOperateType();
					String[] split = operateType.split(",");
					for (int i = 0; i < split.length; i++) {
						if (split[i].equals("1")) {
							business_type += "种植/";
						} else if (split[i].equals("3")) {
							business_type += "养殖/";
						} else if (split[i].equals("4")) {
							map.put("agricultural_sales", "是");
							map.put("sales_years", StringUtils
									.isEmpty(familyOperate.getNsYears()) ? ""
									: familyOperate.getNsYears());
							map.put("sales_revenue", StringUtils
									.isEmpty(familyOperate.getNsScale()) ? ""
									: familyOperate.getNsScale());
							if (familyOperate.getNsSiteType() != null) {
								if (familyOperate.getNsSiteType() == 1) {
									map.put("sales_site_type", "租用");
									map.put("sales_site_fee", StringUtils
											.isEmpty(familyOperate
													.getNsSiteRent()) ? ""
											: familyOperate.getNsSiteRent());
								} else {
									map.put("sales_site_type", "自建");
									map.put("sales_site_fee",
											StringUtils.isEmpty(familyOperate
													.getNsSiteInvestment()) ? ""
													: familyOperate
													.getNsSiteInvestment());
								}
							}

							map.put("sales_revenue", StringUtils
									.isEmpty(familyOperate.getNsScale()) ? ""
									: familyOperate.getNsScale());
							business_type += "农资、农产品经销/";
						} else if (split[i].equals("5")) {
							business_type += "非农职业(含打工)/";
						}
					}
				}
				map.put("business_type",
						StringUtils.isEmpty(business_type) ? ""
								: business_type.subSequence(0,
								business_type.length() - 1));
				map.put("plant_breed_type",
						StringUtils.isEmpty(livestock_type + now_crops_type) ? ""
								: (livestock_type + now_crops_type)
								.subSequence(
										0,
										(livestock_type + now_crops_type)
												.length() - 1));
				map.put("harvest_time",
						StringUtils.isEmpty(familyOperate.getBlcHarvestTime()) ? ""
								: familyOperate.getBlcHarvestTime());
			}

			map.put("fyeartimes", "");
			map.put("overdueTimes", "");
			map.put("overdueCurrent", "");
			map.put("searchOrg", "");
			map.put("searchSel", "");
			if (credit != null) {
				if(credit.getIsWhite()!=null){
					map.put("isWhite", credit.getIsWhite() == 1 ? "是" : "否");
				}
				if(credit.getHasOverdueOutNinetyDay()!=null){
					map.put("hasOverdueOutNinetyDay",
							credit.getHasOverdueOutNinetyDay() == 1 ? "有" : "无");
				}
				if(credit.getNegativeInformation()!=null){
					map.put("negativeInformation",
							credit.getNegativeInformation() == 1 ? "有" : "无");
				}
				map.put("ninetyDayOverdueMoney",
						credit.getNinetyDayOverdueMoney() == null ? ""
								: credit.getNinetyDayOverdueMoney());
				map.put("fyeartimes",
						credit.getLoanTimesWithFiveYear() == null ? ""
								: credit.getLoanTimesWithFiveYear());
				map.put("overdueTimes", credit.getOverdueTimes() == null ? ""
						: credit.getOverdueTimes());
				if(credit.getHasOverdueCurrent()!=null){
					map.put("overdueCurrent",
							credit.getHasOverdueCurrent() == 1 ? "是" : "否");
				}
				map.put("searchOrg",
						credit.getOrgSearchTimesWithCredit() == null ? ""
								: credit.getOrgSearchTimesWithCredit());
				map.put("searchSel",
						credit.getSelSearchTimesWithCredit() == null ? ""
								: credit.getSelSearchTimesWithCredit());
			}
			map.put("relation0", "");
			map.put("name0", "");
			map.put("phone0", "");
			map.put("relation1", "");
			map.put("name1", "");
			map.put("phone1", "");
			map.put("relation2", "");
			map.put("name2", "");
			map.put("phone2", "");
			map.put("relation3", "");
			map.put("name3", "");
			map.put("phone3", "");
			map.put("relation4", "");
			map.put("name4", "");
			map.put("phone4", "");
			if (ocs != null && ocs.size() > 0) {
				for (int i = 0; i < ocs.size(); i++) {
					BusOtherContact oc = ocs.get(i);
					map.put("relation" + i, oc.getRelation());
					map.put("name" + i, oc.getName());
					map.put("phone" + i, oc.getPhone());
				}
			}
			map.put("review_opinion", "");
			if (examine != null) {
				map.put("review_opinion",
						StringUtils.isEmpty(examine.getExamineOpinion()) ? ""
								: examine.getExamineOpinion());
			}
			String pingfen = "";
			if (expspingfen != null && expspingfen.size() > 0) {
				String level = "评分等级：";
				for (int i = 0; i < expspingfen.size(); i++) {
					level += expspingfen.get(i).get("className");
					pingfen += expspingfen.get(i).get("cname") + "："
							+ expspingfen.get(i).get("scoreTotal") + ";";
				}
				level += ";";
				pingfen += level;
			}
			map.put("pingfen", pingfen);
			Map<String, Object> beans = new HashMap<String, Object>();
			beans.put("member", map);
			InputStream is = new BufferedInputStream(new FileInputStream(
					templetFile));
			XLSTransformer transformer = new XLSTransformer();
			Workbook workBook = transformer.transformXLS(is, beans);
			File file = new File(fileConfig.getBaseDir() + "ExportTemp//"
					+ exportMap.get("memberName") + "贷款信息" + ".xls");
			workBook.write(new FileOutputStream(file));
			// Workbook exportExcel = ExcelExportUtil.exportExcel(params, map);
			// exportExcel.write(new FileOutputStream(file));
			HttpHeaders headers = new HttpHeaders();
			String parseGBK = FileUtils.parseGBK(exportMap.get("memberName")
					+ "贷款信息.xls");
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Content-Disposition", "attachment; filename="
					+ parseGBK);
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
			headers.add("Last-Modified", new Date().toString());
			headers.add("ETag", String.valueOf(System.currentTimeMillis()));

			return ResponseEntity
					.ok()
					.headers(headers)
					.contentLength(file.length())
					.contentType(
							MediaType
									.parseMediaType("application/octet-stream"))
					.body(new FileSystemResource(file));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("导出错误");
		}
	}

	@RequestMapping("/examineinfo")
	@ResponseBody
	public ResponseEntity<FileSystemResource> examineInfo(
			HttpServletRequest request, HttpServletResponse response,
			String intopieceId) throws Exception{
		try {
			File templetFile = new File(fileConfig.getBaseDir() + "Export//examineInfo.xlsx");
			Map<String, String> map = new HashMap<String,String>();
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intopieceId);
			SysOrg org = orgService.selectByOrgId(ip.getOrgId());
			BusFins fins = finsService.selectById(ip.getLenderId());
			map.put("org", org.getFullCname());
			map.put("name", ip.getMemberName());
			map.put("address", ip.getAddress());
			map.put("fins", fins.getCname());
			map.put("capital", ip.getCapital().toString());
			map.put("term", ip.getTerm().toString());
			BusFamilyCapital record = new BusFamilyCapital();
			record.setIntoPieceId(ip.getId());
			record.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			BusFamilyCapital fc = familyCapitalService.selectByIntoPieceId(record);
			if(fc == null){
				map.put("land", "");
			}else{
				Float land = new Float(0);
				String land1 = fc.getWaterFarmlandRegistered();
				if(land1 != null){
					land = Float.parseFloat(land1) + land;
				}
				String land2 = fc.getDryFarmlandRegistered();
				if(land2 != null){
					land = Float.parseFloat(land2) + land;
				}
				map.put("land", land.toString());
			}
			map.put("useRemark", ip.getUseRemark() == null ? "" : ip.getUseRemark());
			SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
			map.put("opinion1", ip.getTrialOpinion() == null ? "" : ip.getTrialOpinion());
			map.put("examine_name1", ip.getTrialName() == null ? "" : ip.getTrialName());
			map.put("examine_date1", ip.getTrialDate() == null ? "" : df.format(ip.getTrialDate()));
			List<BusExamine> list = examineService.historyByIpId(ip.getId());
			map.put("opinion2", "");
			map.put("examine_name2", "");
			map.put("examine_date2", "");
			map.put("opinion3", "");
			map.put("examine_name3", "");
			map.put("examine_date3", "");
			map.put("opinion4", "");
			map.put("examine_name4", "");
			map.put("examine_date4", "");
			map.put("opinion5", "");
			map.put("examine_name5", "");
			map.put("examine_date5", "");
			for (int i = 0; i < list.size(); i++) {
				map.put("opinion"+list.get(i).getNode(), list.get(i).getExamineOpinion());
				map.put("examine_name"+list.get(i).getNode(), list.get(i).getExamineName());
				map.put("examine_date"+list.get(i).getNode(), df.format(list.get(i).getExamineDate()));
			}
			InputStream is = new BufferedInputStream(new FileInputStream(templetFile));
			XLSTransformer transformer = new XLSTransformer();
			Workbook workBook = transformer.transformXLS(is, map);
			File file = new File(fileConfig.getBaseDir() + "ExportTemp//"
					+ ip.getMemberName() + "审批意见表.xlsx");
			workBook.write(new FileOutputStream(file));
			HttpHeaders headers = new HttpHeaders();
			String parseGBK = FileUtils.parseGBK("审批意见表.xlsx");
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Content-Disposition", "attachment; filename="
					+ parseGBK);
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
			headers.add("Last-Modified", new Date().toString());
			headers.add("ETag", String.valueOf(System.currentTimeMillis()));

			return ResponseEntity
					.ok()
					.headers(headers)
					.contentLength(file.length())
					.contentType(
							MediaType
									.parseMediaType("application/octet-stream"))
					.body(new FileSystemResource(file));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("导出错误");
		}
	}

	@RequestMapping("/investigationreport")
	@ResponseBody
	public ResponseEntity<FileSystemResource> investigationReport(
			HttpServletRequest request, HttpServletResponse response,
			String intopieceId) throws Exception{
		try {
			File templetFile = new File(fileConfig.getBaseDir() + "Export//investigationreport.xlsx");
			Map<String, String> map = new HashMap<String,String>();
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intopieceId);
			map.put("name", ip.getMemberName());
			String sex = "";
			if(ip.getSex() != null){
				sex = ip.getSex() == 1 ? "男" : "女";
			}
			map.put("sex", sex);
			map.put("age", ip.getAge() == null ? "" : ip.getAge().toString());
			map.put("idCard", ip.getIdCard() == null ? "" : ip.getIdCard());
			map.put("phone", ip.getPhone() == null ? "" : ip.getPhone());
			map.put("address", ip.getAddress() == null ? "" : ip.getAddress());
			if(ip.getEducationLevel() == null){
				map.put("educationLevel", "");
			}else if(ip.getEducationLevel() == 1){
				map.put("educationLevel", "初中以下");
			}else if(ip.getEducationLevel() == 2){
				map.put("educationLevel", "中专/高中");
			}else if(ip.getEducationLevel() == 3){
				map.put("educationLevel", "大专");
			}else{
				map.put("educationLevel", "本科及以上");
			}
			if(ip.getMaritalStatus() == null){
				map.put("maritalStatus", "");
			}else if(ip.getMaritalStatus() == 1){
				map.put("maritalStatus", "已婚");
			}else if(ip.getMaritalStatus() == 2){
				map.put("maritalStatus", "未婚");
			}else if(ip.getMaritalStatus() == 3){
				map.put("maritalStatus", "离异");
			}else if(ip.getMaritalStatus() == 4){
				map.put("maritalStatus", "丧偶");
			}else{
				map.put("maritalStatus", "二婚");
			}
			map.put("capital",  ip.getCapital() == null ? "" : ip.getCapital().toString());
			map.put("term", ip.getTerm() == null ? "" :ip.getTerm().toString());
			map.put("useRemark", ip.getUseRemark() == null ? "" :ip.getUseRemark());
			for (int i = 1; i < 7; i++) {
				map.put("familyName"+i, "");
				map.put("familyAge"+i, "");
				map.put("familyIdCard"+i, "");
				map.put("familyWork"+i, "");
			}
			BusFamilySituation fs = new BusFamilySituation();
			fs.setIntoPieceId(intopieceId);
			fs.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			List<BusFamilySituation> familyList = familySituationService.queryByIntopId(fs);
			if(familyList!=null){
				for (int i = 0; i < familyList.size(); i++) {
					BusFamilySituation family = familyList.get(i);
					if(family.getType()!=null&&family.getType() == 7)
						break;
					map.put("familyName"+family.getType(), family.getName() == null ? "" : family.getName());
					map.put("familyAge"+family.getType(), family.getAge() == null ? "" : family.getAge().toString());
					map.put("familyIdCard"+family.getType(), family.getIdCard() == null ? "" :family.getIdCard());
					map.put("familyWork"+family.getType(), family.getNonfarmComAddress() == null ? "" :family.getNonfarmComAddress());
				}
			}

			BusFamilyCapital record = new BusFamilyCapital();
			record.setIntoPieceId(ip.getId());
			record.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			BusFamilyCapital fc = familyCapitalService.selectByIntoPieceId(record);
			if(fc == null)
				fc = new BusFamilyCapital();

			String houseType = "";
			if(fc.getRentHouse() !=null && fc.getRentHouse() == 1){
				houseType = houseType + " 在外租房";
			}
			if(fc.getCountrysideHouse() != null && fc.getCountrysideHouse() == 1){
				houseType = houseType + " 农村房屋";
			}
			if(fc.getSmallPropertyHouse() != null && fc.getSmallPropertyHouse() == 1){
				houseType = houseType + " 自购小产权房/安置房";
			}
			if(fc.getBigPropertyHouse() != null && fc.getBigPropertyHouse() == 1){
				houseType = houseType + " 自购大产权房/商品房";
			}
			map.put("houseType", houseType);
			map.put("smallPropertyHouseArea", fc.getSmallPropertyHouseArea()==null?"":fc.getSmallPropertyHouseArea());
			map.put("smallPropertyHouseValue", fc.getSmallPropertyHouseValue()==null?"":fc.getSmallPropertyHouseValue());
			map.put("bigPropertyHouseArea", fc.getBigPropertyHouseArea()==null?"":fc.getBigPropertyHouseArea());
			map.put("bigPropertyHouseValue", fc.getBigPropertyHouseValue()==null?"":fc.getBigPropertyHouseValue());

			String car = "";
			if(fc.getCar() !=null && fc.getCar() == 1){
				car = car + " 轿车";
			}
			if(fc.getMinibus() != null && fc.getMinibus() == 1){
				car = car + " 面包车";
			}
			if(fc.getTruck() != null && fc.getTruck() == 1){
				car = car + " 货车";
			}
			if(fc.getNongMachine() !=null && fc.getNongMachine()== 1){
				car = car + " 农机";
			}
			map.put("car", car);
			map.put("carName", fc.getCarName()==null?"":fc.getCarName());
			map.put("carValue", fc.getCarValue()==null?"":fc.getCarValue());
			map.put("minibusValue", fc.getMinibusValue()==null?"":fc.getMinibusValue());
			map.put("truckValue", fc.getTruckValue()==null?"":fc.getTruckValue());
			map.put("nongMachineRemark", fc.getNongMachineRemark()==null?"":fc.getNongMachineRemark());
			map.put("nongMachineEstimatedValue", fc.getNongMachineEstimatedValue()==null?"":fc.getNongMachineEstimatedValue());

			map.put("landCertId", fc.getLandCertId()==null?"":fc.getLandCertId());
			map.put("waterFarmlandRegistered", fc.getWaterFarmlandRegistered()==null?"":fc.getWaterFarmlandRegistered());
			map.put("dryFarmlandRegistered", fc.getDryFarmlandRegistered()==null?"":fc.getDryFarmlandRegistered());
			map.put("waterFarmlandUnregistered", fc.getWaterFarmlandUnregistered()==null?"":fc.getWaterFarmlandUnregistered());
			map.put("dryFarmlandUnregistered", fc.getDryFarmlandUnregistered()==null?"":fc.getDryFarmlandUnregistered());
			map.put("waterFarmlandContract", fc.getWaterFarmlandContract()==null?"":fc.getWaterFarmlandContract());
			map.put("dryFarmlandContract", fc.getDryFarmlandContract()==null?"":fc.getDryFarmlandContract());

			map.put("bankLoanIndebtedness", fc.getBankLoanIndebtedness()==null?"":fc.getBankLoanIndebtedness());
			map.put("p2pPettyLoanIndebtedness", fc.getP2pPettyLoanIndebtedness()==null?"":fc.getP2pPettyLoanIndebtedness());
			map.put("friendLoanIndebtedness", fc.getFriendLoanIndebtedness()==null?"":fc.getFriendLoanIndebtedness());
			map.put("guaranteeLoanIndebtedness", fc.getGuaranteeLoanIndebtedness()==null?"":fc.getGuaranteeLoanIndebtedness());
			if(fc.getMainInvest() == null){
				map.put("mainInvest", "");
			}else if(fc.getMainInvest() == 1){
				map.put("mainInvest", "有");
			}else{
				map.put("mainInvest", "无");
			}

			BusFamilyCredit credit = new BusFamilyCredit();
			credit.setIntoPieceId(intopieceId);
			credit.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			BusFamilyCredit familyCredit = familyCreditService.selectByIpId(credit);
			if(familyCredit == null){
				familyCredit = new BusFamilyCredit();
			}
			if(familyCredit.getIsWhite() == null){
				map.put("isWhite", "");
			}else if(familyCredit.getIsWhite() == 1){
				map.put("isWhite", "是");
			}else{
				map.put("isWhite", "否");
			}
			map.put("loanTimesWithFiveYear", familyCredit.getLoanTimesWithFiveYear() == null ? "" : familyCredit.getLoanTimesWithFiveYear().toString());
			if(familyCredit.getHasOverdueCurrent() == null){
				map.put("hasOverdueCurrent", "");
			}else if(familyCredit.getHasOverdueCurrent() == 1){
				map.put("hasOverdueCurrent", "有");
			}else{
				map.put("hasOverdueCurrent", "无");
			}
			map.put("overdueTimes", familyCredit.getOverdueTimes() == null ? "": familyCredit.getOverdueTimes().toString());
			if(familyCredit.getHasOverdueOutNinetyDay() == null){
				map.put("hasOverdueOutNinetyDay", "");
			}else if(familyCredit.getHasOverdueOutNinetyDay() == 1){
				map.put("hasOverdueOutNinetyDay", "有");
			}else{
				map.put("hasOverdueOutNinetyDay", "无");
			}
			map.put("ninetyDayOverdueMoney", familyCredit.getNinetyDayOverdueMoney() == null ? "" : familyCredit.getNinetyDayOverdueMoney().toString());
			map.put("orgSearchTimesWithCredit", familyCredit.getOrgSearchTimesWithCredit()== null ? "" : familyCredit.getOrgSearchTimesWithCredit().toString());
			map.put("selSearchTimesWithCredit", familyCredit.getSelSearchTimesWithCredit()== null ? "" : familyCredit.getSelSearchTimesWithCredit().toString());
			if(familyCredit.getNegativeInformation() == null){
				map.put("negativeInformation", "");
			}else if(familyCredit.getNegativeInformation() == 1){
				map.put("negativeInformation", "有");
			}else{
				map.put("negativeInformation", "无");
			}
			BusFamilyOperate fo = familyOperateService.selectByIpId(intopieceId);
			if(fo == null)
				fo = new BusFamilyOperate();
			if(fo.getBigLandCropType() == null){
				map.put("bigLandCropType", "");
			}else{
				String bigLandCropType = "";
				if("1".indexOf(fo.getBigLandCropType()) > 0){
					bigLandCropType = bigLandCropType + " 水稻";
				}
				if("2".indexOf(fo.getBigLandCropType()) > 0){
					bigLandCropType = bigLandCropType + " 玉米";
				}
				if("3".indexOf(fo.getBigLandCropType()) > 0){
					bigLandCropType = bigLandCropType + " 小麦";
				}
				if("5".indexOf(fo.getBigLandCropType()) > 0){
					bigLandCropType = bigLandCropType + " 经济作物";
				}
				if("4".indexOf(fo.getBigLandCropType()) > 0){
					bigLandCropType = bigLandCropType + " 其他";
				}
				map.put("bigLandCropType", bigLandCropType);
			}
			map.put("blcScale", fo.getBlcScale() == null ? "" :fo.getBlcScale());
			map.put("blcYears", fo.getBlcYears() == null ? "" :fo.getBlcYears());
			map.put("blcExpectedValue", fo.getBlcExpectedValue() == null ? "" :fo.getBlcExpectedValue());
			map.put("blcHarvestTime", fo.getBlcHarvestTime() == null ? "" :fo.getBlcHarvestTime());
			if(fo.getBlcHistoryOperate() == null){
				map.put("blcHistoryOperate", "");
			}else if(fo.getBlcHistoryOperate() == 1){
				map.put("blcHistoryOperate", "盈亏平衡");
			}else if(fo.getBlcHistoryOperate() == 2){
				map.put("blcHistoryOperate", "略有盈利");
			}else if(fo.getBlcHistoryOperate() == 3){
				map.put("blcHistoryOperate", "较大部分盈利");
			}else{
				map.put("blcHistoryOperate", "略有亏损");
			}

			SysOrg org = orgService.selectByOrgId(ip.getOrgId());
			map.put("operator", org.getLeader()== null ? "" :org.getLeader());
			SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
			map.put("creDate", df.format(ip.getCreDate()));

			InputStream is = new BufferedInputStream(new FileInputStream(templetFile));
			XLSTransformer transformer = new XLSTransformer();
			Workbook workBook = transformer.transformXLS(is, map);
			File file = new File(fileConfig.getBaseDir() + "ExportTemp//"+ UUIDUtil.getUUID() + ".xlsx");
			workBook.write(new FileOutputStream(file));
			HttpHeaders headers = new HttpHeaders();
			String parseGBK = FileUtils.parseGBK("入户调查表.xlsx");
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Content-Disposition", "attachment; filename="
					+ parseGBK);
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
			headers.add("Last-Modified", new Date().toString());
			headers.add("ETag", String.valueOf(System.currentTimeMillis()));

			return ResponseEntity
					.ok()
					.headers(headers)
					.contentLength(file.length())
					.contentType(
							MediaType
									.parseMediaType("application/octet-stream"))
					.body(new FileSystemResource(file));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("导出错误");
		}
	}

	@RequestMapping("/findPro")
	@ResponseBody
	public JSONObject findPro(Model model,HttpServletRequest request,String intoPieceId){
		JSONObject retJson = new JSONObject();
		try {
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
			if(ip.getType()==null||ip.getType().equals(new Integer(Constants.LOAN_LAND)) ){
				retJson.put("appType", ip.getType());
				return retJson;
			}
			List<NjProduct> njproList = njproductService.queryAllNjProduct();
			List<NjProductOrder> njproorderList = njProductOrderService.queryAllOnNjProductOrders();
			JSONArray arr = new JSONArray();
			if(njproList!=null&&njproList.size()>0){
				for (int i = 0; i < njproList.size(); i++) {
					NjProduct njProduct = njproList.get(i);
					JSONObject obj = new JSONObject();
					obj.put("id", njProduct.getId());
					obj.put("productName", njProduct.getProductName());
					obj.put("price", njProduct.getPrice());
					obj.put("type", Constants.PRODUCT);
					arr.add(obj);
				}
			}
			if(njproorderList!=null&&njproorderList.size()>0){
				for (int i = 0; i < njproorderList.size(); i++) {
					NjProductOrder njProductOrder = njproorderList.get(i);
					JSONObject obj = new JSONObject();
					obj.put("id", njProductOrder.getId());
					obj.put("productName", njProductOrder.getOrderName());
					obj.put("price", njProductOrder.getOrderPrice());
					obj.put("type", Constants.ORDER);
					arr.add(obj);
				}
			}
			retJson.put("productList", arr);
			if(StringUtils.isEmpty(ip.getProductListInfo())){
				retJson.put("productmel", new JSONArray());
			}else{
				JSONArray parseArray = JSONArray.parseArray(ip.getProductListInfo());
				retJson.put("productmel", parseArray);
			}
			retJson.put("appType", ip.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retJson;

	}
	
	@RequestMapping("/findProDetail")
	@ResponseBody
	public JSONObject findProDetail(String id,String productType){
		JSONObject retJson = new JSONObject();
		try {
			//商品
			if(Constants.PRODUCT.equals(productType)){
				NjProduct njpro = njproductService.queryNjProductByPrimaryKey(id);
				retJson.put("msg", njpro.getProductDesc());
				retJson.put("code", 200);
			}else{
				NjProductOrder njproorder = njProductOrderService.queryProductOrderByPK(id);
				retJson.put("msg", njproorder.getOrderDesc());
				retJson.put("code", 200);
			}
			return retJson;
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("msg", "未查询到详情");
			retJson.put("code", 300);
		}
		return retJson;
		
	}

	@RequestMapping("/findByIpId")
	@ResponseBody
	public Map<String, Object> findByIpId(String intoPieceId,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
			map.put("result", productService.selectListByFinsId(ip.getLenderId()));
			map.put("proId", ip.getProduct());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", null);
			map.put("proId", null);
		}
		return map;
	}

	@RequestMapping("/caculateNongZi")
	@ResponseBody
	public Map<String, Object> caculateNongZi(String capital,HttpServletRequest request,HttpServletResponse response, String list) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			JSONArray array = JSONArray.parseArray(list);
			BigDecimal Capital = new BigDecimal(capital);
			BigDecimal recieveNongZi = new BigDecimal("0");
			BigDecimal recieveCash = new BigDecimal("0");
			for (int i = 0; i < array.size(); i++) {
				JSONObject proorder = array.getJSONObject(i);
				String productId = proorder.getString("productId");
				String productNum = proorder.getString("productNum");
				NjProduct product = njproductService.queryNjProductByPrimaryKey(productId);
				if(product==null){
					NjProductOrder productOrder =njProductOrderService.queryProductOrderByPK(productId);
					recieveNongZi=recieveNongZi.add(new BigDecimal(productOrder.getOrderPrice()).multiply(new BigDecimal(productNum)));
				}else{
					recieveNongZi=recieveNongZi.add(new BigDecimal(product.getPrice()).multiply(new BigDecimal(productNum)));
				}
			}
			recieveCash=Capital.subtract(recieveNongZi);
			map.put("recieveNongZi", recieveNongZi);
			map.put("recieveCash", recieveCash);
			map.put("code", 200);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "系统异常");
			map.put("code", 400);
		}
		return map;
	}

	@RequestMapping("/applyDetailSave")
	@ResponseBody
	public Map<String, Object> applyDetailSave(BusIntoPiece intoPiece,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPiece.getId());
			ip.setCapital(intoPiece.getCapital());
			ip.setProduct(intoPiece.getProduct());
			if(intoPiece.getType()!=null){
				if(intoPiece.getType().equals(new Integer(Constants.LOAN_LAND_PRODUCT))||intoPiece.getType().equals(new Integer(Constants.LOAN_LAND_PRODUCT_GRAIN)) ){
					JSONArray array = JSONArray.parseArray(intoPiece.getProductListInfo());
					BigDecimal recieveNongZi = new BigDecimal("0");
					BigDecimal nongzisettle = new BigDecimal("0");
					for (int i = 0; i < array.size(); i++) {
						JSONObject proorder = array.getJSONObject(i);
						String productTime = proorder.getString("productTime");
						if(StringUtils.isNotEmpty(productTime)){
							Date date = DateUtils.parse(productTime, "yyyy-MM-dd");
							if(date.before(new Date())){
								map.put("msg", "预计使用时间不能在今天之前！");
								map.put("code", 400);
								return map;
							}
						}
						String productId = proorder.getString("productId");
						String productNum = proorder.getString("productNum");
						NjProduct product = njproductService.queryNjProductByPrimaryKey(productId);
						if(product==null){
							NjProductOrder productOrder =njProductOrderService.queryProductOrderByPK(productId);
							recieveNongZi=recieveNongZi.add(new BigDecimal(productOrder.getOrderPrice()).multiply(new BigDecimal(productNum)));
							nongzisettle=nongzisettle.add(new BigDecimal(productOrder.getOrderSettleprice()).multiply(new BigDecimal(productNum)));
						}else{
							recieveNongZi=recieveNongZi.add(new BigDecimal(product.getPrice()).multiply(new BigDecimal(productNum)));
							nongzisettle=nongzisettle.add(new BigDecimal(product.getSettlePrice()).multiply(new BigDecimal(productNum)));
						}

					}
					int compareNongZi = recieveNongZi.compareTo(new BigDecimal(intoPiece.getRecieveNongZi()));
					if(compareNongZi!=0){
						map.put("msg", "计算错误，请重新提交");
						map.put("code", 400);
						return map;
					}else{
						ip.setProductPrice(recieveNongZi.toString());
						ip.setProductSettlePrice(nongzisettle.toString());
						ip.setProductListInfo(intoPiece.getProductListInfo());
						ip.setRecieveCash(intoPiece.getRecieveCash());
						ip.setRecieveNongZi(intoPiece.getRecieveNongZi());
					}
				}
			}
			intoPieceService.updateByPrimaryKey(ip);
			map.put("msg", "保存成功");
			map.put("code", 200);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "系统异常");
			map.put("code", 400);
		}
		return map;
	}

	@RequestMapping("/getsubmittedInfo")
	@ResponseBody
	public Map<String, Object> getsubmittedInfo(String intoPieceId,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);
//			AppApplication app = applicationService.selectByModelId(ip.getModelId());
//			BusFamilyCapital fc = new BusFamilyCapital();
//			fc.setIntoPieceId(intoPieceId);
//			fc.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
//			BusFamilyCapital familyCapital = familyCapitalService.selectByIntoPieceId(fc);
//			String expressionString = app.getShortEname();
//			String creditCapital ="";
//			if(familyCapital==null){
//				familyCapital=new BusFamilyCapital();
//			}
//			if(StringUtils.isNoneEmpty(expressionString)&&expressionString.contains("=") ){
//				Integer term = ip.getTerm();
//				if(term%12!=0){
//					map.put("msg", "贷款期限不是整年");
//					map.put("code", 400);
//					return map;
//				}
//				Integer year =  term/12;
//				String[] split = expressionString.split("=");
//				expressionString=split[1];
//				expressionString = expressionString.replaceAll("DL", StringUtils.isEmpty(familyCapital.getDryFarmlandRegistered()) ?"0":familyCapital.getDryFarmlandRegistered());
//				expressionString = expressionString.replaceAll("WL", StringUtils.isEmpty(familyCapital.getWaterFarmlandRegistered())?"0":familyCapital.getWaterFarmlandRegistered());
//				expressionString = expressionString.replaceAll("T", year.toString());
//				JexlEngine je = new JexlBuilder().create();
//				JexlExpression createExpression = je.createExpression(expressionString);
//				Object creditCapital1 =  createExpression.evaluate(null);
//				creditCapital=creditCapital1.toString();
//				BigDecimal bd = new BigDecimal(creditCapital);
//				bd=bd.divide(new BigDecimal("1000"), 0,BigDecimal.ROUND_DOWN).multiply(new BigDecimal("1000"));
//				creditCapital=bd.toString();
//				if(bd.compareTo(new BigDecimal(50000))==1){
//					creditCapital="50000";
//				}
//			}else{
//				map.put("msg", "授信额度计算公式有误");
//				map.put("code", 400);
//				return map;
//			}
//			map.put("creditCapital", creditCapital);
			map.put("capital", ip.getCapital());
			map.put("term", ip.getTerm());
			map.put("recieveNongZi", ip.getRecieveNongZi());
			map.put("recieveCash", ip.getRecieveCash());
			map.put("code", 200);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "系统异常");
			map.put("code", 400);
		}
		return map;
	}

	@RequestMapping("/intopieceexport")
	@ResponseBody
	public ResponseEntity<FileSystemResource> intopieceExport(
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		IntoPieceMap ip = new IntoPieceMap();
		String orgName = request.getParameter("orgName");
		if (StrUtils.isNotEmpty(orgName))
			ip.setFullCname(orgName);
		String memberName = request.getParameter("memberName");
		if (StrUtils.isNotEmpty(memberName))
			ip.setMemberName(memberName);
		String idCard = request.getParameter("idCard");
		if (StrUtils.isNotEmpty(idCard))
			ip.setIdCard(idCard);
		String phone = request.getParameter("phone");
		if (StrUtils.isNotEmpty(phone))
			ip.setPhone(phone);
		String status = request.getParameter("status");
		if (StrUtils.isNotEmpty(status))
			ip.setStatus(status);
		String use = request.getParameter("use");
		if(StrUtils.isNotEmpty(use))
			ip.setUse(Integer.parseInt(use));
		String capital = request.getParameter("capital");
		if(StrUtils.isNotEmpty(capital))
			ip.setCapital(new BigDecimal(capital));
		String appName = request.getParameter("appName");
		if(StrUtils.isNotEmpty(appName))
			ip.setAppName(appName);
		String startDate = request.getParameter("startDate");
		if (StrUtils.isNotEmpty(startDate))
			ip.setStartDate(startDate);
		String endDate = request.getParameter("endDate");
		if (StrUtils.isNotEmpty(endDate))
			ip.setEndDate(endDate + " 23:59:59");
		ip.setPersonId(request.getSession()
				.getAttribute(Constants.SESSION_PERSONCD).toString());
		ip.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
		try {
			List<IntoPieceMap> list = intoPieceService.selectByCondition(ip);

			List<BusFins> finsList = finsService.queryAll();
			Map<String,String> finsMap = new HashMap<String,String>();
			for (int i = 0; i < finsList.size(); i++) {
				finsMap.put(finsList.get(i).getFinsId(), finsList.get(i).getCname());
			}

			Map<String,String> paraMap = new HashMap<String,String>();
			SysParaGroup paraGroup = paraGroupService.selectByName("INTOPIECE_STATUS");
			if(paraGroup!=null){
				List<SysPara> paraList = paraService.selectListByPGroupId(paraGroup.getPgroupId());
				for (int i = 0; i < paraList.size(); i++) {
					paraMap.put(paraList.get(i).getValue(), paraList.get(i).getDescr());
				}
			}

			List<ExcelExportEntity> colList = new ArrayList<ExcelExportEntity>();
			ExcelExportEntity colEntity = new ExcelExportEntity("序号","no");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(5);
			colList.add(colEntity);

			colEntity = new ExcelExportEntity("部门", "orgName");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(15);
			colList.add(colEntity);

			colEntity = new ExcelExportEntity("客户类型", "use");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(10);
			colList.add(colEntity);

			colEntity = new ExcelExportEntity("贷户姓名", "name");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(10);
			colList.add(colEntity);

			colEntity = new ExcelExportEntity("身份证号", "idCard");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(20);
			colList.add(colEntity);

			colEntity = new ExcelExportEntity("手机号", "phone");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(15);
			colList.add(colEntity);

			colEntity = new ExcelExportEntity("家庭住址", "address");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(30);
			colList.add(colEntity);

			colEntity = new ExcelExportEntity("申请金额", "capital");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(10);
			colList.add(colEntity);

			colEntity = new ExcelExportEntity("申请日期", "creDate");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(20);
			colList.add(colEntity);

			colEntity = new ExcelExportEntity("放款机构（银行）", "fins");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(30);
			colList.add(colEntity);

			colEntity = new ExcelExportEntity("操作人", "operName");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(10);
			colList.add(colEntity);

			colEntity = new ExcelExportEntity("状态", "status");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(10);
			colList.add(colEntity);

			List<Map<String, Object>> listexp = new ArrayList<Map<String, Object>>();
			if(list.size()>0){
				int no = 1;
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for (int i = 0; i < list.size(); i++) {
					IntoPieceMap model = list.get(i);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("no", no);
					map.put("orgName", model.getFullCname());
					if(model.getUse()==null){
						map.put("use", "-");
					}else if(model.getUse()==1){
						map.put("use", "新增");
					}else if(model.getUse()==2){
						map.put("use", "转贷");
					}else{
						map.put("use", "-");
					}
					map.put("name", model.getMemberName());
					map.put("idCard", model.getIdCard());
					map.put("phone", model.getPhone());
					map.put("address", model.getAddress());
					map.put("capital", model.getCapital());
					map.put("creDate", df.format(model.getCreDate()));
					map.put("fins", finsMap.get(model.getFinsId()));
					map.put("operName", model.getOperName());
					map.put("status", paraMap.get(model.getStatus()));
					listexp.add(map);
					no ++ ;
				}
			}
			Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), colList,listexp);
			String baseurl = fileConfig.getBaseDir();
			baseurl = baseurl + "xlsFile\\download\\";
			FileUtils.createDirectory(baseurl);
			File file = new File(baseurl, UUIDUtil.getUUID() + ".xls");
			workbook.write(new FileOutputStream(file));
			HttpHeaders headers = new HttpHeaders();
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			String parseGBK = FileUtils.parseGBK("进件信息.xls");
			headers.add("Content-Disposition", "attachment; filename="
					+ parseGBK);
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
			headers.add("Last-Modified", new Date().toString());
			headers.add("ETag", String.valueOf(System.currentTimeMillis()));
			return ResponseEntity
					.ok()
					.headers(headers)
					.contentLength(file.length())
					.contentType(
							MediaType
									.parseMediaType("application/octet-stream"))
					.body(new FileSystemResource(file));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("导出错误");
		}
	}

	@RequestMapping("/abandon")
	@ResponseBody
	public JSONObject abandon(HttpServletRequest request ,String intoPieceId) throws Exception {
		JSONObject json = new JSONObject();
		try {
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(intoPieceId);

			AppEntry entry = appEntryService.queryByAppModeId(intoPieceId);
			JSONObject node = flowMgrImpl.abandon(entry);
			if(node != null && node.getIntValue("code") == 200){
				JSONArray arr = node.getJSONArray("nodes");
				if(arr != null){
					String nextNodeId = arr.getJSONObject(0).getString("nodeId");
					BusinessObject business = new BusinessObject(entry,ip,applicationService.selectByModelId(ip.getModelId()));
					flowMgrImpl.changeNode(business, nextNodeId, request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
				}else{
					json.put("msg", "未配置流程节点");
					json.put("errono", 3000);
					return json;
				}
			}else{
				json.put("msg", "未配置流程节点");
				json.put("errono", 3000);
				return json;
			}
			boolean flag = true;
			//先锋
			if(ip.getServiceFeeWay()!=null&&ip.getServiceFeeWay().equals(1)){
				JSONObject node1 = flowMgrImpl.getNextTask(entry);
				if(node1 != null && node1.getIntValue("code") == 200){
					JSONArray arr = node1.getJSONArray("nodes");
					if(arr != null){
						String nextNodeId = arr.getJSONObject(0).getString("nodeId");
						String pcId = node1.getString("pcId");
						BusinessObject business = new BusinessObject(entry,ip,applicationService.selectByModelId(ip.getModelId()));
						flowMgrImpl.next(business, nextNodeId, pcId, request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
					}
				}
				//微信
			}else if(ip.getServiceFeeWay()!=null&&ip.getServiceFeeWay().equals(2)){
				BusGuaranteeFeeInfo info = guaranteeFeeInfoService.selectByIntopieceId(intoPieceId);
				ApiWXGuaranteeFee wxGuaranteeFee = new ApiWXGuaranteeFee();
				wxGuaranteeFee.setIntoPieceId(intoPieceId);
				wxGuaranteeFee.setStatus("S");
				wxGuaranteeFee= wxGuaranteefeeService.selectByIpIdAndStatus(wxGuaranteeFee);
				//成功通过微信支付付款
				if(info!=null&&info.getPayWay().equals("2") &&info.getStatus().equals("S")){
					flag=false;
					BusRefund refund = refundService.selectByIntoPieceId(intoPieceId);
					if(refund==null){
						refund=new BusRefund();
						refund.setId(UUIDUtil.getUUID());
						refund.setOrgId(info.getOrgId());
						refund.setOrgName(info.getOrgName());
						refund.setIntoPieceId(intoPieceId);
						refund.setUse(info.getUse());
						refund.setAccountName(info.getAccountName());
						refund.setCertificateNo(info.getCertificateNo());
						//贷款总金额
						refund.setAccountNo(info.getAccountNo());
						refund.setMobileNo(info.getMobileNo());
						refund.setTotalAmount(info.getTotalAmount());
						refund.setPayer(info.getPayer());
						refund.setPayerIdcard(info.getPayerIdcard());
						refund.setPayWay(info.getPayWay());
						refund.setStatus("P");
						refund.setCreateBy((String)request.getSession().getAttribute(Constants.SESSION_PERSONCD));
						refund.setCreateDate(DateUtils.getNowDate());
						//服务费
						refund.setRefundType(1);
						refund.setPaymentMerid(wxGuaranteeFee.getMerchantNo());
						refundService.insert(refund);
					}
				}else{
					if(info!=null&&info.getPayWay().equals("2") &&info.getStatus().equals("I")){
						BusMessageReminder reminder = new BusMessageReminder();
						reminder.setType(Constants.WEI_XIN_SERVICE_PAY);
						reminder.setIntoPieceId(intoPieceId);
						reminder.setIsDelete(Constants.COMMON_ISNOT_DELETE);
						BusMessageReminder m = messageReminderService.queryMRByTypeAndDelete(reminder);
						if(m!=null){
							m.setIsDelete(new Short("1"));
							messageReminderService.updateByPrimaryKey(m);
						}
					}
					info.setStatus("GL");
					guaranteeFeeInfoService.updateByPrimaryKey(info);
					JSONObject node1 = flowMgrImpl.getNextTask(entry);
					if(node1 != null && node1.getIntValue("code") == 200){
						JSONArray arr = node1.getJSONArray("nodes");
						if(arr != null){
							String nextNodeId = arr.getJSONObject(0).getString("nodeId");
							String pcId = node1.getString("pcId");
							BusinessObject business = new BusinessObject(entry,ip,applicationService.selectByModelId(ip.getModelId()));
							flowMgrImpl.next(business, nextNodeId, pcId, request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
						}
					}
				}
				//线下或暂不支付
			}else{
				JSONObject node1 = flowMgrImpl.getNextTask(entry);
				if(node1 != null && node1.getIntValue("code") == 200){
					JSONArray arr = node1.getJSONArray("nodes");
					if(arr != null){
						String nextNodeId = arr.getJSONObject(0).getString("nodeId");
						String pcId = node1.getString("pcId");
						BusinessObject business = new BusinessObject(entry,ip,applicationService.selectByModelId(ip.getModelId()));
						flowMgrImpl.next(business, nextNodeId, pcId, request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
					}else{
						json.put("msg", "未配置流程节点");
						json.put("errono", 3000);
						return json;
					}
				}else{
					json.put("msg", "未配置流程节点");
					json.put("errono", 3000);
					return json;
				}
			}
			if(flag){
				List<BusGuaranteeReverse> list = guaranteeReverseService.selectByIntoPieceId(intoPieceId);
				if(list!=null&&list.size()>0){
					for (int i = 0; i < list.size(); i++) {
						BusGuaranteeReverse guaranteeReverse = list.get(i);
						if("S".equals(guaranteeReverse.getStatus()) ){
							//银行卡支付退款
							if("1".equals(guaranteeReverse.getPayWay()) ){

								//微信支付退款
							}else if("2".equals(guaranteeReverse.getPayWay()) ){
								ApiWXGuaranteeFee wxGuaranteeFee = new ApiWXGuaranteeFee();
								wxGuaranteeFee.setIntoPieceId(intoPieceId);
								wxGuaranteeFee.setStatus("S");
								wxGuaranteeFee= wxGuaranteefeeService.selectByIpIdAndStatus(wxGuaranteeFee);
								String url = pieceConfig.getWxRefundCheck();
								SysWebUser user = userService.selectUserByUserName(pieceConfig.getUserName());
								String signature = user.getUserId() + user.getUsername() + user.getPassword();
								Map<String, String> map = new HashMap<String, String>();
								map.put("userid", user.getUserId());
								map.put("signature", DigestUtils.md5Hex(signature));
								map.put("companyType", "HLJSX");
								map.put("amount", guaranteeReverse.getTotalAmount().toString());
//								map.put("certificateNo", person.getCardNo());
								map.put("certificateNo", guaranteeReverse.getPayerIdcard());
								map.put("accountName", guaranteeReverse.getPayer());
								map.put("mobileNo", guaranteeReverse.getPayerMobile());
								map.put("intoPieceId", intoPieceId);
								map.put("refundId", guaranteeReverse.getId());
								map.put("merchantNo", wxGuaranteeFee.getMerchantNo());
								System.err.println(map);
								String retMap = HttpClientUtil.doPost(url, map,
										"utf-8");
								System.err.println("退款申请返回数据"+retMap);
								if(retMap!=null){
									JSONObject retMap1 = JSONObject.parseObject(retMap);
									if(retMap1==null||StringUtils.isEmpty(retMap1.getString("isSuccess"))||retMap1.getString("isSuccess").equals(Constants.GATE_RESULT_FAIL)){
										json.put("msg", "微信申请退款失败");
										json.put("errono", 3000);
										return json;
									}
									if(!StringUtils.isEmpty(retMap1.getString("refundId"))&&!StringUtils.isEmpty(retMap1.getString("status"))){
										if(retMap1.getString("refundId").equals(guaranteeReverse.getId())){
											guaranteeReverse.setStatus("R"+retMap1.getString("status"));
											guaranteeReverseService.updateByPrimaryKey(guaranteeReverse);
										}else{
											json.put("msg", "微信退款标识错误");
											json.put("errono", 3000);
											return json;
										}
									}else{
										json.put("msg", "微信申请退款失败");
										json.put("errono", 3000);
										return json;
									}
								}else{
									json.put("msg", "未接收到微信回应");
									json.put("errono", 3000);
									return json;
								}
							}else if(guaranteeReverse.getPayWay().equals("3")){
								guaranteeReverse.setStatus("RS");
								guaranteeReverse.setUpdateDate(DateUtils.getNowDate());
								guaranteeReverseService.updateByPrimaryKey(guaranteeReverse);
								//暂不支付
							}else if(guaranteeReverse.getPayWay().equals("4")){
								guaranteeReverse.setStatus("RS");
								guaranteeReverse.setUpdateDate(DateUtils.getNowDate());
								guaranteeReverseService.updateByPrimaryKey(guaranteeReverse);
							}
						}else{
							BusMessageReminder reminder = new BusMessageReminder();
							reminder.setIntoPieceId(guaranteeReverse.getIntoPieceId());
							reminder.setIsDelete(Constants.COMMON_ISNOT_DELETE);
							//客户
							if(guaranteeReverse.getType().equals(1)){
								reminder.setType(Constants.MGUATANTEE_REVERSE);
								//站长
							}else{
								reminder.setType(Constants.UGUATANTEE_REVERSE);
							}
							BusMessageReminder message = messageReminderService.queryMRByTypeAndDelete(reminder);
							if(message!=null){
								message.setIsDelete(Constants.COMMON_IS_DELETE);
								messageReminderService.updateByPrimaryKey(message);
							}
						}

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			json.put("msg", "申请失败");
			json.put("errono", 3000);
			return json;
		}
		json.put("msg", "保存成功");
		json.put("errono", 2000);
		return json;
	}
}
