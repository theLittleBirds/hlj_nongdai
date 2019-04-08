package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.AppItemScvar;

public interface IAppItemScvarService {

	void delAppItem(Integer appItemid) throws Exception;

	void updateAppItem(AppItemScvar appItemScvar) throws Exception;

	void addAppItem(AppItemScvar appItemScvar) throws Exception;

	List<AppItemScvar> selectByAppscId(String appscId) throws Exception;
	
	AppItemScvar queryByScvarId(String scvarId, String scoreId, String appId) throws Exception;

	List<AppItemScvar> existItemId(String itemId, String appscId) throws Exception;

	List<AppItemScvar> existItemscvarId(String scvarId, String appscId) throws Exception;

	AppItemScvar selectById(Integer itemscvarId) throws Exception;

	void deleteById(Integer itemscvarId) throws Exception;

	List<AppItemScvar> existItemId1(Integer appItemScvarId, String itemId,String appscId) throws Exception;

	List<AppItemScvar> existItemscvarId1(Integer appItemScvarId,String scvarId, String appscId) throws Exception;

}
