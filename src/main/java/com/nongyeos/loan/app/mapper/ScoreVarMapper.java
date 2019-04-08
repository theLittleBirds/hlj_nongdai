package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.ScoreVar;

public interface ScoreVarMapper {
    int deleteByPrimaryKey(String varId);

    int insert(ScoreVar record);

    int insertSelective(ScoreVar record);

    ScoreVar selectByPrimaryKey(String varId);

    int updateByPrimaryKeySelective(ScoreVar record);

    int updateByPrimaryKey(ScoreVar record);

	List<ScoreVar> selectAll();

	int count();
}