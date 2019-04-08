package com.nongyeos.loan.core.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.nongyeos.loan.app.entity.ScoreScore;
import com.nongyeos.loan.core.model.BusinessObject;

public interface ScoreMgr {
	
	public List<ScoreScore> calculate(BusinessObject actAForm, String scoreId) throws Exception;

	List<ScoreScore> calculate2(BusinessObject scoreMgr, String appscId,
			SqlSession session) throws Exception;

}
