package com.nongyeos.loan.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.app.entity.ScoreComvalue;
import com.nongyeos.loan.app.mapper.ScoreComvalueMapper;
import com.nongyeos.loan.app.service.IScoreComvalueService;

@Service("comvalueService")
public class ScoreComvalueServiceImpl implements IScoreComvalueService{
	
	@Autowired
	private ScoreComvalueMapper comvalueMapper;

	@Override
	public List<ScoreComvalue> selectByPage(String scvarId) {
		try {
			if (scvarId==null || scvarId.equals("")) {
				throw new Exception("scvarId为空");
			}
			List<ScoreComvalue> list = comvalueMapper.selectAll(scvarId);
		    return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void add(ScoreComvalue comvalue) throws Exception{
		try{
			comvalueMapper.insertSelective(comvalue);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(ScoreComvalue comvalue) throws Exception{
		try{
			comvalueMapper.updateByPrimaryKey(comvalue);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(String cvId)throws Exception{
		try{
			if(cvId != null && !cvId.equals("")){
				comvalueMapper.deleteByPrimaryKey(cvId);
			}else{
				throw new Exception("评分卡变量Id为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
}
