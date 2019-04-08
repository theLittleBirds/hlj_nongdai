package com.nongyeos.loan.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nongyeos.loan.admin.entity.SysWebUser;

public interface SysWebUserMapper {
	SysWebUser selectByPrimaryKey(String userId);
	
	SysWebUser selectByName(@Param("name")String name);
	
    List<SysWebUser> selectAll();
    
    int deleteByPrimaryKey(String userId);

    int insert(SysWebUser record);

    int insertSelective(SysWebUser record);

    int updateByPrimaryKeySelective(SysWebUser user);

    int updateByPrimaryKey(SysWebUser user);
    
    int count();
}