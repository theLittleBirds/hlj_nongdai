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
import org.springframework.web.servlet.ModelAndView;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.entity.vo.BaseEntityTypeConstants;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusGuaranteeFee;
import com.nongyeos.loan.admin.entity.BusGuaranteeFeeInfo;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.model.FileConfig;
import com.nongyeos.loan.admin.model.IntoPieceConfig;
import com.nongyeos.loan.admin.service.IGuaranteeFeeInfoService;
import com.nongyeos.loan.admin.service.IGuaranteeFeeService;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.admin.service.IMessageReminderService;
import com.nongyeos.loan.admin.service.IOrgService;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.FileUtils;
import com.nongyeos.loan.base.util.HttpClientUtil;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;
@Controller
@RequestMapping("/guaranteeFee")
public class GuaranteeFeeController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(IntoPieceController.class);
	
	@Autowired
	private IGuaranteeFeeInfoService guaranteeFeeInfoService;
	
	@Autowired
	private IGuaranteeFeeService guaranteeFeeService;
	
	@Autowired
	private IOrgService orgService;
	
	@Autowired
	private IWebUserService userService;

	@Autowired
	private IntoPieceConfig pieceConfig;
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@Autowired
	private IMessageReminderService messageReminderService;
	
	@Autowired
	private FileConfig fileConfig;
	
	
	/**
	 * 扣费列表
	 * @return
	 */
	@RequestMapping("/payments")
	public String payments(){
		return "intoPiece/paymentlist";
	}
	
	
	
	@RequestMapping("/list")
	@ResponseBody
	public PageBeanUtil<BusGuaranteeFeeInfo> list(int currentPage, int pageSize,
			String orgName, String memberName, String idCard, String phone,
			String status, String startDate, String endDate,HttpServletRequest request)throws Exception{
		String personId = (String) request.getSession().getAttribute(Constants.SESSION_PERSONCD);
		try {
			BusGuaranteeFeeInfo feeInfo = new BusGuaranteeFeeInfo();
			feeInfo.setOrgName(StringUtils.isEmpty(orgName)?null:orgName);
			feeInfo.setAccountName(StringUtils.isEmpty(memberName)?null:memberName);
			feeInfo.setCertificateNo(StringUtils.isEmpty(idCard)?null:idCard);
			feeInfo.setMobileNo(StringUtils.isEmpty(phone)?null:phone);
			feeInfo.setStatus(StringUtils.isEmpty(status)?null:status);
			feeInfo.setCreateBy(personId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("feeInfo", feeInfo);
			if(StrUtils.isNotEmpty(startDate))
				map.put("startDate", startDate);
			if(StrUtils.isNotEmpty(endDate))
				map.put("endDate", endDate+" 23:59:59");
			return guaranteeFeeInfoService.selectByPage(personId,map,currentPage,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return new PageBeanUtil<BusGuaranteeFeeInfo>();
		}
		
	}
	
	@RequestMapping("/guaranteefeeinfoexport")
    @ResponseBody
	public ResponseEntity<FileSystemResource> guaranteeFeeInfoExport(
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
			BusGuaranteeFeeInfo feeInfo = new BusGuaranteeFeeInfo();
			feeInfo.setOrgName(StringUtils.isEmpty(orgName)?null:orgName);
			feeInfo.setAccountName(StringUtils.isEmpty(memberName)?null:memberName);
			feeInfo.setCertificateNo(StringUtils.isEmpty(idCard)?null:idCard);
			feeInfo.setMobileNo(StringUtils.isEmpty(phone)?null:phone);
			feeInfo.setStatus(StringUtils.isEmpty(status)?null:status);
			feeInfo.setCreateBy(personId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("feeInfo", feeInfo);
			if(StrUtils.isNotEmpty(startDate))
				map.put("startDate", startDate);
			if(StrUtils.isNotEmpty(endDate))
				map.put("endDate", endDate+" 23:59:59");
			
			List<BusGuaranteeFeeInfo> list = guaranteeFeeInfoService.queryList(personId, map);
			
			List<ExcelExportEntity> colList = new ArrayList<ExcelExportEntity>();
			ExcelExportEntity colEntity = new ExcelExportEntity("序号","no");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(5);
			colList.add(colEntity);

			colEntity = new ExcelExportEntity("部门", "orgName");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(15);
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
			
			colEntity = new ExcelExportEntity("申请金额", "capital");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(10);
			colList.add(colEntity);
			
			colEntity = new ExcelExportEntity("服务费", "totalAmount");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(10);
			colEntity.setType(BaseEntityTypeConstants.DOUBLE_TYPE.intValue());
			colList.add(colEntity);
			
			colEntity = new ExcelExportEntity("支付人", "payer");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(10);
			colList.add(colEntity);	
			
			colEntity = new ExcelExportEntity("支付方式", "payType");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(10);
			colList.add(colEntity);	
			
			colEntity = new ExcelExportEntity("状态", "status");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(10);
			colList.add(colEntity);
			
			colEntity = new ExcelExportEntity("扣款时间", "updateDate");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(20);
			colList.add(colEntity);			
			
			List<Map<String, Object>> listexp = new ArrayList<Map<String, Object>>();
			
			BigDecimal total = new BigDecimal("0");
			if(list.size()>0){
				int no = 1;
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for (int i = 0; i < list.size(); i++) {
					BusGuaranteeFeeInfo fee = list.get(i);
					Map<String, Object> item = new HashMap<String, Object>();
					item.put("no", no);
					if(fee.getCreateDate()!= null){
						item.put("creDate", df.format(fee.getCreateDate()));
					}else{
						item.put("creDate", "");
					}
					item.put("orgName", fee.getOrgName());
					if(fee.getUse()==null){
						item.put("use","");
					}else if(fee.getUse()==1){
						item.put("use","新增");
					}else if(fee.getUse()==2){
						item.put("use","转贷");
					}else{
						item.put("use","");
					}
					item.put("name",fee.getAccountName());
					item.put("idCard",fee.getCertificateNo());
					item.put("phone",fee.getMobileNo());
					item.put("capital",fee.getAccountNo());
					item.put("totalAmount",fee.getTotalAmount());
					if(fee.getTotalAmount() != null){
						total = total.add(fee.getTotalAmount());
					}
					item.put("payer",fee.getPayer());
					if(fee.getPayWay() == null){
						item.put("payType","");
					}else if("1".equals(fee.getPayWay())){
						item.put("payType","银行卡支付");
					}else if("2".equals(fee.getPayWay())){
						item.put("payType","微信支付");
					}else if("3".equals(fee.getPayWay())){
						item.put("payType","暂不支付");
					}else{
						item.put("payType","");
					}
					if(fee.getStatus()==null){
						item.put("status","");
					}else if("S".equals(fee.getStatus())){
						item.put("status","支付成功");
					}else if("F".equals(fee.getStatus())){
						item.put("status","支付失败");
					}else if("I".equals(fee.getStatus())){
						item.put("status","支付处理中");
					}else if("RI".equals(fee.getStatus())){
						item.put("status","退款处理中");
					}else if("RF".equals(fee.getStatus())){
						item.put("status","退款失败");
					}else if("RS".equals(fee.getStatus())){
						item.put("status","退款成功");
					}else if("GL".equals(fee.getStatus())){
						item.put("status","放弃支付");
					}else{
						item.put("status","");
					}
					if(fee.getUpdateDate()==null){
						item.put("updateDate","");
					}else{
						item.put("updateDate",df.format(fee.getUpdateDate()));
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
				obj.put("capital", "");
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
			String parseGBK = FileUtils.parseGBK("扣费信息.xls");
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
	
	
	@RequestMapping("/repay")
	@ResponseBody
	public JSONObject repay(String id, HttpServletRequest request)throws Exception{
		JSONObject retJson = new JSONObject();
		try {
			 BusGuaranteeFeeInfo guaranteeFeeInfo = guaranteeFeeInfoService.selectByPrimaryKey(id);
			 //站长代付
			 if(guaranteeFeeInfo!=null&&guaranteeFeeInfo.getPayWay().equals("1")){
				 SysWebUser user =userService.selectUserByUserName(pieceConfig.getUserName());
				 BusIntoPiece ip = intoPieceService.selectByPrimaryKey(guaranteeFeeInfo.getIntoPieceId());
				 SysOrg org = orgService.selectByOrgId(ip.getOrgId());
				 guaranteeFeeInfo.setStatus("I");
				 guaranteeFeeInfoService.updateByPrimaryKey(guaranteeFeeInfo);
				 Map<String, String> map1 = new HashMap<String, String>();
				 map1.put("amount", guaranteeFeeInfo.getTotalAmount().toString());
				 map1.put("certificateNo", org.getIdCard());
				 map1.put("accountNo", org.getCardNo());
				 map1.put("accountName", org.getLeader());
				 map1.put("mobileNo", org.getPhone());
				 map1.put("intoPieceId", guaranteeFeeInfo.getIntoPieceId());
				 map1.put("userid", user.getUserId());
				 map1.put("companyType", ip.getChannel());
				 String signature = user.getUserId() + user.getUsername() +
				 user.getPassword();
				 map1.put("signature", DigestUtils.md5Hex(signature));
				 String retMap =
						 HttpClientUtil.doPost(pieceConfig.getXfGuaranteeFeeUrl(), map1,
						 "utf-8");
						 if(retMap!=null){
							 JSONObject retMap1 = JSONObject.parseObject(retMap);
							 if(retMap1==null||StringUtils.isEmpty(retMap1.getString("isSuccess"))||retMap1.getString("isSuccess").equals(Constants.GATE_RESULT_FAIL)){
								 retJson.put("code", "400");
								 System.err.println("无法执行原因================="+retMap1.getString("data")+"==================");
								 retJson.put("msg", "代扣服务费失败，请联系管理员！");
								 return retJson;
							 }else{
								 retJson.put("code", "200");
								 retJson.put("msg", "重新发起扣款成功！");
								 return retJson;
							 }
						 }else{
							 retJson.put("code", "400");
							 retJson.put("msg", "代扣服务费失败，请联系管理员！");
							 return retJson;
						 }
						 //微信支付
			 }else if(guaranteeFeeInfo!=null&&guaranteeFeeInfo.getPayWay().equals("2")){
				 BusIntoPiece ip = intoPieceService.selectByPrimaryKey(guaranteeFeeInfo.getIntoPieceId());
				 messageReminderService.saveweixinpay(Constants.WEI_XIN_SERVICE_PAY,ip.getChannel(),ip.getId(),guaranteeFeeInfo.getTotalAmount().toString());
				 guaranteeFeeInfo.setStatus("I");
				 guaranteeFeeInfoService.updateByPrimaryKey(guaranteeFeeInfo);
			 }else if(guaranteeFeeInfo!=null&&guaranteeFeeInfo.getPayWay().equals("5")){
				 BusIntoPiece ip = intoPieceService.selectByPrimaryKey(guaranteeFeeInfo.getIntoPieceId());
				 messageReminderService.saveweixinpay(Constants.XIAO_CHENG_XU_SERVICE_PAY,ip.getChannel(),ip.getId(),guaranteeFeeInfo.getTotalAmount().toString());
				 guaranteeFeeInfo.setStatus("I");
				 guaranteeFeeInfoService.updateByPrimaryKey(guaranteeFeeInfo);
			 }
			 retJson.put("code", "200");
			 retJson.put("msg", "重新发起扣款成功！");
			 return retJson;
			 
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("code", "400");
		    retJson.put("msg", "代扣服务费失败，请联系管理员！");
		    return retJson;
		}
		
	}
	
	@RequestMapping("/detail")
	public ModelAndView detail(String id){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("id", id);
		modelAndView.setViewName("intoPiece/paymentdetail");
		return modelAndView;
	}
	
	@RequestMapping("/guaranteefeelist")
	@ResponseBody
	public PageBeanUtil<BusGuaranteeFee> guaranteefeelist(int currentPage, int pageSize,String id){
		try {
			BusGuaranteeFeeInfo info = guaranteeFeeInfoService.selectByPrimaryKey(id);
				return guaranteeFeeService.selectByIpIdPage(currentPage, pageSize,info.getIntoPieceId());
		} catch (Exception e) {
			e.printStackTrace();
			return new PageBeanUtil<BusGuaranteeFee>();
		}
	}
	
}
