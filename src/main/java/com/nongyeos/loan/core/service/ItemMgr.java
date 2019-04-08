package com.nongyeos.loan.core.service;

import com.nongyeos.loan.core.model.BusinessObject;

public interface ItemMgr
{
	
	public Object getObjectValue(BusinessObject bo, String itemId) throws Exception;
	
	public Object getObjectItemValue(BusinessObject bo, String itemId) throws Exception;
	
	public void setObjectValue(BusinessObject bo, String itemId, Object newValue) throws Exception;
}
