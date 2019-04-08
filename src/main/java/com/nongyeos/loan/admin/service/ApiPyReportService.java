package com.nongyeos.loan.admin.service;

import com.nongyeos.loan.admin.entity.ApiPyReport;


public interface ApiPyReportService {
    int deleteByPrimaryKey(String id) throws Exception;

    int insert(ApiPyReport record) throws Exception;

    int addPyReportSelective(ApiPyReport record) throws Exception;

    ApiPyReport selectByPrimaryKey(String id) throws Exception;

    int updateByPrimaryKeySelective(ApiPyReport record) throws Exception;

    int updateByPrimaryKey(ApiPyReport record) throws Exception;
    
    ApiPyReport queryPyReportSelective(ApiPyReport record) throws Exception;
}