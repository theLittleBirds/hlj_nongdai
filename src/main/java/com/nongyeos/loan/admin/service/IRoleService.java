package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.SysMenuRight;
import com.nongyeos.loan.admin.entity.SysPersonRole;
import com.nongyeos.loan.admin.entity.SysRole;
import com.nongyeos.loan.app.entity.ScoreScoreright;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IRoleService {

	PageBeanUtil<SysRole> getRoleListByOrgId(int limit, int offset, String orgId) throws Exception;
	
	List<SysRole> getRoleListByOrgId(String orgId) throws Exception;

	void save(SysRole roleBean) throws Exception;

	void update(SysRole roleBean) throws Exception;

	void delete(String roleId) throws Exception;

	SysRole selectByCname(String nameCn, String orgId) throws Exception;
	
	SysRole selectByEname(String nameEn, String orgId) throws Exception;
	
	void delByPersonId(String personId) throws Exception;

	void saveByPersonIdAndRoleId(SysPersonRole personRole) throws Exception;

	List<SysRole> getRoleAll() throws Exception;

	List<SysRole> listByStatus(short status, String orgId) throws Exception;

	void delByMenuId(String menuId) throws Exception;

	void saveByMenuIdAndRoleId(SysMenuRight menuRight) throws Exception;

	void saveByScoreIdAndRoleId(ScoreScoreright scoreRight) throws Exception;

	List<SysRole> getRolesByPersonOrg(List<String> orgs) throws Exception;

	void delByRoleId(String roleId) throws Exception;

}
