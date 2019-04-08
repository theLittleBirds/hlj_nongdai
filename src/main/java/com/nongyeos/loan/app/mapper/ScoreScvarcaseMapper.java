package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.ScoreScvarcase;

public interface ScoreScvarcaseMapper {
    int deleteByPrimaryKey(String caseId);

    int insert(ScoreScvarcase record);

    int insertSelective(ScoreScvarcase record);

    ScoreScvarcase selectByPrimaryKey(String caseId);

    int updateByPrimaryKeySelective(ScoreScvarcase record);

    int updateByPrimaryKey(ScoreScvarcase record);

	List<ScoreScvarcase> selectAll(String scvarid);

	List<ScoreScvarcase> selectByPage(List<String> scvarIdList);

	int count(List<String> scvarIdList);

	int deleteByScvarId(String scvarId);

}