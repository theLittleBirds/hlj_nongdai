package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.BusSmsHistory;
import com.nongyeos.loan.admin.mapper.BusSmsHistoryMapper;
import com.nongyeos.loan.admin.service.ISmsHistoryService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("smsHistoryService")
public class SmsHistoryServiceImpl implements ISmsHistoryService {
	
	@Autowired
	private BusSmsHistoryMapper smsHistoryMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insert(BusSmsHistory record) throws Exception{
		if(record == null){
			throw new Exception("短信记录模板为空");
		}
		if(record.getId() == null){
			throw new Exception("短信记录主键为空");
		}
		try {
			return smsHistoryMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKeySelective(BusSmsHistory record) throws Exception {
		if(record == null){
			throw new Exception("短信记录模板为空");
		}
		if(record.getId() == null){
			throw new Exception("短信记录主键为空");
		}
		try {
			return smsHistoryMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}	
	}

	@Override
	public PageBeanUtil<BusSmsHistory> selectByPage(int currentPage,
			int pageSize, BusSmsHistory record) throws Exception {
		try {
			PageHelper.startPage(currentPage, pageSize, false);
			List<BusSmsHistory> list = smsHistoryMapper.selectAll(record);
			int countNums = smsHistoryMapper.selectCount(record);
			PageBeanUtil<BusSmsHistory> pageData = new PageBeanUtil<BusSmsHistory>(currentPage, pageSize, countNums);
	        pageData.setItems(list);
	        return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		
	}

	

}
