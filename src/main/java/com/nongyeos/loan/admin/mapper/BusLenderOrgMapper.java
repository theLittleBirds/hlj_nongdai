package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusLenderOrg;

public interface BusLenderOrgMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusLenderOrg record);

    int insertSelective(BusLenderOrg record);

    BusLenderOrg selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusLenderOrg record);

    int updateByPrimaryKey(BusLenderOrg record);

	List<BusLenderOrg> selectByLenderId(String lenderId);

	void deleteByLenderId(String lenderId);
}