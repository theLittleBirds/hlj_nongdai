package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.ApiThirdMsg;

public interface ApiThirdMsgMapper {
	List<ApiThirdMsg> queryByPostRisk(String postRisk);
	
	List<ApiThirdMsg> queryByPlatform(String idCardNo, String platform);
	
	List<ApiThirdMsg> queryByIntoPiecePlatform(String intoPieceId, String platform);
	
	ApiThirdMsg queryByPrimaryPlatform(String id, String platform);
	
    int deleteByPrimaryKey(String id);

    int insert(ApiThirdMsg record);

    int addPlatformMsg(ApiThirdMsg record);

    ApiThirdMsg selectByPrimaryKey(String id);

    int updateApiThirdMsg(ApiThirdMsg record);

    int updateByPrimaryKey(ApiThirdMsg record);

	ApiThirdMsg selectLastByIntoPAndIDC(ApiThirdMsg thirdsql);

	ApiThirdMsg selectLastByIDC(ApiThirdMsg thirdsql);

	void updateByIntoPAndIDCSelective(ApiThirdMsg thirdsqlConditionbr);
}