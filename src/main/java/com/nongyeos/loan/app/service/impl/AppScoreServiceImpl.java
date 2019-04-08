package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.app.entity.AppScore;
import com.nongyeos.loan.app.mapper.AppScoreMapper;
import com.nongyeos.loan.app.service.IAppScoreService;

@Service("appScoreService")
public class AppScoreServiceImpl implements IAppScoreService {

	@Autowired
	private AppScoreMapper appScoreMapper;
	
	@Override
	public List<AppScore> selectByAppId(String appId) throws Exception {
		List<AppScore> list=null;
		try{
			if(appId!=null && !appId.equals("")){
				list=appScoreMapper.selectByAppId(appId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("appId为空");
		}
		return list;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addAppScore(AppScore appScore) throws Exception {
		try{
			if(appScore != null){
				appScoreMapper.insert(appScore);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("appScore为空");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateAppScore(AppScore appScore) throws Exception {
		try{
			if(appScore != null){
				appScoreMapper.updateByPrimaryKey(appScore);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("appScore为空");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delAppScore(String appScoreId) throws Exception {
		try{
			if(appScoreId != null && !appScoreId.equals("")){
				appScoreMapper.deleteByPrimaryKey(appScoreId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("appScoreId为空");
		}
	}

	@Override
	public AppScore selectByAppscId(String appscId) throws Exception {
		AppScore appScore = null;
		try{
			if(appscId != null && !appscId.equals("")){
				appScore = appScoreMapper.selectByPrimaryKey(appscId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("appScoreId为空");
		}
		return appScore;
	}

}
