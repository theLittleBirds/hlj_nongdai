package com.nongyeos.loan.core.model;

import com.nongyeos.loan.app.entity.AppApplication;
import com.nongyeos.loan.app.entity.AppEntry;
import com.nongyeos.loan.app.entity.FlowNode;

public class BusinessObject extends ActionBase
{

	private AppEntry entry;
	private Object bean;
	private AppApplication curApp;
	private FlowNode curNode;
	
	public BusinessObject(AppEntry entry, Object bean, AppApplication curApp)
	{
		this.entry = entry;
		this.bean = bean;
		this.curApp = curApp;
	}

	public AppEntry getEntry()
	{
		return entry;
	}

	public void setEntry(AppEntry entry)
	{
		this.entry = entry;
	}

	public Object getBean()
	{
		return bean;
	}

	public void setBean(Object bean)
	{
		this.bean = bean;
	}

	public AppApplication getCurApp()
	{
		return curApp;
	}

	public void setCurApp(AppApplication curApp)
	{
		this.curApp = curApp;
	}

	public FlowNode getCurNode()
	{
		return curNode;
	}

	public void setCurNode(FlowNode curNode)
	{
		this.curNode = curNode;
	}
	
	
	
}
