package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.app.entity.AppItem;
import com.nongyeos.loan.app.mapper.AppItemMapper;
import com.nongyeos.loan.app.service.IAppItemService;

@Service("itemService")
public class AppItemServiceImpl implements IAppItemService{
	
	@Autowired
	private AppItemMapper itemMapper;

	@Override
	public List<AppItem> queryAppItemByDegisnAndEmpty(AppItem appItem)
			throws Exception {
		try {
			if(appItem == null){
				throw new Exception("appItem为空");
			}
			return itemMapper.queryAppItemByDegisnAndEmpty(appItem);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e); 
		}
	}	
	
	@Override
	public List<AppItem> selectAllByEntityId(String entityId) throws Exception {
		List<AppItem> list = null;
		try{
			if(entityId!=null && !entityId.equals("")){
			 list = itemMapper.selectByEntityId(entityId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("entityId为空");
		}
		return list;
	}
	
	@Override
	public AppItem selectByFiledName(String filedName){
		try{
			AppItem item = itemMapper.selectByFiledName(filedName);
			return item;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addItem(AppItem item){
		try{
			itemMapper.insertSelective(item);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateItem(AppItem item){
		try{
			itemMapper.updateByPrimaryKey(item);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteItem(String itemId)throws Exception{
		try{
			if(itemId != null && !itemId.equals("")){
				itemMapper.deleteByPrimaryKey(itemId);
			}else{
				throw new Exception("Id不能为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	public AppItem selectItem(String itemId)throws Exception{
		try{
			AppItem beanAppItem = itemMapper.selectByPrimaryKey(itemId);
			return beanAppItem;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<AppItem> getAllItem()throws Exception{
		try{
			List<AppItem> list = itemMapper.selectAll();
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
