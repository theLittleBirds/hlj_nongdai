package com.nongyeos.loan.admin.mapper;

import com.nongyeos.loan.admin.entity.BusNjorderinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BusNjorderinfoMapper {
    int deleteByPrimaryKey(String orderInfoId);

    int insert(BusNjorderinfo record);

    int insertSelective(BusNjorderinfo record);

    BusNjorderinfo selectByPrimaryKey(String orderInfoId);

    List<BusNjorderinfo> selectByOrderId(String orderId);

    List<BusNjorderinfo> selectByOrgId(String orgId);

    List<BusNjorderinfo> selectByOrderIdAndOrgId(@Param("orderId")String orderId, @Param("orgId")String orgId);

    int updateByPrimaryKeySelective(BusNjorderinfo record);

    int updateByPrimaryKey(BusNjorderinfo record);

}