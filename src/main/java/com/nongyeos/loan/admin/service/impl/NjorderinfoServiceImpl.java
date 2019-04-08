package com.nongyeos.loan.admin.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.BusNjorderinfo;
import com.nongyeos.loan.admin.mapper.BusNjorderinfoMapper;
import com.nongyeos.loan.admin.service.INjorderinfoService;
import com.nongyeos.loan.base.util.PageBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ThinkPad on 2019/3/25.
 */
@Service("njorderinfoService")
public class NjorderinfoServiceImpl implements INjorderinfoService {

    @Autowired
    private BusNjorderinfoMapper njorderinfoMapper;

    @Override
    public void deleteByPrimaryKey(String orderInfoId) throws Exception{
        try {
            if(StringUtils.isEmpty(orderInfoId)){
                throw new Exception("子订单id为空");
            }
            njorderinfoMapper.deleteByPrimaryKey(orderInfoId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public void insertSelective(BusNjorderinfo record) throws Exception{
        try {
            if(record == null){
                throw new Exception("子订单为空");
            }
            if(StringUtils.isEmpty(record.getOrderInfoId())){
                throw new Exception("子订单id为空");
            }
            njorderinfoMapper.insertSelective(record);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public BusNjorderinfo selectByPrimaryKey(String orderInfoId) throws Exception{
        try {
            if(StringUtils.isEmpty(orderInfoId)){
                throw new Exception("商品id为空");
            }
            return njorderinfoMapper.selectByPrimaryKey(orderInfoId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public void updateByPrimaryKeySelective(BusNjorderinfo record) throws Exception{
        try {
            if(record == null){
                throw new Exception("子订单为空");
            }
            if(StringUtils.isEmpty(record.getOrderInfoId())){
                throw new Exception("子订单id为空");
            }
            njorderinfoMapper.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public List<BusNjorderinfo> selectByOrderId(String orderId) throws Exception {
        try {
            if(StringUtils.isEmpty(orderId)){
                throw new Exception("订单id为空");
            }
            return njorderinfoMapper.selectByOrderId(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public List<BusNjorderinfo> selectByOrgId(String orgId) throws Exception {
        try {
            if(StringUtils.isEmpty(orgId)){
                throw new Exception("订单id为空");
            }
            return njorderinfoMapper.selectByOrgId(orgId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public List<BusNjorderinfo> selectByOrderIdAndOrgId(String orderId, String orgId) throws Exception {
        try {
            if(StringUtils.isEmpty(orderId) || StringUtils.isEmpty(orgId) ){
                throw new Exception("订单id为空或商户Id为空");
            }
            return njorderinfoMapper.selectByOrderIdAndOrgId(orderId,orgId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public PageBeanUtil<BusNjorderinfo> njOrderInfoList(int currentPage, int pageSize, String orgId, String orderId) throws Exception {
        try {
            PageHelper.startPage(currentPage, pageSize);
            List<BusNjorderinfo> njOrderInfoList = njorderinfoMapper.selectByOrderIdAndOrgId(orderId,orgId);
            int total;
            if(njOrderInfoList==null){
                total = 0;
            }else{
                total = njOrderInfoList.size();
            }
            PageBeanUtil<BusNjorderinfo> pageData = new PageBeanUtil<BusNjorderinfo>(currentPage, pageSize, total);
            pageData.setItems(njOrderInfoList);
            return pageData;
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }
}
