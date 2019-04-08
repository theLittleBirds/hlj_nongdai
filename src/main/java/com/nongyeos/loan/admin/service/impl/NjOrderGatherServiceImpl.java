package com.nongyeos.loan.admin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.NjOrderGather;
import com.nongyeos.loan.admin.mapper.NjOrderGatherMapper;
import com.nongyeos.loan.admin.resultMap.IntoPieceMap;
import com.nongyeos.loan.admin.service.NjOrderGatherService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("njOrderGatherService")
public class NjOrderGatherServiceImpl implements NjOrderGatherService{
	
	@Autowired
	private NjOrderGatherMapper njOrderGatherMapper;

	@Override
	public PageBeanUtil<NjOrderGather> queryNjProductOrderSelective(
			int currentPage, int pageSize, NjOrderGather record)
			throws Exception {
		try {
			if(record == null){
				throw new Exception("联合社订单为空");
			}
			
			List<NjOrderGather> gathertotal = njOrderGatherMapper.queryOrderGatherSelective(record);
			
			PageHelper.startPage(currentPage, pageSize,false);
			List<NjOrderGather> gatherSelective = njOrderGatherMapper.queryOrderGatherSelective(record);
			int total = gathertotal.size();
			
			PageBeanUtil<NjOrderGather> pageData = new PageBeanUtil<NjOrderGather>(currentPage, pageSize, total);
			pageData.setItems(gatherSelective);
			
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addOrderGatherSelective(NjOrderGather record) throws Exception {
		try {
			if(record == null){
				throw new Exception("联合社订单为空");
			}
			return njOrderGatherMapper.addOrderGatherSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateOrderGatherByPKSelective(NjOrderGather record) throws Exception {
		try {
			if(record == null){
				throw new Exception("联合社订单为空");
			}
			if(StringUtils.isEmpty(record.getId())){
				throw new Exception("联合社订单id为空");
			}
			return njOrderGatherMapper.updateOrderGatherByPKSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	public NjOrderGather queryOrderGatherByPk(String id) throws Exception {
		try {
			if(StringUtils.isEmpty(id)){
				throw new Exception("联合社订单id为空");
			}
			return njOrderGatherMapper.queryOrderGatherByPk(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public NjOrderGather queryOrderGatherByIpid(String intoPieceId)
			throws Exception {
		try {
			if(StringUtils.isEmpty(intoPieceId)){
				throw new Exception("联合社订单进件id为空");
			}
			return njOrderGatherMapper.queryOrderGatherByIpid(intoPieceId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}	
	
	@Override
	public int updateByPrimaryKey(NjOrderGather record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int deleteByPrimaryKey(String id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(NjOrderGather record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PageBeanUtil<NjOrderGather> selectByloginId(Integer page1, int pageSize,
			String loginId, String associateDeliverOrder) throws Exception {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("loginId", loginId);
			map.put("status", associateDeliverOrder);
			PageHelper.startPage(page1, pageSize, false);
			List<NjOrderGather> list = njOrderGatherMapper.selectByloginId(map);
			int countNums = njOrderGatherMapper.selectCountByloginId(map);
			PageBeanUtil<NjOrderGather> pageData = new PageBeanUtil<NjOrderGather>(page1, pageSize, countNums);
			pageData.setItems(list);
	        return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询失败！");
		}
	}

}