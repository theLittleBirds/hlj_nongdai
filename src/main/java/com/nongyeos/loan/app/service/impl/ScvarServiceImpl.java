package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.app.entity.ScoreScvar;
import com.nongyeos.loan.app.mapper.ScoreScvarMapper;
import com.nongyeos.loan.app.service.IScvarService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("scvarService")
public class ScvarServiceImpl implements IScvarService{
	
	@Autowired
	private ScoreScvarMapper scvarMapper;

	@Override
	public List<ScoreScvar> selectAll(String scoreid) {
		try {
			List<ScoreScvar> list = scvarMapper.selectAll(scoreid);
		    return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void add(ScoreScvar scvar) throws Exception{
		try{
			scvarMapper.insertSelective(scvar);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(ScoreScvar scvar) throws Exception{
		try{
			scvarMapper.updateByPrimaryKey(scvar);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(String scvarId)throws Exception{
		try{
			if(scvarId != null && !scvarId.equals("")){
				scvarMapper.deleteByPrimaryKey(scvarId);
			}else{
				throw new Exception("评分卡变量Id为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	public ScoreScvar getScvarById(String scvarId)throws Exception{
		try{
			if(scvarId != null && !scvarId.equals("")){
				ScoreScvar application = scvarMapper.selectByPrimaryKey(scvarId);
		        return application;
			}else{
				throw new Exception("评分卡变量Id为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public PageBeanUtil<ScoreScvar> selectByPage(int limit, int offset, String scoreid) throws Exception {
		try {
			//设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
			PageHelper.startPage(offset, limit, false);
			List<ScoreScvar> allItems = scvarMapper.selectAll(scoreid);
			int countNums = scvarMapper.count(scoreid);//总记录数
			PageBeanUtil<ScoreScvar> pageData = new PageBeanUtil<ScoreScvar>(offset, limit, countNums);
			pageData.setItems(allItems);
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<ScoreScvar> selectByPage(String scoreid, short type) {
		try {
			List<ScoreScvar> list = scvarMapper.selectByPage(scoreid,type);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

