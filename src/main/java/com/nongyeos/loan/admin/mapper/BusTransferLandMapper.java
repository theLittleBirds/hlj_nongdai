package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusTransferLand;

public interface BusTransferLandMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusTransferLand record);

    int insertSelective(BusTransferLand record);

    BusTransferLand selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusTransferLand record);

    int updateByPrimaryKey(BusTransferLand record);

	List<BusTransferLand> selectByIpId(String intopieceId);

	Integer selectMaxSortByIpId(String intoPieceId);
}