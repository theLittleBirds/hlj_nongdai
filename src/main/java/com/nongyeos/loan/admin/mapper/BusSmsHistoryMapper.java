package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusSmsHistory;

public interface BusSmsHistoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusSmsHistory record);

    int insertSelective(BusSmsHistory record);

    BusSmsHistory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusSmsHistory record);

    int updateByPrimaryKey(BusSmsHistory record);
    
    List<BusSmsHistory> selectAll(BusSmsHistory record);
    
    int selectCount(BusSmsHistory record);
}