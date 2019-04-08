package com.nongyeos.loan.admin.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nongyeos.loan.admin.entity.ApiWXGuaranteeFee;
import com.nongyeos.loan.admin.mapper.ApiWXGuaranteeFeeMapper;
import com.nongyeos.loan.admin.service.ApiWxGuaranteefeeService;

@Service("apiWxGuaranteefeeService")
public class ApiWxGuaranteefeeServiceImpl implements ApiWxGuaranteefeeService {

	@Autowired
	private ApiWXGuaranteeFeeMapper wxGuaranteeFeeMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) throws Exception {
		if(StringUtils.isEmpty(id)){
			throw new  Exception("微信订单id为空");
		}
		try {
			return wxGuaranteeFeeMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new  Exception("删除出错");
		}
		
	}

	@Override
	public int insert(ApiWXGuaranteeFee record) throws Exception {
		if(record==null){
			throw new  Exception("微信订单模板为空");
		}
		try {
			return wxGuaranteeFeeMapper.insert(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new  Exception("保存出错");
		}
	}

	@Override
	public int insertSelective(ApiWXGuaranteeFee record) throws Exception {
		if(record==null){
			throw new  Exception("微信订单模板为空");
		}
		try {
			return wxGuaranteeFeeMapper.insertSelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new  Exception("保存出错");
		}
	}

	@Override
	public ApiWXGuaranteeFee selectByPrimaryKey(String id) throws Exception {
		if(StringUtils.isEmpty(id)){
			throw new  Exception("微信订单id为空");
		}
		try {
			return wxGuaranteeFeeMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new  Exception("查询出错");
		}
	}

	@Override
	public int updateByPrimaryKeySelective(ApiWXGuaranteeFee record)
			throws Exception {
		if(record==null){
			throw new  Exception("微信订单模板为空");
		}
		try {
			return wxGuaranteeFeeMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new  Exception("保存出错");
		}
	}

	@Override
	public int updateByPrimaryKey(ApiWXGuaranteeFee record) throws Exception {
		if(record==null){
			throw new  Exception("微信订单模板为空");
		}
		try {
			return wxGuaranteeFeeMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new  Exception("保存出错");
		}
	}

	@Override
	public ApiWXGuaranteeFee selectByIpIdAndStatus(
			ApiWXGuaranteeFee wxGuaranteeFee) throws Exception {
		if(wxGuaranteeFee==null){
			throw new  Exception("微信订单模板为空");
		}
		try {
			return wxGuaranteeFeeMapper.selectByIpIdAndStatus(wxGuaranteeFee);
		} catch (Exception e) {
			e.printStackTrace();
			throw new  Exception("查询出错");
		}
	}

}
