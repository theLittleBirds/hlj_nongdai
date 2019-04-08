package com.nongyeos.loan.admin.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.BusIntopieceReverse;

public interface BusIntopieceReverseMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusIntopieceReverse record);

    int insertSelective(BusIntopieceReverse record);

    BusIntopieceReverse selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusIntopieceReverse record);

    int updateByPrimaryKey(BusIntopieceReverse record);

	BusIntopieceReverse selectByIpId(String intoPieceId);

	List<BusIntopieceReverse> selectByCondition(Map<String, Object> map);

	int selectCountByCondition(Map<String, Object> map);
}