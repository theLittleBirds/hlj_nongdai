package com.nongyeos.loan.admin.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.BusGuaranteeFeeInfo;

public interface BusGuaranteeFeeInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusGuaranteeFeeInfo record);

    int insertSelective(BusGuaranteeFeeInfo record);

    BusGuaranteeFeeInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusGuaranteeFeeInfo record);

    int updateByPrimaryKey(BusGuaranteeFeeInfo record);

	List<BusGuaranteeFeeInfo> selectByCondition(Map<String, Object> map);

	int selectCountByCondition(Map<String, Object> map);

	BusGuaranteeFeeInfo selectByIpId(String intopieceId);
}