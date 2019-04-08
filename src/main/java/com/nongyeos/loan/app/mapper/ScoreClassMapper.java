package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.ScoreClass;

public interface ScoreClassMapper {
    int deleteByPrimaryKey(String classId);

    int insert(ScoreClass record);

    int insertSelective(ScoreClass record);

    ScoreClass selectByPrimaryKey(String classId);

    int updateByPrimaryKeySelective(ScoreClass record);

    int updateByPrimaryKey(ScoreClass record);

	List<ScoreClass> selectAll(String scoreId);

	int count(String scoreId);
}