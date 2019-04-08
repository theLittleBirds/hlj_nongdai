package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusRejectReason;

public interface BusRejectReasonMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusRejectReason record);

    int insertSelective(BusRejectReason record);

    BusRejectReason selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusRejectReason record);

    int updateByPrimaryKey(BusRejectReason record);

	List<BusRejectReason> selectByIpId(String intoPieceId);
}