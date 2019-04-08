package com.nongyeos.loan.admin.service;

import java.util.Map;

import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusMember;
import com.nongyeos.loan.base.util.PageBeanUtil;

public interface IMemberService {

	PageBeanUtil<BusMember> selectByPage(int currentPage, int pageSize, BusMember member)throws Exception;

	boolean existedSameName(BusMember member)throws Exception;

	void addMember(BusMember member, String channel)throws Exception;

	void updateMember(BusMember member, String channel)throws Exception;

	void deleteUser(String memberId) throws Exception;

	Map<String, Object> addMemberByIntoPiece(BusIntoPiece intoPiece,String intoPieceId) throws Exception;
	
	//根据身份证查找客户
	BusMember selectByIdCard(String idCard)throws Exception;

	BusMember selectByPrimaryKey(String memberId)throws Exception;
	
	int insert(BusMember member)throws Exception;
}
