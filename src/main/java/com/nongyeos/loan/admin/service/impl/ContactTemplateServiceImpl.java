package com.nongyeos.loan.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.BusContactTemplate;
import com.nongyeos.loan.admin.mapper.BusContactTemplateMapper;
import com.nongyeos.loan.admin.service.IContactTemplateService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("contactTemplateService")
public class ContactTemplateServiceImpl implements IContactTemplateService {
	
	@Autowired
	private BusContactTemplateMapper contactTemplateMapper;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int insert(BusContactTemplate record)  throws Exception{
		if(record == null){
			throw new Exception("合同模板为空");
		}
		try {
			return contactTemplateMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public BusContactTemplate selectByPrimaryKey(String id) throws Exception{
		if(id == null){
			throw new Exception("合同模板id为空");
		}
		try {
			return contactTemplateMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKeySelective(BusContactTemplate record) throws Exception{
		if(record == null){
			throw new Exception("合同模板为空");
		}
		try {
			return contactTemplateMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateByPrimaryKey(BusContactTemplate record) throws Exception{
		if(record == null){
			throw new Exception("合同模板为空");
		}
		try {
			return contactTemplateMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public PageBeanUtil<BusContactTemplate> selectByPage(int currentPage, int pageSize,Map<String, String> map)
			throws Exception {
		try {
			PageHelper.startPage(currentPage, pageSize, false);
			List<BusContactTemplate> list = contactTemplateMapper.selectByFinsId(map);
			int countNums = contactTemplateMapper.selectCountByFinsId(map);
			PageBeanUtil<BusContactTemplate> pageData = new PageBeanUtil<BusContactTemplate>(currentPage, pageSize, countNums);
	        pageData.setItems(list);
	        return pageData;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

	@Override
	public List<BusContactTemplate> waitForSign(String finsId) throws Exception {
		if(finsId == null){
			throw new Exception("金融机构标识为空");
		}
		try {
			return contactTemplateMapper.waitForSign(finsId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}

}
