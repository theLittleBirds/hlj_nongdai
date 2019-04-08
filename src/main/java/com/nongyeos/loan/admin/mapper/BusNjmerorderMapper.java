package com.nongyeos.loan.admin.mapper;

import com.nongyeos.loan.admin.entity.BusNjmerorder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BusNjmerorderMapper {
    int deleteByPrimaryKey(Integer merOrderId);

    int insert(BusNjmerorder record);

    int insertSelective(BusNjmerorder record);

    BusNjmerorder selectByPrimaryKey(Integer merOrderId);

    BusNjmerorder selectByOrderIdAndOrgId(@Param("orderId")String orderId, @Param("orgId")String orgId);

    int updateByPrimaryKeySelective(BusNjmerorder record);

    int updateByPrimaryKey(BusNjmerorder record);

    List<BusNjmerorder> selectNjmerorderList(@Param("njmerorder")BusNjmerorder njmerorder, @Param("personId")String personId);

    List<BusNjmerorder> selectNjmerorderListByOrderId(String orderId);
}