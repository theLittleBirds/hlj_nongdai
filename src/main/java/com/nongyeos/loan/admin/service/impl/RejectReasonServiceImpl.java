package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nongyeos.loan.admin.entity.BusRejectReason;
import com.nongyeos.loan.admin.mapper.BusRejectReasonMapper;
import com.nongyeos.loan.admin.service.IRejectReasonService;
@Service("rejectReasonService")
public class RejectReasonServiceImpl implements IRejectReasonService {

	@Autowired
	private BusRejectReasonMapper reasonMapper;
	
	@Override
	public void addRejectReason(List<BusRejectReason> list) throws Exception {
		if(list==null||list.size()<1){
			throw new Exception("没有拒件原因!");
		}
		try {
			for (BusRejectReason busRejectReason : list) {
				reasonMapper.insert(busRejectReason);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存拒件原因失败!");
		}
		
	}

	@Override
	public List<BusRejectReason> selectByIpId(String intoPieceId) throws Exception {
		try {
			return reasonMapper.selectByIpId(intoPieceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错！");
		}
		 
	}

	@Override
	public void insert(BusRejectReason record) throws Exception {
		try {
			reasonMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("系统错误");
		}
		
	}
	
}
