package com.nongyeos.loan.admin.mapper;

import com.nongyeos.loan.admin.entity.BusNjshopingcar;

import java.util.List;

public interface BusNjshopingcarMapper {
    int deleteByPrimaryKey(String shopingCarId);

    int insert(BusNjshopingcar record);

    int insertSelective(BusNjshopingcar record);

    BusNjshopingcar selectByPrimaryKey(String shopingCarId);

    int updateByPrimaryKeySelective(BusNjshopingcar record);

    int updateByPrimaryKey(BusNjshopingcar record);

    List<BusNjshopingcar> quertShoppingCarList(String personId);
}