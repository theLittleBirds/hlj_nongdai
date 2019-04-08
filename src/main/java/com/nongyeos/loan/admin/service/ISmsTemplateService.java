package com.nongyeos.loan.admin.service;

import java.util.Map;

import com.nongyeos.loan.admin.entity.BusSmsTemplate;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface ISmsTemplateService {
	
	int addRecord(BusSmsTemplate record) throws Exception;

	BusSmsTemplate selectById(String smsId) throws Exception;

    int updateByPrimaryKeySelective(BusSmsTemplate record) throws Exception;
    
    int deleteById(String smsId) throws Exception;
    
    PageBeanUtil<BusSmsTemplate> selectByPage(int currentPage,int pageSize) throws Exception;
    
    BusSmsTemplate checkExist(BusSmsTemplate record) throws Exception;
    
    int smsSend(String msgCode, String baseOrgId, Map<String,String> map, String phone) throws Exception;
}
