package com.nongyeos.loan.admin.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.entity.vo.BaseEntityTypeConstants;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.ApiWXGuaranteeFee;
import com.nongyeos.loan.admin.entity.BusGuaranteeFeeInfo;
import com.nongyeos.loan.admin.entity.BusGuaranteeReverse;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusMessageReminder;
import com.nongyeos.loan.admin.entity.BusRefund;
import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.mapper.BusMessageReminderMapper;
import com.nongyeos.loan.admin.model.FileConfig;
import com.nongyeos.loan.admin.model.IntoPieceConfig;
import com.nongyeos.loan.admin.service.ApiWxGuaranteefeeService;
import com.nongyeos.loan.admin.service.IGuaranteeFeeInfoService;
import com.nongyeos.loan.admin.service.IGuaranteeReverseService;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.admin.service.IPersonService;
import com.nongyeos.loan.admin.service.IRefundService;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.service.IAppEntryService;
import com.nongyeos.loan.app.service.IApplicationService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.FileUtils;
import com.nongyeos.loan.base.util.HttpClientUtil;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;
import com.nongyeos.loan.core.model.BusinessObject;
import com.nongyeos.loan.core.service.FlowMgr;

@Controller
@RequestMapping("/refund")
public class RefundController {
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(RefundController.class);
	
	@Autowired
	private IRefundService refundService;
	
	@Autowired
	private IAppEntryService entryService;
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@Autowired
	private FlowMgr flowMgrImpl;

	@Autowired
	private IApplicationService applicationService;
	
	@Autowired
	private IWebUserService userService;
	@Autowired
	private IntoPieceConfig pieceConfig;
	@Autowired
	private IPersonService personService;
	@Autowired
	private IGuaranteeFeeInfoService guaranteeFeeInfoService;
	
	@Autowired
	private IGuaranteeReverseService guaranteeReverseService;
	
	@Autowired
	private ApiWxGuaranteefeeService wxGuaranteefeeService;
	
	@Autowired
	private FileConfig fileConfig;
	
	@Autowired
	private BusMessageReminderMapper messageReminderMapper;
	
	@RequestMapping("/refunds")
	public String payments(){
		return "intoPiece/refundlist";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public PageBeanUtil<BusRefund> list(int currentPage, int pageSize,
			String orgName, String memberName, String idCard, String phone,
			String status, String startDate, String endDate,HttpServletRequest request)throws Exception{
		String personId = (String) request.getSession().getAttribute(Constants.SESSION_PERSONCD);
		try {
				BusRefund refund = new BusRefund();
				refund.setOrgName(StringUtils.isEmpty(orgName)?null:orgName);
				refund.setAccountName(StringUtils.isEmpty(memberName)?null:memberName);
				refund.setCertificateNo(StringUtils.isEmpty(idCard)?null:idCard);
				refund.setMobileNo(StringUtils.isEmpty(phone)?null:phone);
				refund.setStatus(StringUtils.isEmpty(status)?null:status);
				refund.setCreateBy(personId);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("refund", refund);
				if(StrUtils.isNotEmpty(startDate))
					map.put("startDate", startDate);
				if(StrUtils.isNotEmpty(endDate))
					map.put("endDate", endDate+" 23:59:59");
				return refundService.selectByPage(personId,map,currentPage,pageSize);
			} catch (Exception e) {
				e.printStackTrace();
				return new PageBeanUtil<BusRefund>();
			}
	}
	
	
	@RequestMapping("/approveAbandon")
	@ResponseBody
	public JSONObject approveAbandon(HttpServletRequest request,String id,String option)throws Exception{
		JSONObject retJson = new JSONObject();
		if(StringUtils.isEmpty(id)){
			retJson.put("errono", 3000);
			retJson.put("msg", "退款标识为空");
			return retJson;
		}
		try {
			
			BusRefund refund = refundService.selectByPrimaryKey(id);
			if(!StringUtils.isEmpty(id)){
				BusIntoPiece ip = intoPieceService.selectByPrimaryKey(refund.getIntoPieceId());
				BusGuaranteeFeeInfo info = guaranteeFeeInfoService.selectByIntopieceId(refund.getIntoPieceId());
				if(refund!=null&&refund.getStatus()!=null&&refund.getStatus().equals("P")){
					//同意弃贷
					if(option.equals("1")){
						String channel = ip.getChannel();
						if(StringUtils.isEmpty(channel)){
							retJson.put("msg", "版本错误");
							retJson.put("errono", 3000);
							return retJson;
						}
						//微信支付服务费退款url
						String url = pieceConfig.getWxRefundCheck();
						SysWebUser user = userService.selectUserByUserName(pieceConfig.getUserName());
						SysPerson person = personService.selectByPersonId(ip.getOperUserId());
						Map<String, String> map = new HashMap<String, String>();
						map.put("userid", user.getUserId());
						String signature = user.getUserId() + user.getUsername() + user.getPassword();
						map.put("signature", DigestUtils.md5Hex(signature));
						map.put("companyType", channel);
						map.put("amount", refund.getTotalAmount().toString());
						map.put("totalAmount", refund.getTotalAmount().toString());
//						map.put("certificateNo", person.getCardNo());
						map.put("certificateNo", person.getCardNo());
						map.put("accountName", person.getNameCn());
						map.put("mobileNo", person.getMobile());
						map.put("intoPieceId", ip.getId());
						map.put("refundId", refund.getId());
						map.put("merchantNo", refund.getPaymentMerid());
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
								if(retMap1.getString("refundId").equals(refund.getId())){
									refund.setStatus(retMap1.getString("status"));
									refundService.updateByPrimaryKey(refund);
									info.setStatus("R"+retMap1.getString("status"));
									guaranteeFeeInfoService.updateByPrimaryKey(info);
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
			}
			
					
					List<BusGuaranteeReverse> list = guaranteeReverseService.selectByIntoPieceId(refund.getIntoPieceId());
					if(list!=null&&list.size()>0){
						for (int i = 0; i < list.size(); i++) {
							BusGuaranteeReverse guaranteeReverse = list.get(i);
							if("S".equals(guaranteeReverse.getStatus()) ){
								//银行卡支付退款
								if("1".equals(guaranteeReverse.getPayWay()) ){
									
									//微信支付退款
								}else if("2".equals(guaranteeReverse.getPayWay()) ){
									ApiWXGuaranteeFee wxGuaranteeFee = new ApiWXGuaranteeFee();
									wxGuaranteeFee.setIntoPieceId(refund.getIntoPieceId());
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
//									map.put("certificateNo", person.getCardNo());
									map.put("certificateNo", guaranteeReverse.getPayerIdcard());
									map.put("accountName", guaranteeReverse.getPayer());
									map.put("mobileNo", guaranteeReverse.getPayerMobile());
									map.put("intoPieceId", refund.getIntoPieceId());
									map.put("refundId", guaranteeReverse.getId());
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
											if(retMap1.getString("refundId").equals(guaranteeReverse.getId())){
												guaranteeReverse.setStatus("R"+retMap1.getString("status"));
												guaranteeReverseService.updateByPrimaryKey(guaranteeReverse);
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
								BusMessageReminder message = messageReminderMapper.queryMRByTypeAndDelete(reminder);
								if(message!=null){
									message.setIsDelete(Constants.COMMON_IS_DELETE);
									messageReminderMapper.updateByPrimaryKey(reminder);
								}
							}
							
						}
					}
					
					//不同意
				}else{
					AppEntry entry = entryService.queryByAppModeId(refund.getIntoPieceId());
					String nextNodeId =entry.getRecordId();
					BusinessObject business = new BusinessObject(entry,ip,applicationService.selectByModelId(ip.getModelId()));
					flowMgrImpl.next(business, nextNodeId, null, request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
					refundService.deleteByPrimaryKey(id);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("errono", 3000);
			retJson.put("msg", "网络异常");
			return retJson;
		}
		retJson.put("errono", 2000);
		retJson.put("msg", "操作成功");
		return retJson;
	}
	
	@RequestMapping("/refundAgain")
	@ResponseBody
	public JSONObject refundAgain(HttpServletRequest request,String id)throws Exception{
		JSONObject retJson = new JSONObject();
		if(StringUtils.isEmpty(id)){
			retJson.put("errono", 3000);
			retJson.put("msg", "退款标识为空");
			return retJson;
		}
		try {
			BusRefund refund = refundService.selectByPrimaryKey(id);
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(refund.getIntoPieceId());
			BusGuaranteeFeeInfo info = guaranteeFeeInfoService.selectByIntopieceId(refund.getIntoPieceId());
		//同意弃贷
			String channel = ip.getChannel();
			if(StringUtils.isEmpty(channel)){
				retJson.put("msg", "版本错误");
				retJson.put("errono", 3000);
				return retJson;
			}
			//微信支付服务费退款url
			String url = pieceConfig.getWxRefundCheck();
			SysWebUser user = userService.selectUserByUserName(pieceConfig.getUserName());
			SysPerson person = personService.selectByPersonId(ip.getOperUserId());
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", user.getUserId());
			String signature = user.getUserId() + user.getUsername() + user.getPassword();
			map.put("signature", DigestUtils.md5Hex(signature));
			map.put("companyType", channel);
			map.put("amount", refund.getTotalAmount().toString());
//					map.put("certificateNo", person.getCardNo());
			map.put("certificateNo", person.getCardNo());
			map.put("accountName", person.getNameCn());
			map.put("mobileNo", person.getMobile());
			map.put("intoPieceId", ip.getId());
			map.put("refundId", refund.getId());
			map.put("merchantNo", refund.getPaymentMerid());
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
					if(retMap1.getString("refundId").equals(refund.getId())){
						refund.setStatus(retMap1.getString("status"));
						refundService.updateByPrimaryKey(refund);
						info.setStatus("R"+retMap1.getString("status"));
						guaranteeFeeInfoService.updateByPrimaryKey(info);
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
					//不同意
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("errono", 3000);
			retJson.put("msg", "网络异常");
			return retJson;
		}
		retJson.put("errono", 2000);
		retJson.put("msg", "操作成功");
		return retJson;
	}
	
	@RequestMapping("/refundexport")
    @ResponseBody
	public ResponseEntity<FileSystemResource> refundexport(
			HttpServletRequest request, HttpServletResponse response)  throws Exception{
		try {
			String orgName = request.getParameter("orgName");
			String memberName = request.getParameter("memberName");
			String idCard = request.getParameter("idCard");
			String phone = request.getParameter("phone");
			String status = request.getParameter("status");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String personId = (String) request.getSession().getAttribute(Constants.SESSION_PERSONCD);
			BusRefund refund = new BusRefund();
			refund.setOrgName(StringUtils.isEmpty(orgName)?null:orgName);
			refund.setAccountName(StringUtils.isEmpty(memberName)?null:memberName);
			refund.setCertificateNo(StringUtils.isEmpty(idCard)?null:idCard);
			refund.setMobileNo(StringUtils.isEmpty(phone)?null:phone);
			refund.setStatus(StringUtils.isEmpty(status)?null:status);
			refund.setCreateBy(personId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("refund", refund);
			if(StrUtils.isNotEmpty(startDate))
				map.put("startDate", startDate);
			if(StrUtils.isNotEmpty(endDate))
				map.put("endDate", endDate+" 23:59:59");
			
			List<BusRefund> list = refundService.queryList(personId, map);
			
			List<ExcelExportEntity> colList = new ArrayList<ExcelExportEntity>();
			ExcelExportEntity colEntity = new ExcelExportEntity("序号","no");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(10);
			colList.add(colEntity);

			colEntity = new ExcelExportEntity("部门", "orgName");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(30);
			colList.add(colEntity);
			
			colEntity = new ExcelExportEntity("申请时间", "creDate");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(20);
			colList.add(colEntity);
			
			colEntity = new ExcelExportEntity("客户类型", "use");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(10);
			colList.add(colEntity);
			
			colEntity = new ExcelExportEntity("贷户姓名", "name");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(20);
			colList.add(colEntity);
			
			colEntity = new ExcelExportEntity("身份证号", "idCard");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(30);
			colList.add(colEntity);
			
			colEntity = new ExcelExportEntity("手机号", "phone");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(30);
			colList.add(colEntity);
			
			colEntity = new ExcelExportEntity("申请金额", "capital");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(10);
			colList.add(colEntity);
			
			colEntity = new ExcelExportEntity("退款金额", "totalAmount");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(10);
			colList.add(colEntity);
			
			colEntity = new ExcelExportEntity("收款人", "payer");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(20);
			colList.add(colEntity);	
			
			colEntity = new ExcelExportEntity("退款方式", "payType");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(20);
			colList.add(colEntity);	
			
			colEntity = new ExcelExportEntity("状态", "status");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(20);
			colList.add(colEntity);
			
			colEntity = new ExcelExportEntity("处理时间", "updateDate");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(20);
			colList.add(colEntity);			
			
			List<Map<String, Object>> listexp = new ArrayList<Map<String, Object>>();
			
			BigDecimal total = new BigDecimal("0");
			if(list.size()>0){
				int no = 1;
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for (int i = 0; i < list.size(); i++) {
					BusRefund refundex = list.get(i);
					Map<String, Object> item = new HashMap<String, Object>();
					item.put("no", no);
					if(refundex.getCreateDate()!= null){
						item.put("creDate", df.format(refundex.getCreateDate()));
					}else{
						item.put("creDate", "");
					}
					item.put("orgName", refundex.getOrgName());
					if(refundex.getUse()==null){
						item.put("use","");
					}else if(refundex.getUse()==1){
						item.put("use","新增");
					}else if(refundex.getUse()==2){
						item.put("use","转贷");
					}else{
						item.put("use","");
					}
					item.put("name",refundex.getAccountName());
					item.put("idCard",refundex.getCertificateNo());
					item.put("phone",refundex.getMobileNo());
					item.put("capital",refundex.getAccountNo());
					item.put("totalAmount",refundex.getTotalAmount());
					if(refundex.getTotalAmount() != null){
						total = total.add(refundex.getTotalAmount());
					}
					item.put("payer",refundex.getPayer());
					if(refundex.getPayWay() == null){
						item.put("payType","");
					}else if("1".equals(refundex.getPayWay())){
						item.put("payType","银行卡支付");
					}else if("2".equals(refundex.getPayWay())){
						item.put("payType","微信支付");
					}else if("3".equals(refundex.getPayWay())){
						item.put("payType","暂不支付");
					}else{
						item.put("payType","");
					}
					if(refundex.getStatus()==null){
						item.put("status","");
					}else if("S".equals(refundex.getStatus())){
						item.put("status","退款成功");
					}else if("F".equals(refundex.getStatus())){
						item.put("status","退款失败");
					}else if("I".equals(refundex.getStatus())){
						item.put("status","退款处理中");
					}else if("P".equals(refundex.getStatus())){
						item.put("status","弃贷待审核");
					}else{
						item.put("status","");
					}
					if(refundex.getUpdateDate()==null){
						item.put("updateDate","");
					}else{
						item.put("updateDate",df.format(refundex.getUpdateDate()));
					}
					listexp.add(item);
					no ++ ;
				}
				Map<String, Object> obj = new HashMap<String, Object>();
				obj.put("no", "");
				obj.put("creDate", "");
				obj.put("orgName", "");
				obj.put("use", "");
				obj.put("name", "");
				obj.put("idCard", "");
				obj.put("phone", "");
				obj.put("capital", "合计");
				obj.put("totalAmount", total);
				obj.put("payer", "");
				obj.put("payType", "");
				obj.put("status", "");
				obj.put("updateDate", "");
				listexp.add(obj);
			}
			Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), colList,listexp);
			String baseurl = fileConfig.getBaseDir();
			baseurl = baseurl + "xlsFile\\download\\";
			FileUtils.createDirectory(baseurl);
			File file = new File(baseurl, UUIDUtil.getUUID() + ".xls");
			workbook.write(new FileOutputStream(file));
			HttpHeaders headers = new HttpHeaders();
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			String parseGBK = FileUtils.parseGBK("退款信息.xls");
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
	
}
