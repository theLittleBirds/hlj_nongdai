package com.nongyeos.loan.core.model;

public class ActionBase
{
	private String resCode;
	private MsgQueue msgQueue;
	
	public String getResCode()
	{
		return resCode;
	}
	public void setResCode(String resCode)
	{
		this.resCode = resCode;
	}
	public MsgQueue getMsgQueue() {
		return msgQueue;
	}
	public void setMsgQueue(MsgQueue msgQueue) {
		this.msgQueue = msgQueue;
	}
	
	
}
