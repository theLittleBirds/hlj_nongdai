package com.nongyeos.loan.admin.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusGuaranteeFeeInfo;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IGuaranteeFeeInfoService {
	int deleteByPrimaryKey(String id)throws Exception;

    int insert(BusGuaranteeFeeInfo record)throws Exception;

    int insertSelective(BusGuaranteeFeeInfo record)throws Exception;

    BusGuaranteeFeeInfo selectByPrimaryKey(String id)throws Exception;

    int updateByPrimaryKeySelective(BusGuaranteeFeeInfo record)throws Exception;

    int updateByPrimaryKey(BusGuaranteeFeeInfo record)throws Exception;

	PageBeanUtil<BusGuaranteeFeeInfo> selectByPage(String personId,
			Map<String, Object> map, int currentPage, int pageSize)throws Exception;

	BusGuaranteeFeeInfo selectByIntopieceId(String intoPieceId)throws Exception;
	
	List<BusGuaranteeFeeInfo> queryList(String personId,
			Map<String, Object> map)throws Exception;

	void payNotice(JSONObject resultJson)throws Exception;
}
