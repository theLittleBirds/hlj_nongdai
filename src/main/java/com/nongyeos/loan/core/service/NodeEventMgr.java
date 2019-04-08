package com.nongyeos.loan.core.service;

import com.nongyeos.loan.core.model.BusinessObject;
import com.nongyeos.loan.core.model.MsgQueue;

public interface NodeEventMgr {
	
	public MsgQueue arrived(BusinessObject business);
	
	public MsgQueue close(BusinessObject business);
	
	public MsgQueue end(BusinessObject business);
	
	public MsgQueue opend(BusinessObject business);
	
	public MsgQueue save(BusinessObject business);
	
	public MsgQueue start(BusinessObject business);
	
	public MsgQueue doEvent(BusinessObject business, short runtime);
}
