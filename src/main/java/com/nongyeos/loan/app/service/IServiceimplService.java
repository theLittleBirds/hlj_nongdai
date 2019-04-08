package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.IntServiceimpl;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IServiceimplService {
	PageBeanUtil<IntServiceimpl> selectByPage(int limit, int offset) throws Exception;

	IntServiceimpl selectByName(String cname) throws Exception;

	void add(IntServiceimpl serimpl) throws Exception;

	void update(IntServiceimpl serimpl) throws Exception;

	void deleteBySerimplCode(String servimplCode) throws Exception;

	IntServiceimpl selectByLocalPCode(String localPrdcode) throws Exception;

	List<IntServiceimpl> selectAll() throws Exception;

}
