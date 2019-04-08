package com.nongyeos.loan.admin.service;

import com.nongyeos.loan.admin.entity.ApiWXGuaranteeFee;

public interface ApiWxGuaranteefeeService {
	int deleteByPrimaryKey(String id)throws Exception;

    int insert(ApiWXGuaranteeFee record)throws Exception;

    int insertSelective(ApiWXGuaranteeFee record)throws Exception;

    ApiWXGuaranteeFee selectByPrimaryKey(String id)throws Exception;

    int updateByPrimaryKeySelective(ApiWXGuaranteeFee record)throws Exception;

    int updateByPrimaryKey(ApiWXGuaranteeFee record)throws Exception;

	ApiWXGuaranteeFee selectByIpIdAndStatus(ApiWXGuaranteeFee wxGuaranteeFee)throws Exception;
}
