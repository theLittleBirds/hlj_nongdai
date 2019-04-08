package com.nongyeos.loan.admin.service;

import java.util.Map;

import com.nongyeos.loan.admin.entity.BusIntopieceReverse;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IIntopieceReverseService {
	int deleteByPrimaryKey(String id)throws Exception;

    int insert(BusIntopieceReverse record)throws Exception;

    int insertSelective(BusIntopieceReverse record)throws Exception;

    BusIntopieceReverse selectByPrimaryKey(String id)throws Exception;

    int updateByPrimaryKeySelective(BusIntopieceReverse record)throws Exception;

    int updateByPrimaryKey(BusIntopieceReverse record)throws Exception;

	BusIntopieceReverse selectByIpId(String intoPieceId)throws Exception;

	PageBeanUtil<BusIntopieceReverse> selectByPage(String personId,
			Map<String, Object> map, int currentPage, int pageSize)throws Exception;
}
