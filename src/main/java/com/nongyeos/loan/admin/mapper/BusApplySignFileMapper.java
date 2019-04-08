package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusApplySignFile;

public interface BusApplySignFileMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusApplySignFile record);

    int insertSelective(BusApplySignFile record);

    BusApplySignFile selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusApplySignFile record);

    int updateByPrimaryKey(BusApplySignFile record);
    
    List<BusApplySignFile> selectByLoanId(String loanId);
    
    List<BusApplySignFile> waitSign(String loanId);
    
    List<BusApplySignFile> finishSign(String loanId);
    
    BusApplySignFile checkExist(BusApplySignFile record);
}