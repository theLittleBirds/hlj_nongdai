package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.ScoreComcase;

public interface ScoreComcaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScoreComcase record);

    int insertSelective(ScoreComcase record);

    ScoreComcase selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ScoreComcase record);

    int updateByPrimaryKey(ScoreComcase record);

	List<ScoreComcase> selectAll(String cvId);

	int deleteByCvId(String cvId);
}