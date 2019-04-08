package com.nongyeos.loan.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nongyeos.loan.admin.entity.SysPara;

public interface SysParaMapper {
    int deleteByPrimaryKey(Integer paraId);

    int insert(SysPara record);

    int insertSelective(SysPara record);

    SysPara selectByPrimaryKey(Integer paraId);
    
    List<SysPara> selectByPGroupId(Integer pgroupId);

    int updateByPrimaryKeySelective(SysPara record);

    int updateByPrimaryKey(SysPara record);
    
    List<SysPara> selectList(int pgroupId);
    
    int deleteParaGroupId(Integer paraGroupId);
    
    List<SysPara> existedSameName(String name);
    
    List<SysPara> existedSameDesc(String descr);

	int count(int pgroupId);
	
	SysPara queryParaByGroupIdAndVal(@Param("pgroupId")String pgroupId, @Param("value")String value);
}