package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.NjMerGather;
import com.nongyeos.loan.admin.mapper.NjMerGatherMapper;
import com.nongyeos.loan.admin.service.NjMerGatherService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("njMerGatherService")
public class NjMerGatherServiceImpl implements NjMerGatherService{

	@Autowired
	private NjMerGatherMapper njMerGatherMapper;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addMerGatherSelective(NjMerGather record) throws Exception {
		try {
			if(record == null){
				throw new Exception("商户订单信息为空");
			}
			return njMerGatherMapper.addMerGatherSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateMerGatherByPKSelective(NjMerGather record) throws Exception {
		try {
			if (record == null) {
				throw new Exception("商品订单信息为空");
			}
			if (StringUtils.isEmpty(record.getId())) {
				throw new Exception("商品订单id为空");
			}
			return njMerGatherMapper.updateMerGatherByPKSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	@Override
	public NjMerGather queryMerGatherByOrderId(String orderId) throws Exception {
		try {
			if (StringUtils.isEmpty(orderId)) {
				throw new Exception("商品订单orderid为空");
			}
			return njMerGatherMapper.queryMerGatherByOrderId(orderId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}	
	
	@Override
	public NjMerGather queryMerGatherByPK(String id) throws Exception {
		try {
			if (StringUtils.isEmpty(id)) {
				throw new Exception("商品订单id为空");
			}
			return njMerGatherMapper.queryMerGatherByPK(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public PageBeanUtil<NjMerGather> queryNjMerGatherSelective(int currentPage, 
			int pageSize, NjMerGather record) throws Exception {
		try {
			if(record == null){
				throw new Exception("商户订单为空");
			}
			
			List<NjMerGather> merGatherTotal = njMerGatherMapper.queryMerGatherSelective(record);
			
			PageHelper.startPage(currentPage, pageSize);
			List<NjMerGather> merGatherSelective = njMerGatherMapper.queryMerGatherSelective(record);
			int total = merGatherTotal.size();
			
			PageBeanUtil<NjMerGather> pageData = new PageBeanUtil<NjMerGather>(currentPage, pageSize, total);
			pageData.setItems(merGatherSelective);
			
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}	
	
	@Override
	public int updateByPrimaryKey(NjMerGather record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int deleteByPrimaryKey(String id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(NjMerGather record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}