package com.nongyeos.loan.admin.service.impl;

/*
 *  Creat by zhoudi on 2019/3/26.
 */

import com.github.pagehelper.StringUtil;
import com.nongyeos.loan.admin.entity.BusNjaddress;
import com.nongyeos.loan.admin.mapper.BusNjaddressMapper;
import com.nongyeos.loan.admin.service.NjAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("njAddressService")
public class NjAddressServiceImpl implements NjAddressService {

    @Autowired
    private BusNjaddressMapper busNjaddressMapper;

    @Override
    public List<BusNjaddress> queryAddressAll(String personId) throws Exception {
        List<BusNjaddress> busNjaddressList = null;
        try {
            if(StringUtil.isNotEmpty(personId)){
                busNjaddressList = busNjaddressMapper.queryAddressListAll(personId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return busNjaddressList;
    }

    @Override
    public BusNjaddress queryAddressByOrgId(String personId) throws Exception {
        BusNjaddress busNjaddress = null;
        try {
            if(StringUtil.isNotEmpty(personId)){
                busNjaddress = busNjaddressMapper.queryAddressByOrgId(personId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return busNjaddress;
    }

    @Override
    public void inserAddress(BusNjaddress busNjaddress) throws Exception {
        try {
            busNjaddressMapper.insert(busNjaddress);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public void updateAddress(BusNjaddress busNjaddress1) throws Exception {
        try {
            busNjaddressMapper.updateByPrimaryKeySelective(busNjaddress1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public void delAddressById(String id) throws Exception {
        try {
            busNjaddressMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public BusNjaddress queryAddressById(String id) throws Exception {
        BusNjaddress busNjaddress = null;
        try {
            busNjaddress = busNjaddressMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return busNjaddress;
    }
}
