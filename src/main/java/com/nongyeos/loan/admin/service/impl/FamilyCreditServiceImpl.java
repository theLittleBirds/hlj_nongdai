package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.admin.entity.BusFamilyCapital;
import com.nongyeos.loan.admin.entity.BusFamilyCredit;
import com.nongyeos.loan.admin.mapper.BusFamilyCapitalMapper;
import com.nongyeos.loan.admin.mapper.BusFamilyCreditMapper;
import com.nongyeos.loan.admin.service.IFamilyCreditService;

@Service("familyCreditService")
public class FamilyCreditServiceImpl implements IFamilyCreditService {
	
	@Autowired
	private BusFamilyCreditMapper familyCreditMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insert(BusFamilyCredit record) throws Exception{
		if(record == null){
			throw new Exception("家庭征信模板为空");
		}
		if(record.getId() == null || "".equals(record.getId())){
			throw new Exception("家庭征信模板id为空");
		}
		try {
			return familyCreditMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKey(BusFamilyCredit record) throws Exception{
		if(record == null){
			throw new Exception("家庭征信模板为空");
		}
		if(record.getId() == null || "".equals(record.getId())){
			throw new Exception("家庭征信模板id为空");
		}
		try {
			return familyCreditMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusFamilyCredit selectByIpId(BusFamilyCredit record) throws Exception{
		if(record == null){
			throw new Exception("家庭征信模板为空");
		}
		try {
			return familyCreditMapper.selectByIpId(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	public List<Object> getSonEntryByParentItemId(String mainId,SqlSession session) throws Exception{
		List<Object> list = null;
		try{
			if(mainId != null && !mainId.equals("")){
				list = session.getMapper(BusFamilyCreditMapper.class).selectByParentItemId(mainId);
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
				session.getMapper(BusFamilyCreditMapper.class).updateByPrimaryKeySelective((BusFamilyCredit) record);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("record为空");
		}
	}
}
