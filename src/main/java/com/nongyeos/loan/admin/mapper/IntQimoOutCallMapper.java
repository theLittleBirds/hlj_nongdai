package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.IntQimoOutCall;

public interface IntQimoOutCallMapper {
    int deleteByPrimaryKey(String id);

    int insert(IntQimoOutCall record);

    int insertSelective(IntQimoOutCall record);

    IntQimoOutCall selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(IntQimoOutCall record);

    int updateByPrimaryKey(IntQimoOutCall record);
    
    List<IntQimoOutCall> selectByIpId(String intoPieceId);
}