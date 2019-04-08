package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusMember;

public interface BusMemberMapper {
    int deleteByPrimaryKey(String memberId);

    int insert(BusMember record);

    int insertSelective(BusMember record);

    BusMember selectByPrimaryKey(String memberId);

    int updateByPrimaryKeySelective(BusMember record);

    int updateByPrimaryKey(BusMember record);
    
    List<BusMember> selectByPage(BusMember member);

	BusMember selectByIdCard(String idCard);

	int selectTotalNum(BusMember member);
}