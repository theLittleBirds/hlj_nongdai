package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.AppItem;


public interface AppItemMapper {
    int deleteByPrimaryKey(String itemId);

    int insert(AppItem record);

    int insertSelective(AppItem record);

    AppItem selectByPrimaryKey(String itemId);

    int updateByPrimaryKeySelective(AppItem record);

    int updateByPrimaryKey(AppItem record);
    
    List<AppItem> selectByEntityId(String entityId);
    
    AppItem selectByFiledName(String filedName);
     
    List<AppItem> selectAll();

	List<AppItem> selectByAppId(String appId);
	
	List<AppItem> queryAppItemByDegisnAndEmpty(AppItem appItem);
}