package com.nongyeos.loan.admin.service;

import com.nongyeos.loan.admin.entity.BusGuaranteeFee;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IGuaranteeFeeService {
	int deleteByPrimaryKey(String id) throws Exception;

    int insert(BusGuaranteeFee record)throws Exception;

    int insertSelective(BusGuaranteeFee record)throws Exception;

    BusGuaranteeFee selectByPrimaryKey(String id)throws Exception;

    int updateByPrimaryKeySelective(BusGuaranteeFee record)throws Exception;

    int updateByPrimaryKey(BusGuaranteeFee record)throws Exception;

	BusGuaranteeFee selectByIntopieceId(String id)throws Exception;

	PageBeanUtil<BusGuaranteeFee> selectByIpIdPage(int currentPage,
			int pageSize, String id) throws Exception;
}
