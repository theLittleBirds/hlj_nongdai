package com.nongyeos.loan.app.service;

import java.util.HashSet;
import java.util.List;

import com.nongyeos.loan.app.entity.ScoreScore;
import com.nongyeos.loan.app.entity.ScoreScoreright;

public interface IScoreService {

	void add(ScoreScore score) throws Exception;

	void update(ScoreScore score) throws Exception;

	void deleteById(String scoreId) throws Exception;

	ScoreScore getScoreById(String scoreId) throws Exception;

	List<ScoreScore> selectByFinsId(String finsId) throws Exception;

	void delByScoreId(String scoreid) throws Exception;

	List<ScoreScoreright> getScoreRightByScoreId(String scoreid) throws Exception;
	
	List<ScoreScore> selectByCategory(String value) throws Exception;

	List<ScoreScore> selectByCategoryAndIDList(String value,HashSet<String> idSet) throws Exception;

	List<ScoreScore> selectAll() throws Exception;

	ScoreScore selectById(String scoreId) throws Exception;

	List<ScoreScoreright> selectByRoleId(List<String> roleIdList) throws Exception;

}
