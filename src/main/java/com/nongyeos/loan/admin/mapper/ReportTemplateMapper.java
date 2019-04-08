package com.nongyeos.loan.admin.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.ReportTemplate;

public interface ReportTemplateMapper {
    int deleteByPrimaryKey(String tplId);

    int insert(ReportTemplate record);

    int insertSelective(ReportTemplate record);

    ReportTemplate selectByPrimaryKey(String tplId);

    int updateByPrimaryKeySelective(ReportTemplate record);

    int updateByPrimaryKey(ReportTemplate record);

	List<ReportTemplate> selectByRptId(String rptId);

	List<ReportTemplate> existenceRptId(String rptId, String tplId);

	ReportTemplate selectByRptIdAndStatus(Map<String, Object> map);
}