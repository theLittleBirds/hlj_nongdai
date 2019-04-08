package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.AppScore;

public interface AppScoreMapper {
    int deleteByPrimaryKey(String appscId);

    int insert(AppScore record);

    int insertSelective(AppScore record);

    AppScore selectByPrimaryKey(String appscId);

    int updateByPrimaryKeySelective(AppScore record);

    int updateByPrimaryKey(AppScore record);

	List<AppScore> selectByAppId(String appId);
}