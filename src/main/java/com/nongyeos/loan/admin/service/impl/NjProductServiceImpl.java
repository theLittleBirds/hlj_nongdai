package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.NjProduct;
import com.nongyeos.loan.admin.mapper.NjProductMapper;
import com.nongyeos.loan.admin.service.NjProductService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("njProductService")
public class NjProductServiceImpl implements NjProductService{

	@Autowired
	private NjProductMapper njProductMapper;

	@Override
	public List<NjProduct> queryAllNjProduct() throws Exception {
		try {
			return njProductMapper.queryAllNjProduct();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int deleteNjProductByPK(String id) throws Exception {
		try {
			if(StringUtils.isEmpty(id)){
				throw new Exception("商品id为空");
			}
			return njProductMapper.deleteNjProductByPK(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int addNjProductSelective(NjProduct record) throws Exception {
		try {
			if(record == null){
				throw new Exception("商品为空");
			}
			return njProductMapper.addNjProductSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public NjProduct queryNjProductByPrimaryKey(String id) throws Exception {
		try {
			if(StringUtils.isEmpty(id)){
				throw new Exception("商品id为空");
			}
			return njProductMapper.queryNjProductByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public PageBeanUtil<NjProduct> queryNjProductSelective(int currentPage, int pageSize, NjProduct record) throws Exception {
		try {
			if(record == null){
				throw new Exception("商品为空");
			}
			
			List<NjProduct> queryNjProducttotal = njProductMapper.queryNjProductSelective(record);
			PageHelper.startPage(currentPage, pageSize);
			List<NjProduct> queryNjProductSelective = njProductMapper.queryNjProductSelective(record);
			int total = queryNjProducttotal.size();
			
			PageBeanUtil<NjProduct> pageData = new PageBeanUtil<NjProduct>(currentPage, pageSize, total);
	        pageData.setItems(queryNjProductSelective);
			
			return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateNjProductPKSelective(NjProduct record) throws Exception {
		try {
			if(record == null){
				throw new Exception("商品为空");
			}
			if(StringUtils.isEmpty(record.getId())){
				throw new Exception("商品id为空");
			}
			return njProductMapper.updateNjProductPKSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public int updateByPrimaryKey(NjProduct record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(NjProduct record) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
