package com.nongyeos.loan.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.SysMenuRight;
import com.nongyeos.loan.admin.entity.SysPersonRole;
import com.nongyeos.loan.admin.entity.SysRole;
import com.nongyeos.loan.admin.mapper.SysMenuRightMapper;
import com.nongyeos.loan.admin.mapper.SysPersonRoleMapper;
import com.nongyeos.loan.admin.mapper.SysRoleMapper;
import com.nongyeos.loan.admin.service.IRoleService;
import com.nongyeos.loan.app.entity.ScoreScoreright;
import com.nongyeos.loan.app.mapper.ScoreScorerightMapper;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("roleService")
public class RoleServiceImpl implements IRoleService{
	
	@Autowired
	private SysRoleMapper roleMapper;
	@Autowired
	private SysMenuRightMapper menuRightMapper;
	@Autowired
	private SysPersonRoleMapper personRoleMapper;
	@Autowired
	private ScoreScorerightMapper scoreRightMapper;

	@Override
	public PageBeanUtil<SysRole> getRoleListByOrgId(int limit, int offset,
			String orgId) throws Exception {
		try {
			//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			PageHelper.startPage(offset, limit, false);
			List<SysRole> allItems = roleMapper.getRoleListByOrgId(orgId);
			int countNums = roleMapper.count(orgId);//总记录数
			PageBeanUtil<SysRole> pageData = new PageBeanUtil<SysRole>(offset, limit, countNums);
			pageData.setItems(allItems);
			return pageData;
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
        }
	}
	
	@Override
	public List<SysRole> getRoleListByOrgId(String id) throws Exception {
		try{
			if (id != null) {
				List<SysRole> list = roleMapper.getRoleListByOrgId(id);
				return list;
			}else {
				throw new Exception("id为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	public List<SysRole> getRolesByPersonOrg(List<String> orgs) throws Exception {
		try{
			if(orgs != null && orgs.size() > 0){
				List<SysRole> list = roleMapper.getRolesByPersonOrg(orgs);
				return list;
			}else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<SysRole> getRoleAll() throws Exception{
		try{
			List<SysRole> list = roleMapper.selectAll();
			return list;
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("list为空");
		}
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void save(SysRole sysRole) throws Exception {
		try{
			if (sysRole != null) {
				roleMapper.insert(sysRole);
			}else {
				throw new Exception("sysrole为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(SysRole sysRole) throws Exception {
		try{
			if (sysRole != null) {
				roleMapper.updateByPrimaryKey(sysRole);
			}else {
				throw new Exception("sysrole为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(String roleId) throws Exception {
		try{
			if(roleId != null){
				roleMapper.deleteByPrimaryKey(roleId);
			}else {
				throw new Exception("roleId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
		
	}

	@Override
	public SysRole selectByCname(String nameCn, String orgId) throws Exception {
		try{
			Map<String,Object> map = new HashMap<String, Object>();
			if(nameCn != null && orgId != null){
				map.put("nameCn", nameCn);
				map.put("orgId", orgId);
				SysRole sysRole = roleMapper.selectByCname(map);
				return sysRole;
			}else {
				throw new Exception("namecn为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
		
	}
	@Override
	public SysRole selectByEname(String nameEn, String orgId) throws Exception {
		try{
			Map<String,Object> map = new HashMap<String, Object>();
			if(nameEn != null && orgId != null){
				map.put("nameEn", nameEn);
				map.put("orgId", orgId);
				SysRole sysRole = roleMapper.selectByEname(map);
				return sysRole;
			}else {
				throw new Exception("nameen为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delByPersonId(String personId) throws Exception {
		try{
			if(personId!=null){
				personRoleMapper.deleteByPersonId(personId);
			}else {
				throw new Exception("personid为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delByMenuId(String menuId) throws Exception{
		try{
			if(menuId!=null){
				menuRightMapper.deleteByMenuId(menuId);
			}else {
				throw new Exception("menuId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delByRoleId(String roleId) throws Exception{
		try{
			if(roleId != null){
				menuRightMapper.deleteByRoleId(roleId);
			}else{
				throw new Exception("roleId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveByPersonIdAndRoleId(SysPersonRole personRole) throws Exception{
		try{
			if(personRole!=null){
				personRoleMapper.insertSelective(personRole);
			}else {
				throw new Exception("personRole为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveByMenuIdAndRoleId(SysMenuRight menuRight) throws Exception{
		try{
			if(menuRight!=null){
				menuRightMapper.insertSelective(menuRight);
			}else {
				throw new Exception("menuRight为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	public List<SysRole> listByStatus(short status, String orgId) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			map.put("status", status);
			map.put("orgId", orgId);
			List<SysRole> list= roleMapper.selectByStatus(map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveByScoreIdAndRoleId(ScoreScoreright scoreRight) throws Exception {
		try{
			if(scoreRight!=null){
				scoreRightMapper.insertSelective(scoreRight);
			}else {
				throw new Exception("scoreRight为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
    
}
