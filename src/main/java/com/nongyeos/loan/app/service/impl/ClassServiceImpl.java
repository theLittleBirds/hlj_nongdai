package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.app.entity.ScoreClass;
import com.nongyeos.loan.app.mapper.ScoreClassMapper;
import com.nongyeos.loan.app.service.IClassService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("classService")
public class ClassServiceImpl implements IClassService{
	
	@Autowired
	private ScoreClassMapper classMapper;

	@Override
	public List<ScoreClass> selectAll(String scoreid) {
		try {
			List<ScoreClass> list = classMapper.selectAll(scoreid);
		    return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void add(ScoreClass sclass) throws Exception{
		try{
			classMapper.insertSelective(sclass);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(ScoreClass sclass) throws Exception{
		try{
			classMapper.updateByPrimaryKey(sclass);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(String classId)throws Exception{
		try{
			if(classId != null && !classId.equals("")){
				classMapper.deleteByPrimaryKey(classId);
			}else{
				throw new Exception("评分卡变量Id为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	public PageBeanUtil<ScoreClass> selectByPage(int limit, int offset, String scoreid) throws Exception {
		try {
			//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			PageHelper.startPage(offset, limit, false);
			List<ScoreClass> allItems = classMapper.selectAll(scoreid);
			int countNums = classMapper.count(scoreid);//总记录数
			PageBeanUtil<ScoreClass> pageData = new PageBeanUtil<ScoreClass>(offset, limit, countNums);
			pageData.setItems(allItems);
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

}
