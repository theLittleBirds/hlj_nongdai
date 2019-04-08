package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusPicture;

public interface BusPictureMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusPicture record);

    int insertSelective(BusPicture record);

    BusPicture selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusPicture record);

    int updateByPrimaryKey(BusPicture record);
    
    List<BusPicture> selectByIpId(BusPicture record);
    
    int existenceByKey(BusPicture record);
}