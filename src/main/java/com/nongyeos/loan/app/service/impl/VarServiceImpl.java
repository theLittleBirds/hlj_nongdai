package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.app.entity.ScoreVar;
import com.nongyeos.loan.app.mapper.ScoreVarMapper;
import com.nongyeos.loan.app.service.IVarService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("varService")
public class VarServiceImpl implements IVarService{
	
	@Autowired
	private ScoreVarMapper varMapper;

	@Override
	public List<ScoreVar> selectAll() {
		try {
			List<ScoreVar> list = varMapper.selectAll();
		    return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void add(ScoreVar var) throws Exception{
		try{
			varMapper.insertSelective(var);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(ScoreVar var) throws Exception{
		try{
			varMapper.updateByPrimaryKey(var);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(String varId)throws Exception{
		try{
			if(varId != null && !varId.equals("")){
				varMapper.deleteByPrimaryKey(varId);
			}else{
				throw new Exception("变量Id为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public ScoreVar getScvarById(String varId) throws Exception {
		try{
			ScoreVar var = varMapper.selectByPrimaryKey(varId);
			return var;
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public PageBeanUtil<ScoreVar> selectByPage(int limit, int offset) throws Exception {
		try {
			//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			PageHelper.startPage(offset, limit, false);
			List<ScoreVar> allItems = varMapper.selectAll();
			int countNums = varMapper.count();// 总记录数
			PageBeanUtil<ScoreVar> pageData = new PageBeanUtil<ScoreVar>(offset, limit, countNums);
			pageData.setItems(allItems);
			return pageData;
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
}
