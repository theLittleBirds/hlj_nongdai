package com.nongyeos.loan.admin.service;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.BusIntopieceScore;

public interface IBusIntopieceScoreService {
    int deleteByPrimaryKey(String id) throws Exception;

    int insert(BusIntopieceScore record) throws Exception;

    int addIntopieceScoreSelective(BusIntopieceScore record) throws Exception;

    BusIntopieceScore selectByPrimaryKey(String id) throws Exception;
    
    List<BusIntopieceScore> queryByIntopieceId(String intoPieceId) throws Exception;

    int updateByPrimaryKeySelective(BusIntopieceScore record) throws Exception;

    int updateByPrimaryKey(BusIntopieceScore record) throws Exception;
    
    int deleteByIpId(String intoPieceId,String scoreId) throws Exception;
    
    List<BusIntopieceScore>  historyScore(String intoPieceId) throws Exception;

	List<Map<String, Object>> selectExpByIntopieceId(String intopieceId)throws Exception;
}