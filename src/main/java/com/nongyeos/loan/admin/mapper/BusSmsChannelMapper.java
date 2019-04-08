package com.nongyeos.loan.admin.mapper;

import com.nongyeos.loan.admin.entity.BusSmsChannel;

public interface BusSmsChannelMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusSmsChannel record);

    int insertSelective(BusSmsChannel record);

    BusSmsChannel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusSmsChannel record);

    int updateByPrimaryKey(BusSmsChannel record);

	BusSmsChannel selectUsedChannel();
}