package com.nongyeos.loan.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.nongyeos.loan.app.entity.AppItemScvar;
import com.nongyeos.loan.app.mapper.AppItemScvarMapper;
import com.nongyeos.loan.app.service.IAppItemScvarService;

@Service("itemScvarService")
public class ItemScvarServiceImpl implements IAppItemScvarService {

	@Autowired
	private AppItemScvarMapper itemScvarMapper;

	@Override
	public AppItemScvar queryByScvarId(String scvarId, String scoreId, String appId) throws Exception {
		try {
			if(StringUtils.isEmpty(scvarId)){
				throw new Exception("评分变量scvarId为空");
			}
			if(StringUtils.isEmpty(scoreId)){
				throw new Exception("评分卡scoreId为空");
			}
			if(StringUtils.isEmpty(appId)){
				throw new Exception("产品应用appId为空");
			}
			return itemScvarMapper.queryByScvarId(scvarId,scoreId,appId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delAppItem(Integer appItemid) throws Exception {
		try{
			if(appItemid!=null){
				itemScvarMapper.deleteByPrimaryKey(appItemid);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("appItemid为空");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateAppItem(AppItemScvar appItemScvar) throws Exception {
		try{
			if(appItemScvar!=null){
				itemScvarMapper.updateByPrimaryKey(appItemScvar);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("appItemScvar为空");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addAppItem(AppItemScvar appItemScvar) throws Exception {
		try{
			if(appItemScvar!=null){
				itemScvarMapper.insert(appItemScvar);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("appItemScvar为空");
		}
	}

	@Override
	public List<AppItemScvar> selectByAppscId(String appscId) throws Exception {
		List<AppItemScvar> list=null;
		try{
			if(appscId!=null && !appscId.equals("")){
				list=itemScvarMapper.selectByAppscId(appscId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("appscId为空");
		}
		return list;
	}

	@Override
	public List<AppItemScvar> existItemId(String itemId,String appscId) throws Exception {
		List<AppItemScvar> list=null;
		try{
			if(itemId!=null && !itemId.equals("")){
				list=itemScvarMapper.existItemId(itemId,appscId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("itemId为空");
		}
		return list;
	}

	@Override
	public List<AppItemScvar> existItemscvarId(String scvarId,String appscId) throws Exception {
		List<AppItemScvar> list=null;
		try{
			if(scvarId!=null && !scvarId.equals("")){
				list=itemScvarMapper.existScvarId(scvarId,appscId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("scvarId为空");
		}
		return list;
	}

	@Override
	public AppItemScvar selectById(Integer itemscvarId) throws Exception {
		AppItemScvar appItemScvar=null;
		try{
			if(itemscvarId!=null){
				appItemScvar=itemScvarMapper.selectByPrimaryKey(itemscvarId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("itemscvarId为空");
		}
		return appItemScvar;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Integer itemscvarId) throws Exception {
		try{
			if(itemscvarId!=null){
				itemScvarMapper.deleteByPrimaryKey(itemscvarId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("itemscvarId为空");
		}
	}

	@Override
	public List<AppItemScvar> existItemId1(Integer appItemScvarId,String itemId, String appscId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AppItemScvar> list=null;
		try{
			if(itemId!=null && !itemId.equals("")){
				map.put("appItemScvarId", appItemScvarId);
				map.put("itemId", itemId);
				map.put("appscId", appscId);
				list=itemScvarMapper.existItemId1(map);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("itemId为空");
		}
		return list;
	}

	@Override
	public List<AppItemScvar> existItemscvarId1(Integer appItemScvarId,String scvarId, String appscId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AppItemScvar> list=null;
		try{
			if(scvarId!=null && !scvarId.equals("")){
				map.put("appItemScvarId", appItemScvarId);
				map.put("scvarId", scvarId);
				map.put("appscId", appscId);
				list=itemScvarMapper.existScvarId1(map);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("scvarId为空");
		}
		return list;
	}

}
