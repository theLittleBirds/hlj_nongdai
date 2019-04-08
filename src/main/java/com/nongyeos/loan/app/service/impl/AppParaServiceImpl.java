package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.app.entity.AppPara;
import com.nongyeos.loan.app.mapper.AppParaMapper;
import com.nongyeos.loan.app.service.IAppParaService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("appParaService")
public class AppParaServiceImpl implements IAppParaService {

	@Autowired
	private AppParaMapper appParaMapper;


	// 全查
	@Override
	public PageBeanUtil<AppPara> selectAll(int limit, int offset, String appId) throws Exception{
		try {
			//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			PageHelper.startPage(offset, limit, false);
			List<AppPara> allItems = appParaMapper.selectAll(appId);
			int countNums = appParaMapper.count(appId);//总记录数
			PageBeanUtil<AppPara> pageData = new PageBeanUtil<AppPara>(offset, limit, countNums);
			pageData.setItems(allItems);
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	// 添加
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addAppPara(AppPara appPara) throws Exception{
		try {
			if(appPara==null){
				throw new Exception("appPara为空");
			}
			appParaMapper.insertSelective(appPara);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 更新
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateAppPara(AppPara appPara) throws Exception{
		try {
			if(appPara==null){
				throw new Exception("appPara为空");
			}
			appParaMapper.updateByPrimaryKey(appPara);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteAppPara(Integer appParaId) throws Exception{
		try {
			if(appParaId==null){
				throw new Exception("appParaId为空");
			}
			appParaMapper.deleteByPrimaryKey(appParaId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//根据名称查询
	@Override
	public List<AppPara> selectByGroupName(AppPara appPara) throws Exception {
		try {
			return  appParaMapper.selectByGroupName(appPara);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	//查询参数 不重复的
	@Override
	public List<AppPara> selectByOne(String appId)throws Exception {
		try{
			List<AppPara> list= appParaMapper.selectByOne(appId);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<AppPara> selectAllDS() throws Exception {
		try{
			List<AppPara> list= appParaMapper.selectAllDS();
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<AppPara> selectByName(String appParaGroupName,String appId) throws Exception {
		List<AppPara> list=null;
		try{
			if(appParaGroupName!=null && !appParaGroupName.equals("") && appId !=null && !appId.equals("")){
				list= appParaMapper.selectByName(appParaGroupName,appId);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("appParaGroupName或者appId为空");
		}
		return list;
	}

}
