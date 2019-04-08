package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.SysAppVersion;

public interface SysAppVersionMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysAppVersion record);

    int insertSelective(SysAppVersion record);

    SysAppVersion selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysAppVersion record);

    int updateByPrimaryKey(SysAppVersion record);

	List<SysAppVersion> selectByCondition(SysAppVersion sav);

	int selectCountByCondition(SysAppVersion sav);

	List<SysAppVersion> newForceVersions(SysAppVersion appVersion);

	SysAppVersion selectNewest(SysAppVersion appVersion);
}