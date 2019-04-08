package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusMemberOperateMedia;

public interface BusMemberOperateMediaMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusMemberOperateMedia record);

    int insertSelective(BusMemberOperateMedia record);

    BusMemberOperateMedia selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusMemberOperateMedia record);

    int updateByPrimaryKey(BusMemberOperateMedia record);
    
    int existenceByKey(BusMemberOperateMedia record);

	List<BusMemberOperateMedia> selectByMOId(String id);
}