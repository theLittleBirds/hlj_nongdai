package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.NjMerGather;

public interface NjMerGatherMapper {
    int deleteByPrimaryKey(String id);

    int insert(NjMerGather record);

    int addMerGatherSelective(NjMerGather record);

    NjMerGather queryMerGatherByPK(String id);

    NjMerGather queryMerGatherByOrderId(String orderId);

    List<NjMerGather> queryMerGatherSelective(NjMerGather record);

    int updateMerGatherByPKSelective(NjMerGather record);

    int updateByPrimaryKey(NjMerGather record);
}