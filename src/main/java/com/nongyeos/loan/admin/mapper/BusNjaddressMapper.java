package com.nongyeos.loan.admin.mapper;

import com.nongyeos.loan.admin.entity.BusNjaddress;

import java.util.List;

public interface BusNjaddressMapper {
    int deleteByPrimaryKey(String addressId);

    int insert(BusNjaddress record);

    int insertSelective(BusNjaddress record);

    BusNjaddress selectByPrimaryKey(String addressId);

    int updateByPrimaryKeySelective(BusNjaddress record);

    int updateByPrimaryKey(BusNjaddress record);

    List<BusNjaddress> queryAddressListAll(String personId);

    BusNjaddress queryAddressByOrgId(String personId);
}