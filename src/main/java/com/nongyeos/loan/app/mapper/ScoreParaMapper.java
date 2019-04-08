package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.ScorePara;

public interface ScoreParaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScorePara record);

    int insertSelective(ScorePara record);

    ScorePara selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ScorePara record);

    int updateByPrimaryKey(ScorePara record);

	List<ScorePara> selectAll();

	int count();

	List<ScorePara> selectParaDS();
}