package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.nongyeos.loan.admin.entity.BusApplyPieceExt;
import com.nongyeos.loan.admin.mapper.BusApplyPieceExtMapper;
import com.nongyeos.loan.admin.mapper.BusFamilyCapitalMapper;
import com.nongyeos.loan.admin.resultMap.DynamicDataMap;
import com.nongyeos.loan.admin.service.IApplyPieceExtService;
import com.nongyeos.loan.base.util.StrUtils;

@Service("applyPieceExtService")
public class ApplyPieceExtServiceImpl implements IApplyPieceExtService {
	
	@Autowired
	private BusApplyPieceExtMapper applyPieceExtMapper;

	@Override
	public BusApplyPieceExt queryByApplyPieceAndItem(String intoPieceId,
			String itemId) throws Exception {
		try {
			if(StringUtils.isEmpty(intoPieceId)){
				throw new Exception("进件id为空");
			}
			if(StringUtils.isEmpty(itemId)){
				throw new Exception("数据项id为空");
			}
			return applyPieceExtMapper.queryByApplyPieceAndItem(intoPieceId, itemId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}	
	
	@Override
	public List<DynamicDataMap> selectBySectionId(DynamicDataMap record)  throws Exception{
		if(record ==null )
			throw new Exception("查询模板为空");
		if(StrUtils.isEmpty(record.getIntoPieceId()))
			throw new Exception("进件ID为空");
		if(StrUtils.isEmpty(record.getSectionId()))
			throw new Exception("类型为空");
		return applyPieceExtMapper.selectBySectionId(record);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int deleteByIpAndItem(DynamicDataMap record) throws Exception{
		if(record ==null )
			throw new Exception("查询模板为空");
		if(StrUtils.isEmpty(record.getIntoPieceId()))
			throw new Exception("进件ID为空");
		if(StrUtils.isEmpty(record.getSectionId()))
			throw new Exception("类型为空");
		return applyPieceExtMapper.deleteByIpAndItem(record);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insert(BusApplyPieceExt record) throws Exception {
		if(record == null)
			throw new Exception("模板为空");
		try {
			return applyPieceExtMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		
	}
	

	public List<Object> getSonEntryByParentItemId(String mainId,SqlSession session) throws Exception{
		List<Object> list = null;
		try{
			if(mainId != null && !mainId.equals("")){
				list = session.getMapper(BusApplyPieceExtMapper.class).selectByParentItemId(mainId);
			}
		}catch(Exception e){
			e.printStackTrace();
			return list;
		}
		return list;
	}

	@Override
	public List<BusApplyPieceExt> selectByIpId(String id) throws Exception {
		if(StringUtils.isEmpty(id)){
			throw new Exception("进件标识为空");
		}
		try {
			return applyPieceExtMapper.selectByIpId(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
