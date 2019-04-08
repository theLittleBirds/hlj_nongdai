package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusRejectReason;

public interface IRejectReasonService {

	void addRejectReason(List<BusRejectReason> list) throws Exception;

	List<BusRejectReason> selectByIpId(String intoPieceId)throws Exception;
	
	void insert(BusRejectReason record)throws Exception;
	
}
