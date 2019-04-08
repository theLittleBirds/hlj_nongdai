package com.nongyeos.loan.admin.mapper;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusSmsTemplate;

public interface BusSmsTemplateMapper {
    int deleteByPrimaryKey(String msgId);

    int insert(BusSmsTemplate record);

    int insertSelective(BusSmsTemplate record);

    BusSmsTemplate selectByPrimaryKey(String msgId);

    int updateByPrimaryKeySelective(BusSmsTemplate record);

    int updateByPrimaryKey(BusSmsTemplate record);
    
    
    List<BusSmsTemplate> selectAll();
    
    BusSmsTemplate checkExist(BusSmsTemplate record);
    
    int selectCount();

    BusSmsTemplate selectByCodeAndRoot(BusSmsTemplate smsTemplate);
}