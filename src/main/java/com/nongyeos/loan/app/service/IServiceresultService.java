package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.IntServiceresult;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IServiceresultService {

	void add(IntServiceresult servRes);

	void update(IntServiceresult servRes);

	void deleteByPCode(String pCode);

	PageBeanUtil<IntServiceresult> selectByPage(int limit, int offset, String code) throws Exception;

	List<IntServiceresult> getListByCode(String servimplCode);

	List<IntServiceresult> selectServiceResultByServimplId(String servimplId) throws Exception;


	List<IntServiceresult> selectAll() throws Exception;


}
