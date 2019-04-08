package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.ScorePara;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IScoreParaService {
	
	PageBeanUtil<ScorePara> selectAll(int limit, int offset) throws Exception;

	void add(ScorePara scorePara) throws Exception;

	void update(ScorePara scorePara) throws Exception;

	void delete(Integer scoreParaId) throws Exception;

	List<ScorePara> getParaDS() throws Exception;

}
