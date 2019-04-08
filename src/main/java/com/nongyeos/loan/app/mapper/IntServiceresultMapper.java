package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.IntServiceresult;

public interface IntServiceresultMapper {
    int deleteByPrimaryKey(String servresCode);

    int insert(IntServiceresult record);

    int insertSelective(IntServiceresult record);

    IntServiceresult selectByPrimaryKey(String servresCode);

    int updateByPrimaryKeySelective(IntServiceresult record);

    int count(String code);

    int updateByPrimaryKey(IntServiceresult record);

	List<IntServiceresult> selectAllByCode(String code);

	List<IntServiceresult> selectByServimplId(String servimplId);

	List<IntServiceresult> selectAll();
}