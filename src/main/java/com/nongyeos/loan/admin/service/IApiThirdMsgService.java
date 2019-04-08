package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.ApiThirdMsg;

public interface IApiThirdMsgService {
	List<ApiThirdMsg> queryByPostRisk(String postRisk) throws Exception;
	
	List<ApiThirdMsg> queryByPlatform(String idCardNo, String platform) throws Exception;
	
	List<ApiThirdMsg> queryByIntoPiecePlatform(String intoPieceId, String platform) throws Exception;
	
	ApiThirdMsg queryByPrimaryPlatform(String id, String platform) throws Exception;
	
    int deleteByPrimaryKey(String id) throws Exception;

    int insert(ApiThirdMsg record) throws Exception;

    int addPlatformMsg(ApiThirdMsg record) throws Exception;

    ApiThirdMsg selectByPrimaryKey(String id) throws Exception;

    int updateApiThirdMsg(ApiThirdMsg record) throws Exception;

    int updateByPrimaryKey(ApiThirdMsg record) throws Exception;

	ApiThirdMsg selectLastByIntoPAndIDC(ApiThirdMsg thirdsql) throws Exception;

	ApiThirdMsg selectLastByIDC(ApiThirdMsg thirdsql) throws Exception;

	void updateByIntoPAndIDCSelective(ApiThirdMsg thirdsqlConditionbr) throws Exception;
}