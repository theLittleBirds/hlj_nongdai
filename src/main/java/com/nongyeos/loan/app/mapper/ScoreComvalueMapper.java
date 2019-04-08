package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.ScoreComvalue;

public interface ScoreComvalueMapper {
    int deleteByPrimaryKey(String cvId);

    int insert(ScoreComvalue record);

    int insertSelective(ScoreComvalue record);

    ScoreComvalue selectByPrimaryKey(String cvId);

    int updateByPrimaryKeySelective(ScoreComvalue record);

    int updateByPrimaryKey(ScoreComvalue record);
    
    List<ScoreComvalue> selectAll(String scvarId);

}