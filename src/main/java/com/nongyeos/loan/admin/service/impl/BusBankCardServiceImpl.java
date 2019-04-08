package com.nongyeos.loan.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.nongyeos.loan.admin.entity.BusBankCard;
import com.nongyeos.loan.admin.mapper.BusBankCardMapper;
import com.nongyeos.loan.admin.service.BusBankCardService;

@Service("apiBusBankCardService")
public class BusBankCardServiceImpl implements BusBankCardService{

	@Autowired
	private BusBankCardMapper apiBusBankCardMapper;

	@Override
	public BusBankCard queryByLoanId(String loanId) throws Exception {
		try {
			if(StringUtils.isEmpty(loanId)){
				throw new Exception("贷款id为空");
			}
			return apiBusBankCardMapper.queryByLoanId(loanId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}	
	
	@Override
	public int deleteByPrimaryKey(String id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insert(BusBankCard record) throws Exception {
		if(record == null)
			throw new Exception("银行卡模板为空");
		try {
			return apiBusBankCardMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public int insertSelective(BusBankCard record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BusBankCard selectByPrimaryKey(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKeySelective(BusBankCard record)
			throws Exception {
		if(record == null)
			throw new Exception("银行卡模板为空");
		try {
			return apiBusBankCardMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKey(BusBankCard record) throws Exception {
		if(record == null)
			throw new Exception("银行卡模板为空");
		try {
			return apiBusBankCardMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

}