package com.nongyeos.loan.admin.service;

import com.nongyeos.loan.admin.entity.BusFamilyCredit;

public interface IFamilyCreditService {
	
	int insert(BusFamilyCredit record) throws Exception;
	
	int updateByPrimaryKey(BusFamilyCredit record) throws Exception;
	
	BusFamilyCredit selectByIpId(BusFamilyCredit record) throws Exception;
}
