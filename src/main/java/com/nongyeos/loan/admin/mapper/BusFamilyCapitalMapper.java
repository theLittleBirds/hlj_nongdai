package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusFamilyCapital;

public interface BusFamilyCapitalMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusFamilyCapital record);

    int insertSelective(BusFamilyCapital record);

    BusFamilyCapital selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusFamilyCapital record);

    int updateByPrimaryKey(BusFamilyCapital record);
    
    BusFamilyCapital selectByIntoPieceId(BusFamilyCapital record);
    
    List<Object> selectByParentItemId(String mainId);
}