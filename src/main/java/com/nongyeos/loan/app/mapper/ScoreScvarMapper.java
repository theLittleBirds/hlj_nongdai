package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.ScoreScvar;

public interface ScoreScvarMapper {
    int deleteByPrimaryKey(String scvarId);

    int insert(ScoreScvar record);

    int insertSelective(ScoreScvar record);

    ScoreScvar selectByPrimaryKey(String scvarId);

    int updateByPrimaryKeySelective(ScoreScvar record);

    int updateByPrimaryKey(ScoreScvar record);

	List<ScoreScvar> selectAll(String scoreId);

	int count(String scoreid);

	List<ScoreScvar> selectByPage(String scoreId, short type);

}