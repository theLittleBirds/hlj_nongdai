package com.nongyeos.loan.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.nongyeos.loan.admin.entity.BusNjshopingcar;
import com.nongyeos.loan.admin.mapper.BusNjshopingcarMapper;
import com.nongyeos.loan.admin.service.NjShoppingCarService;
import com.nongyeos.loan.base.util.PageBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 *  Creat by zhoudi on 2019/3/26.
 */

@Service("njShoppingCarService")
public class NjShoppingCarServiceImpl implements NjShoppingCarService {

    @Autowired
    private BusNjshopingcarMapper busNjshopingcarMapper;

    @Override
    public List<BusNjshopingcar> queryShoppingCarList(String orgId) throws Exception {
        List<BusNjshopingcar> busNjshopingcarsList = null;
        try {
            busNjshopingcarsList = busNjshopingcarMapper.quertShoppingCarList(orgId);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
        return busNjshopingcarsList;
    }

    @Override
    public void insertShoppingCar(BusNjshopingcar busNjshopingcar) throws Exception {
        try {
            busNjshopingcarMapper.insert(busNjshopingcar);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public void updNjShoppingCar(BusNjshopingcar busNjshopingcar) throws Exception {
        try {
            busNjshopingcarMapper.updateByPrimaryKey(busNjshopingcar);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public PageBeanUtil<BusNjshopingcar> queryBusNjShoppingCarList(int currentPage, int pageSize, String personId) throws Exception {
        try {
            List<BusNjshopingcar> queryNjProducttotal = busNjshopingcarMapper.quertShoppingCarList(personId);
            PageHelper.startPage(currentPage, pageSize);
            List<BusNjshopingcar> queryNjProductSelective = busNjshopingcarMapper.quertShoppingCarList(personId);
            int total = queryNjProducttotal.size();

            PageBeanUtil<BusNjshopingcar> pageData = new PageBeanUtil<BusNjshopingcar>(currentPage, pageSize, total);
            pageData.setItems(queryNjProductSelective);

            return pageData;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public void deleteNjProductByPK(String id) throws Exception {
        try {
           if(StringUtil.isNotEmpty(id)){
               busNjshopingcarMapper.deleteByPrimaryKey(id);
           }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public BusNjshopingcar queryBusNjShoppingCarById(String carId) throws Exception {
        BusNjshopingcar busNjshopingcar = null;
        try {
            if(StringUtil.isNotEmpty(carId)){
                busNjshopingcar = busNjshopingcarMapper.selectByPrimaryKey(carId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return busNjshopingcar;
    }
}
