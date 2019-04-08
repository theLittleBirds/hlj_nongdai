package com.nongyeos.loan.admin.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.BusContactTemplate;

public interface BusContactTemplateMapper {
	int deleteByPrimaryKey(String id);

    int insert(BusContactTemplate record);

    int insertSelective(BusContactTemplate record);

    BusContactTemplate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusContactTemplate record);

    int updateByPrimaryKey(BusContactTemplate record);
    
    int selectCountByFinsId(Map<String, String> map);
    
    List<BusContactTemplate> selectByFinsId(Map<String, String> map);
    
    List<BusContactTemplate> waitForSign(String finsId);
}