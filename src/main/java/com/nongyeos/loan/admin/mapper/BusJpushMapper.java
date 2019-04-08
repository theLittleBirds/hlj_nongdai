package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusJpush;

public interface BusJpushMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusJpush record);

    int insertSelective(BusJpush record);

    BusJpush selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusJpush record);

    int updateByPrimaryKey(BusJpush record);

	List<BusJpush> selectAllPush();
}