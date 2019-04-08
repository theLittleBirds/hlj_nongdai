package com.nongyeos.loan.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.SysPerson;
import com.nongyeos.loan.admin.entity.SysPersonRole;
import com.nongyeos.loan.admin.entity.SysPersonorg;
import com.nongyeos.loan.admin.mapper.SysPersonMapper;
import com.nongyeos.loan.admin.mapper.SysPersonRoleMapper;
import com.nongyeos.loan.admin.mapper.SysPersonorgMapper;
import com.nongyeos.loan.admin.service.IPersonService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("personService")
public class PersonServiceImpl implements IPersonService{
	
	@Autowired
	private SysPersonMapper personMapper;
	@Autowired
	private SysPersonRoleMapper personRoleMapper;
	@Autowired
	private SysPersonorgMapper personOrgMapper;
	
	@Override
	public SysPerson selectByUserIdAndIsdefault(String userId) throws Exception { 
		try{
			if (userId != null) {
				SysPerson user = personMapper.selectByUserIdAndIsdefault(userId);
				return user;
			}else {
				throw new Exception("userid为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
    }

	@Override
	public PageBeanUtil<SysPerson> selectByPage(int limit,int offset,String orgId) throws Exception {
		try {
			//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			PageHelper.startPage(offset, limit, false);
			List<SysPerson> allItems = personMapper.selectByOrgId(orgId);
			int countNums = personMapper.count(orgId);//总记录数
			PageBeanUtil<SysPerson> pageData = new PageBeanUtil<SysPerson>(offset, limit, countNums);
			pageData.setItems(allItems);
			return pageData;
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
        }
	}
	
	@Override
	public List<SysPerson> selectByPage(String orgId) throws Exception {
		try {
			if (orgId != null) {
				List<SysPerson> list = personMapper.selectByOrgId(orgId);
				return list;
			}else {
				throw new Exception("orgid为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
        }
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addPerson(SysPerson sysPerson) throws Exception {
		try{
			if (sysPerson != null) {
				personMapper.insert(sysPerson);
			}else {
				throw new Exception("sysPerson为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	} 
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updatePerson(SysPerson sysPerson) throws Exception{
		try{
			if (sysPerson != null) {
				personMapper.updateByPrimaryKey(sysPerson);
			}else {
				throw new Exception("sysPerson为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deletePerson(String personId) throws Exception{
		try{
			if (personId != null) {
				personMapper.deleteByPrimaryKey(personId);
			}else {
				throw new Exception("personId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	public List<SysPersonRole> getRoles(String personId) throws Exception{
		try{
			if (personId != null && !personId.equals("")) {
			    List<SysPersonRole> list = personRoleMapper.getRoleByPerson(personId);
			    return list;
			}else {
				throw new Exception("personId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	public List<SysPersonorg> getOrgs(String personId) throws Exception{
		try{
			if(personId != null && !personId.equals("")){
				List<SysPersonorg> list = personOrgMapper.selectByPersonId(personId);
			    return list;
			}else{
				throw new Exception("personId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	public List<SysPersonRole> getPersonByRole(String roleId) throws Exception{
		try{
			if(roleId != null && !roleId.equals("")){
				List<SysPersonRole> list = personRoleMapper.getPersonByRole(roleId);
				return list;
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
	public void addPersonorg(SysPersonorg porg) throws Exception{
		try{
			if(porg != null){
				personOrgMapper.insertSelective(porg);
			}else{
				throw new Exception("对象为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delPersonorg(String personId) throws Exception{
		try{
			if(personId != null && !personId.equals("")){
				personOrgMapper.deleteByPersonId(personId);
			}else{
				throw new Exception("personId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delPersonorg2(String orgId) throws Exception{
		try{
			if(orgId != null && !orgId.equals("")){
				personOrgMapper.deleteByOrgId(orgId);
			}else{
				throw new Exception("orgId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public SysPerson selectByPersonId(String operUserId) throws Exception {
		if(StringUtils.isEmpty(operUserId)){
			throw new Exception("人员Id为空");
		}
		try {
			return personMapper.selectByPrimaryKey(operUserId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错");
		}
	}

	@Override
	public SysPerson sectByName(String name) throws Exception {
		try {
			return personMapper.selectByName(name);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错");
		}
	}

	@Override
	public Map<String, Object> selectNameAndOrgByUserId(String userId) throws Exception {
		try {
			return personMapper.selectNameAndOrgByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错");
		}
	}

	@Override
	public List<SysPerson> queryByOrgId(String orgId) throws Exception {
		try {
			return personMapper.selectByOrgId(orgId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错");
		}
	}
	
}
