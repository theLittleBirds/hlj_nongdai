package com.nongyeos.loan.admin.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.BusRefund;

public interface BusRefundMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusRefund record);

    int insertSelective(BusRefund record);

    BusRefund selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusRefund record);

    int updateByPrimaryKey(BusRefund record);

	BusRefund selectByIntoPieceId(String intoPieceId);

	List<BusRefund> selectByCondition(Map<String, Object> map);

	int selectCountByCondition(Map<String, Object> map);
}