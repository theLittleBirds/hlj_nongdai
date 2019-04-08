package com.nongyeos.loan.admin.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.ReportSourcesql;

public interface ReportSourcesqlMapper {
    int deleteByPrimaryKey(String dsId);

    int insert(ReportSourcesql record);

    int insertSelective(ReportSourcesql record);

    ReportSourcesql selectByPrimaryKey(String dsId);

    int updateByPrimaryKeySelective(ReportSourcesql record);

    int updateByPrimaryKey(ReportSourcesql record);

	List<ReportSourcesql> selectByTplId(String tplId);

	List<ReportSourcesql> getList();

	List<ReportSourcesql> selectByTplIdAndStatus(Map<String, Object> map);

}