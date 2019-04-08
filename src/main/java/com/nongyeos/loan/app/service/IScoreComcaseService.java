package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.ScoreComcase;

public interface IScoreComcaseService {

	void add(ScoreComcase comcase) throws Exception;

	void update(ScoreComcase comcase) throws Exception;

	void deleteById(Integer cvId) throws Exception;

	List<ScoreComcase> selectByPage(String cvId) throws Exception;

	void deleteByCvId(String comvalueId) throws Exception;

}
