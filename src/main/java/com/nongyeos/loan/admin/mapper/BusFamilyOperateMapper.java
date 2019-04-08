package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusFamilyOperate;

public interface BusFamilyOperateMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusFamilyOperate record);

    int insertSelective(BusFamilyOperate record);

    BusFamilyOperate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusFamilyOperate record);

    int updateByPrimaryKey(BusFamilyOperate record);
    
    BusFamilyOperate selectByIpId(String intoPieceId);
    
	List<Object> selectByParentItemId(String mainId);
}