package com.nongyeos.loan.admin.controller;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusLoan;
import com.nongyeos.loan.admin.entity.BusMember;
import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.mapper.BusIntoPieceMapper;
import com.nongyeos.loan.admin.model.ExportIntoPiece;
import com.nongyeos.loan.admin.service.ILoanService;
import com.nongyeos.loan.admin.service.IMemberService;
import com.nongyeos.loan.admin.service.IOrgService;
import com.nongyeos.loan.admin.service.IPersonService;
import com.nongyeos.loan.app.entity.AppApplication;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.entity.BusFins;
import com.nongyeos.loan.app.entity.FlowNode;
import com.nongyeos.loan.app.service.IAppEntryService;
import com.nongyeos.loan.app.service.IApplicationService;
import com.nongyeos.loan.app.service.IBusFinsService;
import com.nongyeos.loan.app.service.IFlowNodeService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.StrUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Controller
@RequestMapping("/exportintoPiece")
public class ExportIntoPieceController {
	
	@Autowired
	private IBusFinsService busFinsService;
	
	@Autowired
	private IOrgService orgService;
	
	@Autowired
	private IMemberService memberService;
	
	@Autowired
	private IApplicationService applicationService;
	
	@Autowired
	private IPersonService personService;
	
	@Autowired
	private BusIntoPieceMapper intoPieceMapper;
	
	@Autowired
	private IFlowNodeService fleNodeService;
	
	@Autowired
	private IAppEntryService appEntryService;
	
	@Autowired
	private ILoanService loanService;
	
	@RequestMapping("/export")
    @ResponseBody
	public String export(String path) throws Exception{
		if(StrUtils.isEmpty(path)){
			System.err.println("path为空");
			return "path为空";
		}
		File file = new File(path);
		if(!file.exists()){
			System.err.println("文件不存在");
			return "文件不存在";
		}
		ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);
        List<ExportIntoPiece> list = ExcelImportUtil.importExcel(file,ExportIntoPiece.class, params);
        int size = list.size();
        if(size == 0){
        	System.err.println("无数据");
			return "无数据";
        }
        try {
        	List<BusFins> finsList = busFinsService.queryAll();
            Map<String, String> finsMap = new HashMap<String, String>();
            for (int i = 0; i < finsList.size(); i++) {
            	finsMap.put(finsList.get(i).getCname(), finsList.get(i).getFinsId());
    		}
            
            List<SysOrg> orgList = orgService.selectAll();
            Map<String, String> orgMap = new HashMap<String, String>();
            for (int i = 0; i < orgList.size(); i++) {
            	orgMap.put(orgList.get(i).getFullCname(), orgList.get(i).getOrgId());
    		}
            
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < size; i++) {
            	ExportIntoPiece export = list.get(i);
            	
            	if(StrUtils.isEmpty(export.getIdCard())){
            		throw new Exception(export.getName()+"身份证号为空");
            	}
            	String finsId = finsMap.get(export.getFins());
            	String orgId = orgMap.get(export.getOrg());
            	AppApplication app = new AppApplication();
            	app.setFinsCode(finsId);
            	//1土地贷
            	app.setEname("1");
            	List<AppApplication> appList = applicationService.quetyByFinsIdType(app);
            	if(appList == null){
            		throw new Exception(export.getFins()+"未配置贷款流程");
            	}
            	String appId = appList.get(0).getAppId();
            	String start = export.getStartDate().substring(2,4);
            	String end = export.getEndDate().substring(2,4);
            	int term = (Integer.parseInt(end) - Integer.parseInt(start))*12;
            	Date startTime = df.parse(export.getStartDate());
            	
            	BusMember member = memberService.selectByIdCard(export.getIdCard());
            	if(member == null){
            		member = new BusMember();
            		member.setMemberId(UUIDUtil.getUUID());
            		member.setAddress(export.getAddress());
            		member.setIdCard(export.getIdCard());
            		member.setName(export.getName());
            		member.setBankPhone(export.getPhone());
            		member.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
            		member.setCreDate(startTime);
            		member.setUpdDate(startTime);
            		member.setCreOperId("1");
            		member.setUpdOperId("1");
            		memberService.insert(member);
            	}
            	
            	
            	BusIntoPiece ip = new BusIntoPiece();
            	ip.setId(UUIDUtil.getUUID());
            	ip.setModelId(appId);
            	ip.setOrgId(orgId);
            	ip.setCapital(new BigDecimal(export.getCapital()));
            	ip.setTerm(term);
            	if(StrUtils.isNotEmpty(export.getType())){
            		ip.setUse(Integer.parseInt(export.getType()));
            	}
            	ip.setMemberId(member.getMemberId());
            	ip.setMemberName(member.getName());
            	ip.setIdCard(member.getIdCard());
            	ip.setPhone(member.getBankPhone());
            	ip.setAddress(member.getAddress());
            	ip.setChannel("HLJSX");
            	//1土地贷
            	ip.setType(1);
            	ip.setLenderId(finsId);
            	List<SysPerson> personList = personService.queryByOrgId(orgId);
            	if(personList.size()>0){
            		ip.setOperUserId(personList.get(0).getPersonId());
            	}
            	ip.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
            	ip.setCreDate(startTime);
            	ip.setUpdDate(startTime);
            	ip.setCreOperId("1");
            	ip.setUpdOperId("1");
            	intoPieceMapper.insert(ip);
            	
            	AppEntry entry = new AppEntry();
            	entry.setEntryId(UUIDUtil.getUUID());
            	entry.setChannelId("3");
            	entry.setAppId(appId);
            	entry.setFromId("t_bus_intopiece");
            	entry.setModeId(ip.getId());
            	entry.setOrgId(orgId);
            	entry.setFinsId(finsId);
            	entry.setAppId(appId);
            	entry.setAppName(appList.get(0).getCname());
            	if("0".equals(export.getBalance())){
            		entry.setNodeName(Constants.FLOW_REPAY_COMPLETE);
            	}else{
            		entry.setNodeName(Constants.FLOW_REPAYMENT);
            	}
            	FlowNode node = new  FlowNode();
            	node.setAppId(appId);
            	node.setEname(entry.getNodeName());
            	FlowNode node1 = fleNodeService.queryByEnameAndModel(node);
            	if(node1 == null){
            		throw new Exception(export.getFins()+"流程节点未找到");
            	}
            	entry.setNodeId(node1.getNodeId());
            	entry.setCreDate(startTime);
            	entry.setUpdDate(startTime);
            	appEntryService.addAppEntrySelective(entry);
            	
            	
            	BusLoan loan = new BusLoan();
            	loan.setId(UUIDUtil.getUUID());
            	loan.setIntoPieceId(ip.getId());
            	loan.setCapital(ip.getCapital());
            	loan.setMemberId(ip.getMemberId());
            	loan.setMemberName(ip.getMemberName());
            	loan.setTerm(term);
            	loan.setLoanedManFee(ip.getCapital());
            	loan.setReceiveCapital(ip.getCapital().subtract(new BigDecimal(export.getBalance())).setScale(2));
            	loan.setUse(export.getType());
            	loan.setLoanDate(startTime);
            	loan.setStartDate(export.getStartDate());
            	loan.setEndDate(export.getEndDate());
            	loan.setLastRepaymentDate(df.parse(export.getEndDate()));
            	loan.setChannel("HLJSX");
            	loan.setStatus(1);
            	loan.setLenderId(finsId);
            	loan.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
            	loan.setCreDate(startTime);
            	loan.setUpdDate(startTime);
            	loan.setCreOperId("1");
            	loan.setUpdOperId("1");
            	loanService.insert(loan);
            	System.err.println("第"+ i +"数据导入成功");
			}
            
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
        
		return "success";
	}
}
