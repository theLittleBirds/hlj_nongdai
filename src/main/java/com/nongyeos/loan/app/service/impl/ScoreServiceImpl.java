package com.nongyeos.loan.app.service.impl;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.app.entity.ScoreScore;
import com.nongyeos.loan.app.entity.ScoreScoreright;
import com.nongyeos.loan.app.mapper.ScoreScoreMapper;
import com.nongyeos.loan.app.mapper.ScoreScorerightMapper;
import com.nongyeos.loan.app.service.IScoreService;

@Service("scoreService")
public class ScoreServiceImpl implements IScoreService{
	
	@Autowired
	private ScoreScoreMapper scoreMapper;
	@Autowired
	private ScoreScorerightMapper scoreRightMapper;
	
	@Override
	public List<ScoreScore> selectByFinsId(String finsId)throws Exception{
		try{
			if(finsId != null && !finsId.equals("")){
				List<ScoreScore> list = scoreMapper.selectByFinsId(finsId);
				return list;
			}else{
				throw new Exception("机构编号为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void add(ScoreScore score) throws Exception{
		try{
			scoreMapper.insertSelective(score);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(ScoreScore score) throws Exception{
		try{
			scoreMapper.updateByPrimaryKey(score);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(String scoreId)throws Exception{
		try{
			if(scoreId != null && !scoreId.equals("")){
				scoreMapper.deleteByPrimaryKey(scoreId);
			}else{
				throw new Exception("评分卡Id为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	public ScoreScore getScoreById(String scoreId)throws Exception{
		try{
			if(scoreId != null && !scoreId.equals("")){
				ScoreScore application = scoreMapper.selectByPrimaryKey(scoreId);
		        return application;
			}else{
				throw new Exception("评分卡Id为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delByScoreId(String scoreid) throws Exception {
		try{
			if(scoreid!=null){
				scoreRightMapper.deleteByScoreId(scoreid);
			}else {
				throw new Exception("scoreid为空");
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<ScoreScoreright> getScoreRightByScoreId(String scoreid) throws Exception{
		try{
			if(scoreid != null){
				List<ScoreScoreright> list = scoreRightMapper.selectByScoreId(scoreid);
				return list;
			}else {
				throw new Exception("scoreid为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	public List<ScoreScore> selectByCategory(String value) throws Exception {
		try {
			List<ScoreScore> list = scoreMapper.selectByCategory(value);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ScoreScore> selectByCategoryAndIDList(String value,HashSet<String> idSet) throws Exception {
		try {
			List<ScoreScore> list = scoreMapper.selectByCategoryAndIDList(value,idSet);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<ScoreScore> selectAll() throws Exception {
		List<ScoreScore> list=scoreMapper.selectAll();
		return list;
	}

	@Override
	public ScoreScore selectById(String scoreId) throws Exception {
		ScoreScore score = null;
		try{
			if(scoreId != null){
				score = scoreMapper.selectByPrimaryKey(scoreId);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return score;
	}

	@Override
	public List<ScoreScoreright> selectByRoleId(List<String> roleIdList) throws Exception {
		try {
			List<ScoreScoreright> list = scoreRightMapper.selectByRoleId(roleIdList);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
