package com.nongyeos.loan.admin.mapper;

import com.nongyeos.loan.admin.entity.BusMoneyLog;

public interface BusMoneyLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusMoneyLog record);

    int insertSelective(BusMoneyLog record);

    BusMoneyLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusMoneyLog record);

    int updateByPrimaryKey(BusMoneyLog record);
}