package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.admin.entity.BusFamilyOperate;
import com.nongyeos.loan.admin.mapper.BusFamilyCreditMapper;
import com.nongyeos.loan.admin.mapper.BusFamilyOperateMapper;
import com.nongyeos.loan.admin.service.IFamilyOperateService;
import com.nongyeos.loan.base.util.StrUtils;

@Service("familyOperateService")
public class FamilyOperateServiceImpl implements IFamilyOperateService {
	
	@Autowired
	private BusFamilyOperateMapper familyOperateMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insert(BusFamilyOperate record) throws Exception {
		if(record == null)
			throw new Exception("经营情况模板为空");
		if(StrUtils.isEmpty(record.getId()))
			throw new Exception("经营情况id为空");
		try {
			return familyOperateMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}	
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKeySelective(BusFamilyOperate record)
			throws Exception {
		if(record == null)
			throw new Exception("经营情况模板为空");
		if(StrUtils.isEmpty(record.getId()))
			throw new Exception("经营情况id为空");
		try {
			return familyOperateMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKey(BusFamilyOperate record) throws Exception {
		if(record == null)
			throw new Exception("经营情况模板为空");
		if(StrUtils.isEmpty(record.getId()))
			throw new Exception("经营情况id为空");
		try {
			return familyOperateMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusFamilyOperate selectByIpId(String intoPieceId) throws Exception {
		if(StrUtils.isEmpty(intoPieceId))
			throw new Exception("进件标识为空");
		try {
			return familyOperateMapper.selectByIpId(intoPieceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	public List<Object> getSonEntryByParentItemId(String mainId,SqlSession session) throws Exception{
		List<Object> list = null;
		try{
			if(mainId != null && !mainId.equals("")){
				list = session.getMapper(BusFamilyOperateMapper.class).selectByParentItemId(mainId);
			}
		}catch(Exception e){
			e.printStackTrace();
			return list;
		}
		return list;
	}

}

