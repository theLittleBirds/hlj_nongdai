package com.nongyeos.loan.admin.service;

import com.nongyeos.loan.admin.entity.BusFamilyOperate;

public interface IFamilyOperateService {
	
	int insert(BusFamilyOperate record) throws Exception;
	
	int updateByPrimaryKeySelective(BusFamilyOperate record) throws Exception;

	int updateByPrimaryKey(BusFamilyOperate record) throws Exception;
	    
	BusFamilyOperate selectByIpId(String intoPieceId) throws Exception;
}
