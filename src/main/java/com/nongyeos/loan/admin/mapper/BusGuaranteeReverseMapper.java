package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusGuaranteeReverse;

public interface BusGuaranteeReverseMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusGuaranteeReverse record);

    int insertSelective(BusGuaranteeReverse record);

    BusGuaranteeReverse selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusGuaranteeReverse record);

    int updateByPrimaryKey(BusGuaranteeReverse record);

	List<BusGuaranteeReverse> selectByIntoPieceId(String intoPieceId);

	BusGuaranteeReverse selectByTypeAndIpId(BusGuaranteeReverse record);
}