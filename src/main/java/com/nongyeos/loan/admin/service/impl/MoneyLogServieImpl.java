package com.nongyeos.loan.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.admin.entity.BusMoneyLog;
import com.nongyeos.loan.admin.mapper.BusMoneyLogMapper;
import com.nongyeos.loan.admin.service.IMoneyLogService;

@Service("moneyLogServie")
public class MoneyLogServieImpl implements IMoneyLogService {
	
	@Autowired
	private BusMoneyLogMapper moneyLogMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insert(BusMoneyLog record) throws Exception {
		if(record == null)
			throw new Exception("流水模板为空");
		try {
			return moneyLogMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

}
