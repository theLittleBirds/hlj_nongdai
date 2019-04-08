package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.ScoreVar;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IVarService {
	
	void add(ScoreVar var) throws Exception;

	void update(ScoreVar var) throws Exception;

	void deleteById(String varId) throws Exception;

	ScoreVar getScvarById(String varId) throws Exception;

	List<ScoreVar> selectAll();

	PageBeanUtil<ScoreVar> selectByPage(int limit, int offset) throws Exception;

}
