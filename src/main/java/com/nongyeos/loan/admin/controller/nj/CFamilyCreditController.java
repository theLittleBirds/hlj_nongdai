package com.nongyeos.loan.admin.controller.nj;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusFamilyCredit;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusRejectReason;
import com.nongyeos.loan.admin.service.IFamilyCreditService;
import com.nongyeos.loan.admin.service.IIntoPieceService;
import com.nongyeos.loan.admin.service.IRejectReasonService;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.entity.FlowNode;
import com.nongyeos.loan.app.service.IAppEntryService;
import com.nongyeos.loan.app.service.IFlowEntranceService;
import com.nongyeos.loan.app.service.IFlowNodeService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

/**
 * 
 * Title:CFamilyCapitalController 
 * Description:  
 * @author lcg
 * @date 2018年5月8日 下午3:13:04
 */
@Controller
@RequestMapping("/nj/familyCredit")
public class CFamilyCreditController {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(CFamilyCreditController.class);
	
	@Autowired
	private IIntoPieceService intoPieceService;
	
	@Autowired
	private IFlowEntranceService flowEntranceService;
	@Autowired
	private IFlowNodeService fleNodeService;
	@Autowired
	private IAppEntryService appEntryService;//业务_状态记录
	
	@Autowired
	private IFamilyCreditService familyCreditService;
	
	@Autowired
	private IRejectReasonService reasonService;
	
	/**
	 * 
	 * @Title: saveFamilyCredit 
	 * @Description: 保存家庭征信情况
	 * @param @param request
	 * @param @return     
	 * @return JSONObject     
	 * @throws
	 */
	@RequestMapping("/save")
	@ResponseBody
	public JSONObject saveFamilyCredit(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String intoPieceId = request.getParameter("intoPieceId");
		if(StringUtils.isEmpty(intoPieceId)){
			retJson.put("message", "家庭征信进件标识为空");
			retJson.put("errno", 3001);
			response.setStatus(400);
			return retJson;
		}
		String loginId = (String) request.getAttribute("loginId");
		BusFamilyCredit familyCredit = new BusFamilyCredit();
		String isWhite = request.getParameter("isWhite");
		String hasOverdueCurrent = request.getParameter("hasOverdueCurrent");
		String hasOverdueOutNinetyDay = request.getParameter("hasOverdueOutNinetyDay");
		String loanTimesWithFiveYear = request.getParameter("loanTimesWithFiveYear");
		String ninetyDayOverdueMoney = request.getParameter("ninetyDayOverdueMoney");
		String orgSearchTimesWithCredit = request.getParameter("orgSearchTimesWithCredit");
		String overdueTimes = request.getParameter("overdueTimes");
		String selSearchTimesWithCredit = request.getParameter("selSearchTimesWithCredit");
		String negativeInformation = request.getParameter("negativeInformation");
		try {
			
			familyCredit.setIntoPieceId(intoPieceId);
			familyCredit.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			BusFamilyCredit fc = familyCreditService.selectByIpId(familyCredit);
			if(fc==null){
				familyCredit.setId(UUIDUtil.getUUID());
				familyCredit.setIntoPieceId(intoPieceId);
				familyCredit.setIsWhite(StringUtils.isEmpty(isWhite)?null:new Integer(isWhite));
				familyCredit.setHasOverdueCurrent(StringUtils.isEmpty(hasOverdueCurrent)?null:new Integer(hasOverdueCurrent));
				familyCredit.setHasOverdueOutNinetyDay(StringUtils.isEmpty(hasOverdueOutNinetyDay)?null:new Integer(hasOverdueOutNinetyDay));
				familyCredit.setLoanTimesWithFiveYear(StringUtils.isEmpty(loanTimesWithFiveYear)?null:new Integer(loanTimesWithFiveYear));
				familyCredit.setNinetyDayOverdueMoney(StringUtils.isEmpty(ninetyDayOverdueMoney)?null:new BigDecimal(ninetyDayOverdueMoney));
				familyCredit.setOrgSearchTimesWithCredit(StringUtils.isEmpty(orgSearchTimesWithCredit)?null:new Integer(orgSearchTimesWithCredit));
				familyCredit.setOverdueTimes(StringUtils.isEmpty(overdueTimes)?null:new Integer(overdueTimes));
				familyCredit.setSelSearchTimesWithCredit(StringUtils.isEmpty(selSearchTimesWithCredit)?null:new Integer(selSearchTimesWithCredit));
				familyCredit.setNegativeInformation(StringUtils.isEmpty(negativeInformation)?null:new Integer(negativeInformation));
				familyCredit.setCreOperId(loginId);
				familyCredit.setUpdOperId(loginId);
				familyCredit.setCreDate(DateUtils.getNowDate());
				familyCredit.setUpdDate(DateUtils.getNowDate());
				familyCreditService.insert(familyCredit);
			}else{
				fc.setIsWhite(StringUtils.isEmpty(isWhite)?null:new Integer(isWhite));
				fc.setHasOverdueCurrent(StringUtils.isEmpty(hasOverdueCurrent)?null:new Integer(hasOverdueCurrent));
				fc.setHasOverdueOutNinetyDay(StringUtils.isEmpty(hasOverdueOutNinetyDay)?null:new Integer(hasOverdueOutNinetyDay));
				fc.setLoanTimesWithFiveYear(StringUtils.isEmpty(loanTimesWithFiveYear)?null:new Integer(loanTimesWithFiveYear));
				fc.setNinetyDayOverdueMoney(StringUtils.isEmpty(ninetyDayOverdueMoney)?null:new BigDecimal(ninetyDayOverdueMoney));
				fc.setOrgSearchTimesWithCredit(StringUtils.isEmpty(orgSearchTimesWithCredit)?null:new Integer(orgSearchTimesWithCredit));
				fc.setOverdueTimes(StringUtils.isEmpty(overdueTimes)?null:new Integer(overdueTimes));
				fc.setSelSearchTimesWithCredit(StringUtils.isEmpty(selSearchTimesWithCredit)?null:new Integer(selSearchTimesWithCredit));
				fc.setNegativeInformation(StringUtils.isEmpty(negativeInformation)?null:new Integer(negativeInformation));
				fc.setUpdOperId(loginId);
				fc.setUpdDate(DateUtils.getNowDate());
				familyCreditService.updateByPrimaryKey(fc);
			}
			BusIntoPiece intoPiece = intoPieceService.selectByPrimaryKey(intoPieceId);
			if(!StringUtils.isEmpty(ninetyDayOverdueMoney)&&new BigDecimal(ninetyDayOverdueMoney).compareTo(new BigDecimal(1000))==1){
				BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,intoPiece.getIdCard(),"逾期90天以上金额大于1000元",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
				reasonService.insert(busRejectReason);
				retJson.put("message", "不满足借款条件,已拒件！");
				retJson.put("errno", 3034);
				response.setStatus(400);
	        	return retJson;
	        }
	        if(!StringUtils.isEmpty(overdueTimes)&&Integer.parseInt(overdueTimes)>9){
	        	//拒件
	        	BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,intoPiece.getIdCard(),"逾期次数超过9次",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
				reasonService.insert(busRejectReason);
	        	reject(intoPieceId);
				retJson.put("message", "不满足借款条件,已拒件！");
				retJson.put("errno", 3034);
				response.setStatus(400);
	        	return retJson;
	        }
	        if(!StringUtils.isEmpty(selSearchTimesWithCredit)&&Integer.parseInt(selSearchTimesWithCredit)>10){
	        	//拒件
	        	BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,intoPiece.getIdCard(),"网查征信次数超过10次",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
				reasonService.insert(busRejectReason);
	        	reject(intoPieceId);
				retJson.put("message", "不满足借款条件,已拒件！");
				retJson.put("errno", 3034);
				response.setStatus(400);
	        	return retJson;
	        }
	        if(!StringUtils.isEmpty(hasOverdueCurrent)&&Integer.parseInt(hasOverdueCurrent)==1){
	        	//拒件
	        	BusRejectReason busRejectReason = new BusRejectReason(UUIDUtil.getUUID(),intoPieceId,intoPiece.getIdCard(),"征信当前有逾期",DateUtils.getNowDate(),Constants.REJECT_FLAG_NO);
				reasonService.insert(busRejectReason);
	        	reject(intoPieceId);
				retJson.put("message", "不满足借款条件,已拒件！");
				retJson.put("errno", 3034);
				response.setStatus(400);
	        	return retJson;
	        }
			retJson.put("message", "保存成功！");
			retJson.put("errno", 0);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "保存失败！");
			response.setStatus(400);
			return retJson;
		}
		
		return retJson;
	}
	
	@RequestMapping("/familyCreditInfo")
	@ResponseBody
	public JSONObject familyCreditInfo(HttpServletRequest request,HttpServletResponse response){
		JSONObject retJson = new JSONObject();
		String intoPieceId = request.getParameter("intoPieceId");
		if(StringUtils.isEmpty(intoPieceId)){
			retJson.put("message", "家庭财产进件标识为空");
			retJson.put("errno", 3001);
			response.setStatus(400);
			return retJson;
		}
		try {
			BusFamilyCredit familyCredit = new BusFamilyCredit();
			familyCredit.setIntoPieceId(intoPieceId);
			familyCredit.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
			BusFamilyCredit fc = familyCreditService.selectByIpId(familyCredit);
			if(fc==null){
				fc = new BusFamilyCredit();
			}
			JSONObject obj = new JSONObject();
			obj.put("isWhite", fc.getIsWhite()==null?"":fc.getIsWhite().toString());
			obj.put("loanTimesWithFiveYear", fc.getLoanTimesWithFiveYear()==null?"":fc.getLoanTimesWithFiveYear().toString());
			obj.put("overdueTimes", fc.getOverdueTimes()==null?"":fc.getOverdueTimes().toString());
			obj.put("hasOverdueCurrent", fc.getHasOverdueCurrent()==null?"":fc.getHasOverdueCurrent().toString());
			obj.put("hasOverdueOutNinetyDay", fc.getHasOverdueOutNinetyDay()==null?"":fc.getHasOverdueOutNinetyDay().toString());
			obj.put("orgSearchTimesWithCredit", fc.getOrgSearchTimesWithCredit()==null?"":fc.getOrgSearchTimesWithCredit().toString());
			obj.put("selSearchTimesWithCredit", fc.getSelSearchTimesWithCredit()==null?"":fc.getSelSearchTimesWithCredit().toString());
			obj.put("ninetyDayOverdueMoney", fc.getNinetyDayOverdueMoney()==null?"":fc.getNinetyDayOverdueMoney().toString());
			obj.put("negativeInformation", fc.getNegativeInformation()==null?"":fc.getNegativeInformation().toString());
			retJson.put("errno", 0);
			response.setStatus(200);
			retJson.put("data", obj);
		} catch (Exception e) {
			e.printStackTrace();
			retJson.put("message", "系统错误！");
			response.setStatus(400);
			return retJson;
		}
		return retJson;
	}
	
	protected void reject(String intoPieceId) throws Exception {
		//自动拒件	 自动拒件让流程节点直接到已拒件
		BusIntoPiece primaryKey = intoPieceService.selectByPrimaryKey(intoPieceId);
		if(primaryKey == null){
			throw new Exception("没有找到相应进件");
		}
		
		//查询流程节点
		FlowNode flowNode = new FlowNode();
		flowNode.setAppId(primaryKey.getModelId());
		flowNode.setEname(Constants.FLOW_REFUSE);
		FlowNode fleNodeId = fleNodeService.queryByEnameAndModel(flowNode);
		//获的相关进件业务数据
		AppEntry appEntry = appEntryService.queryByAppModeId(primaryKey.getId());
		appEntry.setNodeId(fleNodeId.getNodeId());//流程节点id 主键
		appEntry.setNodeName(fleNodeId.getEname());//流程节点状态码
		//更改拒件流程节点
		appEntryService.updateByAppEntrySelective(appEntry);
	}
	
	
	
}
