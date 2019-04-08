package com.nongyeos.loan.app.service;

import com.nongyeos.loan.app.entity.IntPartner;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IPartnerService {

	void deletePartner(Integer partnerId) throws Exception;

	boolean existedSameName(IntPartner partner) throws Exception;
	
	void addPartner(IntPartner partner) throws Exception;
	
	void updatePartner(IntPartner partner) throws Exception;
	
	PageBeanUtil<IntPartner> selectAll(int limit, int offset) throws Exception;
}
