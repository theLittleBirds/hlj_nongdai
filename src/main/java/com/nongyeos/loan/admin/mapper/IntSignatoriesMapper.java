package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.IntSignatories;

public interface IntSignatoriesMapper {
    int deleteByPrimaryKey(String id);

    int insert(IntSignatories record);

    int insertSelective(IntSignatories record);

    IntSignatories selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(IntSignatories record);

    int updateByPrimaryKey(IntSignatories record);
    
    List<IntSignatories> selectIdSendSms(String loanId);
    
    List<IntSignatories> selectByapplySignFileId(String applySignFileId);
}