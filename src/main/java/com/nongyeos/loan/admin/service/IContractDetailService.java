package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusContractDetail;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IContractDetailService {
	
	int insert(BusContractDetail record) throws Exception;
	
	BusContractDetail selectByPrimaryKey(String id) throws Exception;
	
	int updateByPrimaryKeySelective(BusContractDetail record) throws Exception;

	int updateByPrimaryKey(BusContractDetail record) throws Exception;
	
	PageBeanUtil<BusContractDetail> selectByPage(int currentPage,int pageSize,String finsId) throws Exception;
	
	List<BusContractDetail> contractList(String finsId) throws Exception;
}
