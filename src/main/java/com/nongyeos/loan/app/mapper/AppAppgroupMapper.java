package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.AppAppgroup;


public interface AppAppgroupMapper {
    int deleteByPrimaryKey(String groupId);

    int insert(AppAppgroup record);

    int insertSelective(AppAppgroup record);

    AppAppgroup selectByPrimaryKey(String groupId);

    int updateByPrimaryKeySelective(AppAppgroup record);

    int updateByPrimaryKey(AppAppgroup record);
    
    List<AppAppgroup> selectByFinsId(String finsId);
    
    List<AppAppgroup> selectByParentId(String parentId);
    
    List<AppAppgroup> selectAllByFindId(String finsId);
    
    List<AppAppgroup> selectAll();
}