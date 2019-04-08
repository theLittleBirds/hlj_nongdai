package com.nongyeos.loan.admin.service;

import java.util.List;

import com.nongyeos.loan.admin.entity.BusApplyPieceExt;
import com.nongyeos.loan.admin.resultMap.DynamicDataMap;

public interface IApplyPieceExtService {
	
	List<DynamicDataMap> selectBySectionId(DynamicDataMap record) throws Exception;
	
	int deleteByIpAndItem(DynamicDataMap record)  throws Exception;
	
	int insert(BusApplyPieceExt record) throws Exception;
	
	BusApplyPieceExt queryByApplyPieceAndItem(String intoPieceId, String itemId) throws Exception;

	List<BusApplyPieceExt> selectByIpId(String id)throws Exception;
}
