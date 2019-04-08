package com.nongyeos.loan.admin.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusLoan;
import com.nongyeos.loan.admin.entity.BusLoanDetail;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.model.FileConfig;
import com.nongyeos.loan.admin.model.IntoPieceConfig;
import com.nongyeos.loan.admin.model.RepayXlsx;
import com.nongyeos.loan.admin.resultMap.LoanMap;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.admin.service.ILoanDetailService;
import com.nongyeos.loan.admin.service.ILoanService;
import com.nongyeos.loan.admin.service.IParaGroupService;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.service.IAppEntryService;
import com.nongyeos.loan.app.service.IApplicationService;
import com.nongyeos.loan.app.service.IFlowNodeService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.FileUtils;
import com.nongyeos.loan.base.util.HttpClientUtil;
import com.nongyeos.loan.base.util.JPushDO;
import com.nongyeos.loan.base.util.JpushUtils;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.core.service.FlowMgr;


@Controller
@RequestMapping("/postlending")
public class PostLendingController {
	
	@Autowired
	private ILoanDetailService loanDetailService;
	
	@Autowired  
    private IParaGroupService paraGroupService;
	
	@Autowired
	private ILoanService loanService;
	
	@Autowired
	private FlowMgr flowMgrImpl;
	
	@Autowired
	private IAppEntryService appEntryService;
	
	@Autowired
	private IFlowNodeService flowNodeService;
	
	@Autowired
	private JpushUtils jpushUtils;
	
	@Autowired
	private IApplicationService applicationService;
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@Autowired
	private IWebUserService userService;
	
	@Autowired
	private IntoPieceConfig pieceConfig;
	
	@Autowired
	private FileConfig fileConfig; 

	/**
	 * 异步获取列表 还款中列表
	 * @param currentPage
	 * @param pageSize
	 * @param orgName
	 * @param memberName
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
    @ResponseBody
    public PageBeanUtil<LoanMap> list(int currentPage,int pageSize,String orgName,String memberName,
    		String ipstatus,Integer loanstatus,String month,HttpServletRequest request) throws Exception{
		try {
			LoanMap loanMap = new LoanMap();
			if(StrUtils.isNotEmpty(orgName))
				loanMap.setFullCname(orgName);
			if(StrUtils.isNotEmpty(memberName))
				loanMap.setMemberName(memberName);
			if(StrUtils.isNotEmpty(ipstatus))
				loanMap.setIpStatus(ipstatus);
			if(loanstatus != null)
				loanMap.setStatus(loanstatus);
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
			//1本月还完   2本月未还完
			if("1".equals(month) || "2".equals(month)){
				SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		        Calendar calendar = Calendar.getInstance();  
		        calendar.add(Calendar.MONTH, 1);  		        
				loanMap.setTime(df.format(calendar.getTime())+"01");
				loanMap.setMonthStatus(month);
			}
			loanMap.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
	    	return  loanService.selectByPage(currentPage, pageSize, loanMap);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return null;
    }
	
	/**
	 * 还款列表
	 * @return
	 */
	@RequestMapping("/repayinglist")
	public ModelAndView repayintList(){
		ModelAndView mv = new ModelAndView();
		try {
			mv.addObject("statusList", paraGroupService.getSelectOption("LOAN_STATUS", null));
		} catch (Exception e) {
			e.printStackTrace();
		}		
		mv.setViewName("postLending/repayinglist");
		return mv;
	}
	
	
	/**
	 * 还款详情页面
	 * @return
	 */
	@RequestMapping("/loandetail")
	public ModelAndView loanDetail(String id){
		ModelAndView mv = new ModelAndView();
		mv.addObject("id", id);
		try {
			BusLoan loan = loanService.selectByPrimaryKey(id);
			mv.addObject("channel", loan.getChannel());
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.setViewName("postLending/loandetail");
		return mv;
	}
	
	/**
	 * 异步获取还款详情列表
	 * @return
	 */
	@RequestMapping("/loandetaillist")
	@ResponseBody
	public List<BusLoanDetail> getLoanDetailList(String id) throws Exception{
		return loanDetailService.selectByLoanId(id);
	}
	
	/**
	 * 首信导入本期还款完成
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping("/importrepayxlsx")
	@ResponseBody
	public JSONObject importRepayXlsx(HttpServletRequest request,MultipartFile file){
		JSONObject json = new JSONObject();
		try {
			ImportParams params = new ImportParams();
	        params.setTitleRows(0);
	        params.setHeadRows(1);
	        String baseurl = fileConfig.getBaseDir();
	        String format = DateUtils.format(DateUtils.getNowDate(), "yyyyMMdd-HHmmss");
	        baseurl =baseurl+"xlsFile\\upload\\";
			FileUtils.createDirectory(baseurl);
	        File pathDest = new File(baseurl, format+".xls");
	        if (!pathDest.exists()) {
	        	pathDest.createNewFile();
	           }
	        FileOutputStream fos = new FileOutputStream(pathDest);
	        byte[] f = file.getBytes();
	        fos.write(f); 
	        fos.close();
	        List<RepayXlsx> list = ExcelImportUtil.importExcel(pathDest,RepayXlsx.class, params);
	        int size = list.size();
	        if(size == 0){
	        	json.put("code", "400");
	        	json.put("msg", "数据为空");
	        	return json;
	        }
	        loanDetailService.importRepayXlse(list, request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
	        json.put("code", "200");
        	json.put("msg", "导入成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", "400");
        	json.put("msg", "导入失败");
		}
		return json;
	}
	
	
	/**
	 * 首信导入提前还款完成
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping("/importfinishxlsx")
	@ResponseBody
	public JSONObject importFinishXlsx(HttpServletRequest request,MultipartFile file){
		JSONObject json = new JSONObject();
		try {
			ImportParams params = new ImportParams();
	        params.setTitleRows(0);
	        params.setHeadRows(1);
	        String baseurl = fileConfig.getBaseDir();
	        String format = DateUtils.format(DateUtils.getNowDate(), "yyyyMMdd-HHmmss");
	        baseurl =baseurl+"xlsFile\\upload\\";
			FileUtils.createDirectory(baseurl);
	        File pathDest = new File(baseurl, format+".xls");
	        if (!pathDest.exists()) {
	        	pathDest.createNewFile();
	           }
	        FileOutputStream fos = new FileOutputStream(pathDest);
	        byte[] f = file.getBytes();
	        fos.write(f); 
	        fos.close();
	        List<RepayXlsx> list = ExcelImportUtil.importExcel(pathDest,RepayXlsx.class, params);
	        int size = list.size();
	        if(size == 0){
	        	json.put("code", "400");
	        	json.put("msg", "数据为空");
	        	return json;
	        }
	        loanDetailService.importFinishXlse(list, request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
	        json.put("code", "200");
        	json.put("msg", "导入成功");
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", "400");
        	json.put("msg", e.getMessage());
		}
		return json;
	}
	
	/**
	 * 还款详情id获取记录
	 * @return
	 */
	@RequestMapping("/loandetailbyid")
	@ResponseBody
	public JSONObject getById(String id){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(id)){
			json.put("code", 400);
			json.put("msg", "还款标识为空");
			return json;
		}
		try {
			BusLoanDetail  detail = loanDetailService.selectByPrimaryKey(id);
			if(detail == null){
				json.put("code", 400);
				json.put("msg", "未查询到详情");
				return json;
			}
			json.put("code", 200);
			json.put("model", detail);
		} catch (Exception e) {
			json.put("code", 400);
			json.put("msg", e.getMessage());			
		}
		return json;
	}
	
	/**
	 * 线下还款
	 * @param id
	 * @return
	 */
	@RequestMapping("/underlinerepay")
	@ResponseBody
	public JSONObject underLineRepay(HttpServletRequest request,String id){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(id)){
			json.put("code", 400);
			json.put("msg", "还款标识为空");
			return json;
		}
		try {
			JSONObject object = loanDetailService.underLineRepay(id,request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			BusLoanDetail ld = loanDetailService.selectByPrimaryKey(id);
			BusLoan busLoan = loanService.selectByPrimaryKey(ld.getLoanId());
			AppEntry entry = appEntryService.queryByAppModeId(busLoan.getIntoPieceId());
			JSONObject node = flowMgrImpl.getNextTask(entry);
			String nextNodeId = node.getJSONArray("nodes").getJSONObject(0).getString("nodeId");
			JPushDO.Do(intoPieceService, appEntryService, flowNodeService, jpushUtils, applicationService, busLoan.getIntoPieceId(), nextNodeId, request);
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	/**
	 * 线上还款
	 * @param id
	 * @return
	 */
	@RequestMapping("/onLineRepay")
	@ResponseBody
	public JSONObject onLineRepay(HttpServletRequest request,String id){
		JSONObject json = new JSONObject();
		if(StrUtils.isEmpty(id)){
			json.put("code", 400);
			json.put("msg", "贷款明细id为空");
			return json;
		}
		try {
			//贷款明细
			BusLoanDetail loanDetail = loanDetailService.selectByPrimaryKey(id);
			BusLoan loan = loanService.selectByPrimaryKey(loanDetail.getLoanId());
			
			String userId = request.getSession().getAttribute(Constants.SESSION_USERCD).toString();
			SysWebUser user = userService.selectUserById(userId);
			Map<String,String> map = new HashMap<String, String>();
			String signature = user.getUserId() + user.getUsername() + user.getPassword();
			map.put("signature", DigestUtils.md5Hex(signature));
			map.put("userid", userId);
			map.put("ids", loanDetail.getLoanId());
			map.put("companyType", loan.getChannel());
			
			//先锋免短免密，新协议支付
			String xfNoSmsNoContractNo = HttpClientUtil.doPost(pieceConfig.getXfNoSmsNoContractNo(), map, "utf-8");
			
			JSONObject httpResult = JSONObject.parseObject(xfNoSmsNoContractNo);
			if(Constants.GATE_RESULT_SUCCESS.equals(httpResult.get("isSuccess"))){
				json.put("msg", httpResult.get("msg"));
			}else{
				throw new Exception("先锋新协议支付调用失败");
			}
			
			BusLoanDetail ld = loanDetailService.selectByPrimaryKey(id);
			BusLoan busLoan = loanService.selectByPrimaryKey(ld.getLoanId());
			AppEntry entry = appEntryService.queryByAppModeId(busLoan.getIntoPieceId());
			JSONObject node = flowMgrImpl.getNextTask(entry);
			String nextNodeId = node.getJSONArray("nodes").getJSONObject(0).getString("nodeId");
			JPushDO.Do(intoPieceService, appEntryService, flowNodeService, jpushUtils, applicationService, busLoan.getIntoPieceId(), nextNodeId, request);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			json.put("code", 400);
			json.put("msg", e.getMessage());
		}
		return json;
	}	
	
	/**
	 * 还款列表
	 * @return
	 */
	@RequestMapping("/loanfinish")
	public String loanFinish(){
		return "postLending/loanfinishlist";
	}
	
	/**
	 * 异步获取列表 还款完成
	 * @param currentPage
	 * @param pageSize
	 * @param orgName
	 * @param memberName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/finishlist")
    @ResponseBody
    public PageBeanUtil<LoanMap> finishList(int currentPage,int pageSize,String orgName,
    		String memberName,String ipstatus,HttpServletRequest request) throws Exception{
		try {
			LoanMap loanMap = new LoanMap();
			if(StrUtils.isNotEmpty(orgName))
				loanMap.setFullCname(orgName);
			if(StrUtils.isNotEmpty(memberName))
				loanMap.setMemberName(memberName);
			if(StrUtils.isNotEmpty(ipstatus))
				loanMap.setIpStatus(ipstatus);
			loanMap.setPersonId(request.getSession().getAttribute(Constants.SESSION_PERSONCD).toString());
			loanMap.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
	    	return  loanService.loanFinishPage(currentPage, pageSize, loanMap);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return null;
    }
	
	/**
	 * 
	 * @Title: yuqiBad 
	 * @Description: 逾期坏账
	 * @param @param request
	 * @param @param id
	 * @param @return     
	 * @return JSONObject     
	 * @throws
	 */
	@RequestMapping("/yuqiBad")
	@ResponseBody
	public void yuqiBad(HttpServletRequest request,String id){
		try {
			AppEntry entry = appEntryService.queryByAppModeId(id);
			JSONObject node = flowMgrImpl.getNextTask(entry);
			String nextNodeId = node.getJSONArray("nodes").getJSONObject(0).getString("nodeId");
			JPushDO.Do(intoPieceService, appEntryService, flowNodeService, jpushUtils, applicationService, id, nextNodeId, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
