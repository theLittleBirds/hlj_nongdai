package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.BusOtherContact;
import com.nongyeos.loan.admin.mapper.BusOtherContactMapper;
import com.nongyeos.loan.admin.resultMap.IntoPieceMap;
import com.nongyeos.loan.admin.service.IOtherContactService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("otherContactService")
public class OtherContactServiceImpl implements IOtherContactService {
	
	@Autowired
	private BusOtherContactMapper otherContactMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insert(BusOtherContact record) throws Exception{
		if(record == null){
			throw new Exception("联系人模板为空");
		}
		if(record.getId() == null || "".equals(record.getId())){
			throw new Exception("联系人模板模板id为空");
		}
		try {
			return otherContactMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKey(BusOtherContact record) throws Exception{
		if(record == null){
			throw new Exception("联系人模板为空");
		}
		if(record.getId() == null || "".equals(record.getId())){
			throw new Exception("联系人模板模板id为空");
		}
		try {
			return otherContactMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKeySelective(BusOtherContact record) throws Exception{
		if(record == null){
			throw new Exception("联系人模板为空");
		}
		if(record.getId() == null || "".equals(record.getId())){
			throw new Exception("联系人模板模板id为空");
		}
		try {
			return otherContactMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusOtherContact selectByPrimaryKey(String id) throws Exception {
		if(id == null || "".equals(id)){
			throw new Exception("联系人模板模板id为空");
		}
		try {
			return otherContactMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public PageBeanUtil<BusOtherContact> selectByPage(int currentPage,
			int pageSize, BusOtherContact record) throws Exception {
		try {
			PageHelper.startPage(currentPage, pageSize, false);
			List<BusOtherContact> list = otherContactMapper.selectByIpId(record);
			int countNums = otherContactMapper.selectCountByIpId(record);
			PageBeanUtil<BusOtherContact> pageData = new PageBeanUtil<BusOtherContact>(currentPage, pageSize, countNums);
	        pageData.setItems(list);
	        return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<BusOtherContact> selectByIntpId(BusOtherContact oc) throws Exception {
		if(oc==null){
			throw new Exception("其他联系人模板为空！");
		}
		List<BusOtherContact> list =null;
		try {
			list =otherContactMapper.selectByIpId(oc);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return list;
	}

}
