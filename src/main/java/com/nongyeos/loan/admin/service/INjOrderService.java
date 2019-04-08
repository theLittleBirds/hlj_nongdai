package com.nongyeos.loan.admin.service;

import com.nongyeos.loan.admin.entity.BusNjorder;
import com.nongyeos.loan.base.util.PageBeanUtil;

import java.util.Map;

/**
 * Created by ThinkPad on 2019/3/25.
 */
public interface INjOrderService {

    void deleteByPrimaryKey(String orderId) throws Exception ;

    void insertSelective(BusNjorder record) throws Exception ;

    BusNjorder selectByPrimaryKey(String orderId) throws Exception ;

    void updateByPrimaryKeySelective(BusNjorder record) throws Exception ;

    PageBeanUtil<BusNjorder> njOrderList(int currentPage, int pageSize, BusNjorder njorder) throws Exception;

    void createOrder(BusNjorder busNjorder, String carIds) throws Exception;

    PageBeanUtil<BusNjorder> njOrderListByPersonId(int currentPage, int pageSize, Map map) throws Exception;
}
