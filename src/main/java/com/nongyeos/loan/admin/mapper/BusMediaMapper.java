package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusMedia;

public interface BusMediaMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusMedia record);

    int insertSelective(BusMedia record);

    BusMedia selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusMedia record);

    int updateByPrimaryKey(BusMedia record);
    
    List<BusMedia> selectByIpId(BusMedia record);
    
    int existenceByKey(BusMedia record);
}