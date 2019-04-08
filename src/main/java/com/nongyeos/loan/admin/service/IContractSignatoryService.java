package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusContractSignatory;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IContractSignatoryService {
	
	int insert(BusContractSignatory record) throws Exception;
	
	BusContractSignatory selectByPrimaryKey(String id) throws Exception;

    int updateByPrimaryKeySelective(BusContractSignatory record) throws Exception;

    int updateByPrimaryKey(BusContractSignatory record) throws Exception;
    
    PageBeanUtil<BusContractSignatory> selectByPage(int currentPage,int pageSize,BusContractSignatory record) throws Exception;
    
    List<BusContractSignatory> selectList(BusContractSignatory record) throws Exception;
}
