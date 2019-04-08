package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.IntQimoOutCall;

public interface IQimoOutCallService {
	
	List<IntQimoOutCall> selectByIpId(String intoPieceId) throws Exception;
}
