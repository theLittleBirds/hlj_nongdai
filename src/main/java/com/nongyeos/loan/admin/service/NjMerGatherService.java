package com.nongyeos.loan.admin.service;

import com.nongyeos.loan.admin.entity.NjMerGather;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface NjMerGatherService {
    int deleteByPrimaryKey(String id) throws Exception;

    int insert(NjMerGather record) throws Exception;

    int addMerGatherSelective(NjMerGather record) throws Exception;

    NjMerGather queryMerGatherByPK(String id) throws Exception;
    
    NjMerGather queryMerGatherByOrderId(String orderId) throws Exception;
    
    PageBeanUtil<NjMerGather> queryNjMerGatherSelective(int currentPage, int pageSize, NjMerGather record) throws Exception;
    
    int updateMerGatherByPKSelective(NjMerGather record) throws Exception;

    int updateByPrimaryKey(NjMerGather record) throws Exception;
}