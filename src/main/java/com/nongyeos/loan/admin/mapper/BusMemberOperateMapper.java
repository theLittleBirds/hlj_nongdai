package com.nongyeos.loan.admin.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.BusMemberOperate;

public interface BusMemberOperateMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusMemberOperate record);

    int insertSelective(BusMemberOperate record);

    BusMemberOperate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusMemberOperate record);

    int updateByPrimaryKey(BusMemberOperate record);

	BusMemberOperate selectHistory(BusMemberOperate operate);

	List<BusMemberOperate> selectByOrgIds(List<String> orgIds);

	List<BusMemberOperate> selectByOrgIdsAndCondition(Map<String, Object> map);
	
	int selectCount(BusMemberOperate record);
	
	List<BusMemberOperate> selectPage(BusMemberOperate record);

	List<BusMemberOperate> selectByCondition(BusMemberOperate busMemberOperate);

	List<BusMemberOperate> selectByIds(List<String> asList);

}
