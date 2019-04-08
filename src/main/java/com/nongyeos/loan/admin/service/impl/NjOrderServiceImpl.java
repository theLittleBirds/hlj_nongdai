package com.nongyeos.loan.admin.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.*;
import com.nongyeos.loan.admin.mapper.*;
import com.nongyeos.loan.admin.service.INjOrderService;
import com.nongyeos.loan.base.util.DateUtils;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.base.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ThinkPad on 2019/3/25.
 */
@Service("njOrderService")
public class NjOrderServiceImpl implements INjOrderService {

    @Autowired
    private BusNjorderMapper njorderMapper;
    @Autowired
    private BusNjshopingcarMapper busNjshopingcarMapper;
    @Autowired
    private SysOrgMapper sysOrgMapper;
    @Autowired
    private BusNjorderinfoMapper busNjorderinfoMapper;
    @Autowired
    private NjProductMapper njProductMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteByPrimaryKey(String orderId)  throws Exception {
        try {
            if(StringUtils.isEmpty(orderId)){
                throw new Exception("商品id为空");
            }
            njorderMapper.deleteByPrimaryKey(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public void insertSelective(BusNjorder record)  throws Exception {
        try {
            if(record == null){
                throw new Exception("订单为空");
            }
            if(StringUtils.isEmpty(record.getOrgId())){
                throw new Exception("订单id为空");
            }
            njorderMapper.insertSelective(record);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public BusNjorder selectByPrimaryKey(String orderId)  throws Exception {
        try {
            if(StringUtils.isEmpty(orderId)){
                throw new Exception("商品id为空");
            }
            return njorderMapper.selectByPrimaryKey(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public void updateByPrimaryKeySelective(BusNjorder record)  throws Exception {
        try {
            if(record == null){
                throw new Exception("订单为空");
            }
            if(StringUtils.isEmpty(record.getOrgId())){
                throw new Exception("订单id为空");
            }
            njorderMapper.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public PageBeanUtil<BusNjorder> njOrderList(int currentPage, int pageSize, BusNjorder njorder) throws Exception {

        try {
            PageHelper.startPage(currentPage, pageSize);
            List<BusNjorder> njorderList = njorderMapper.selectNjOrderList(njorder);
            int total;
            if(njorderList==null){
                total = 0;
            }else{
                total = njorderList.size();
            }
            PageBeanUtil<BusNjorder> pageData = new PageBeanUtil<BusNjorder>(currentPage, pageSize, total);
            pageData.setItems(njorderList);
            return pageData;
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * 生成主订单和子订单，删除购物车
     * @param busNjorder
     * @param carIds
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor ={Exception.class,RuntimeException.class})
    public void createOrder(BusNjorder busNjorder, String carIds) throws Exception{
        try {
            //从购物车中查询出当前购物的商品
            List<BusNjshopingcar> busNjshopingcarList = new ArrayList<>();
            BusNjshopingcar busNjshopingcar = null;
            String[] carids = carIds.split(",");
            for (String carId : carids){
                busNjshopingcar = busNjshopingcarMapper.selectByPrimaryKey(carId);
                busNjshopingcarList.add(busNjshopingcar);
            }
            //计算显示总价和结算总价
            double totalPrice = 0.00;
            double totalSettlePrice = 0.00;
            for (BusNjshopingcar busNjshopingcar1 : busNjshopingcarList){
                double price = Double.parseDouble(busNjshopingcar1.getProductPrice());//显示单价
                double settleprice = Double.parseDouble(busNjshopingcar1.getProductSettlePrice());//结算单价
                Integer num = busNjshopingcar1.getProductNum();
                double tmpPrice = price * num;
                double tmpSettlePrice = settleprice * num;
                totalPrice += tmpPrice;
                totalSettlePrice += tmpSettlePrice;
            }
            //创建主订单
            busNjorder.setTotalPrice(String.valueOf(totalPrice));
            busNjorder.setTotalSettlePrice(String.valueOf(totalSettlePrice));
            njorderMapper.insertSelective(busNjorder);
            //创建子订单
            for (BusNjshopingcar busNjshopingcar1 : busNjshopingcarList){
                String productId = busNjshopingcar1.getProductId();
                NjProduct njProduct = njProductMapper.queryNjProductByPrimaryKey(productId);
                String orgId = njProduct.getOrgId();//商品所属的商品的orgId
                SysOrg sysOrg = sysOrgMapper.selectByPrimaryKey(orgId);
                BusNjorderinfo busNjorderinfo = new BusNjorderinfo();
                busNjorderinfo.setOrderInfoId(UUIDUtil.getUUID());
                busNjorderinfo.setOrderId(busNjorder.getOrderId());
                busNjorderinfo.setProductId(busNjshopingcar1.getProductId());
                busNjorderinfo.setMerId(orgId);
                busNjorderinfo.setMerName(sysOrg.getFullCname());
                busNjorderinfo.setProductName(busNjshopingcar1.getProductName());
                busNjorderinfo.setProductBrandName(busNjshopingcar1.getProductBrandName());
                busNjorderinfo.setProductCategoryName(busNjshopingcar1.getProductCategoryName());
                busNjorderinfo.setProductNum(busNjshopingcar1.getProductNum());
                busNjorderinfo.setProductPrice(busNjshopingcar1.getProductPrice());
                busNjorderinfo.setProductSettlePrice(busNjshopingcar1.getProductSettlePrice());
                busNjorderinfo.setCreateDate(DateUtils.getNowDate());
                busNjorderinfo.setUpdateDate(DateUtils.getNowDate());
                busNjorderinfoMapper.insert(busNjorderinfo);
            }
            //删除购物车里面加入订单的商品
            for (String carId : carids){
                busNjshopingcarMapper.deleteByPrimaryKey(carId);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public PageBeanUtil<BusNjorder> njOrderListByPersonId(int currentPage, int pageSize, Map map) throws Exception {
        try {
            PageHelper.startPage(currentPage, pageSize);
            List<BusNjorder> njorderList = njorderMapper.selectNjOrderListByPersonId(map);
//            List<BusNjorder> njorderList = njorderMapper.selectNjOrderListByPersonId(map.get("status").toString(),map.get("personId").toString());
            int total;
            if(njorderList==null){
                total = 0;
            }else{
                total = njorderList.size();
            }
            PageBeanUtil<BusNjorder> pageData = new PageBeanUtil<BusNjorder>(currentPage, pageSize, total);
            pageData.setItems(njorderList);
            return pageData;
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }
}
