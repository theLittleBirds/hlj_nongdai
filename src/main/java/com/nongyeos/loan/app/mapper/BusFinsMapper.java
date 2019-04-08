package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.BusFins;

public interface BusFinsMapper {
    int deleteByPrimaryKey(String finsId);

    int insert(BusFins record);

    int insertSelective(BusFins record);

    BusFins selectByPrimaryKey(String finsId);

    int updateByPrimaryKeySelective(BusFins record);

    int updateByPrimaryKey(BusFins record);

	List<BusFins> selectAll();

	void deleteBusFins(String busFin);

	int countByOrgIdList(List<String> orgIdList);

	List<BusFins> selectByOrgIdList(List<String> orgIdList);
}