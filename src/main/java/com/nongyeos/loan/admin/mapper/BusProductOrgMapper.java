package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusProductOrg;

public interface BusProductOrgMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusProductOrg record);

    int insertSelective(BusProductOrg record);

    BusProductOrg selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusProductOrg record);

    int updateByPrimaryKey(BusProductOrg record);

	List<BusProductOrg> selectOrgByProductId(String productId);

	int deleteOrgByProductId(String productId);
}