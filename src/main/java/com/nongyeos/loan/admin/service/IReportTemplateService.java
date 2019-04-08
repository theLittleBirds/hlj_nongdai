package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.ReportTemplate;

public interface IReportTemplateService {

	List<ReportTemplate> selectByRptId(String rptId) throws Exception;

	void addTemplate(ReportTemplate reportTemplate) throws Exception;

	void updateTemplate(ReportTemplate reportTemplate) throws Exception;

	void delRptTemplate(String templateId) throws Exception;

	boolean existenceRptId(String rptId, String tplId) throws Exception;

	ReportTemplate selectByRptIdAndStatus(String rptId, Short modelStatusOpen) throws Exception;

	ReportTemplate selectByTplId(String tplId) throws Exception;

}
