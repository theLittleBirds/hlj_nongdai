package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nongyeos.loan.admin.entity.IntQimoOutCall;
import com.nongyeos.loan.admin.mapper.IntQimoOutCallMapper;
import com.nongyeos.loan.admin.service.IQimoOutCallService;
@Service("intQimoOutCallService")
public class QimoOutCallServiceImpl implements IQimoOutCallService {
	
	@Autowired
	private IntQimoOutCallMapper intQimoOutCallMapper;
	
	@Override
	public List<IntQimoOutCall> selectByIpId(String intoPieceId) throws Exception{
		if(intoPieceId == null || "".equals(intoPieceId)){
			throw new Exception("进件标识为空");
		}
		try {
			return intQimoOutCallMapper.selectByIpId(intoPieceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

}
