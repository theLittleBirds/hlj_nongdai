package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusOtherContact;

public interface BusOtherContactMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusOtherContact record);

    int insertSelective(BusOtherContact record);

    BusOtherContact selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusOtherContact record);

    int updateByPrimaryKey(BusOtherContact record);
    
    List<BusOtherContact> selectByIpId(BusOtherContact record);
    
    int selectCountByIpId(BusOtherContact record);
}