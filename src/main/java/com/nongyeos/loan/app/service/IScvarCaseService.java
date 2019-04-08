package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.ScoreScvarcase;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IScvarCaseService {

	void add(ScoreScvarcase varcase) throws Exception;

	void update(ScoreScvarcase varcase) throws Exception;

	void deleteById(String caseId) throws Exception;

	void deleteByScvarId(String scvarId) throws Exception;

	List<ScoreScvarcase> selectAll(String scvarid);
	
	PageBeanUtil<ScoreScvarcase> selectByPage(int limit, int offset, String scoreid) throws Exception;

}
