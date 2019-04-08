package com.nongyeos.loan.admin.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.nongyeos.loan.admin.entity.BusFamilySituation;
import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.entity.BusLoan;
import com.nongyeos.loan.admin.resultMap.IntoPieceMap;
import com.nongyeos.loan.base.util.PageBeanUtil;
import com.nongyeos.loan.core.model.BusinessObject;

public interface IIntoPieceService {
	
	int insert(BusIntoPiece record) throws Exception;
	
	BusIntoPiece selectByPrimaryKey(String id) throws Exception;
	
	int updateByPrimaryKeySelective(BusIntoPiece record) throws Exception;
	
	PageBeanUtil<IntoPieceMap> selectByPage(int currentPage,int pageSize,IntoPieceMap record) throws Exception;

	BusIntoPiece saveIntoPieceAndMember(BusIntoPiece intoPiece)throws Exception;

	String selectModelByIntoPID(String intoPieceId, String orgId)throws Exception;

	Map<String, Object> getOrgTree(String intopId) throws Exception;
	
	int updateByPrimaryKey(BusIntoPiece record) throws Exception;

	PageBeanUtil<IntoPieceMap> selectByMemberLoginId(Integer page1,
			int pageSize, String condition, String loginId,
			short commonIsnotDelete, String channel)throws Exception;

	List<IntoPieceMap> selectAllIntoPieces(Map<String, Object> map)throws Exception;

	PageBeanUtil<IntoPieceMap> selectByUserId(Integer page1, int pageSize, String condition,
			String userId, short commonIsnotDelete, String memberName)throws Exception;

	List<IntoPieceMap> selectByUserIdAndStatus(String userId,
			short commonIsnotDelete)throws Exception;
	
	List<BusIntoPiece> selectByMemberId(String memberId) throws Exception;
	
	List<IntoPieceMap> weekIpStatistics(Map<String, Object> map) throws Exception;
	
	List<IntoPieceMap> weekMoneyStatistics(Map<String, Object> map) throws Exception;
	
	int todayCreCount(Map<String, Object> map) throws Exception;
	
	List<BusIntoPiece> todayMoneyToMember(Map<String, Object> map) throws Exception;
	
	JSONObject submitPrimary(BusIntoPiece ip,BusLoan loan,List<BusFamilySituation> list,String personId) throws Exception;

	Map<String, Object> getOrgs(String personId, String intoPieceId)throws Exception;

	Map<String, Object> selectExportMap(String intopieceId)throws Exception;
	
	PageBeanUtil<BusIntoPiece> selectNongZiPage(int currentPage,int pageSize,Map<String, Object> map) throws Exception;
	
	List<IntoPieceMap> selectByCondition(IntoPieceMap record) throws Exception;  
}
