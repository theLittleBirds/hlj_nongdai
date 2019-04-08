package com.nongyeos.loan.admin.service;

/*
 *  Creat by zhoudi on 2019/3/26.
 */


import com.nongyeos.loan.admin.entity.BusNjshopingcar;
import com.nongyeos.loan.base.util.PageBeanUtil;

import java.util.List;

public interface NjShoppingCarService {

    List<BusNjshopingcar> queryShoppingCarList(String orgId)throws Exception;

    void insertShoppingCar(BusNjshopingcar busNjshopingcar)throws Exception;

    void updNjShoppingCar(BusNjshopingcar busNjshopingcar)throws Exception;

    PageBeanUtil<BusNjshopingcar> queryBusNjShoppingCarList(int currentPage, int pageSize, String personId) throws Exception;

    void deleteNjProductByPK(String id) throws Exception;

    BusNjshopingcar queryBusNjShoppingCarById(String carId) throws Exception;
}
