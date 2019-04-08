package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.app.entity.IntServiceifa;
import com.nongyeos.loan.app.mapper.IntServiceifaMapper;
import com.nongyeos.loan.app.service.IServiceifaService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("/serviceifaService")
public class ServiceifaServiceImpl implements IServiceifaService {

	@Autowired
	private IntServiceifaMapper serviceifaMapper;
	
	//全查
	@Override
	public List<IntServiceifa> selectAll() throws Exception{
		List<IntServiceifa> ls = null;
		try {
			ls = serviceifaMapper.selectAll();
			if(ls==null){
				throw new Exception("全查为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}

	@Override
	public PageBeanUtil<IntServiceifa> selectAll(int limit, int offset) throws Exception {
		try {
			//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			PageHelper.startPage(offset, limit, false);
			List<IntServiceifa> allItems = serviceifaMapper.selectAll();
			int countNums = serviceifaMapper.count();//总记录数
			PageBeanUtil<IntServiceifa> pageData = new PageBeanUtil<IntServiceifa>(offset, limit, countNums);
			pageData.setItems(allItems);
			return pageData;
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
        }
	}

	//添加
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void add(IntServiceifa serviceifa) throws Exception{
		try{
			if(serviceifa==null){
				throw new Exception("serviceifa为空");
			}
			serviceifaMapper.insertSelective(serviceifa);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//更新
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(IntServiceifa serviceifa) throws Exception{
		try{
			if(serviceifa==null){
				throw new Exception("serviceifa为空");
			}
			serviceifaMapper.updateByPrimaryKey(serviceifa);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//删除
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteServiceifa(String serviceifaID) throws Exception{
		try{
			if(serviceifaID==null){
				throw new Exception("serviceifaID为空");
			}
			serviceifaMapper.deleteByPrimaryKey(serviceifaID);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
