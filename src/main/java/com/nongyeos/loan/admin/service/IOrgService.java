package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.SysOrg;
import com.nongyeos.loan.admin.entity.SysOrgApplication;
import com.nongyeos.loan.admin.entity.SysPersonorg;

public interface IOrgService {

	SysOrg selectByOrgId(String orgId) throws Exception;
	
	String getBaseOrgId(String orgId) throws Exception;//根据孩子节点code获得根节点对象
	
	String getListByParentId(String personId, boolean firstLevel, boolean checkbox) throws Exception;

	SysOrg selectByName(String name) throws Exception;
	
	void addOrg(SysOrg sysOrg) throws Exception;
    
    void updateOrg(SysOrg sysOrg) throws Exception;
    
    List<SysOrg> getChildOrgs(String orgId) throws Exception;
    
    void deleteByOrgId(String orgId) throws Exception;

	List<SysOrg> getFirstLevelList(SysOrg pItem) throws Exception;

	List<SysOrg> selectByNameSearch(String orgName, String rootOrgId)throws Exception;

	String orgTreeString(List<String> orgIdList);

	List<SysOrg> selectOrgsByPerson(String personId)throws Exception;

	void delOrgapp(String orgId) throws Exception;

	void addOrgapp(SysOrgApplication bean) throws Exception;

	List<SysOrgApplication> getOrgs(String orgId) throws Exception;

	List<SysPersonorg> getOrgByPersonId(String personId) throws Exception;

	List<SysOrg> selectAll() throws Exception;

	List<SysOrg> selectBaseOrg() throws Exception;
}
