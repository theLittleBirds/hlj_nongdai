package com.nongyeos.loan.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.nongyeos.loan.app.entity.AppItemScvar;

public interface AppItemScvarMapper {
    int deleteByPrimaryKey(Integer itemscvarId);

    int insert(AppItemScvar record);

    int insertSelective(AppItemScvar record);

    AppItemScvar selectByPrimaryKey(Integer itemscvarId);

    int updateByPrimaryKeySelective(AppItemScvar record);

    int updateByPrimaryKey(AppItemScvar record);

	List<AppItemScvar> selectByAppscId(String appscId);
	
	AppItemScvar queryByScvarId(@Param("scvarId")String scvarId, @Param("scoreId")String scoreId,@Param("appId")String appId);

	List<AppItemScvar> existItemId(String itemId, String appscId);

	List<AppItemScvar> existScvarId(String scvarId, String appscId);

	List<AppItemScvar> existItemId1(Map<String, Object> map);

	List<AppItemScvar> existScvarId1(Map<String, Object> map);
}