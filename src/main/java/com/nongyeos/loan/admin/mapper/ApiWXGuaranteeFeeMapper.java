package com.nongyeos.loan.admin.mapper;

import com.nongyeos.loan.admin.entity.ApiWXGuaranteeFee;

public interface ApiWXGuaranteeFeeMapper {
    int deleteByPrimaryKey(String id);

    int insert(ApiWXGuaranteeFee record);

    int insertSelective(ApiWXGuaranteeFee record);

    ApiWXGuaranteeFee selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApiWXGuaranteeFee record);

    int updateByPrimaryKey(ApiWXGuaranteeFee record);

	ApiWXGuaranteeFee selectByIpIdAndStatus(ApiWXGuaranteeFee wxGuaranteeFee);
}