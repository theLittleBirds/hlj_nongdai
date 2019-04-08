package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusFamilyCredit;

public interface BusFamilyCreditMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusFamilyCredit record);

    int insertSelective(BusFamilyCredit record);

    BusFamilyCredit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusFamilyCredit record);

    int updateByPrimaryKey(BusFamilyCredit record);
    
    BusFamilyCredit selectByIpId(BusFamilyCredit record);

	List<Object> selectByParentItemId(String mainId);
}