package com.nongyeos.loan.app.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.app.entity.AppEntry;

public interface AppEntryMapper {
    int deleteByPrimaryKey(String entryId);

    int insert(AppEntry record);

    int addAppEntrySelective(AppEntry record);

    AppEntry selectByPrimaryKey(String entryId);

    int updateByAppEntrySelective(AppEntry record);

    int updateByPrimaryKey(AppEntry record);
    
    AppEntry queryByAppModeId(String modeId);
    
    List<AppEntry> todayStatistics(Map<String,Object> map);
    
    int todayRefuse(Map<String,Object> map);
}