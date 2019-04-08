package com.nongyeos.loan.app.mapper;

import java.util.List;

import com.nongyeos.loan.app.entity.IntPartner;

public interface IntPartnerMapper {
    int deleteByPrimaryKey(Integer partnerId);

    int insert(IntPartner record);

    int insertSelective(IntPartner record);

    IntPartner selectByPrimaryKey(Integer partnerId);

    int updateByPrimaryKeySelective(IntPartner record);

    int updateByPrimaryKey(IntPartner record);

	List<IntPartner> selectAll();

	int count();

	//void deleteByPartnerId(String partnerId);

	IntPartner selectPartnerByName(String cname);
}