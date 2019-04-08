package com.nongyeos.loan.admin.service;

import com.nongyeos.loan.admin.entity.BusContact;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IContactService {
	
	int insert(BusContact record) throws Exception;
	
	int selectRepeat(BusContact record) throws Exception;

    PageBeanUtil<BusContact> selectByPage(int currentPage,
			int pageSize, BusContact record) throws Exception;

	BusContact selectByPhoneMember(BusContact contact)throws Exception;
}
