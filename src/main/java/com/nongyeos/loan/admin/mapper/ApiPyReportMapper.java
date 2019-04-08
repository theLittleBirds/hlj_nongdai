package com.nongyeos.loan.admin.mapper;

import com.nongyeos.loan.admin.entity.ApiPyReport;


public interface ApiPyReportMapper {
    int deleteByPrimaryKey(String id);

    int insert(ApiPyReport record);

    int addPyReportSelective(ApiPyReport record);

    ApiPyReport selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApiPyReport record);

    int updateByPrimaryKey(ApiPyReport record);
    
    ApiPyReport queryPyReportSelective(ApiPyReport record);

	void updateByIntoPAndIDCSelective(ApiPyReport pyReport);

	ApiPyReport selectByIDC(ApiPyReport pysql);
}