package com.nongyeos.loan.app.mapper;

import java.util.HashSet;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nongyeos.loan.app.entity.ScoreScore;

public interface ScoreScoreMapper {
    int deleteByPrimaryKey(String scoreId);

    int insert(ScoreScore record);

    int insertSelective(ScoreScore record);

    ScoreScore selectByPrimaryKey(String scoreId);

    int updateByPrimaryKeySelective(ScoreScore record);

    int updateByPrimaryKey(ScoreScore record);

	List<ScoreScore> selectByFinsId(String finsId);
	
	List<ScoreScore> selectByCategory(String value);

	List<ScoreScore> selectByCategoryAndIDList(@Param("value")String value,@Param("idSet")HashSet<String> idSet);

	List<ScoreScore> selectAll();
}