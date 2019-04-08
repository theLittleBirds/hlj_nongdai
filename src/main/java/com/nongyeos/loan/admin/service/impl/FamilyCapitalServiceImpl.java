package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.admin.entity.BusFamilyCapital;
import com.nongyeos.loan.admin.mapper.BusFamilyCapitalMapper;
import com.nongyeos.loan.admin.service.IFamilyCapitalService;
import com.nongyeos.loan.base.util.ApplicationContextProvider;
import com.nongyeos.loan.base.util.StrUtils;

@Service("familyCapitalService")
public class FamilyCapitalServiceImpl implements IFamilyCapitalService{
	
	@Autowired
	private BusFamilyCapitalMapper familyCapitalMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insert(BusFamilyCapital record) throws Exception {
		if(record == null)
			throw new Exception("家庭财产模板为空");
		if(StrUtils.isEmpty(record.getId()))
			throw new Exception("家庭财产id为空");
		try {
			return familyCapitalMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKey(BusFamilyCapital record) throws Exception {
		if(record == null)
			throw new Exception("家庭财产模板为空");
		if(StrUtils.isEmpty(record.getId()))
			throw new Exception("家庭财产id为空");
		try {
			return familyCapitalMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusFamilyCapital selectByIntoPieceId(BusFamilyCapital record)
			throws Exception {
		if(record==null)
			throw new Exception("进件标识为空");
		try {
			return familyCapitalMapper.selectByIntoPieceId(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	public List<Object> getSonEntryByParentItemId(String mainId,SqlSession session) throws Exception{
		List<Object> list = null;
		try{
			if(mainId != null && !mainId.equals("")){
				list = session.getMapper(BusFamilyCapitalMapper.class).selectByParentItemId(mainId);
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
				session.getMapper(BusFamilyCapitalMapper.class).updateByPrimaryKeySelective((BusFamilyCapital) record);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("record为空");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKeySelective(BusFamilyCapital record)
			throws Exception {
		if(record == null)
			throw new Exception("家庭财产模板为空");
		if(StrUtils.isEmpty(record.getId()))
			throw new Exception("家庭财产id为空");
		try {
			return familyCapitalMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

}
