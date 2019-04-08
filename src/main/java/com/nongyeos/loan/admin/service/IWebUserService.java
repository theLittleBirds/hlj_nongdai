package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IWebUserService {

	//用户登录  
	SysWebUser selectUserByUserName(String name);
	
	SysWebUser selectUserById(String userId) throws Exception;
	
	List<SysWebUser> selectAll() throws Exception;
	
	PageBeanUtil<SysWebUser> selectByPage(int currentPage,int pageSize) throws Exception;
	
	boolean existedSameName(SysWebUser fwUser);
	
	void addUser(SysWebUser user) throws Exception;
	
	void updateUser(SysWebUser user) throws Exception;
	
	void deleteUser(String userId) throws Exception;

}
