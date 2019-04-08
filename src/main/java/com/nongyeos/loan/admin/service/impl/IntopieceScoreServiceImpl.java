package com.nongyeos.loan.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.nongyeos.loan.admin.entity.BusFamilySituation;
import com.nongyeos.loan.admin.entity.BusIntopieceScore;
import com.nongyeos.loan.admin.mapper.BusFamilySituationMapper;
import com.nongyeos.loan.admin.mapper.BusIntopieceScoreMapper;
import com.nongyeos.loan.admin.service.IBusIntopieceScoreService;

@Service("intopieceScoreService")
public class IntopieceScoreServiceImpl implements IBusIntopieceScoreService{

	@Autowired
	private BusIntopieceScoreMapper busIntopieceScoreMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addIntopieceScoreSelective(BusIntopieceScore record) throws Exception {
		try {
			if(record == null){
				throw new Exception("BusIntopieceScore对象为空");
			}
			return busIntopieceScoreMapper.addIntopieceScoreSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}	
	
	@Override
	public List<BusIntopieceScore> queryByIntopieceId(String intoPieceId)
			throws Exception {
		try {
			if(StringUtils.isEmpty(intoPieceId)){
				throw new Exception("进件id为空");
			}
			return busIntopieceScoreMapper.queryByIntopieceId(intoPieceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}	
	
	@Override
	public int deleteByPrimaryKey(String id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(BusIntopieceScore record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BusIntopieceScore selectByPrimaryKey(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(BusIntopieceScore record)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(BusIntopieceScore record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Object> getSonEntryByParentItemId(String mainId,SqlSession session) throws Exception{
		List<Object> list = null;
		try{
			if(mainId != null && !mainId.equals("")){
				list = session.getMapper(BusIntopieceScoreMapper.class).selectByParentItemId(mainId);
			}
		}catch(Exception e){
			e.printStackTrace();
			return list;
		}
		return list;
	}
	
	public void updateBean(Object record,SqlSession session) throws Exception{
		try{
			if(record != null){
				session.getMapper(BusIntopieceScoreMapper.class).updateByPrimaryKeySelective((BusIntopieceScore) record);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("record为空");
		}
	}

	@Override
	public int deleteByIpId(String intoPieceId,String scoreId) throws Exception {
		if(intoPieceId == null){
			throw new Exception("进件标识为空");
		}
		try {			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("intoPieceId", intoPieceId);
			map.put("scoreId", scoreId);
			return busIntopieceScoreMapper.deleteByIpId(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<BusIntopieceScore> historyScore(String intoPieceId)
			throws Exception {
		if(intoPieceId == null){
			throw new Exception("进件标识为空");
		}
		try {			
			return busIntopieceScoreMapper.historyScore(intoPieceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<Map<String, Object>> selectExpByIntopieceId(String intopieceId)
			throws Exception {
		if(intopieceId == null){
			throw new Exception("进件标识为空");
		}
		try {			
			return busIntopieceScoreMapper.selectExpByIntopieceId(intopieceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询评分出错");
		}
	}
}