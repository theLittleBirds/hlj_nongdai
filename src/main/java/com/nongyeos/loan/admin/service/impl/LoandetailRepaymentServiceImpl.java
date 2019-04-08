package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.ApiTdRiskPost;
import com.nongyeos.loan.admin.entity.BusLoandetailRepayment;
import com.nongyeos.loan.admin.mapper.BusLoandetailRepaymentMapper;
import com.nongyeos.loan.admin.resultMap.LoandetailRepaymentMap;
import com.nongyeos.loan.admin.service.ILoandetailRepaymentService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("loandetailRepaymentService")
public class LoandetailRepaymentServiceImpl implements ILoandetailRepaymentService{

	@Autowired
	private BusLoandetailRepaymentMapper busLoandetailRepaymentMapper;

	@Override
	public PageBeanUtil<LoandetailRepaymentMap> queryWaitVerify(int currentPage,
			int pageSize, LoandetailRepaymentMap record) throws Exception {
		try {
			if(record == null){
				throw new Exception("贷款明细记录为空");
			}
			if(record.getStatus() == null){
				throw new Exception("贷款明细记录状态为空");
			}
			if(record.getDelFlag() == null){
				throw new Exception("贷款明细记录是否有效状态为空");
			}		
			if(StringUtils.isEmpty(record.getPersonId())){
				throw new Exception("人员personid为空");
			}
			
			PageHelper.startPage(currentPage, pageSize, false);
			List<LoandetailRepaymentMap> verifyList = busLoandetailRepaymentMapper.queryWaitVerify(record);
			int total = verifyList.size();
			for (LoandetailRepaymentMap ldrMap : verifyList) {
				ldrMap.setRepayment_way("2".equals(ldrMap.getRepayment_way())?"代扣":"本系统");
				ldrMap.setReceive_capital_interest(ldrMap.getReceive_capital().add(ldrMap.getReceive_interest()));
			}
			PageBeanUtil<LoandetailRepaymentMap> pageData = new PageBeanUtil<LoandetailRepaymentMap>(currentPage, pageSize, total);
			pageData.setItems(verifyList);
			
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}	
	
	@Override
	public int deleteByPrimaryKey(String id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(BusLoandetailRepayment record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(BusLoandetailRepayment record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BusLoandetailRepayment selectByPrimaryKey(String id)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(BusLoandetailRepayment record)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(BusLoandetailRepayment record)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}