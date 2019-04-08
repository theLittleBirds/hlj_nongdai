package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.SysParaGroup;

public interface SysParaGroupMapper {
    int deleteByPrimaryKey(Integer pgroupId);

    int insert(SysParaGroup record);

    int insertSelective(SysParaGroup record);

    SysParaGroup selectByPrimaryKey(Integer pgroupId);
    
    SysParaGroup selectByName(String name);

    int updateByPrimaryKeySelective(SysParaGroup record);

    int updateByPrimaryKey(SysParaGroup record);
    
    List<SysParaGroup> selectAll();
    
    List<SysParaGroup> selectList();
    
    List<SysParaGroup> existedSameName(String name);
    
    List<SysParaGroup> existedSameDesc(String descr);

	int count();
}