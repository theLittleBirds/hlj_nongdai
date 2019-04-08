package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusMessageReminder;

public interface BusMessageReminderMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusMessageReminder record);

    int insertSelective(BusMessageReminder record);

    BusMessageReminder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusMessageReminder record);

    int updateByPrimaryKey(BusMessageReminder record);

	List<BusMessageReminder> selectByCondition(
			BusMessageReminder messageReminder);
	
	BusMessageReminder queryMRByTypeAndDelete(
			BusMessageReminder messageReminder);
	
	
}