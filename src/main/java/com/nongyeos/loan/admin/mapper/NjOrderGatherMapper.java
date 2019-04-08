package com.nongyeos.loan.admin.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.NjOrderGather;

public interface NjOrderGatherMapper {
    int deleteByPrimaryKey(String id);

    int insert(NjOrderGather record);

    int addOrderGatherSelective(NjOrderGather record);

    NjOrderGather queryOrderGatherByPk(String id);
    
    NjOrderGather queryOrderGatherByIpid(String intoPieceId);

    List<NjOrderGather> queryOrderGatherSelective(NjOrderGather record);

    int updateOrderGatherByPKSelective(NjOrderGather record);

    int updateByPrimaryKey(NjOrderGather record);

	List<NjOrderGather> selectByloginId(Map<String, Object> map);

	int selectCountByloginId(Map<String, Object> map);
}