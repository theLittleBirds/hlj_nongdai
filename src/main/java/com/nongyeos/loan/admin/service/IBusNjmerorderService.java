package com.nongyeos.loan.admin.service;

import com.nongyeos.loan.admin.entity.BusNjmerorder;
import com.nongyeos.loan.base.util.PageBeanUtil;

import java.util.List;

/**
 * Created by ThinkPad on 2019/3/28.
 */
public interface IBusNjmerorderService {

    void deleteByPrimaryKey(Integer merOrderId) throws Exception;

    void insertSelective(BusNjmerorder record) throws Exception;

    BusNjmerorder selectByPrimaryKey(Integer merOrderId) throws Exception;

    BusNjmerorder selectByOrderIdAndOrgId(String orderId, String orgId)throws Exception;

    void updateByPrimaryKeySelective(BusNjmerorder record) throws Exception;

    PageBeanUtil<BusNjmerorder> njmerorderList(int currentPage, int pageSize, BusNjmerorder njmerorder,String personId) throws Exception;

    List<BusNjmerorder> selectByOrderId(String orderId) throws Exception;
}
