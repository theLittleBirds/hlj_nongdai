package com.nongyeos.loan.admin.service;

import java.util.HashMap;
import java.util.List;

import com.nongyeos.loan.admin.entity.ReportEntry;

public interface IReportEntryService {

	List<ReportEntry> selectByGroupId(String groupId) throws Exception;

	void addEntry(ReportEntry reportEntry) throws Exception;

	void updateEntry(ReportEntry reportEntry) throws Exception;

	void delRptEntry(String rptId) throws Exception;

	String appTreeString() throws Exception;

	List doSql(String sqlstr) throws Exception;

	List doLookSql(String sqlstr) throws Exception;

}
