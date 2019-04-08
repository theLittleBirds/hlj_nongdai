package com.nongyeos.loan.admin.service;

/*
 *  Creat by zhoudi on 2019/3/26.
 */

import com.nongyeos.loan.admin.entity.BusNjaddress;

import java.util.List;

public interface NjAddressService {

    List<BusNjaddress> queryAddressAll(String personId) throws Exception;

    BusNjaddress queryAddressByOrgId(String personId) throws Exception;

    void inserAddress(BusNjaddress busNjaddress) throws Exception;

    void updateAddress(BusNjaddress busNjaddress1) throws Exception;

    void delAddressById(String id) throws Exception;

    BusNjaddress queryAddressById(String id) throws Exception;
}
