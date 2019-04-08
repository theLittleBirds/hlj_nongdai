package com.nongyeos.loan.admin.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.BusProduct;

public interface BusProductMapper {
    int deleteByPrimaryKey(String productId);

    int insert(BusProduct record);

    int insertSelective(BusProduct record);

    BusProduct selectByPrimaryKey(String productId);

    int updateByPrimaryKeySelective(BusProduct record);

    int updateByPrimaryKey(BusProduct record);

	List<BusProduct> selectByPage(Map<String, Object> map);

	int selectTotalNum(Map<String, Object> map);

	BusProduct selectByName(String name);
	
	List<BusProduct> selectByOrgId(String orgId);

	List<BusProduct> selectByFinsId(String lenderId);
}