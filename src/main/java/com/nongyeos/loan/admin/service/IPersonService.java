package com.nongyeos.loan.admin.service;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.entity.SysPersonRole;
import com.nongyeos.loan.admin.entity.SysPersonorg;
import com.nongyeos.loan.base.util.PageBeanUtil;


public interface IPersonService {

	SysPerson selectByUserIdAndIsdefault(String userId) throws Exception;
	
	PageBeanUtil<SysPerson> selectByPage(int limit, int offset, String orgId) throws Exception;
	
	void addPerson(SysPerson sysPerson) throws Exception;

	void updatePerson(SysPerson sysPerson) throws Exception;

	void deletePerson(String personId) throws Exception;

	List<SysPersonRole> getRoles(String personId) throws Exception;

	List<SysPerson> selectByPage(String orgId) throws Exception;

	List<SysPersonorg> getOrgs(String personId) throws Exception;

	void addPersonorg(SysPersonorg porg) throws Exception;

	void delPersonorg(String personId) throws Exception;

	List<SysPersonRole> getPersonByRole(String roleId) throws Exception;

	SysPerson selectByPersonId(String operUserId)throws Exception;

	SysPerson sectByName(String updOperId)throws Exception;

	void delPersonorg2(String orgId) throws Exception;

	Map<String, Object> selectNameAndOrgByUserId(String userId)throws Exception;
	
	List<SysPerson> queryByOrgId(String orgId)throws Exception;

}
