package com.nongyeos.loan.admin.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.BusFollowItem;
import com.nongyeos.loan.admin.resultMap.FollowItemMap;

public interface BusFollowItemMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusFollowItem record);

    int insertSelective(BusFollowItem record);

    BusFollowItem selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusFollowItem record);

    int updateByPrimaryKey(BusFollowItem record);
    
    List<FollowItemMap> selectDynamicByType(Map<String, String> map);
    
    List<BusFollowItem> selectByType(String type);
    
    int selectCountByType(String type);
}