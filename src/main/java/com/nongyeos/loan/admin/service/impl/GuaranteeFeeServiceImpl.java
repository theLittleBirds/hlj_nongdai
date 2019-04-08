package com.nongyeos.loan.admin.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.nongyeos.loan.admin.entity.BusGuaranteeFee;
import com.nongyeos.loan.admin.entity.BusGuaranteeFeeInfo;
import com.nongyeos.loan.admin.mapper.BusGuaranteeFeeMapper;
import com.nongyeos.loan.admin.service.IGuaranteeFeeService;
import com.nongyeos.loan.base.util.PageBeanUtil;

@Service("guaranteeFeeService")
public class GuaranteeFeeServiceImpl implements IGuaranteeFeeService {
	
	@Autowired
	private BusGuaranteeFeeMapper guaranteeFeeMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) throws Exception {
		if(StringUtils.isEmpty(id)){
			throw new Exception("担保费标识为空！");
		}
		try {
			return guaranteeFeeMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("删除出错！");
		}
		
	}

	@Override
	public int insert(BusGuaranteeFee record) throws Exception {
		if(record==null){
			throw new Exception("担保费模板为空！");
		}
		try {
			return guaranteeFeeMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存失败！");
		}
	}

	@Override
	public int insertSelective(BusGuaranteeFee record) throws Exception {
		if(record==null){
			throw new Exception("担保费模板为空！");
		}
		try {
			return guaranteeFeeMapper.insertSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存失败！");
		}
	}

	@Override
	public BusGuaranteeFee selectByPrimaryKey(String id) throws Exception {
		if(StringUtils.isEmpty(id)){
			throw new Exception("担保费标识为空！");
		}
		try {
			return guaranteeFeeMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询出错！");
		}
	}

	@Override
	public int updateByPrimaryKeySelective(BusGuaranteeFee record) throws Exception {
		if(record==null){
			throw new Exception("担保费模板为空！");
		}
		try {
			return guaranteeFeeMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存失败！");
		}
	}

	@Override
	public int updateByPrimaryKey(BusGuaranteeFee record) throws Exception {
		if(record==null){
			throw new Exception("担保费模板为空！");
		}
		try {
			return guaranteeFeeMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("保存失败！");
		}
	}

	@Override
	public BusGuaranteeFee selectByIntopieceId(String id) throws Exception {
		if(StringUtils.isEmpty(id)){
			throw new Exception("进件标识为空！");
		}
		try {
			List<BusGuaranteeFee> selectByIntopieceId = guaranteeFeeMapper.selectByIntopieceId(id);
			if(selectByIntopieceId!=null&&selectByIntopieceId.size()>0){
				return selectByIntopieceId.get(0);
			}else{
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询失败！");
		}
		
	}

	@Override
	public PageBeanUtil<BusGuaranteeFee> selectByIpIdPage(int currentPage,
			int pageSize, String id) throws Exception {
		try {
			PageHelper.startPage(currentPage, pageSize, false);
			List<BusGuaranteeFee> list = guaranteeFeeMapper.selectByIntopieceId(id);
			int countNums = guaranteeFeeMapper.selectCountByIntopieceId(id);
			PageBeanUtil<BusGuaranteeFee> pageBeanUtil = new PageBeanUtil<BusGuaranteeFee>(currentPage, pageSize, countNums);
			pageBeanUtil.setItems(list);
			return pageBeanUtil;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("查询错误");
		}
	}

}
