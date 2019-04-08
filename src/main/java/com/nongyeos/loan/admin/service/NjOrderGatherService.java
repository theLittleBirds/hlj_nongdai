package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.NjOrderGather;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface NjOrderGatherService {
    int deleteByPrimaryKey(String id) throws Exception;

    int insert(NjOrderGather record) throws Exception;

    int addOrderGatherSelective(NjOrderGather record) throws Exception;

    NjOrderGather queryOrderGatherByPk(String id) throws Exception;
    
    NjOrderGather queryOrderGatherByIpid(String intoPieceId) throws Exception;

    int updateOrderGatherByPKSelective(NjOrderGather record) throws Exception;

    int updateByPrimaryKey(NjOrderGather record) throws Exception;
    
    PageBeanUtil<NjOrderGather> queryNjProductOrderSelective(int currentPage, int pageSize, NjOrderGather record) throws Exception;

	PageBeanUtil<NjOrderGather> selectByloginId(Integer page1, int pageSize,
			String loginId, String associateDeliverOrder)throws Exception;
}