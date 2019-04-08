package com.nongyeos.loan.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.app.entity.AppSrvunit;
import com.nongyeos.loan.app.mapper.AppSrvunitMapper;
import com.nongyeos.loan.app.service.IAppSvrunitService;

@Service("svrunitService")
public class AppSvrunitServiceImpl implements IAppSvrunitService {

	@Resource
	private AppSrvunitMapper appsrvunitMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addSvrunit(AppSrvunit srvunit) throws Exception {
		try{
			if(srvunit != null){
				appsrvunitMapper.insertSelective(srvunit);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("srvunit为空");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updSvrunit(AppSrvunit srvunit) throws Exception {
		try{
			if(srvunit != null){
				appsrvunitMapper.updateByPrimaryKey(srvunit);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("srvunit为空");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteSrvunit(String srvunitId) throws Exception {
		try{
			if(srvunitId != null && !srvunitId.equals("")){
				appsrvunitMapper.deleteByPrimaryKey(srvunitId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("srvunitId为空");
		}
	}

	@Override
	public List<AppSrvunit> selectByAppId(String appId) throws Exception {
		List<AppSrvunit> list = null;
		try{
			if(appId != null && !appId.equals("")){
				list=appsrvunitMapper.selectByAppid(appId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("appId为空");
		}
		return list;
	}

	@Override
	public AppSrvunit selectBySrvunitId(String srvunId) throws Exception {
		AppSrvunit srvunit=null;
		try{
			if(srvunId != null && !srvunId.equals("")){
				srvunit=appsrvunitMapper.selectByPrimaryKey(srvunId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("srvunId为空");
		}
		return srvunit;
	}

}
