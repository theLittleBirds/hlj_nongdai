package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.BusContact;
import com.nongyeos.loan.admin.mapper.BusContactMapper;
import com.nongyeos.loan.admin.service.IContactService;
import com.nongyeos.loan.base.util.PageBeanUtil;
@Service("contactService")
public class ContactServiceImpl implements IContactService {
	
	@Autowired
	private BusContactMapper contactMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insert(BusContact record) throws Exception{
		if(record == null){
			throw new Exception("用户模板为空");
		}
		try {
			return contactMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public int selectRepeat(BusContact record) throws Exception{
		if(record == null){
			throw new Exception("用户模板为空");
		}
		try {
			return contactMapper.selectRepeat(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public PageBeanUtil<BusContact> selectByPage(int currentPage, int pageSize,
			BusContact record) throws Exception {
		try {
			PageHelper.startPage(currentPage, pageSize, false);
			List<BusContact> list = contactMapper.selectAll(record);
			int countNums = contactMapper.selectCount(record);
			PageBeanUtil<BusContact> pageData = new PageBeanUtil<BusContact>(currentPage, pageSize, countNums);
	        pageData.setItems(list);
	        return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusContact selectByPhoneMember(BusContact contact) throws Exception {
		if(contact==null){
			throw new Exception("通讯录模板为空！");
		}
		BusContact usedContact =null;
		try {
			usedContact = contactMapper.selectByPhoneMember(contact);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usedContact;
	}

}
