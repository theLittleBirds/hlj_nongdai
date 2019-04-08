package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.ScoreClass;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IClassService {

	void add(ScoreClass scvar) throws Exception;

	void update(ScoreClass scvar) throws Exception;

	void deleteById(String scvarId) throws Exception;

	List<ScoreClass> selectAll(String scoreid);

	PageBeanUtil<ScoreClass> selectByPage(int limit, int offset, String scoreid) throws Exception;

}
