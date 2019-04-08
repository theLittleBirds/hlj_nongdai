package com.nongyeos.loan.admin.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusLoan;
import com.nongyeos.loan.admin.entity.BusLoanDetail;
import com.nongyeos.loan.admin.entity.BusLoandetailRepayment;
import com.nongyeos.loan.admin.entity.BusMoneyLog;
import com.nongyeos.loan.admin.entity.BusProduct;
import com.nongyeos.loan.admin.mapper.BusIntoPieceMapper;
import com.nongyeos.loan.admin.mapper.BusLoanDetailMapper;
import com.nongyeos.loan.admin.mapper.BusLoanMapper;
import com.nongyeos.loan.admin.mapper.BusLoandetailRepaymentMapper;
import com.nongyeos.loan.admin.mapper.BusMoneyLogMapper;
import com.nongyeos.loan.admin.model.RepayXlsx;
import com.nongyeos.loan.admin.service.ILoanDetailService;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.entity.BusFins;
import com.nongyeos.loan.app.mapper.AppEntryMapper;
import com.nongyeos.loan.app.mapper.BusFinsMapper;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.GetRepaymentUtils;
import com.nongyeos.loan.base.util.NumberUtils;
import com.nongyeos.loan.base.util.UUIDUtil;
import com.nongyeos.loan.core.model.BusinessObject;
import com.nongyeos.loan.core.service.FlowMgr;

@Service("loanDetailService")
public class LoanDetailServiceImpl implements ILoanDetailService {
	
	@Autowired
	private BusLoanDetailMapper loanDetailMapper;
	
	@Autowired
	private BusMoneyLogMapper moneyLogMapper;
	
	@Autowired
	private BusLoanMapper loanMapper; 
	
	@Autowired
	private AppEntryMapper appEntryMapper;
	
	@Autowired
	private FlowMgr flowMgrImpl;
	
	@Autowired
	private BusFinsMapper finsMapper;
	
	@Autowired
	private BusIntoPieceMapper intoPieceMapper;
	
	@Autowired
	private BusLoandetailRepaymentMapper loandetailRepaymentMapper;

	@Override
	public List<Map<String, Object>> queryLoanDetail(BusLoanDetail record)
			throws Exception {
		try {
			if(record == null){
				throw new Exception("代扣明细对象为空");
			}
			if(StringUtils.isEmpty(record.getLoanId())){
				throw new Exception("代扣明细loanid为空");
			}
			
			return loanDetailMapper.queryLoanDetail(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKeySelective(BusLoanDetail record)
			throws Exception {
		if(record == null)
			throw new Exception("借款详情模板为空");
		try {
			return loanDetailMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<BusLoanDetail> selectByLoanId(String loanId) throws Exception {
		if(loanId == null || "".equals(loanId))
			throw new Exception("借款标识为空");
		try {
			return loanDetailMapper.selectByLoanId(loanId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusLoanDetail selectByPrimaryKey(String id) throws Exception {
		if(id == null || "".equals(id))
			throw new Exception("借款标识为空");
		try {
			return loanDetailMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusLoanDetail selectNextRepay(BusLoanDetail record) throws Exception {
		if(record == null)
			throw new Exception("借款详情模板为空");
		try {
			return loanDetailMapper.selectNextRepay(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	/**
	 * ldrId为loandetailRepayment的id
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public JSONObject onLineRepay(String ldrId,String pId) throws Exception{
		JSONObject json = new JSONObject();
		
		/**
		 * 贷款明细记录
		 */
		BusLoandetailRepayment ldr = loandetailRepaymentMapper.selectByPrimaryKey(ldrId);
		ldr.setStatus(Constants.LOAN_DETIAL_REPAYMENT_COMPLETE);
		ldr.setUpdateBy(pId);
		ldr.setUpdateDate(new Date());
		loandetailRepaymentMapper.updateByPrimaryKeySelective(ldr);
		
		/**
		 * 贷款明细
		 */
		BusLoanDetail detail = loanDetailMapper.selectByPrimaryKey(ldr.getLoanDetailId());
		detail.setReceiveCapital(detail.getReceiveCapital().add(ldr.getReceiveCapital()));
		detail.setReceiveInterest(detail.getReceiveInterest().add(ldr.getReceiveInterest()));
		detail.setReceiveOverdue(detail.getReceiveOverdue().add(ldr.getReceiveOverdue()));
		
		/**
		 * detail.getCapital().compareTo(detail.getReceiveCapital())<=0
		 * 意思detail.getCapital()小于等于detail.getReceiveCapital()
		 */
		if(detail.getCapital().compareTo(detail.getReceiveCapital())<=0 && 
				detail.getIntrest().compareTo(detail.getReceiveInterest())<=0 ){
			detail.setStatus(Constants.COMPLETE);
			detail.setRecentWaitRep(0);
			// 修改最新待还款详细
			if(detail.getSort() == detail.getTotal()){
				detail.setSort(detail.getSort()+1);
				BusLoanDetail nextDetail = loanDetailMapper.selectNextRepay(detail);
				nextDetail.setRecentWaitRep(1);
				nextDetail.setUpdDate(new Date());
				nextDetail.setUpdOperId(pId);
				loanDetailMapper.updateByPrimaryKey(nextDetail);
			}
			
		}else{
			detail.setStatus(Constants.REPAYMENTING);
		}
		detail.setUpdDate(new Date());
		detail.setUpdOperId(pId);
		loanDetailMapper.updateByPrimaryKey(detail);
		
		BusMoneyLog ml = new BusMoneyLog();
		ml.setId(UUIDUtil.getUUID());
		ml.setCapital(ldr.getReceiveCapital());
		ml.setInterest(ldr.getReceiveInterest());
		ml.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
		ml.setLoanId(detail.getLoanId());
		ml.setType(Constants.MONEY_IN);
		ml.setUpdDate(new Date());
		ml.setUpdOperId(pId);
		moneyLogMapper.insert(ml);
		
		BusLoan loan = loanMapper.selectByPrimaryKey(detail.getLoanId());
		//还款完成
		if(detail.getSort() == detail.getTotal()){
			loan.setReceiveCapital(loan.getReceiveCapital().add(ldr.getReceiveCapital()));
			loan.setReceiveInterest(loan.getReceiveInterest().add(ldr.getReceiveInterest()));
			loan.setReceiveOverdue(loan.getReceiveOverdue().add(ldr.getReceiveOverdue()));
			loan.setStatus(Constants.LOAN_FINISH);
			loanMapper.updateByPrimaryKey(loan);
			
			/**
			 * 更新流程
			 */
			AppEntry entry = appEntryMapper.queryByAppModeId(loan.getIntoPieceId());
			if(entry == null)
				throw new Exception("无流程记录");
			JSONObject node = flowMgrImpl.getNextTask(entry);
			if(node != null && node.getIntValue("code") == 200){
				JSONArray arr = node.getJSONArray("nodes");
				if(arr != null){
					String nextNodeId = arr.getJSONObject(0).getString("nodeId");
					String pcId = node.getString("pcId");
					BusinessObject business = new BusinessObject(entry,loan,null);
					JSONObject obj =  flowMgrImpl.next(business, nextNodeId, pcId, pId);
					if(obj.getIntValue("code") == 200){
						obj.put("msg", "所有还原已还清");
					}
					return obj;
				}
			}
			throw new Exception("未配置下一步节点");
		}else{
			
			BusLoanDetail nextDetail = loanDetailMapper.selectNextRepay(detail);
			/**
			 * 更新贷款信息
			 */
			loan.setReceiveCapital(loan.getReceiveCapital().add(ldr.getReceiveCapital()));
			loan.setReceiveInterest(loan.getReceiveInterest().add(ldr.getReceiveInterest()));
			loan.setReceiveOverdue(loan.getReceiveOverdue().add(ldr.getReceiveOverdue()));
			loan.setFirstRepaymentDate(nextDetail.getDeadlineDate());
			//逾期、坏账
			if(loan.getStatus() == Constants.LOAN_OVERDUE || loan.getStatus() == Constants.LOAN_BAD){
				if(new Date().before(nextDetail.getDeadlineDate())){
					loan.setStatus(Constants.LOAN_NORMAL);
				}
			}
			loanMapper.updateByPrimaryKey(loan);
			json.put("code", 200);
			json.put("msg", "本期还款完成");
			return json;
		}					
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public JSONObject underLineRepay(String id,String pId) throws Exception{
		JSONObject json = new JSONObject();
		BusLoanDetail detail = loanDetailMapper.selectByPrimaryKey(id);
		BigDecimal capital = detail.getCapital();
		BigDecimal intrest = detail.getIntrest();
		detail.setReceiveCapital(capital);
		detail.setReceiveInterest(intrest);
		detail.setStatus(Constants.COMPLETE);
		detail.setRecentWaitRep(0);
		detail.setUpdDate(new Date());
		detail.setUpdOperId(pId);
		loanDetailMapper.updateByPrimaryKey(detail);
		
		BusMoneyLog ml = new BusMoneyLog();
		ml.setId(UUIDUtil.getUUID());
		ml.setCapital(capital);
		ml.setInterest(intrest);
		ml.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
		ml.setLoanId(detail.getLoanId());
		ml.setType(Constants.MONEY_IN);
		ml.setUpdDate(new Date());
		ml.setUpdOperId(pId);
		moneyLogMapper.insert(ml);
		
		BusLoan loan = loanMapper.selectByPrimaryKey(detail.getLoanId());
		//还款完成
		if(detail.getSort() == detail.getTotal()){
			loan.setReceiveCapital(loan.getReceiveCapital() == null ? capital : loan.getReceiveCapital().add(capital));
			loan.setReceiveInterest(loan.getReceiveInterest() == null ? intrest : loan.getReceiveInterest().add(intrest));
			loan.setStatus(Constants.LOAN_FINISH);
			loanMapper.updateByPrimaryKey(loan);
			AppEntry entry = appEntryMapper.queryByAppModeId(loan.getIntoPieceId());
			if(entry == null)
				throw new Exception("无流程记录");
			JSONObject node = flowMgrImpl.getNextTask(entry);
			if(node != null && node.getIntValue("code") == 200){
				JSONArray arr = node.getJSONArray("nodes");
				if(arr != null){
					String nextNodeId = arr.getJSONObject(0).getString("nodeId");
					String pcId = node.getString("pcId");
					BusinessObject business = new BusinessObject(entry,loan,null);
					JSONObject obj =  flowMgrImpl.next(business, nextNodeId, pcId, pId);
					if(obj.getIntValue("code") == 200){
						obj.put("msg", "所有还原已还清");
					}
					return obj;
				}
			}
			throw new Exception("未配置下一步节点");
		}else{
			detail.setSort(detail.getSort()+1);
			BusLoanDetail nextDetail = loanDetailMapper.selectNextRepay(detail);
			nextDetail.setRecentWaitRep(1);
			detail.setUpdDate(new Date());
			detail.setUpdOperId(pId);
			loanDetailMapper.updateByPrimaryKey(nextDetail);
			loan.setReceiveCapital(loan.getReceiveCapital() == null ? capital : loan.getReceiveCapital().add(capital));
			loan.setReceiveInterest(loan.getReceiveInterest() == null ? intrest : loan.getReceiveInterest().add(intrest));
			loan.setFirstRepaymentDate(nextDetail.getDeadlineDate());
			//逾期、坏账
			if(loan.getStatus() == Constants.LOAN_OVERDUE || loan.getStatus() == Constants.LOAN_BAD){
				if(new Date().before(nextDetail.getDeadlineDate())){
					loan.setStatus(Constants.LOAN_NORMAL);
				}
			}
			loanMapper.updateByPrimaryKey(loan);
			json.put("code", 200);
			json.put("msg", "本期还款完成");
			return json;
		}					
	}

	@Override
	public BusLoanDetail selectLastComplete(String intoPieceId) throws Exception {
		if(StringUtils.isEmpty(intoPieceId)){
			throw new Exception("未找到进件标识！");
		}
		try {
			return loanDetailMapper.selectLastComplete(intoPieceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询还款出错！");
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void creatDetail(BusLoan loan,BusProduct product,Date loanTime) throws Exception {
		if(loan == null){
			throw new Exception("借款信息为空！");
		}
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			loanDetailMapper.deleteByLoanId(loan.getId());			
			BusLoanDetail loanDetail = null;
			BigDecimal capital = loan.getCapital();
			int term = loan.getTerm();
			int years =term/12;
			BigDecimal rate = product.getMonthRate();
			Short rateType = product.getMonthRateType();
			Short serviceRateType = product.getServiceRateType();
			BigDecimal serviceRate = product.getServiceRate();
			Date parseByAll = DateUtils.getNowDate();
			if(loanTime!=null){
				parseByAll=loanTime;
			}
			long borrowTime = DateUtils.getUnixTime(parseByAll);
			Short repaymentType = product.getRepaymentWay();
			Map<String, Object> monthRepayment =null;
			switch (repaymentType) {
				//等额本息
			case 1:
				if(serviceRateType.equals(Constants.YEAR)){
					serviceRate=serviceRate.divide(new BigDecimal("12"), 20, RoundingMode.HALF_UP).multiply(new BigDecimal(term));
				}else{
					serviceRate=serviceRate.multiply(new BigDecimal(term));
				}
				if(rateType.equals(Constants.YEAR)){
					rate = rate.divide(new BigDecimal("12"), 20, RoundingMode.HALF_UP);
				}
				monthRepayment = GetRepaymentUtils.equalMonth(capital, term, rate, serviceRate, borrowTime, "");
				break;
				//先息后本，无服务费
			case 2:
			case 8:
				if(rateType.equals(Constants.YEAR)){
					rate = rate.divide(new BigDecimal("12"), 20, RoundingMode.HALF_UP);
				}
				monthRepayment = GetRepaymentUtils.equalEndMonth(capital, term, rate, borrowTime, "");
				break;
				//组合贷款
			case 3:
//				Map<String, Object> equalMonth = GetRepaymentUtils.equalMonth(capital, term, rate, serviceRate, borrowTime, "");
				break;
				//惠农通
			case 4:
//				Map<String, Object> equalMonth = GetRepaymentUtils.equalMonth(capital, term, rate, serviceRate, borrowTime, "");
				break;
				//按季度还息，到期还本
			case 5:
				if(serviceRateType.equals(Constants.YEAR)){
					serviceRate=serviceRate.divide(new BigDecimal("12"), 20, RoundingMode.HALF_UP).multiply(new BigDecimal(term));
				}else{
					serviceRate=serviceRate.multiply(new BigDecimal(term));
				}
				if(rateType.equals(Constants.YEAR)){
					rate = rate.divide(new BigDecimal("12"), 20, RoundingMode.HALF_UP);
				}
				monthRepayment = GetRepaymentUtils.quarterlyRepayment(capital, term, rate, serviceRate, borrowTime, "");
				break;
				//一次性还清本息
			case 6:
				if(serviceRateType.equals(Constants.MONTH)){
					serviceRate=serviceRate.multiply(new BigDecimal("12"));
				}
				if(rateType.equals(Constants.MONTH)){
					rate = rate.multiply(new BigDecimal("12"));
				}
				monthRepayment = GetRepaymentUtils.shouXinYearsPayBack(capital, years, rate, serviceRate, borrowTime, "");
				break;
			case 7:
				if(serviceRateType.equals(Constants.YEAR)){
					serviceRate=serviceRate.divide(new BigDecimal("12"), 20, RoundingMode.HALF_UP).multiply(new BigDecimal(term));
				}else{
					serviceRate=serviceRate.multiply(new BigDecimal(term));
				}
				if(rateType.equals(Constants.YEAR)){
					rate = rate.divide(new BigDecimal("12"), 20, RoundingMode.HALF_UP);
				}
				monthRepayment = GetRepaymentUtils.equalMonthHN(capital, term, rate, serviceRate, borrowTime, "");
				break;
			default:
				break;
			}
			if(monthRepayment!=null){
				List<Map<String, Object>> list = (List<Map<String, Object>>)monthRepayment.get("list");
				if(repaymentType == 6){
					for (int i = 0; i < list.size(); i++) {
						Map<String, Object> map = list.get(i);
						loanDetail = new BusLoanDetail(); 
						loanDetail.setId(UUIDUtil.getUUID());
		                loanDetail.setOrderId(GetRepaymentUtils.createLoanDetailOrderId());
		                loanDetail.setLoanId(loan.getId());
		                loanDetail.setCapital(NumberUtils.toBigDecimal(map.get("yearCapital")));
		                loanDetail.setIntrest(NumberUtils.toBigDecimal(map.get("interest")));
		                loanDetail.setStatus(Constants.WAIT_REPAYMENT);
		                loanDetail.setReceiveCapital(new BigDecimal("0.00"));
		                loanDetail.setReceiveInterest(new BigDecimal("0.00"));
		                loanDetail.setReceiveOverdue(new BigDecimal("0.00"));
		                loanDetail.setServiceFee(NumberUtils.toBigDecimal(map.get("serviceFee")));
		                loanDetail.setDeadlineDate(DateUtils.parseDateByTime(NumberUtils.toLong(map.get("payBackDate") == null ? "" : map.get("payBackDate").toString())));
		                loanDetail.setSort(Integer.parseInt(map.get("index").toString()) + 1);
		                loanDetail.setOverplusMoney(NumberUtils.toBigDecimal(map.get("shenbenjin")));
		                loanDetail.setTotal(years);
		                loanDetail.setCreDate(DateUtils.getNowDate());
		                loanDetail.setUpdDate(DateUtils.getNowDate());
		                loanDetail.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
		             // 第一笔，添加最新一笔收款日期
	                  if (Integer.parseInt(map.get("index").toString()) == 0) {
	                	  loan.setFirstRepaymentDate(loanDetail.getDeadlineDate());
	                      loanDetail.setRecentWaitRep(new Integer(Constants.COMMON_YES));
	                      loanDetail.setReceiveServiceFee(loanDetail.getServiceFee());                    
	                  } else {
	                      loanDetail.setRecentWaitRep(new Integer(Constants.COMMON_NO));
	                  }
	                  loanDetailMapper.insert(loanDetail);
	                  
	                  if (Integer.parseInt(map.get("index").toString()) == list.size() - 1) {
	                	loan.setLastRepaymentDate(loanDetail.getDeadlineDate());
	                	loan.setEndDate(df.format(loan.getLastRepaymentDate()));
	                	//还款总额（利息加本金）
	                	loan.setLoanedManFee(NumberUtils.toBigDecimal(map.get("capitalAndInterest")));
	                  }

					}
				}else if(repaymentType==7){
					// 还款明细生成贷款服务费。
		            for (int i = 0; i < list.size(); i++) {
		            	Map<String, Object> map = list.get(i);
						loanDetail = new BusLoanDetail(); 
						loanDetail.setId(UUIDUtil.getUUID());
		                loanDetail.setOrderId(GetRepaymentUtils.createLoanDetailOrderId());
		                loanDetail.setLoanId(loan.getId());
		                loanDetail.setCapital(NumberUtils.toBigDecimal(map.get("capital")));
		                loanDetail.setIntrest(NumberUtils.toBigDecimal(map.get("interest")));
		                loanDetail.setStatus(Constants.WAIT_REPAYMENT);
		                loanDetail.setServiceFee(new BigDecimal("0.00"));
		                loanDetail.setOverplusMoney(new BigDecimal("0.00"));
		                loanDetail.setReceiveCapital(new BigDecimal("0.00"));
		                loanDetail.setReceiveInterest(new BigDecimal("0.00"));
		                loanDetail.setReceiveOverdue(new BigDecimal("0.00"));
		                loanDetail.setDeadlineDate(DateUtils.parseDateByTime(NumberUtils.toLong(map.get("payBackDate") == null ? "" : map.get("payBackDate").toString())));
		                loanDetail.setSort(Integer.parseInt(map.get("index").toString()) + 1);
		                loanDetail.setTotal(term);
		                loanDetail.setCreDate(DateUtils.getNowDate());
		                loanDetail.setUpdDate(DateUtils.getNowDate());
		                loanDetail.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
		                // 第一笔，添加最新一笔收款日期
		                if (Integer.parseInt(map.get("index").toString()) == 0) {
		                    loan.setFirstRepaymentDate(loanDetail.getDeadlineDate());

		                    loanDetail.setRecentWaitRep(new Integer(Constants.COMMON_YES));

		                } else {
		                    loanDetail.setRecentWaitRep(new Integer(Constants.COMMON_NO));
		                }
		                loanDetailMapper.insert(loanDetail);

		                // 最后一笔，添加最后一笔还款日期
		                if (Integer.parseInt(map.get("index").toString()) == list.size() - 1) {
		                    loan.setLastRepaymentDate(loanDetail.getDeadlineDate());
		                  //还款总额（利息加本金）
		                	loan.setLoanedManFee(NumberUtils.toBigDecimal(map.get("capitalAndInterestWithService")));
		                }
		            }
				}else if(repaymentType==2||repaymentType==8){
					// 还款明细生成贷款服务费。
		            for (int i = 0; i < list.size(); i++) {
		            	Map<String, Object> map = list.get(i);
						loanDetail = new BusLoanDetail(); 
						loanDetail.setId(UUIDUtil.getUUID());
		                loanDetail.setOrderId(GetRepaymentUtils.createLoanDetailOrderId());
		                loanDetail.setLoanId(loan.getId());
		                loanDetail.setCapital(NumberUtils.toBigDecimal(map.get("capital")));
		                loanDetail.setIntrest(NumberUtils.toBigDecimal(map.get("interest")));
		                loanDetail.setStatus(Constants.WAIT_REPAYMENT);
		                loanDetail.setServiceFee(new BigDecimal("0.00"));
		                loanDetail.setOverplusMoney(new BigDecimal("0.00"));
		                loanDetail.setReceiveCapital(new BigDecimal("0.00"));
		                loanDetail.setReceiveInterest(new BigDecimal("0.00"));
		                loanDetail.setReceiveOverdue(new BigDecimal("0.00"));
		                loanDetail.setDeadlineDate(DateUtils.parseDateByTime(NumberUtils.toLong(map.get("payBackDate") == null ? "" : map.get("payBackDate").toString())));
		                loanDetail.setSort(Integer.parseInt(map.get("index").toString()) + 1);
		                loanDetail.setTotal(term);
		                loanDetail.setCreDate(DateUtils.getNowDate());
		                loanDetail.setUpdDate(DateUtils.getNowDate());
		                loanDetail.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
		                // 第一笔，添加最新一笔收款日期
		                if (Integer.parseInt(map.get("index").toString()) == 0) {
		                    loan.setFirstRepaymentDate(loanDetail.getDeadlineDate());

		                    loanDetail.setRecentWaitRep(new Integer(Constants.COMMON_YES));

		                } else {
		                    loanDetail.setRecentWaitRep(new Integer(Constants.COMMON_NO));
		                }
		                loanDetailMapper.insert(loanDetail);

		                // 最后一笔，添加最后一笔还款日期
		                if (Integer.parseInt(map.get("index").toString()) == list.size() - 1) {
		                    loan.setLastRepaymentDate(loanDetail.getDeadlineDate());
		                  //还款总额（利息加本金）
		                	loan.setLoanedManFee(NumberUtils.toBigDecimal(map.get("capitalAndInterest")));
		                }
		            }
				}else{

					// 还款明细生成贷款服务费。
		            for (int i = 0; i < list.size(); i++) {
		            	Map<String, Object> map = list.get(i);
						loanDetail = new BusLoanDetail(); 
						loanDetail.setId(UUIDUtil.getUUID());
		                loanDetail.setOrderId(GetRepaymentUtils.createLoanDetailOrderId());
		                loanDetail.setLoanId(loan.getId());
		                loanDetail.setCapital(NumberUtils.toBigDecimal(map.get("monthMoney")));
		                loanDetail.setIntrest(NumberUtils.toBigDecimal(map.get("monthFee")));
		                loanDetail.setStatus(Constants.WAIT_REPAYMENT);
		                loanDetail.setServiceFee(new BigDecimal("0.00"));
		                loanDetail.setOverplusMoney(new BigDecimal("0.00"));
		                loanDetail.setReceiveCapital(new BigDecimal("0.00"));
		                loanDetail.setReceiveInterest(new BigDecimal("0.00"));
		                loanDetail.setReceiveOverdue(new BigDecimal("0.00"));
		                loanDetail.setDeadlineDate(DateUtils.parseDateByTime(NumberUtils.toLong(map.get("payBackDate") == null ? "" : map.get("payBackDate").toString())));
		                loanDetail.setSort(Integer.parseInt(map.get("index").toString()) + 1);
		                loanDetail.setTotal(term);
		                loanDetail.setCreDate(DateUtils.getNowDate());
		                loanDetail.setUpdDate(DateUtils.getNowDate());
		                loanDetail.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
		                // 第一笔，添加最新一笔收款日期
		                if (Integer.parseInt(map.get("index").toString()) == 0) {
		                    loan.setFirstRepaymentDate(loanDetail.getDeadlineDate());

		                    loanDetail.setRecentWaitRep(new Integer(Constants.COMMON_YES));

		                } else {
		                    loanDetail.setRecentWaitRep(new Integer(Constants.COMMON_NO));
		                }
		                loanDetailMapper.insert(loanDetail);

		                // 最后一笔，添加最后一笔还款日期
		                if (Integer.parseInt(map.get("index").toString()) == list.size() - 1) {
		                    loan.setLastRepaymentDate(loanDetail.getDeadlineDate());
		                  //还款总额（利息加本金）
		                	loan.setLoanedManFee(NumberUtils.toBigDecimal(map.get("capitalAndInterest")));
		                }
		            }
				
				}
				loan.setLoanDate(loanTime);
				loan.setReceiveCapital(new BigDecimal(0));
				loan.setReceiveInterest(new BigDecimal(0));
				loan.setReceiveOverdue(new BigDecimal(0));
				loanMapper.updateByPrimaryKeySelective(loan);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void importRepayXlse(List<RepayXlsx> list,String personId) throws Exception {
		List<BusFins> finsList =  finsMapper.selectAll();
		Map<String, String> finsMap = new HashMap<String, String>();
		for (int i = 0; i < finsList.size(); i++) {
			finsMap.put(finsList.get(i).getCname(), finsList.get(i).getFinsId());
		}
		for (int i = 0; i < list.size(); i++) {
			RepayXlsx xlsx = list.get(i);
			String finsId = finsMap.get(xlsx.getFins());
			if(finsId == null)
				throw new Exception("金融机构未找到："+xlsx.getFins());
			Map<String,String> para = new HashMap<String, String>();
			para.put("memberName", xlsx.getName());
			para.put("idCard", xlsx.getIdCard());
			para.put("finsId", finsId);
			String intoPieceId = intoPieceMapper.selectRepayXlsx(para);
			if(intoPieceId == null)
				throw new Exception("进件信息未找到："+xlsx.getName()+xlsx.getIdCard());
			BusLoan loan = loanMapper.selectByIpId(intoPieceId);										
			
			if("0".equals(xlsx.getMoney())){
				loan.setReceiveCapital(loan.getLoanedManFee());
				loan.setStatus(Constants.LOAN_FINISH);
				loanMapper.updateByPrimaryKey(loan);
				AppEntry entry = appEntryMapper.queryByAppModeId(loan.getIntoPieceId());
				if(entry == null)
					throw new Exception("无流程记录");
				JSONObject node = flowMgrImpl.getNextTask(entry);
				if(node != null && node.getIntValue("code") == 200){
					JSONArray arr = node.getJSONArray("nodes");
					if(arr != null){
						String nextNodeId = arr.getJSONObject(0).getString("nodeId");
						String pcId = node.getString("pcId");
						BusinessObject business = new BusinessObject(entry,loan,null);
						flowMgrImpl.next(business, nextNodeId, pcId, personId);
					}
				}
			}else{
				loan.setReceiveCapital(loan.getLoanedManFee().subtract(new BigDecimal(xlsx.getMoney())));				
				loanMapper.updateByPrimaryKey(loan);
			}			
		}		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void importFinishXlse(List<RepayXlsx> list, String personId) throws Exception {
		List<BusFins> finsList =  finsMapper.selectAll();
		Map<String, String> finsMap = new HashMap<String, String>();
		for (int i = 0; i < finsList.size(); i++) {
			finsMap.put(finsList.get(i).getCname(), finsList.get(i).getFinsId());
		}
		for (int i = 0; i < list.size(); i++) {
			RepayXlsx xlsx = list.get(i);
			String finsId = finsMap.get(xlsx.getFins());
			if(finsId == null)
				throw new Exception("金融机构未找到："+xlsx.getFins());
			Map<String,String> para = new HashMap<String, String>();
			para.put("memberName", xlsx.getName());
			para.put("idCard", xlsx.getIdCard());
			para.put("finsId", finsId);
			String intoPieceId = intoPieceMapper.selectRepayXlsx(para);
			if(intoPieceId == null)
				throw new Exception("进件信息未找到："+xlsx.getName()+xlsx.getIdCard());
			BusLoan loan = loanMapper.selectByIpId(intoPieceId);
			List<BusLoanDetail> detailList = loanDetailMapper.selectNonRepay(loan.getId());
			BigDecimal capital = new BigDecimal("0");
			BigDecimal interest = new BigDecimal("0");
			for (int j = 0; j < detailList.size(); j++) {
				BusLoanDetail detail = detailList.get(i);
				capital = capital.add(detail.getCapital());
				interest = interest.add(detail.getIntrest());
				detail.setReceiveCapital(detail.getCapital());
				detail.setReceiveInterest(detail.getIntrest());
				detail.setStatus(4);//本期还款完成
				detail.setRecentWaitRep(0);
				detail.setUpdDate(new Date());
				detail.setUpdOperId(personId);
				loanDetailMapper.updateByPrimaryKey(detail);	
			}							
			loan.setReceiveCapital(loan.getReceiveCapital() == null ? capital : loan.getReceiveCapital().add(capital));
			loan.setReceiveInterest(loan.getReceiveInterest() == null ? interest : loan.getReceiveInterest().add(interest));
			loan.setStatus(Constants.LOAN_FINISH);
			loanMapper.updateByPrimaryKey(loan);
			AppEntry entry = appEntryMapper.queryByAppModeId(loan.getIntoPieceId());
			if(entry == null)
				throw new Exception("无流程记录");
			JSONObject node = flowMgrImpl.getNextTask(entry);
			if(node != null && node.getIntValue("code") == 200){
				JSONArray arr = node.getJSONArray("nodes");
				if(arr != null){
					String nextNodeId = arr.getJSONObject(0).getString("nodeId");
					String pcId = node.getString("pcId");
					BusinessObject business = new BusinessObject(entry,loan,null);
					flowMgrImpl.next(business, nextNodeId, pcId, personId);
				}
			}
			
		}
		
	}


}
