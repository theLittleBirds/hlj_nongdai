package com.nongyeos.loan.admin.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.BusLoanDetail;

public interface BusLoanDetailMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusLoanDetail record);

    int insertSelective(BusLoanDetail record);

    BusLoanDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusLoanDetail record);

    int updateByPrimaryKey(BusLoanDetail record);

	int deleteByLoanId(String loanId);
	
	List<BusLoanDetail> selectByLoanId(String loanId);
	
	BusLoanDetail selectNextRepay(BusLoanDetail record);
	
	BusLoanDetail selectNowRepay(String  loanId);
	
	List<BusLoanDetail> selectNonRepay(String  loanId);

	BusLoanDetail selectLastComplete(String intoPieceId);
	
	List<Map<String, Object>> queryLoanDetail(BusLoanDetail record);
}