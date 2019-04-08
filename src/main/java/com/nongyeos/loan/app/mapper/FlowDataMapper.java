package com.nongyeos.loan.app.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.app.entity.FlowData;


public interface FlowDataMapper {
    int deleteByPrimaryKey(Integer dataId);

    int insert(FlowData record);

    int insertSelective(FlowData record);

    FlowData selectByPrimaryKey(Integer dataId);

    int updateByPrimaryKeySelective(FlowData record);

    int updateByPrimaryKey(FlowData record);

	List<FlowData> selectByNodeIdAndType(Map<Object, Object> map);

	List<FlowData> selectByControlType(Map<Object, Object> map);
	
	List<FlowData> selectByNodeId(String nodeId);

}