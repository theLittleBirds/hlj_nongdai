package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusOtherContact;
import com.nongyeos.loan.admin.entity.BusTransferLand;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface ITransferLandService {
	int deleteByPrimaryKey(String id)throws Exception;

    int insert(BusTransferLand record)throws Exception;

    int insertSelective(BusTransferLand record)throws Exception;

    BusTransferLand selectByPrimaryKey(String id)throws Exception;

    int updateByPrimaryKeySelective(BusTransferLand record)throws Exception;

    int updateByPrimaryKey(BusTransferLand record)throws Exception;

	List<BusTransferLand> selectByIpId(String intopieceId)throws Exception;

	PageBeanUtil<BusTransferLand> selectPageByIpId(int currentPage, int pageSize, String intoPieceId)throws Exception;

	Integer selectMaxSortByIpId(String intoPieceId)throws Exception;
}
