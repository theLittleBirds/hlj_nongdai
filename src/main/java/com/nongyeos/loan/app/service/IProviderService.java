package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.IntProvider;
import com.nongyeos.loan.base.util.PageBeanUtil;


public interface IProviderService {

	PageBeanUtil<IntProvider> selectByPage(int limit, int offset) throws Exception;

	IntProvider selectByName(String cname) throws Exception;

	void add(IntProvider pro) throws Exception;

	void update(IntProvider pro) throws Exception;

	void deleteByPCode(String providerCode) throws Exception;

	List<IntProvider> selectAll();


}
