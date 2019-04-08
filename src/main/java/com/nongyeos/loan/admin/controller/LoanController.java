package com.nongyeos.loan.admin.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusApplySignFile;
import com.nongyeos.loan.admin.entity.BusContactTemplate;
import com.nongyeos.loan.admin.entity.BusContractData;
import com.nongyeos.loan.admin.entity.BusFamilySituation;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusLoan;
import com.nongyeos.loan.admin.entity.BusProduct;
import com.nongyeos.loan.admin.entity.IntSignatories;
import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.model.FileConfig;
import com.nongyeos.loan.admin.model.IntoPieceConfig;
import com.nongyeos.loan.admin.model.RootPointConfig;
import com.nongyeos.loan.admin.resultMap.LoanMap;
import com.nongyeos.loan.admin.service.IApplySignFileService;
import com.nongyeos.loan.admin.service.IContactMakeService;
import com.nongyeos.loan.admin.service.IContactTemplateService;
import com.nongyeos.loan.admin.service.IContractDataService;
import com.nongyeos.loan.admin.service.IExamineService;
import com.nongyeos.loan.admin.service.IFamilySituationService;
import com.nongyeos.loan.admin.service.IGuaranteeReverseService;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.admin.service.ILoanService;
import com.nongyeos.loan.admin.service.IParaGroupService;
import com.nongyeos.loan.admin.service.IPersonService;
import com.nongyeos.loan.admin.service.IProductService;
import com.nongyeos.loan.admin.service.ISignatorieService;
import com.nongyeos.loan.admin.service.ISmsTemplateService;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.app.entity.BusFins;
import com.nongyeos.loan.app.entity.FlowNode;
import com.nongyeos.loan.app.service.IAppEntryService;
import com.nongyeos.loan.app.service.IApplicationService;
import com.nongyeos.loan.app.service.IBusFinsService;
import com.nongyeos.loan.app.service.IFlowNodeService;
import com.nongyeos.loan.base.util.CnNumberUtils;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.FileUtils;
import com.nongyeos.loan.base.util.HttpClientUtil;
import com.nongyeos.loan.base.util.JPushDO;
import com.nongyeos.loan.base.util.JpushUtils;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/loan")
public class LoanController {
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@Autowired
	private IExamineService examineService;
	
	@Autowired
	private ILoanService loanService;
	
	@Autowired
	private IFamilySituationService familySituationService;
	
	@Autowired
	private IBusFinsService busFinsService;
	
	@Autowired  
    private IParaGroupService paraGroupService;
	
	@Autowired
	private IContractDataService contactDataService;
	
	@Autowired
	private IAppEntryService appEntryService;
	
	@Autowired
	private IFlowNodeService flowNodeService;
	
	@Autowired
	private JpushUtils jpushUtils;
	
	@Autowired
	private IApplicationService applicationService;
	
	@Autowired
	private IApplySignFileService applySignFileService;
	
	@Autowired
	private IContactTemplateService contactTemplateService;
	
	@Autowired
	private IContactMakeService contactMakeService;	
	
	@Autowired
	private ISignatorieService signatorieService;
	
	@Autowired
	private IntoPieceConfig pieceConfig;
	
	@Autowired
	private IWebUserService userService;
	
	@Value("${fileupload.baseDir}")
	private String baseDir;
	
	@Autowired
	private FileConfig fileConfig;
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private IPersonService personService;
	
	@Autowired
	private ISmsTemplateService smsTemplateService;
	
	@Autowired
    private RootPointConfig rootPointConfig;
	
	@Autowired
	private IGuaranteeReverseService reverseService;
	
	/**
	 * 异步获取列表 合同制作，合同签署，待放款，已放款列表
	 * @param currentPage
	 * @param pageSize
	 * @param orgName
	 * @param memberName
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/contractlist")
    @ResponseBody
    public PageBeanUtil<LoanMap> contractList(int currentPage,int pageSize,String orgName,String memberName,String status,HttpServletRequest request){
		try {
			LoanMap loanMap = new LoanMap();
			if(StrUtils.isNotEmpty(orgName))
				loanMap.setFullCname(orgName);
			if(StrUtils.isNotEmpty(memberName))
				loanMap.setMemberName(memberName);
			if(StrUtils.isNotEmpty(status))
				loanMap.setIpStatus(status);
			String idCard = request.getParameter("idCard");
			if(StrUtils.isNotEmpty(idCard))
				loanMap.setIdCard(idCard);
			String startDate = request.getParameter("startDate");
			if(StrUtils.isNotEmpty(startDate))
				loanMap.setStartDate(startDate);
			String endDate = request.getParameter("endDate");
			if(StrUtils.isNotEmpty(endDate))
				loanMap.setEndDate(endDate);
			loanMap.setPersonId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			loanMap.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
	    	return  loanService.selectLoanByPage(currentPage, pageSize, loanMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	
	@RequestMapping("/alreadyloanexport")
    @ResponseBody
	public ResponseEntity<FileSystemResource> alreadyLoanExport(
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			LoanMap loanMap = new LoanMap();
			String idCard = request.getParameter("idCard");
			String orgName = request.getParameter("orgName");
			String memberName = request.getParameter("memberName");
			if(StrUtils.isNotEmpty(orgName))
				loanMap.setFullCname(orgName);
			if(StrUtils.isNotEmpty(memberName))
				loanMap.setMemberName(memberName);
			if(StrUtils.isNotEmpty(idCard))
				loanMap.setIdCard(idCard);
			loanMap.setIpStatus(Constants.FLOW_ALREADY);
			loanMap.setPersonId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			loanMap.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			List<LoanMap> list = loanService.alreadyLoanExport(loanMap);
			
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
			
			colEntity = new ExcelExportEntity("家庭住址", "address");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(30);
			colList.add(colEntity);
			
			colEntity = new ExcelExportEntity("贷款金额", "capital");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(10);
			colEntity.setType(BaseEntityTypeConstants.DOUBLE_TYPE.intValue());
			colList.add(colEntity);
			
			colEntity = new ExcelExportEntity("服务费金额", "serviceFee");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(10);
			colEntity.setType(BaseEntityTypeConstants.DOUBLE_TYPE.intValue());
			colList.add(colEntity);
			
			colEntity = new ExcelExportEntity("放款日期", "loanDate");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(15);
			colList.add(colEntity);
			
			colEntity = new ExcelExportEntity("贷款到期日期", "endDate");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(15);
			colList.add(colEntity);
			
			colEntity = new ExcelExportEntity("放款机构（银行）", "fins");
			colEntity.setNeedMerge(true);
			colEntity.setWidth(30);
			colList.add(colEntity);
			
			List<Map<String, Object>> listexp = new ArrayList<Map<String, Object>>();
			if(list.size()>0){
				int no = 1;
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				for (int i = 0; i < list.size(); i++) {
					LoanMap model = list.get(i);
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("no", no);
					if("1".equals(model.getUse())){
						map.put("use", "新增");
					}else if("2".equals(model.getUse())){
						map.put("use", "转贷");
					}else{
						map.put("use", "");
					}
					map.put("orgName", model.getFullCname());
					map.put("name", model.getMemberName());
					map.put("idCard", model.getIdCard());
					map.put("address", model.getAddress());
					map.put("capital", model.getCapital());
					map.put("serviceFee", model.getCapital().multiply(new BigDecimal("0.023")).setScale(0,BigDecimal.ROUND_HALF_UP));
					map.put("loanDate", df.format(model.getLoanDate()));
					map.put("endDate", model.getEndDate());
					map.put("fins", model.getFinsName());
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
			String parseGBK = FileUtils.parseGBK("已放款.xls");
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
	/**
	 * 合同制作列表页面
	 * @return
	 */
	@RequestMapping("/contractmaking")
	public String contractMaking(){
		return "loan/contractmakinglist";
	}
	
	/**
	 * 借款基础页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/basicloan")
	public ModelAndView basicLoan(String id) throws Exception{
		if(StrUtils.isEmpty(id))
			throw new Exception("借款标识为空");
		ModelAndView mv = new ModelAndView();
		BusContractData data = contactDataService.selectByloanId(id);
		BusLoan loan = loanService.selectByPrimaryKey(id);		
		if(data == null){	
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(loan.getIntoPieceId());
			BusFamilySituation fs = new BusFamilySituation();
			fs.setIntoPieceId(ip.getId());
			fs.setCoBorrower(1);
			List<BusFamilySituation> family = familySituationService.queryCoBorrower(fs);
			JSONArray arr = new JSONArray();
			for (int i = 0; i < family.size(); i++) {
				JSONObject json  = new JSONObject();
				json.put("name", family.get(i).getName());
				json.put("value", family.get(i).getName()+","+family.get(i).getIdCard()+","+family.get(i).getPhone());
				arr.add(json);
			}
			BusFins fins = busFinsService.selectById(ip.getLenderId());
			mv.addObject("loan", loan);
			mv.addObject("ip", ip);
			mv.addObject("lender", fins.getCname());
			mv.addObject("family", arr);
			mv.addObject("REPAYMENT_WAY", paraGroupService.getSelectOption("REPAYMENT_WAY", loan.getRepaymentType() == null ? null : loan.getRepaymentType().toString()));
			mv.setViewName("/loan/basicloan");
		}else{
			List<BusContactTemplate> list = contactTemplateService.waitForSign(loan.getLenderId());
			if(list.size()>0){
				mv.addObject("id", list.get(0).getId());
			}
			JSONArray arr = new JSONArray();
			for (int i = 0; i < list.size(); i++) {
				JSONObject obj = new JSONObject();
				obj.put("id", list.get(i).getId());
				obj.put("title", list.get(i).getTitle());
				arr.add(obj);
			}			
			mv.addObject("arr", arr);
			mv.addObject("loanId", id);
			mv.setViewName("/loan/contactdetail");
		}
		return mv;
	}
	
	/**
	 * 借款基础页面保存
	 * @param id
	 * @return
	 */
	@RequestMapping("/basicloansave")
	@ResponseBody
	public JSONObject basicLoanSave(HttpServletRequest request,String id) throws Exception{
		JSONObject json =  new JSONObject();
		if(StrUtils.isEmpty(id)){
			json.put("code", 400);
			json.put("msg", "进件标识为空");	
			return json;
		}
		try {
			BusLoan updateLoan = new BusLoan();
			updateLoan.setId(id);
			updateLoan.setStartDate(request.getParameter("start_date"));
			updateLoan.setEndDate(request.getParameter("end_dates"));
			loanService.updateByPrimaryKeySelective(updateLoan);
			
			BusLoan loan = loanService.selectByPrimaryKey(id);
			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(loan.getIntoPieceId());
			Enumeration<String> names = request.getParameterNames();
			JSONObject obj = new JSONObject();
			String key = null;
			while(names.hasMoreElements()){
				key = names.nextElement();
				obj.put(key, request.getParameter(key));
			}
			BusProduct product = productService.selectByProductId(ip.getProduct());
			obj.put("capital", loan.getCapital());
			obj.put("upperCapital", CnNumberUtils.toUppercase(loan.getCapital().toString()));
			obj.put("term", loan.getTerm());
			obj.put("rateType", product.getMonthRateType());
			obj.put("rate", loan.getRate());
			obj.put("overdueRate", loan.getOverdueRate());
			obj.put("serviceRateType", product.getServiceRateType());
			obj.put("serviceRate", loan.getServiceRate());
			obj.put("serviceFee", loan.getServiceFee());
			obj.put("repaymentType", loan.getRepaymentType());
			obj.put("today", DateUtils.formatYMD(new Date()));
			String guarantee = obj.getString("guarantee");
			if(StrUtils.isNotEmpty(guarantee)){
				obj.put("upperGuarantee", CnNumberUtils.toUppercase(guarantee.toString()));
			}
			BusContractData cd = new BusContractData();
			cd.setId(UUIDUtil.getUUID());
			cd.setLoanId(id);
			cd.setData(obj.toString());
			contactDataService.insert(cd);
						
			json.put("code", 200);
			json.put("msg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 200);
			json.put("msg", "保存失败");
		}
		return json;
	}
	
	
	/**
	 * 生成富文本的合同
	 * @param id
	 * @return
	 */
	@RequestMapping("/contracthtml")
	@ResponseBody
	public JSONObject contractHtml(String loanId,String id){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(loanId)){
			json.put("code", 400);
			json.put("msg", "进件标识为空");	
			return json;
		}
		if(StrUtils.isEmpty(id)){
			json.put("code", 400);
			json.put("msg", "合同标识为空");	
			return json;
		}
		try {
			BusContractData cd = contactDataService.selectByloanId(loanId);
			BusContactTemplate ct = contactTemplateService.selectByPrimaryKey(id);
			JSONObject data = JSONObject.parseObject(cd.getData());
			String content = ct.getContent();
			String variable = ct.getVariable();
			if(StrUtils.isNotEmpty(variable)){
				String[] code = variable.split(",");
				for (int i = 0; i < code.length; i++) {
					if(StrUtils.isNotEmpty(code[i])){
						String value = data.getString(code[i]);
						if(StrUtils.isEmpty(value)){
							json.put("code", 400);
							json.put("msg", code[i]+"参数不存在");
							return json;
						}
						content = content.replace(code[i], value);
					}
				}
			}
			content = content.replace("{", "");
			content = content.replace("}", "");
			json.put("code", 200);
			json.put("content", content);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", "生成合同失败");
		}
		return json;
	}
	
	/**
	 * 合同作废
	 * @return
	 */
	@RequestMapping("/basicdatacancel")
	@ResponseBody
	public JSONObject basicDataCancel(String id){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(id)){
			json.put("code", 400);
			json.put("msg", "进件标识为空");	
			return json;
		}
		try {
			contactDataService.deleteByLoan(id);
			json.put("code", 200);
			json.put("msg", "已作废，请重新制作合同");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", "失败");
		}
		return json;
	}
	
	/**
	 * 审核页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/examine")
	public ModelAndView examineForm(String id){
		ModelAndView mv= new ModelAndView();
		mv.addObject("loanId", id);
		try {
			BusLoan loan =  loanService.selectByPrimaryKey(id);
			mv.addObject("id", loan.getIntoPieceId());			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mv.setViewName("/loan/examine");
		return mv;
	}
	
	/**
	 * 审核页面保存
	 * @param id
	 * @return
	 */
	@RequestMapping("/examinesave")
	@ResponseBody
	public JSONObject examineSave(HttpServletRequest request,String nextNodeId,String pcId,String id,String loanId){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(id)){
			json.put("code", 400);
			json.put("msg", "进件标识为空");	
			return json;
		}
		try {
			JSONObject object = loanService.examineSave(id, loanId, nextNodeId, pcId, request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			JPushDO.Do(intoPieceService, appEntryService, flowNodeService, jpushUtils, applicationService, id, nextNodeId, request);
//			FlowNode nextNode = flowNodeService.selectByNodeId(nextNodeId);
//			BusIntoPiece ip = intoPieceService.selectByPrimaryKey(id);
//			String mark = rootPointConfig.getMark();
//			JSONObject objectRoot = JSONObject.parseObject(mark);
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("MEMBER", ip.getMemberName());
//			SysPerson person = personService.selectByPersonId(ip.getOperUserId());
			/*if(nextNode.getCname().equals("8")||"SX".equals(ip.getChannel())){
				smsTemplateService.smsSend(Constants.U_PENDING_REMIND, objectRoot.getString(ip.getChannel()), map, person.getMobile());
			}*/
			FlowNode nextNode = flowNodeService.selectByNodeId(nextNodeId);
			if(nextNode.getEname().equals("8")){
				reverseService.savePreReverseMessage(id,request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			}
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());	
			return json;
		}
	}
	
	/**
	 * 合同签署列表页面
	 * @return
	 */
	@RequestMapping("/contractsign")
	public String contractSignList(){
		return "loan/contractsignlist";
	}
	
	/**
	 * 合同签署页面 
	 * @return
	 */
	@RequestMapping("/sign")
	public ModelAndView contractList(String id){
		ModelAndView mv = new ModelAndView();
		mv.addObject("id", id);
		mv.setViewName("loan/contractsign");
		return mv;
	}
	
	/**
	 * ajax获取签署合同列表
	 * @param id
	 * @return
	 */
	@RequestMapping("/contractstatus")
	@ResponseBody
	public JSONObject contractStatus(String id){
		JSONObject json = new JSONObject();
		try {
//			BusContractData data = contactDataService.selectByloanId(id);			
			JSONArray arr = new JSONArray();
			BusLoan loan = loanService.selectByPrimaryKey(id);
			//有签约状态
			if(loan.getSignStatus() != null){
				json.put("flag", 1);
				List<BusApplySignFile> list = applySignFileService.selectByLoanId(id);
				for (int i = 0; i < list.size(); i++) {
					JSONObject file = new JSONObject();
					file.put("id", list.get(i).getId());
					file.put("name", list.get(i).getName());
					file.put("signatories", list.get(i).getSignatories());
					file.put("status", list.get(i).getStatus());
					arr.add(file);
				}
			}
//			else{
//				//未制作上传合同
//				json.put("flag", 2);
//				JSONObject obj = JSONObject.parseObject(data.getData());
//				List<BusContactTemplate> template = contactTemplateService.waitForSign(loan.getLenderId());
//				for (int i = 0; i < template.size(); i++) {
//					JSONObject file = new JSONObject();
//					file.put("name", template.get(i).getTitle());
//					if("2".equals(template.get(i).getSecond())){
//						file.put("signatories", obj.getString("co_name"));
//					}else if("1".equals(template.get(i).getSecond())){
//						file.put("signatories", obj.getString("name"));
//					}
//					arr.add(file);
//				}
//			}
			json.put("arr", arr);
			json.put("code", 200);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	/**
	 * 签署合同
	 * @param id
	 * @return
	 */
	@RequestMapping("/pushsign")
	@ResponseBody
	public JSONObject pushSign(HttpServletRequest request,String id){
		JSONObject json = new JSONObject();
		try {
			BusLoan loan = loanService.selectByPrimaryKey(id);
			if(loan.getSignStatus() == null){
				//设置状态，避免重复签约
				loan.setSignStatus(1);
				loanService.updateByPrimaryKey(loan);
				//生成PDF,上传
				json.put("code", 200);
				json.put("msg", "上传成功");
			}else{
				json.put("code", 400);
				json.put("msg", "不能重复签署合同");
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}		
		return json;
	}
	
	/**
	 * apply_sign_file_id获取签约人信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/contactsignatories")
	@ResponseBody
	public JSONObject contactSignatories(String id){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(id)){
			json.put("code", 400);
			json.put("msg", "合同标识为空");	
			return json;
		}
		try {			
			json.put("list", signatorieService.selectByapplySignFileId(id));
			json.put("code", 200);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	/**
	 * 点击发送签约短信
	 * @param signatories_id
	 * @return
	 */
	@RequestMapping("/sendsms")
	@ResponseBody
	public JSONObject sendSmsToSignatory(String id,String loanId){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(id)){
			json.put("code", 400);
			json.put("msg", "请选择签约人");	
			return json;
		}
		if(StrUtils.isEmpty(loanId)){
			json.put("code", 400);
			json.put("msg", "借款标识为空");	
			return json;
		}
		try {	
			IntSignatories signesFile = signatorieService.selectByPrimaryKey(id);
			BusApplySignFile signBean = applySignFileService.selectByPrimaryKey(signesFile.getApplySignFileId());
			SysWebUser user = userService.selectUserByUserName(pieceConfig.getUserName());
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", user.getUserId());
			String signature = user.getUserId() + user.getUsername() + user.getPassword();
			map.put("signature", DigestUtils.md5Hex(signature));
			map.put("signatoriesId", id);
			map.put("companyType", signBean.getChannel());
			String result = HttpClientUtil.doPost(pieceConfig.getJunziqianSms(), map, "utf-8");
			if(StrUtils.isNotEmpty(result)){
				JSONObject resultObj = JSONObject.parseObject(result);
				String isSuccess = resultObj.getString("isSuccess");
				if("1".equals(isSuccess)){
					json.put("msg", "发送成功");
				}					
			}else{
				json.put("msg", "签约短信发送失败");
			}
			json.put("code", 200);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	/**
	 * 下载签署好的合同
	 * @param id
	 * @return
	 */
	@RequestMapping("/loadpdf")
	public String loadPdf(String id,HttpServletResponse response) throws Exception{
		BusApplySignFile  signFile = applySignFileService.selectByPrimaryKey(id);
		if(StrUtils.isEmpty(signFile.getFileDownpath())){
			BusLoan loan = loanService.selectByPrimaryKey(signFile.getLoanId());
			SysWebUser user = userService.selectUserByUserName(pieceConfig.getUserName());
			Map<String, String> map = new HashMap<String, String>();
			map.put("userid", user.getUserId());
			String signature = user.getUserId() + user.getUsername() + user.getPassword();
			map.put("signature", DigestUtils.md5Hex(signature));
			map.put("applySignFileId", id);
			map.put("targetPath", baseDir);
			map.put("companyType", loan.getChannel());
			String result = HttpClientUtil.doPost(pieceConfig.getJunziqiandown(), map, "utf-8");
			if(result == null)
				throw new Exception("下载失败");
			JSONObject resultObj = JSONObject.parseObject(result);
			String isSuccess = resultObj.getString("isSuccess");
			if(!"1".equals(isSuccess)){
				throw new Exception(resultObj.getString("msg"));
			}
		}
		signFile = applySignFileService.selectByPrimaryKey(id);
		String path = baseDir + signFile.getFileDownpath();
		File file = new File(path);
		if(file.exists()){
			response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(signFile.getName()+".pdf", "UTF-8"));// 设置文件名
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            
		}
		return null;
	}
	
	/**
	 * 计算贷款结束时间
	 * @param start
	 * @param term
	 * @return
	 */
	@RequestMapping("/calculationendtime")
	@ResponseBody
	public JSONObject calculationEndTime(String start,Integer term){
		JSONObject json = new JSONObject();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = df.parse(start);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, term);
			json.put("endTime", df.format(calendar.getTime()));
			json.put("code", 200);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", "计算结束时间出错");
		}
		return json;
	}
	
	/**
	 * 合同记录
	 * @return
	 */
	@RequestMapping("/contractrecord")
	public String contractRecord(){
		return "loan/contractrecordlist";
	}
	
	/**
	 * 合同记录
	 * @return
	 */
	@RequestMapping("/contractrecordlist")
    @ResponseBody
    public PageBeanUtil<LoanMap> contractRecordPage(int currentPage,int pageSize,String orgName,String memberName,String idCard,HttpServletRequest request){
		try {
			LoanMap loanMap = new LoanMap();
			if(StrUtils.isNotEmpty(orgName))
				loanMap.setFullCname(orgName);
			if(StrUtils.isNotEmpty(memberName))
				loanMap.setMemberName(memberName);
			if(StrUtils.isNotEmpty(idCard))
				loanMap.setIdCard(idCard);
			loanMap.setPersonId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			loanMap.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
	    	return  loanService.contactRecordPage(currentPage, pageSize, loanMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
	
	@RequestMapping("/contractrecorddetail")
	public ModelAndView contractRecordDetail(String id){
		ModelAndView mv = new ModelAndView();		
		mv.setViewName("loan/contractrecord");
		try {
			JSONArray arr = new JSONArray();
			List<BusApplySignFile> list = applySignFileService.selectByLoanId(id);
			for (int i = 0; i < list.size(); i++) {
				JSONObject file = new JSONObject();
				file.put("id", list.get(i).getId());
				file.put("name", list.get(i).getName());
				file.put("signatories", list.get(i).getSignatories());
				file.put("status", list.get(i).getStatus());
				arr.add(file);
			}
			mv.addObject("arr", arr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
}
