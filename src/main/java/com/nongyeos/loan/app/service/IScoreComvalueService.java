package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.ScoreComvalue;

public interface IScoreComvalueService {

	void add(ScoreComvalue comvalue) throws Exception;

	void update(ScoreComvalue comvalue) throws Exception;

	void deleteById(String cvId) throws Exception;

	List<ScoreComvalue> selectByPage(String scvarId) throws Exception;

}
