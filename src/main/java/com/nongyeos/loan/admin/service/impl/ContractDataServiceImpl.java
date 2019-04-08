package com.nongyeos.loan.admin.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusContractData;
import com.nongyeos.loan.admin.entity.BusLoan;
import com.nongyeos.loan.admin.entity.BusLoanDetail;
import com.nongyeos.loan.admin.mapper.BusContractDataMapper;
import com.nongyeos.loan.admin.mapper.BusLoanDetailMapper;
import com.nongyeos.loan.admin.mapper.BusLoanMapper;
import com.nongyeos.loan.admin.service.IContractDataService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.GetRepaymentUtils;
import com.nongyeos.loan.base.util.NumberUtils;
import com.nongyeos.loan.base.util.StringUtils;
import com.nongyeos.loan.base.util.UUIDUtil;

@Service("contractDataService")
public class ContractDataServiceImpl implements IContractDataService {
	
	@Autowired
	private BusContractDataMapper contractDataMapper;
	
	@Autowired
	private BusLoanDetailMapper loanDetailMapper;
	
	@Autowired
	private BusLoanMapper loanMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insert(BusContractData record) throws Exception {
		if(record == null)
			throw new Exception("合同基础数据模板为空");
		if(record.getId() == null)
			throw new Exception("合同基础数据模板id为空");
		try {
			saveDetail(record);
			return contractDataMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusContractData selectByPrimaryKey(String id) throws Exception {
		if(id == null)
			throw new Exception("合同基础数据模板id为空");
		try {
			return contractDataMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKeySelective(BusContractData record)
			throws Exception {
		if(record == null)
			throw new Exception("合同基础数据模板为空");
		if(record.getId() == null)
			throw new Exception("合同基础数据模板id为空");
		try {
			saveDetail(record);
//			loanDetail.setCapital();
			return contractDataMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusContractData selectByloanId(String loanId) throws Exception {
		if(loanId == null)
			throw new Exception("合同基础数据模板标识为空");
		try {
			return contractDataMapper.selectByloanId(loanId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	
//	public 
	protected void saveDetail(BusContractData record){
		String data = record.getData();
		JSONObject contractData = JSONObject.parseObject(data);
		BusLoanDetail loanDetail = null;
		BigDecimal capital = contractData.getBigDecimal("capital");
		int term = contractData.getIntValue("term");
		int years =term/12;
		BigDecimal rate = contractData.getBigDecimal("rate");
		Short rateType = contractData.getShort("rateType");
		Short serviceRateType = contractData.getShort("serviceRateType");
		BigDecimal serviceRate = contractData.getBigDecimal("serviceRate");
		String loanStart = contractData.getString("start_date");
		Date parseByAll = DateUtils.parseByAll(loanStart);
		long borrowTime = DateUtils.getUnixTime(parseByAll);
		Short repaymentType = contractData.getShort("repaymentType");
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
//			Map<String, Object> equalMonth = GetRepaymentUtils.equalMonth(capital, term, rate, serviceRate, borrowTime, "");
			break;
			//惠农通
		case 4:
//			Map<String, Object> equalMonth = GetRepaymentUtils.equalMonth(capital, term, rate, serviceRate, borrowTime, "");
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
			loanDetailMapper.deleteByLoanId(record.getLoanId());
			BusLoan loan = loanMapper.selectByPrimaryKey(record.getLoanId());
			if(repaymentType.equals(6)){
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> map = list.get(i);
					loanDetail = new BusLoanDetail(); 
					loanDetail.setId(UUIDUtil.getUUID());
	                loanDetail.setOrderId(GetRepaymentUtils.createLoanDetailOrderId());
	                loanDetail.setLoanId(record.getLoanId());
	                loanDetail.setCapital(NumberUtils.toBigDecimal(map.get("yearCapital")));
	                loanDetail.setIntrest(NumberUtils.toBigDecimal(map.get("interest")));
	                loanDetail.setStatus(Constants.WAIT_REPAYMENT);
	                loanDetail.setReceiveCapital(new BigDecimal("0.00"));
	                loanDetail.setReceiveInterest(new BigDecimal("0.00"));
	                loanDetail.setReceiveOverdue(new BigDecimal("0.00"));
	                loanDetail.setServiceFee(NumberUtils.toBigDecimal(map.get("serviceFee")));
	                loanDetail.setDeadlineDate(DateUtils.parseDateByTime(NumberUtils.toLong(StringUtils.defaultString(map.get("payBackDate")))));
	                loanDetail.setSort(Integer.parseInt(map.get("index").toString()) + 1);
	                loanDetail.setOverplusMoney(NumberUtils.toBigDecimal(map.get("shenbenjin")));
	                loanDetail.setTotal(years);
	                loanDetail.setCreDate(DateUtils.getNowDate());
	                loanDetail.setUpdDate(DateUtils.getNowDate());
	                loanDetail.setIsDeleted(Constants.COMMON_ISNOT_DELETE);
	             // 第一笔，添加最新一笔收款日期
                  if (Integer.parseInt(map.get("index").toString()) == 0) {
                      loanDetail.setRecentWaitRep(new Integer(Constants.COMMON_YES));

                  } else {
                      loanDetail.setRecentWaitRep(new Integer(Constants.COMMON_NO));
                  }
                  loanDetailMapper.insert(loanDetail);
                  
                  if (Integer.parseInt(map.get("index").toString()) == list.size() - 1) {
                	loan.setLastRepaymentDate(loanDetail.getDeadlineDate());
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
	                loanDetail.setLoanId(record.getLoanId());
	                loanDetail.setCapital(NumberUtils.toBigDecimal(map.get("capital")));
	                loanDetail.setIntrest(NumberUtils.toBigDecimal(map.get("interest")));
	                loanDetail.setStatus(Constants.WAIT_REPAYMENT);
	                loanDetail.setServiceFee(new BigDecimal("0.00"));
	                loanDetail.setOverplusMoney(new BigDecimal("0.00"));
	                loanDetail.setReceiveCapital(new BigDecimal("0.00"));
	                loanDetail.setReceiveInterest(new BigDecimal("0.00"));
	                loanDetail.setReceiveOverdue(new BigDecimal("0.00"));
	                loanDetail.setDeadlineDate(DateUtils.parseDateByTime(NumberUtils.toLong(StringUtils.defaultString(map.get("repaymentTime")))));
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
	                loanDetail.setLoanId(record.getLoanId());
	                loanDetail.setCapital(NumberUtils.toBigDecimal(map.get("capital")));
	                loanDetail.setIntrest(NumberUtils.toBigDecimal(map.get("interest")));
	                loanDetail.setStatus(Constants.WAIT_REPAYMENT);
	                loanDetail.setServiceFee(new BigDecimal("0.00"));
	                loanDetail.setOverplusMoney(new BigDecimal("0.00"));
	                loanDetail.setReceiveCapital(new BigDecimal("0.00"));
	                loanDetail.setReceiveInterest(new BigDecimal("0.00"));
	                loanDetail.setReceiveOverdue(new BigDecimal("0.00"));
	                loanDetail.setDeadlineDate(DateUtils.parseDateByTime(NumberUtils.toLong(StringUtils.defaultString(map.get("repaymentTime")))));
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
	                loanDetail.setLoanId(record.getLoanId());
	                loanDetail.setCapital(NumberUtils.toBigDecimal(map.get("monthMoney")));
	                loanDetail.setIntrest(NumberUtils.toBigDecimal(map.get("monthFee")));
	                loanDetail.setStatus(Constants.WAIT_REPAYMENT);
	                loanDetail.setServiceFee(new BigDecimal("0.00"));
	                loanDetail.setOverplusMoney(new BigDecimal("0.00"));
	                loanDetail.setReceiveCapital(new BigDecimal("0.00"));
	                loanDetail.setReceiveInterest(new BigDecimal("0.00"));
	                loanDetail.setReceiveOverdue(new BigDecimal("0.00"));
	                loanDetail.setDeadlineDate(DateUtils.parseDateByTime(NumberUtils.toLong(StringUtils.defaultString(map.get("repaymentTime")))));
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
			loan.setReceiveCapital(new BigDecimal(0));
			loan.setReceiveInterest(new BigDecimal(0));
			loan.setReceiveOverdue(new BigDecimal(0));
			loanMapper.updateByPrimaryKeySelective(loan);
		}
	}

	@Override
	public int deleteByLoan(String loanId) throws Exception {
		if(loanId == null)
			throw new Exception("进件标识为空");
		try {
			return contractDataMapper.deleteByLoanId(loanId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
}
