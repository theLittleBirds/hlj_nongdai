package com.nongyeos.loan.admin.service;

import com.nongyeos.loan.admin.entity.BusNjorderinfo;
import com.nongyeos.loan.base.util.PageBeanUtil;

import java.util.List;

/**
 * Created by ThinkPad on 2019/3/25.
 */
public interface INjorderinfoService {

    void deleteByPrimaryKey(String orderInfoId) throws Exception;

    void insertSelective(BusNjorderinfo record) throws Exception;

    BusNjorderinfo selectByPrimaryKey(String orderInfoId) throws Exception;

    void updateByPrimaryKeySelective(BusNjorderinfo record) throws Exception;

    List<BusNjorderinfo> selectByOrderId(String orderId) throws Exception;

    List<BusNjorderinfo> selectByOrgId(String orgId) throws Exception;

    List<BusNjorderinfo> selectByOrderIdAndOrgId(String orderId,String orgId) throws Exception;

    PageBeanUtil<BusNjorderinfo> njOrderInfoList(int currentPage, int pageSize, String orgId,String orderId) throws Exception;

}
