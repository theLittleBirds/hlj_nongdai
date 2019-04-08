package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.AppAppright;


public interface AppApprightMapper {
    int deleteByPrimaryKey(Integer apprightId);

    int insert(AppAppright record);

    int insertSelective(AppAppright record);

    AppAppright selectByPrimaryKey(Integer apprightId);

    int updateByPrimaryKeySelective(AppAppright record);

    int updateByPrimaryKey(AppAppright record);
    
    List<AppAppright>selectByEntityId(String entityId);
    
    void deleteByEntityId(String entityId);
}