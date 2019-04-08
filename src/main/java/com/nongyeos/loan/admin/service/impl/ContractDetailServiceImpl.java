package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.BusContractDetail;
import com.nongyeos.loan.admin.mapper.BusContractDetailMapper;
import com.nongyeos.loan.admin.service.IContractDetailService;
import com.nongyeos.loan.base.util.PageBeanUtil;
@Service("contractDetailService")
public class ContractDetailServiceImpl implements IContractDetailService {
	
	@Autowired
	private BusContractDetailMapper contractDetailMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insert(BusContractDetail record) throws Exception {
		if(record == null){
			throw new Exception("合同详情模板为空");
		}
		try {
			return contractDetailMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusContractDetail selectByPrimaryKey(String id) throws Exception {
		if(id == null){
			throw new Exception("合同详情id为空");
		}
		try {
			return contractDetailMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKeySelective(BusContractDetail record)
			throws Exception {
		if(record == null){
			throw new Exception("合同详情模板为空");
		}
		try {
			return contractDetailMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKey(BusContractDetail record) throws Exception {
		if(record == null){
			throw new Exception("合同详情模板为空");
		}
		try {
			return contractDetailMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public PageBeanUtil<BusContractDetail> selectByPage(int currentPage,
			int pageSize, String finsId) throws Exception {
		try {
			PageHelper.startPage(currentPage, pageSize, false);
			BusContractDetail cd = new BusContractDetail();
			cd.setFinsId(finsId);
			List<BusContractDetail> list = contractDetailMapper.selectByFinsId(cd);
			int countNums = contractDetailMapper.selectCount(cd);
			PageBeanUtil<BusContractDetail> pageData = new PageBeanUtil<BusContractDetail>(currentPage, pageSize, countNums);
	        pageData.setItems(list);
	        return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<BusContractDetail> contractList(String finsId)
			throws Exception {
		if(finsId == null){
			throw new Exception("金融机构id为空");
		}
		try {
			return contractDetailMapper.contractList(finsId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

}
