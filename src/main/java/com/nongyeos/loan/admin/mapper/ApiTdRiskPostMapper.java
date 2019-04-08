package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.ApiTdRiskPost;
import com.nongyeos.loan.admin.resultMap.ApiTdRiskPostMap;

public interface ApiTdRiskPostMapper {
	ApiTdRiskPost queryRiskByIntoPieceId(String intoPieceId);
	
	List<ApiTdRiskPost> queryAllRiskPost();
	
	List<ApiTdRiskPost> queryAllRiskPostByPage(ApiTdRiskPostMap record);
	
    int deleteByPrimaryKey(String id);

    int insert(ApiTdRiskPost record);

    int addRiskPostSelective(ApiTdRiskPost record);

    ApiTdRiskPost queryRiskByPrimaryKey(String id);

    int updateByTdRiskPostSelective(ApiTdRiskPost record);

    int updateByPrimaryKey(ApiTdRiskPost record);

}