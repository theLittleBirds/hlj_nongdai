package com.nongyeos.loan.admin.service;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.BusMemberLogin;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IMemberLoginService {
	
    int addRecord(BusMemberLogin record) throws Exception;

    BusMemberLogin selectById(String loginId) throws Exception;

    int updateByPrimaryKeySelective(BusMemberLogin record) throws Exception;

    int updateByPrimaryKey(BusMemberLogin record) throws Exception;
    
    BusMemberLogin selectByToken(String token) throws Exception;
    
    PageBeanUtil<BusMemberLogin> selectByPage(int currentPage,int pageSize,BusMemberLogin record) throws Exception;
    
    BusMemberLogin selectByLoginName(BusMemberLogin record) throws Exception;

	List<BusMemberLogin> selectByMemberId(Map<String, String> memberIdChannel)throws Exception;
}
