package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.app.entity.ScoreComcase;
import com.nongyeos.loan.app.mapper.ScoreComcaseMapper;
import com.nongyeos.loan.app.service.IScoreComcaseService;

@Service("comcaseService")
public class ScoreComcaseServiceImpl implements IScoreComcaseService{
	
	@Autowired
	private ScoreComcaseMapper comcaseMapper;

	@Override
	public List<ScoreComcase> selectByPage(String cvId) {
		try {
			if (cvId==null || cvId.equals("")) {
				throw new Exception("cvId为空");
			}
			List<ScoreComcase> list = comcaseMapper.selectAll(cvId);
		    return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void add(ScoreComcase comcase) throws Exception{
		try{
			comcaseMapper.insertSelective(comcase);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(ScoreComcase comcase) throws Exception{
		try{
			comcaseMapper.updateByPrimaryKey(comcase);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Integer cvId)throws Exception{
		try{
			if(cvId != null && !cvId.equals("")){
				comcaseMapper.deleteByPrimaryKey(cvId);
			}else{
				throw new Exception("评分卡变量Id为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public void deleteByCvId(String cvId) throws Exception {
		try{
			if(cvId != null && !cvId.equals("")){
				comcaseMapper.deleteByCvId(cvId);
			}else{
				throw new Exception("cvId为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
}
