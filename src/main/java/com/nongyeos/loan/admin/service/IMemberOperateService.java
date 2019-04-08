package com.nongyeos.loan.admin.service;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.BusMemberOperate;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IMemberOperateService {
	 int deleteByPrimaryKey(String id)throws Exception;

     int insert(BusMemberOperate record)throws Exception;

     int insertSelective(BusMemberOperate record)throws Exception;

     BusMemberOperate selectByPrimaryKey(String id)throws Exception;

     int updateByPrimaryKeySelective(BusMemberOperate record)throws Exception;

     int updateByPrimaryKey(BusMemberOperate record)throws Exception;

	BusMemberOperate selectHistory(BusMemberOperate operate)throws Exception;

	List<BusMemberOperate> selectByUser(String userId)throws Exception;

	List<BusMemberOperate> selectByUserAndCondition(String userId,
			BusMemberOperate mo)throws Exception;
	
	PageBeanUtil<BusMemberOperate> selectByPage(int currentPage, int pageSize, BusMemberOperate operate)throws Exception;

	List<BusMemberOperate> selectByCondition(BusMemberOperate busMemberOperate)throws Exception;

	List<BusMemberOperate> selectByIds(List<String> asList)throws Exception;

}
