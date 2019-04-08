package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.admin.entity.BusReturnVisit;
import com.nongyeos.loan.admin.mapper.BusReturnVisitMapper;
import com.nongyeos.loan.admin.service.ReturnVisitService;
@Service("returnVisitService")
public class ReturnVisitServiceImpl implements ReturnVisitService {
	
	@Autowired
	private BusReturnVisitMapper returnVisitMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insert(BusReturnVisit record) throws Exception {
		if(record == null){
			throw new Exception("回访记录模板为空");
		}
		if(record.getId() == null){
			throw new Exception("回访记录模板id为空");
		}
		try {
			return returnVisitMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<BusReturnVisit> selectByLoanId(String LoanId) throws Exception {
		if(LoanId == null){
			throw new Exception("借款标识为空");
		}
		try {
			return returnVisitMapper.selectByLoanId(LoanId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

}
