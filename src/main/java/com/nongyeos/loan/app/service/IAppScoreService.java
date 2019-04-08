package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.AppScore;

public interface IAppScoreService {

	List<AppScore> selectByAppId(String appId) throws Exception;

	void addAppScore(AppScore appScore) throws Exception;

	void updateAppScore(AppScore appScore) throws Exception;

	void delAppScore(String appScoreId) throws Exception;

	AppScore selectByAppscId(String appscId) throws Exception;

}
