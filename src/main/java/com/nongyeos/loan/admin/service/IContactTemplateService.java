package com.nongyeos.loan.admin.service;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.BusContactTemplate;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IContactTemplateService {

    int insert(BusContactTemplate record) throws Exception;

    BusContactTemplate selectByPrimaryKey(String id) throws Exception;

    int updateByPrimaryKeySelective(BusContactTemplate record) throws Exception;

    int updateByPrimaryKey(BusContactTemplate record) throws Exception;
    
    PageBeanUtil<BusContactTemplate> selectByPage(int currentPage,int pageSize,Map<String, String> map) throws Exception;
    
    List<BusContactTemplate> waitForSign(String finsId) throws Exception;
}
