package com.nongyeos.loan.admin.mapper;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.BusIntopieceScore;

public interface BusIntopieceScoreMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusIntopieceScore record);

    int addIntopieceScoreSelective(BusIntopieceScore record);

    BusIntopieceScore selectByPrimaryKey(String id);
    
    List<BusIntopieceScore> queryByIntopieceId(String intoPieceId);

    int updateByPrimaryKeySelective(BusIntopieceScore record);

    int updateByPrimaryKey(BusIntopieceScore record);

	List<Object> selectByParentItemId(String mainId);
	
	int deleteByIpId(Map<String,Object> map);
	
	List<BusIntopieceScore>  historyScore(String intoPieceId);

	List<Map<String, Object>> selectExpByIntopieceId(String intopieceId);

}