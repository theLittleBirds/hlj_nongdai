package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.BusFollowType;
import com.nongyeos.loan.admin.mapper.BusFollowTypeMapper;
import com.nongyeos.loan.admin.service.IFollowTypeService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("followTypeService")
public class FollowTypeServiceImpl implements IFollowTypeService {
	
	@Autowired
	private BusFollowTypeMapper followTypeMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insert(BusFollowType record) throws Exception {
		if(record == null)
			throw new Exception("跟进信息类型为空");
		try {
			return followTypeMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusFollowType selectByPrimaryKey(String id) throws Exception {
		if(id == null)
			throw new Exception("跟进信息类型标识为空");
		try {
			return followTypeMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKeySelective(BusFollowType record)
			throws Exception {
		if(record == null)
			throw new Exception("跟进信息类型为空");
		try {
			return followTypeMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<BusFollowType> selectAll(String creOrgId) throws Exception {
		try {
			return followTypeMapper.selectAll(creOrgId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public PageBeanUtil<BusFollowType> selectByPage(int currentPage,
			int pageSize,String creOrgId) throws Exception {
		try {
			PageHelper.startPage(currentPage, pageSize, false);
			List<BusFollowType> list = followTypeMapper.selectAll(creOrgId);
			int countNums = followTypeMapper.selectCount(creOrgId);
			PageBeanUtil<BusFollowType> pageData = new PageBeanUtil<BusFollowType>(currentPage, pageSize, countNums);
	        pageData.setItems(list);
	        return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
}
