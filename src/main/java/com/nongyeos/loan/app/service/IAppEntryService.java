package com.nongyeos.loan.app.service;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.app.entity.AppEntry;

public interface IAppEntryService {
    int deleteByPrimaryKey(String entryId) throws Exception;

    int insert(AppEntry record) throws Exception;

    int addAppEntrySelective(AppEntry record) throws Exception;

    AppEntry selectByPrimaryKey(String entryId) throws Exception;

    int updateByAppEntrySelective(AppEntry record) throws Exception;

    int updateByPrimaryKey(AppEntry record) throws Exception;
    
    AppEntry queryByAppModeId(String modeId) throws Exception;
    
    List<AppEntry> todayStatistics(Map<String,Object> map) throws Exception;
    
    int todayRefuse(Map<String,Object> map) throws Exception;
}