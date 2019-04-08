package com.nongyeos.loan.admin.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.BusMemberLogin;

public interface BusMemberLoginMapper {
    int deleteByPrimaryKey(String loginId);

    int insert(BusMemberLogin record);

    int insertSelective(BusMemberLogin record);

    BusMemberLogin selectByPrimaryKey(String loginId);

    int updateByPrimaryKeySelective(BusMemberLogin record);

    int updateByPrimaryKey(BusMemberLogin record);
    
    
    BusMemberLogin selectByToken(String token);
    
    List<BusMemberLogin> selectAllByCondition(BusMemberLogin record);
    
    int selectCountByCondition(BusMemberLogin record);
    
    BusMemberLogin selectByLoginName(BusMemberLogin record);

	List<BusMemberLogin> selectByMemberId(Map<String, String> memberIdChannel);
}