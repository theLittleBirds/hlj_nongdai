package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.FleFlowright;

public interface FleFlowrightMapper {
    int deleteByPrimaryKey(Integer flowrightId);

    int insert(FleFlowright record);

    int insertSelective(FleFlowright record);

    FleFlowright selectByPrimaryKey(Integer flowrightId);

    int updateByPrimaryKeySelective(FleFlowright record);

    int updateByPrimaryKey(FleFlowright record);
    
    List<FleFlowright> selectByFlowCode(String flowId);
    
    void deleteByFlowCode(String flowId);
}