package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.ScoreScoreright;

public interface ScoreScorerightMapper {
    int deleteByPrimaryKey(Integer scrightId);

    int insert(ScoreScoreright record);

    int insertSelective(ScoreScoreright record);

    ScoreScoreright selectByPrimaryKey(Integer scrightId);

    int updateByPrimaryKeySelective(ScoreScoreright record);

    int updateByPrimaryKey(ScoreScoreright record);

	void deleteByScoreId(String scoreid);

	List<ScoreScoreright> selectByScoreId(String scoreid);

	List<ScoreScoreright> selectByRoleId(List<String> roleIdList);
}