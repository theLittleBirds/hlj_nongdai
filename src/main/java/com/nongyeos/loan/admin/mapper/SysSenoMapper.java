package com.nongyeos.loan.admin.mapper;

import com.nongyeos.loan.admin.entity.SysSeno;

public interface SysSenoMapper {
    int deleteByPrimaryKey(String name);

    int insert(SysSeno record);

    int insertSelective(SysSeno record);

    SysSeno selectByPrimaryKey(String name);

    int updateByPrimaryKeySelective(SysSeno record);

    int updateByPrimaryKey(SysSeno record);
}