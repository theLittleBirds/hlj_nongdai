package com.nongyeos.loan.admin.mapper;

import com.nongyeos.loan.admin.entity.BusNjorder;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BusNjorderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(BusNjorder record);

    int insertSelective(BusNjorder record);

    BusNjorder selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(BusNjorder record);

    int updateByPrimaryKey(BusNjorder record);

    List<BusNjorder> selectNjOrderList(@Param("njorder")BusNjorder njorder);

    List<BusNjorder> selectNjOrderListByPersonId(Map map);
//    List<BusNjorder> selectNjOrderListByPersonId(String personId,String status);
}