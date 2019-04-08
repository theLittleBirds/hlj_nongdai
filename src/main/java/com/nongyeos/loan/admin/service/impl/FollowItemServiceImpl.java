package com.nongyeos.loan.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.BusFollowItem;
import com.nongyeos.loan.admin.mapper.BusFollowItemMapper;
import com.nongyeos.loan.admin.resultMap.FollowItemMap;
import com.nongyeos.loan.admin.service.IFollowItemService;
import com.nongyeos.loan.base.util.PageBeanUtil;
@Service("followItemService")
public class FollowItemServiceImpl implements IFollowItemService {
	
	@Autowired
	private BusFollowItemMapper followItemMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insert(BusFollowItem record) throws Exception {
		if(record == null)
			throw new Exception("数据项为空");
		try {
			return followItemMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusFollowItem selectByPrimaryKey(String id) throws Exception {
		if(id == null)
			throw new Exception("数据项标识为空");
		try {
			return followItemMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKeySelective(BusFollowItem record)
			throws Exception {
		if(record == null)
			throw new Exception("数据项为空");
		try {
			return followItemMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKey(BusFollowItem record) throws Exception {
		if(record == null)
			throw new Exception("数据项为空");
		try {
			return followItemMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<FollowItemMap> selectDynamicByType(Map<String, String> map)
			throws Exception {
		if(map == null)
			throw new Exception("跟进项类型为空");
		try {
			return followItemMapper.selectDynamicByType(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public PageBeanUtil<BusFollowItem> selectByPage(int currentPage,
			int pageSize, String type) throws Exception {
		if(type == null)
			throw new Exception("跟进项类型为空");
		try {
			PageHelper.startPage(currentPage, pageSize, false);
			List<BusFollowItem> list = followItemMapper.selectByType(type);
			int countNums = followItemMapper.selectCountByType(type);
			PageBeanUtil<BusFollowItem> pageData = new PageBeanUtil<BusFollowItem>(currentPage, pageSize, countNums);
	        pageData.setItems(list);
	        return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public int selectCountByType(String type) throws Exception {
		if(type == null)
			throw new Exception("数据项类型为空");
		try {
			return followItemMapper.selectCountByType(type);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	
}
