package com.nongyeos.loan.admin.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.SysPerson;

public interface SysPersonMapper {
    int deleteByPrimaryKey(String personId);

    int insert(SysPerson record);

    int insertSelective(SysPerson record);

    SysPerson selectByPrimaryKey(String personId);
    
    SysPerson selectByUserIdAndIsdefault(String userId);

    int updateByPrimaryKeySelective(SysPerson record);

    int updateByPrimaryKey(SysPerson record);
    
    List<SysPerson> selectByOrgId(String orgId);

	int count(String orgId);

	SysPerson selectByName(String name);

	Map<String, Object> selectNameAndOrgByUserId(String userId);
}