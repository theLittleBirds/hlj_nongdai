package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.BusContractSignatory;
import com.nongyeos.loan.admin.mapper.BusContractSignatoryMapper;
import com.nongyeos.loan.admin.service.IContractSignatoryService;
import com.nongyeos.loan.base.util.PageBeanUtil;
@Service("contractSignatoryService")
public class ContractSignatoryServiceImpl implements IContractSignatoryService {
	
	@Autowired
	private BusContractSignatoryMapper contractSignatoryMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insert(BusContractSignatory record) throws Exception {
		if(record == null)
			throw new Exception("合同签署人模板为空");
		try {
			return contractSignatoryMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusContractSignatory selectByPrimaryKey(String id) throws Exception {
		if(id == null)
			throw new Exception("合同签署人标识为空");
		try {
			return contractSignatoryMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKeySelective(BusContractSignatory record)
			throws Exception {
		if(record == null)
			throw new Exception("合同签署人模板为空");
		try {
			return contractSignatoryMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKey(BusContractSignatory record) throws Exception {
		if(record == null)
			throw new Exception("合同签署人模板为空");
		try {
			return contractSignatoryMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public PageBeanUtil<BusContractSignatory> selectByPage(int currentPage,
			int pageSize, BusContractSignatory record) throws Exception {
		try {
			PageHelper.startPage(currentPage, pageSize, false);
			List<BusContractSignatory> list = contractSignatoryMapper.selectList(record);
			int countNums = contractSignatoryMapper.selectCount(record);
			PageBeanUtil<BusContractSignatory> pageData = new PageBeanUtil<BusContractSignatory>(currentPage, pageSize, countNums);
	        pageData.setItems(list);
	        return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<BusContractSignatory> selectList(BusContractSignatory record)
			throws Exception {
		try {
			return contractSignatoryMapper.selectList(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

}
