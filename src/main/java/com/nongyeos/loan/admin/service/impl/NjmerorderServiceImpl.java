package com.nongyeos.loan.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.nongyeos.loan.admin.entity.BusNjmerorder;
import com.nongyeos.loan.admin.mapper.BusNjmerorderMapper;
import com.nongyeos.loan.admin.mapper.BusNjorderMapper;
import com.nongyeos.loan.admin.mapper.BusNjorderinfoMapper;
import com.nongyeos.loan.admin.mapper.SysOrgMapper;
import com.nongyeos.loan.admin.service.IBusNjmerorderService;
import com.nongyeos.loan.base.util.PageBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ThinkPad on 2019/3/28.
 */
@Service("njmerorderService")
public class NjmerorderServiceImpl implements IBusNjmerorderService {

    @Autowired
    private BusNjmerorderMapper njmerorderMapper;
    @Autowired
    private BusNjorderinfoMapper busNjorderinfoMapper;
    @Autowired
    private BusNjorderMapper njorderMapper;
    @Autowired
    private SysOrgMapper sysOrgMapper;

    @Override
    public void deleteByPrimaryKey(Integer merOrderId) throws Exception{

        try {
            if(merOrderId==null){
                throw new Exception("商品id为空");
            }
            njmerorderMapper.deleteByPrimaryKey(merOrderId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }

    }

    @Override
    public void insertSelective(BusNjmerorder record) throws Exception {
        try {
            if(record == null){
                throw new Exception("订单为空");
            }
            njmerorderMapper.insertSelective(record);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public BusNjmerorder selectByPrimaryKey(Integer merOrderId) throws Exception{
        try {
            if(merOrderId==null){
                throw new Exception("商户订单id为空");
            }
            return njmerorderMapper.selectByPrimaryKey(merOrderId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public BusNjmerorder selectByOrderIdAndOrgId(String orderId, String orgId) throws Exception {
        try {
            if(orderId==null || orgId==null){
                throw new Exception("商户订单id为空或订单Id为空");
            }
            return njmerorderMapper.selectByOrderIdAndOrgId(orderId,orgId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public void updateByPrimaryKeySelective(BusNjmerorder record) throws Exception {
        try {
            if(record == null){
                throw new Exception("订单为空");
            }
            njmerorderMapper.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public PageBeanUtil<BusNjmerorder> njmerorderList(int currentPage, int pageSize, BusNjmerorder njmerorder,String personId) throws Exception {
        try {
            PageHelper.startPage(currentPage, pageSize);
            List<BusNjmerorder> njmerorderList = njmerorderMapper.selectNjmerorderList(njmerorder,personId);
            int total;
            if(njmerorderList==null){
                total = 0;
            }else{
                total = njmerorderList.size();
            }
            PageBeanUtil<BusNjmerorder> pageData = new PageBeanUtil<BusNjmerorder>(currentPage, pageSize, total);
            pageData.setItems(njmerorderList);
            return pageData;
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public List<BusNjmerorder> selectByOrderId(String orderId) throws Exception {
        List<BusNjmerorder> busNjmerorderList = null;
        try {
            if(StringUtil.isEmpty(orderId)){
                throw new Exception("系统异常！");
            }else{
                busNjmerorderList = njmerorderMapper.selectNjmerorderListByOrderId(orderId);
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return busNjmerorderList;
    }
}
