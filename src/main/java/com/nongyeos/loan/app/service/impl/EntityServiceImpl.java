package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.app.entity.AppEntity;
import com.nongyeos.loan.app.mapper.AppEntityMapper;
import com.nongyeos.loan.app.service.IEntityService;

@Service("entityService")
public class EntityServiceImpl implements IEntityService {

	@Autowired
	private AppEntityMapper entityMapper;

	/**
	 * 添加事物：全查：只有读的权限 修改：有事物回滚操作
	 */
	// 全查
	@Override
	public List<AppEntity> selectAll(String appId) throws Exception{
		try {
			List<AppEntity> list = entityMapper.selectAll(appId);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	// 添加
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addEntity(AppEntity appEntity) throws Exception{
		try {
			if(appEntity==null){
				throw new Exception("appEntity为空");
			}
			entityMapper.insert(appEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 修改
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateEntity(AppEntity appEntity) throws Exception{
		try {
			if(appEntity==null){
				throw new Exception("appEntity为空");
			}
			entityMapper.updateByPrimaryKey(appEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteEntity(String enityId) throws Exception{
		try {
			if(enityId==null){
				throw new Exception("enityId为空");
			}
			entityMapper.deleteByPrimaryKey(enityId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public List<AppEntity> getMainEntity(String appId){
		try{
			List<AppEntity> list = entityMapper.selectAll(appId);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public AppEntity getAppEntityByEntityId(String entityId)throws Exception{
		try{
			AppEntity beanAppEntity = entityMapper.selectByPrimaryKey(entityId);
		    return beanAppEntity;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
