package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusGuaranteeFee;

public interface BusGuaranteeFeeMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusGuaranteeFee record);

    int insertSelective(BusGuaranteeFee record);

    BusGuaranteeFee selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusGuaranteeFee record);

    int updateByPrimaryKey(BusGuaranteeFee record);

	List<BusGuaranteeFee> selectByIntopieceId(String intopieceId);

	int selectCountByIntopieceId(String id);
}