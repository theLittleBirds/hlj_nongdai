package com.nongyeos.loan.admin.service;

import java.util.List;
import java.util.Map;

import com.nongyeos.loan.admin.entity.BusFamilySituation;

public interface IFamilySituationService {

	BusFamilySituation selectByIntopIdAndType(BusFamilySituation familySituation) throws Exception;

	Map<String, Object> saveOrUpdate(BusFamilySituation selectedRecord)throws Exception;
	
	List<BusFamilySituation> thirdpartycredit(String intoPieceId) throws Exception;
	
	List<BusFamilySituation> queryByIntopId(BusFamilySituation familySituation) throws Exception;
	
	List<BusFamilySituation> queryCoBorrower(BusFamilySituation familySituation) throws Exception;
	
	BusFamilySituation selectByIntopIdAndSeqno(BusFamilySituation familySituation) throws Exception;
	
	int insert(BusFamilySituation record) throws Exception;
	
	int updateByPrimaryKey(BusFamilySituation record) throws Exception;
}
