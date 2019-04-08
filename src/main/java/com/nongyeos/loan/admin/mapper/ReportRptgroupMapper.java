package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.ReportRptgroup;

public interface ReportRptgroupMapper {
    int deleteByPrimaryKey(String groupId);

    int insert(ReportRptgroup record);

    int insertSelective(ReportRptgroup record);

    ReportRptgroup selectByPrimaryKey(String groupId);

    int updateByPrimaryKeySelective(ReportRptgroup record);

    int updateByPrimaryKey(ReportRptgroup record);

	List<ReportRptgroup> selectByFinsId(String finsId);

	List<ReportRptgroup> selectByParentId(String parentId);

	List<ReportRptgroup> selectAllByFindId(String finsId);

	ReportRptgroup selectByParentGroupId(String parentGroupId);
}