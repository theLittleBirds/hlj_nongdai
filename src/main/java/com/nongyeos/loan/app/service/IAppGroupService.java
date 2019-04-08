package com.nongyeos.loan.app.service;

import java.util.List;

import com.nongyeos.loan.app.entity.AppAppgroup;
import com.nongyeos.loan.app.entity.BusFins;

public interface IAppGroupService {

	String groupTreeString(List<String> orgIdList);

	void addGroup(AppAppgroup appGroup) throws Exception;

	void updGroup(AppAppgroup appGroup) throws Exception;

	void delGroup(String groupId) throws Exception;

	List<AppAppgroup> getlistByFinsId(String finsId) throws Exception;

	AppAppgroup getGroupById(String groupId) throws Exception;

	List<AppAppgroup> getAll() throws Exception;

	List<AppAppgroup> selectByParentId(String id) throws Exception;

	List<BusFins> getFinsByOrgList(List<String> orgIdList) throws Exception;


}
