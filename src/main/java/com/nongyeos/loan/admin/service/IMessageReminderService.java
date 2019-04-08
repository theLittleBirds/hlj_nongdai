package com.nongyeos.loan.admin.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.BusMessageReminder;
import com.nongyeos.loan.admin.entity.BusSmsTemplate;

public interface IMessageReminderService {

	List<BusMessageReminder> selectByCondition(
			BusMessageReminder messageReminder)throws Exception;

	void saveServiceRemind(String root,String intopieceId)throws Exception;

	void saveweixinpay(int weiXinServicePay, String channel, String id, String serviceFee)throws Exception;
	
	int updateByPrimaryKey(BusMessageReminder record)throws Exception;

	BusMessageReminder queryMRByTypeAndDelete(BusMessageReminder reminder)throws Exception;

	void saveRefund(String root, String intopieceId,String refund) throws Exception;
	
	void insertreminder(BusSmsTemplate smstemplateuser,
			Map<String, String> value, Date now, String intopieceId, String loginId,Integer type) throws Exception;
}
