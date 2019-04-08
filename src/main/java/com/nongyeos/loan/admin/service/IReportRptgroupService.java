package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.ReportRptgroup;

public interface IReportRptgroupService {

	String groupTreeString();

	void add(ReportRptgroup rptgroup) throws Exception;

	void upd(ReportRptgroup rptgroup) throws Exception;

	void delGroup(String groupId) throws Exception;

	ReportRptgroup getGroupById(String groupId) throws Exception;

	List<ReportRptgroup> getlistByFinsId(String finsId) throws Exception;

	ReportRptgroup selectByParentGroupId(String parentGroupId) throws Exception;

}
