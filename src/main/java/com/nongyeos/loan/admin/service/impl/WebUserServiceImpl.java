package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.mapper.SysWebUserMapper;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("webUserService")
public class WebUserServiceImpl implements IWebUserService{
	
	@Autowired
	private SysWebUserMapper userMapper;
	
	@Override
	public SysWebUser selectUserByUserName(String name) {  
		try {
			if (name != null) {
				SysWebUser user = userMapper.selectByName(name);
				return user;
			}else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    } 
	
	@Override
    public SysWebUser selectUserById(String userId) throws Exception { 
		try {
			if (userId != null) {
				SysWebUser user = userMapper.selectByPrimaryKey(userId); 
			    return user;
			}else {
				throw new Exception("userId为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
    }
    
	@Override
	public List<SysWebUser> selectAll() throws Exception {
		try{
			List<SysWebUser> list =  userMapper.selectAll();
			if(list != null && list.size() > 0){
				return list;
			}else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("list为空");
		}
		
	}

	@Override
	public PageBeanUtil<SysWebUser> selectByPage(int currentPage, int pageSize) throws Exception {
		try {
			//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			PageHelper.startPage(currentPage, pageSize, false);
			List<SysWebUser> allItems = userMapper.selectAll();
			int countNums = userMapper.count();//总记录数
			PageBeanUtil<SysWebUser> pageData = new PageBeanUtil<SysWebUser>(currentPage, pageSize, countNums);
			pageData.setItems(allItems);
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("pagebean为空");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addUser(SysWebUser user) throws Exception {
		try {
			if (user != null) {
			    userMapper.insert(user);
			}else {
				throw new Exception("user为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
        }
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateUser(SysWebUser user) throws Exception {
		try {
			if (user != null) {
				userMapper.updateByPrimaryKey(user);
			}else {
				throw new Exception("user为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	public boolean existedSameName(SysWebUser fwUser)
	{
		SysWebUser user = userMapper.selectByName(fwUser.getUsername());
		if(user != null)
		{
			if(fwUser.getUserId() == null || fwUser.getUserId().equals(""))
			{
				return true;
			}
			else 
			{
				if(!fwUser.getUserId().equals(user.getUserId()))
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteUser(String userId) throws Exception{
		 try {
			 if(userId != null && !userId.equals("")){
	        	userMapper.deleteByPrimaryKey(userId);
			 }else{
				 throw new Exception("userId为空");
			 }
	     } catch (Exception e) {
	            e.printStackTrace();
	            throw new Exception(e);
	     }
	}
	
}
