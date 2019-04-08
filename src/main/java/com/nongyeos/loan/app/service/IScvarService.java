package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.ScoreScvar;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IScvarService {

	void add(ScoreScvar scvar) throws Exception;

	void update(ScoreScvar scvar) throws Exception;

	void deleteById(String scvarId) throws Exception;

	ScoreScvar getScvarById(String scvarId) throws Exception;

	List<ScoreScvar> selectAll(String scoreid);

	PageBeanUtil<ScoreScvar> selectByPage(int limit, int offset, String scoreid) throws Exception;

	List<ScoreScvar> selectByPage(String scoreid, short type);

}

