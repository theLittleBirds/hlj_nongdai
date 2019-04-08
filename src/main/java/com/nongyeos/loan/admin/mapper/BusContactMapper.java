package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusContact;

public interface BusContactMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusContact record);

    int insertSelective(BusContact record);

    BusContact selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusContact record);

    int updateByPrimaryKey(BusContact record);
    
    int selectCount(BusContact record);
    
    List<BusContact> selectAll(BusContact record);
    
    int selectRepeat(BusContact record);

	BusContact selectByPhoneMember(BusContact contact);
}