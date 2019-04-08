package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.SysActLog;
import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.mapper.SysActLogMapper;
import com.nongyeos.loan.admin.service.ActLogService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("logService")
public class ActLogServiceImpl implements ActLogService {

	@Autowired
	private SysActLogMapper ActLogMapper;
	
	@Override
	public PageBeanUtil<SysActLog> logPage(int currentPage, int pageSize) throws Exception{
		try {
			//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			PageHelper.startPage(currentPage, pageSize, false);
			List<SysActLog> allItems = ActLogMapper.queryList();
			int countNums = ActLogMapper.count();//总记录数
			PageBeanUtil<SysActLog> pageData = new PageBeanUtil<SysActLog>(currentPage, pageSize, countNums);
			pageData.setItems(allItems);
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("pagebean为空");
		}
	}

	@Override
	public void add(SysActLog actLog) throws Exception{
		try{
			// TODO Auto-generated method stub
			if(actLog != null){
				ActLogMapper.insert(actLog);
			}else{
				throw new Exception("对象为空 ");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Integer logId) throws Exception{
		try{
			if(logId != null && !logId.equals("")){
				// TODO Auto-generated method stub
				ActLogMapper.deleteById(logId);
			}else{
				throw new Exception("logId为空 ");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void update(SysActLog actLog) throws Exception{
		try{
			if(actLog != null){
				// TODO Auto-generated method stub
				ActLogMapper.update(actLog);
			}else{
				throw new Exception("对象为空 ");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
