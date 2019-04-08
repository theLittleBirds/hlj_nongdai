package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusMemberOperateMedia;

public interface IMemberOperateMediaService {
	int deleteByPrimaryKey(String id)throws Exception;

    int insert(BusMemberOperateMedia record)throws Exception;

    int insertSelective(BusMemberOperateMedia record)throws Exception;

    BusMemberOperateMedia selectByPrimaryKey(String id)throws Exception;

    int updateByPrimaryKeySelective(BusMemberOperateMedia record)throws Exception;

    int updateByPrimaryKey(BusMemberOperateMedia record)throws Exception;

	int existenceByKey(BusMemberOperateMedia pictureforsearch)throws Exception;

	List<BusMemberOperateMedia> selectByMOId(String id)throws Exception;
}
