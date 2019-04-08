package com.nongyeos.loan.admin.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.BusIntoPiece;
import com.nongyeos.loan.admin.resultMap.IntoPieceMap;

public interface BusIntoPieceMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusIntoPiece record);

    int insertSelective(BusIntoPiece record);

    BusIntoPiece selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusIntoPiece record);

    int updateByPrimaryKey(BusIntoPiece record);
    
    int selectCountByCondition(IntoPieceMap record);
    
    List<IntoPieceMap> selectByCondition(IntoPieceMap record);    

	BusIntoPiece selectFirstIdCardAndOrg(BusIntoPiece record);

	List<IntoPieceMap> selectByMemberLoginId(Map<String, Object> map);

	int selectCountByMemberLoginId(Map<String, Object> map);

	int selectCountByOrgIds(Map<String, Object> map);

	List<IntoPieceMap> selectByOrgIds(Map<String, Object> map);
	
	List<BusIntoPiece> selectByMemberId(String memberId);

	List<IntoPieceMap> selectByHistory(Map<String, Object> map);
	
	List<IntoPieceMap> weekIpStatistics(Map<String, Object> map);
	
	List<IntoPieceMap> weekMoneyStatistics(Map<String, Object> map);
	
	int todayCreCount(Map<String, Object> map);
	
	List<BusIntoPiece> todayMoneyToMember(Map<String, Object> map);

	Map<String, Object> selectExportMap(String intopieceId);
	
	String selectRepayXlsx(Map<String, String> map);
	
	List<BusIntoPiece> queryNongZi(Map<String, Object> map);
	
	int queryCountNongZi(Map<String, Object> map);
}
