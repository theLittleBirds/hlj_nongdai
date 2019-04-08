package com.nongyeos.loan.admin.service;

import com.nongyeos.loan.admin.entity.ApiTdRiskPost;
import com.nongyeos.loan.admin.resultMap.ApiTdRiskPostMap;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IApiTdRiskPostService {
	ApiTdRiskPost queryRiskByIntoPieceId(String intoPieceId) throws Exception;
	
	PageBeanUtil<ApiTdRiskPost> queryAllRiskPost(int currentPage, int pageSize, ApiTdRiskPostMap apiTdRiskPost) throws Exception;
	
    int deleteByPrimaryKey(String id) throws Exception;

    int insert(ApiTdRiskPost record) throws Exception;

    int addRiskPostSelective(ApiTdRiskPost record) throws Exception;

    ApiTdRiskPost queryRiskByPrimaryKey(String id) throws Exception;

    int updateByTdRiskPostSelective(ApiTdRiskPost record) throws Exception;

    int updateByPrimaryKey(ApiTdRiskPost record) throws Exception;
}