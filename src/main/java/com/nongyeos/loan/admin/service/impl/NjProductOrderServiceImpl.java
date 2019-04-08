package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.NjProductOrder;
import com.nongyeos.loan.admin.mapper.NjProductOrderMapper;
import com.nongyeos.loan.admin.service.NjProductOrderService;
import com.nongyeos.loan.base.util.Constants;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("njProductOrderService")
public class NjProductOrderServiceImpl implements NjProductOrderService{

	@Autowired
	private NjProductOrderMapper njProductOrderMapper;

	@Override
	public List<NjProductOrder> queryNjProductOrderProductids(
			String orderProductids) throws Exception {
		try {
			if(StringUtils.isEmpty(orderProductids)){
				throw new Exception("套餐包含商品id为空");
			}
			return njProductOrderMapper.queryNjProductOrderProductids(orderProductids);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}	
	
	@Override
	public PageBeanUtil<NjProductOrder> queryNjProductOrderSelective(
			int currentPage, int pageSize, NjProductOrder record)
			throws Exception {
		try {
			if(record == null){
				throw new Exception("商品套餐为空");
			}
			
			List<NjProductOrder> njProductOrdertotal = njProductOrderMapper.queryNjProductOrderSelective(record);
			PageHelper.startPage(currentPage, pageSize);
			List<NjProductOrder> njProductOrderSelective = njProductOrderMapper.queryNjProductOrderSelective(record);
			int total = njProductOrdertotal.size();
			
			PageBeanUtil<NjProductOrder> pageData = new PageBeanUtil<NjProductOrder>(currentPage, pageSize, total);
	        pageData.setItems(njProductOrderSelective);
			
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public NjProductOrder queryProductOrderByPK(String id) throws Exception {
		try {
			if(StringUtils.isEmpty(id)){
				throw new Exception("套餐id为空");
			}
			return njProductOrderMapper.queryProductOrderByPK(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int deleteProductOrderByPK(String id) throws Exception {
		try {
			if(StringUtils.isEmpty(id)){
				throw new Exception("套餐id为空");
			}
			return njProductOrderMapper.deleteProductOrderByPK(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addProductOrderSelective(NjProductOrder record) throws Exception {
		try {
			if(record == null){
				throw new Exception("套餐为空");
			}
			return njProductOrderMapper.addProductOrderSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateProductOrderByPKSelective(NjProductOrder record)
			throws Exception {
		try {
			if(record == null){
				throw new Exception("套餐为空");
			}
			if(StringUtils.isEmpty(record.getId())){
				throw new Exception("套餐id为空");
			}
			return njProductOrderMapper.updateProductOrderByPKSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public int updateByPrimaryKey(NjProductOrder record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}	
	

	@Override
	public int insert(NjProductOrder record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<NjProductOrder> queryAllOnNjProductOrders() throws Exception{
		try {
			NjProductOrder record = new NjProductOrder();
			record.setStatus(Constants.ORDER_GROUD);
			return njProductOrderMapper.queryNjProductOrderSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}