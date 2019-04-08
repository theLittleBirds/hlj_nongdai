package com.nongyeos.loan.admin.mapper;

import java.util.HashMap;
import java.util.List;

import com.nongyeos.loan.admin.entity.ReportEntry;

public interface ReportEntryMapper {
    int deleteByPrimaryKey(String rptId);

    int insert(ReportEntry record);

    int insertSelective(ReportEntry record);

    ReportEntry selectByPrimaryKey(String rptId);

    int updateByPrimaryKeySelective(ReportEntry record);

    int updateByPrimaryKey(ReportEntry record);

	List<ReportEntry> selectByGroupId(String groupId);

	List<ReportEntry> selectByFinsId(String finsId);

	List doSql(String sqlstr);
}