package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nongyeos.loan.admin.entity.IntSignatories;
import com.nongyeos.loan.admin.mapper.IntSignatoriesMapper;
import com.nongyeos.loan.admin.service.ISignatorieService;
@Service("signatorieService")
public class SignatorieServiceImpl implements ISignatorieService {
	
	@Autowired
	private IntSignatoriesMapper signatoriesMapper;
	
	@Override
	public IntSignatories selectByPrimaryKey(String id) throws Exception{
		if(id == null)
			throw new Exception("签约记录模板标识为空");
		try {
			return signatoriesMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKeySelective(IntSignatories record) throws Exception{
		if(record == null)
			throw new Exception("签约记录模板为空");
		try {
			return signatoriesMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKey(IntSignatories record) throws Exception{
		if(record == null)
			throw new Exception("签约记录模板为空");
		try {
			return signatoriesMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<IntSignatories> selectIdSendSms(String loanId) throws Exception {
		if(loanId == null)
			throw new Exception("借款标识为空");
		try {
			return signatoriesMapper.selectIdSendSms(loanId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<IntSignatories> selectByapplySignFileId(String applySignFileId)
			throws Exception {
		if(applySignFileId == null)
			throw new Exception("合同标识为空");
		try {
			return signatoriesMapper.selectByapplySignFileId(applySignFileId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

}
