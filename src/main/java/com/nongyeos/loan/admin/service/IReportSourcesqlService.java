package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.ReportSourcesql;

public interface IReportSourcesqlService {

	List<ReportSourcesql> selectByTplId(String tplId) throws Exception;

	void add(ReportSourcesql reportTemplate) throws Exception;

	void update(ReportSourcesql reportTemplate) throws Exception;

	void del(String sourcesqlId) throws Exception;

	List<ReportSourcesql> getList() throws Exception;

	List<ReportSourcesql> selectByTplIdAndStatus(String tplId,Short sqlStatusOpen) throws Exception;

}
