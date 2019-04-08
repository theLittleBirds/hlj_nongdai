package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.FleFlow;

public interface FleFlowMapper {
    int deleteByPrimaryKey(String flowId);

    int insert(FleFlow record);

    int insertSelective(FleFlow record);

    FleFlow selectByPrimaryKey(String flowId);

    int updateByPrimaryKeySelective(FleFlow record);

    int updateByPrimaryKey(FleFlow record);

	List<FleFlow> selectByFinsCode(String finsId);
}