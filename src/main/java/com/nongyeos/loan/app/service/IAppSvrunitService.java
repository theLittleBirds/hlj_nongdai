package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.AppSrvunit;

public interface IAppSvrunitService {

	void addSvrunit(AppSrvunit srvunit) throws Exception;

	void updSvrunit(AppSrvunit srvunit) throws Exception;

	void deleteSrvunit(String srvunitId) throws Exception;

	List<AppSrvunit> selectByAppId(String appId) throws Exception;

	AppSrvunit selectBySrvunitId(String srvunId) throws Exception;

}
