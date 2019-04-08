package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.IntServiceifa;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IServiceifaService {

	PageBeanUtil<IntServiceifa> selectAll(int limit, int offset) throws Exception;
	
	List<IntServiceifa> selectAll() throws Exception;

	void add(IntServiceifa serviceifa) throws Exception;

	void update(IntServiceifa serviceifa) throws Exception;

	void deleteServiceifa(String serviceifaId) throws Exception;

}
