package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.IntServiceifa;

public interface IntServiceifaMapper {
    int deleteByPrimaryKey(String servifaCode);

    int insert(IntServiceifa record);

    int insertSelective(IntServiceifa record);

    IntServiceifa selectByPrimaryKey(String servifaCode);

    int updateByPrimaryKeySelective(IntServiceifa record);

    int updateByPrimaryKey(IntServiceifa record);

	List<IntServiceifa> selectAll();

	IntServiceifa selectServiceifaByName(String cname);

	int count();
}