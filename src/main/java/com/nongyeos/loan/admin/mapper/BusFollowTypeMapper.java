package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusFollowType;

public interface BusFollowTypeMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusFollowType record);

    int insertSelective(BusFollowType record);

    BusFollowType selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusFollowType record);

    int updateByPrimaryKey(BusFollowType record);
    
    List<BusFollowType> selectAll(String creOrgId);
    
    int selectCount(String creOrgId);
}