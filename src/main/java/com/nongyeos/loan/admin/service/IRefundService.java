package com.nongyeos.loan.admin.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusRefund;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IRefundService {
	int deleteByPrimaryKey(String id) throws Exception;

    int insert(BusRefund record)throws Exception;

    int insertSelective(BusRefund record)throws Exception;

    BusRefund selectByPrimaryKey(String id)throws Exception;

    int updateByPrimaryKeySelective(BusRefund record)throws Exception;

    int updateByPrimaryKey(BusRefund record)throws Exception;

	BusRefund selectByIntoPieceId(String intoPieceId)throws Exception;

	PageBeanUtil<BusRefund> selectByPage(String personId,
			Map<String, Object> map, int currentPage, int pageSize)throws Exception;

	void refundSuccess(JSONObject resultJson)throws Exception;
	
	List<BusRefund> queryList(String personId, Map<String, Object> map)throws Exception;
}
