package com.nongyeos.loan.admin.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.nongyeos.loan.admin.entity.BusFamilyCapital;

public interface IFamilyCapitalService {
	
	int insert(BusFamilyCapital record) throws Exception;
	
	int updateByPrimaryKey(BusFamilyCapital record) throws Exception;
	
	int updateByPrimaryKeySelective(BusFamilyCapital record) throws Exception;
	
	BusFamilyCapital selectByIntoPieceId(BusFamilyCapital record) throws Exception;

	List<Object> getSonEntryByParentItemId(String mainId,SqlSession session)
			throws Exception;
}
